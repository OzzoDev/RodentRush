/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Nuts;

import Game.DeathHandler;
import GameAbilities.GameAbilityHandler;
import Toolkit.StatSaver;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.Stack;
import Player.Player;

/**
 *
 * @author oscar
 */
public class NutGenerator implements Runnable {

    private static int colX;
    private static int rowY;
    public static int colMin = 8;
    public static int colMax = 4;
    public static int rowMin = 2;
    public static int rowMax = 2;
    private static int timer = 0;
    private int delay = 200;
    private int berryTimer = 0;
    public static int berryDelay = 50;
    public static int minTime = 50;
    public static int factor = 1;
    public static boolean nutLoot = false;
    public static boolean lootBerry = false;
    public static Stack<Nut> nuts = new Stack<>();
    public static Stack<Integer> index = new Stack<>();
    public static Stack<Berry> berries = new Stack<>();
    public static Stack<Integer> collect = new Stack<>();
    private final int FPS_SET = 40;
    private Thread gameThread;

    public NutGenerator() {
        delay = (int) (50 + Math.random() * 20);
        for (int i = 1; i < 6; i++) {
            collect.add(i);
        }
        for (int i = 0; i < 20; i++) {
            index.add(0);
        }
        for (int i = 0; i < 10; i++) {
            index.add(1);
        }
        for (int i = 0; i < 3; i++) {
            index.add(2);
        }
        for (int i = 0; i < 7; i++) {
            index.add(3);
        }
        for (int i = 0; i < 15; i++) {
            index.add(4);
        }
        startThread();
    }

    private void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        if (DeathHandler.run && !GameAbilityHandler.boosterInUse()) {
            timer++;
            if (timer > delay && DeathHandler.run) {
                if (!nutLoot) {
                    generate();
                }
                delay = (int) (150 + Math.random() * 80);
                timer = 0;
            }
            synchronized (nuts) {
                for (Nut nut : nuts) {
                    if (!nutLoot) {
                        nut.update();
                    }
                }
            }
            collect();
            berryHandler();
        }
    }

    public static void colRowChange(int colMi, int colMa, int rowMi, int rowMa) {
        colMin = colMi;
        colMax = colMa;
        rowMin = rowMi;
        rowMax = rowMa;
    }

    public static void generate() {
        int x = 2000;
        int y = (int) (Math.random() * 5) * 100 + 100;
        colX = (int) (colMin + Math.random() * colMax);
        rowY = (int) (rowMin + Math.random() * rowMax);
        for (int i = 0; i < rowY; i++) {
            for (int j = 0; j < colX; j++) {
                x += 45;
                randomize();
                if (y < 780) {
                    Nut nut = new Nut(x, y, index.get(0));
                    nuts.push(nut);
                }
            }
            y += 35;
            x = 2000;
        }
    }

    public static void lootGenerate() {
        nuts.clear();
        int x = 2000;
        int y = 50;
        colX = (int) (15 + Math.random() * 20);
        rowY = (int) (8 + Math.random() + 13);
        for (int i = 0; i < rowY; i++) {
            for (int j = 0; j < colX; j++) {
                x += 45;
                randomize();
                if (y < 780) {
                    Nut nut = new Nut(x, y, index.get(0));
                    nuts.push(nut);
                }
            }
            y += 35;
            x = 2000;
        }
        nutLoot = false;
        timer = 0;
    }

    public static void randomize() {
        Collections.shuffle(index);
    }

    public void collect() {
        if (!nuts.isEmpty() && DeathHandler.run && !GameAbilityHandler.boosterInUse()) {
            for (int i = 0; i < nuts.size(); i++) {
                if (nuts.get(i).getCollisionRectangle().intersects(Player.rect)) {
                    switch (nuts.get(i).getIndex()) {
                        case 0:
                            StatSaver.totalNuts += collect.get(0);
                            StatSaver.nuts += collect.get(0);
                            break;
                        case 1:
                            StatSaver.totalNuts += collect.get(1);
                            StatSaver.nuts += collect.get(1);
                            break;
                        case 2:
                            StatSaver.totalNuts += collect.get(4);
                            StatSaver.nuts += collect.get(4);
                            break;
                        case 3:
                            StatSaver.totalNuts += collect.get(3);
                            StatSaver.nuts += collect.get(3);
                            break;
                        case 4:
                            StatSaver.totalNuts += collect.get(2);
                            StatSaver.nuts += collect.get(2);
                            break;
                        default:
                            break;
                    }
                    nuts.remove(i);
                } else if (nuts.get(i).getX() < -50) {
                    nuts.remove(i);
                }
            }
        }
    }

    public void generateBerries() {
        berryTimer++;
        if (berryTimer > berryDelay && DeathHandler.run) {
            berryDelay = (int) (minTime + Math.random() * 140);
            berryTimer = 0;
            Berry berry = new Berry();
            berries.push(berry);
        }
    }

    public void berryLoot() {
        if (lootBerry) {
            int max = (int) (7 + Math.random() * 10);
            for (int i = 0; i < max; i++) {
                Berry berry = new Berry();
                berries.push(berry);
            }
            lootBerry = false;
        }
    }

    public void berryHandler() {
        if (DeathHandler.run && DeathHandler.start) {
            berryLoot();
            generateBerries();
            for (int i = 0; i < berries.size(); i++) {
                berries.get(i).update();
                if (berries.get(i).getX() < -400) {
                    berries.remove(i);
                } else if (Player.rect.intersects(berries.get(i).getRect())) {
                    berries.remove(i);
                    StatSaver.totalBerries++;
                    StatSaver.berries++;
                }
            }
        }
    }

    public static void changeCollect(int x, boolean reset) {
        if (!reset) {
            for (int i = 0; i < collect.size(); i++) {
                int updatedValue = collect.get(i) * x;
                collect.set(i, updatedValue);
            }
            factor += x;
        } else {
            collect.clear();
            factor = 1;
            for (int i = 1; i < 6; i++) {
                collect.add(i);
            }
        }
    }

    public void renderNuts(Graphics2D g2d) {
        synchronized (nuts) {
            for (Nut nut : nuts) {
                nut.renderNut(g2d);
            }
        }
    }

    public void renderBerries(Graphics2D g2d) {
        synchronized (berries) {
            for (Berry berry : berries) {
                berry.renderBerry(g2d);
            }
        }
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();
        int frames = 0;
        long lastCheck = System.currentTimeMillis();
        while (true) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                long start = System.nanoTime();
                if (nutLoot) {
                    lootGenerate();
                }
                long end = System.nanoTime();
                long passed = (end - start);
                lastFrame = now;
                frames++;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                frames = 0;
            }
        }
    }
}
