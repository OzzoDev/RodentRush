/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MysteryBoxes;

import GameAbilities.Perks;
import Game.DeathHandler;
import Game.MapAndPlayerHandler;
import Toolkit.StatSaver;
import Nuts.NutGenerator;
import Player.Acorn;
import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class LootHandler implements Runnable {

    public static double goodLoot = 0.3;
    private static int nutTimer = 0;
    public static int nutDelay = 250;
    private static int unlimitedTimer = 0;
    public static int unlimitedDelay = 300;
    private static int acronTimer = 0;
    public static int acronDelay = 200;
    private int rainDelay = 250;
    private int rainTimer = 0;
    private static boolean nutBouns = false;
    private static boolean acronBouns = false;
    private static boolean rain = false;
    public static boolean unlimitedArcon = false;
    public static Stack<MysteryBoxIcon> icons = new Stack<>();
    public static Stack<Integer> probability = new Stack<>();
    public static Stack<Integer> nutLoot = new Stack<>();
    public static Stack<Integer> acronLoot = new Stack<>();
    public static Stack<Integer> berryLoot = new Stack<>();
    private static Stack<Integer> nutFactor = new Stack<>();
    private static Stack<Integer> acornFactor = new Stack<>();
    private BufferedImage[] valueIcon;
    private BufferedImage[] nonValueIcon;
    private final int FPS_SET = 40;
    private Thread gameThread;

    public LootHandler() {
        valueIcon = GraphicLoader.mysteryBoxLootIcon;
        nonValueIcon = GraphicLoader.mysteryBoxNoneValueIcon;
        probabilityChange(12, 9, 6, 1, 1, 1, 1, 1, 1);
        fillLoot(20, 50, 10, 30, 5, 1);
        startThread();
    }

    public static void whenDead(boolean nutBouns, boolean acronBonus, boolean rain, int nutTimer, int acronTimer) {
        LootHandler.nutBouns = nutBouns;
        LootHandler.acronBouns = acronBonus;
        LootHandler.rain = rain;
        LootHandler.nutTimer = nutTimer;
        LootHandler.acronTimer = acronTimer;
    }

    public void getLoot() {
        Collections.shuffle(probability);
        switch (probability.get(0)) {
            case 1:
                getAmount(true, false, false);
                break;
            case 2:
                getAmount(false, true, false);
                break;
            case 3:
                getAmount(false, false, true);
                break;
            case 4:
                NutGenerator.nutLoot = true;
                createIcon(nonValueIcon[4], 0, 420, true, "");
                break;
            case 5:
                NutGenerator.lootBerry = true;
                createIcon(nonValueIcon[2], 0, 80, true, "");
                break;
            case 6:
                nutTimer = 0;
                nutBouns = true;
                int tempNutFactor = (int) (2 + Math.random() * 4);
                nutFactor.push(tempNutFactor);
                NutGenerator.changeCollect(tempNutFactor, false);
                break;
            case 7:
                acronTimer = 0;
                acronBouns = true;
                int tempAcornFactor = (int) (2 + Math.random() * 2);
                acornFactor.push(tempAcornFactor);
                Perks.changeCollect(tempAcornFactor, false);
                break;
            case 8:
                unlimitedArcon = true;
                unlimitedTimer = 0;
                createIcon(nonValueIcon[1], 0, unlimitedDelay, true, "");
                break;
            case 9:
                rain = true;
                rainDelay = (int) (120 + Math.random() * 100);
                rainTimer = 0;
                createIcon(nonValueIcon[5], 0, rainDelay, true, "");
                break;
            default:
                break;
        }
    }

    public static void probabilityChange(int one, int two, int three, int four, int five, int six, int seven, int eight, int nine) {
        probability.clear();
        for (int i = 0; i < one; i++) {
            probability.push(1);
        }
        for (int i = 0; i < two; i++) {
            probability.push(2);
        }
        for (int i = 0; i < three; i++) {
            probability.push(3);
        }
        for (int i = 0; i < four; i++) {
            probability.push(4);
        }
        for (int i = 0; i < five; i++) {
            probability.push(5);
        }
        for (int i = 0; i < six; i++) {
            probability.push(6);
        }
        for (int i = 0; i < seven; i++) {
            probability.push(7);
        }
        for (int i = 0; i < eight; i++) {
            probability.push(8);
        }
        for (int i = 0; i < nine; i++) {
            probability.push(9);
        }
        Collections.shuffle(probability);
    }

    public static void fillLoot(int nutMax, int nutStep, int acronMax, int acronStep, int berryMax, int berryStep) {
        int fill = 0;
        nutLoot.clear();
        acronLoot.clear();
        berryLoot.clear();
        for (int i = 1; i < nutMax; i++) {
            fill = i * nutStep;
            nutLoot.push(fill);
        }
        fill = 0;
        for (int i = 1; i < acronMax; i++) {
            fill = i * acronStep;
            acronLoot.push(fill);
        }
        fill = 0;
        for (int i = 1; i < berryMax; i++) {
            fill = i * berryStep;
            berryLoot.push(fill);
        }
        fill = 0;
        Collections.shuffle(nutLoot);
        Collections.shuffle(acronLoot);
        Collections.shuffle(berryLoot);
    }

    public void getAmount(boolean nut, boolean acron, boolean berry) {
        Random random = new Random();
        double rand = random.nextDouble();
        int randomNum = 0;
        if (nut) {
            randomNum = nutLoot.get(0);
            if (rand > goodLoot) {
                randomNum -= randomNum * 2;
            }
        } else if (acron) {
            randomNum = acronLoot.get(0);
            if (rand > goodLoot) {
                randomNum -= randomNum * 2;
            }
        } else if (berry) {
            randomNum = berryLoot.get(0);
            if (rand > goodLoot) {
                randomNum -= randomNum * 2;
            }
        }
        if (nut) {
            if (randomNum < 0) {
                createIcon(valueIcon[4], randomNum, 80, false, "");
            } else if (randomNum > 0) {
                createIcon(valueIcon[5], randomNum, 80, false, "");
            }
            if (StatSaver.nuts + randomNum >= 0) {
                if (randomNum > 0) {
                    StatSaver.totalNuts += randomNum;
                }
                StatSaver.nuts += randomNum;
            } else if (StatSaver.nuts + randomNum < 0) {
                StatSaver.nuts = 0;
            }
        } else if (acron) {
            if (randomNum < 0) {
                createIcon(valueIcon[2], randomNum, 80, false, "");
            } else if (randomNum > 0) {
                createIcon(valueIcon[3], randomNum, 80, false, "");
            }
            if (StatSaver.acronMagazine + randomNum >= 0) {
                StatSaver.acronMagazine += randomNum;
            } else if (StatSaver.acronMagazine + randomNum < 0) {
                StatSaver.acronMagazine = 0;
            }
        } else if (berry) {
            if (randomNum < 0) {
                createIcon(valueIcon[0], randomNum, 80, false, "");
            } else if (randomNum > 0) {
                createIcon(valueIcon[1], randomNum, 80, false, "");
            }
            if (StatSaver.berries + randomNum >= 0) {
                StatSaver.totalBerries += randomNum;
                StatSaver.berries += randomNum;
            } else if (StatSaver.berries + randomNum < 0) {
                StatSaver.berries = 0;
            }
        }
        Collections.shuffle(nutLoot);
        Collections.shuffle(acronLoot);
        Collections.shuffle(berryLoot);
    }

    private void createIcon(BufferedImage icon, int vaule, int delay, boolean string, String text) {
        if (icons.size() <= 2) {
            switch (icons.size()) {
                case 0:
                    MysteryBoxIcon boxIcon = new MysteryBoxIcon(1510, 980, delay, icon, vaule, string, text);
                    icons.push(boxIcon);
                    break;
                case 1:
                    boxIcon = new MysteryBoxIcon(1610, 980, delay, icon, vaule, string, text);
                    icons.push(boxIcon);
                    break;
                default:
                    break;
            }
        }
    }

    public void check() {
        if (DeathHandler.run) {
            nutTimer++;
            if (nutTimer > nutDelay && nutBouns) {
                nutTimer = 0;
                nutBouns = false;
                for (int i = 0; i < nutFactor.size(); i++) {
                    NutGenerator.changeCollect(-nutFactor.get(i), false);
                }
                nutFactor.clear();
            }
            unlimitedTimer++;
            if (unlimitedTimer > unlimitedDelay) {
                unlimitedArcon = false;
            }
            acronTimer++;
            if (acronTimer > acronDelay && acronBouns) {
                acronTimer = 0;
                acronBouns = false;
                for (int i = 0; i < acornFactor.size(); i++) {
                    Perks.changeCollect(-acornFactor.get(i), false);
                }
                acornFactor.clear();
            }
            for (int i = 0; i < icons.size(); i++) {
                icons.get(i).duration();
                if (!icons.get(i).getActive()) {
                    icons.remove(i);
                }
            }
        }
    }

    public void acronRain() {
        if (rain) {
            rainTimer++;
            if (rainTimer <= rainDelay) {
                int max = (int) (200 + Math.random() * 200);
                for (int i = 0; i < max; i++) {
                    int x = -100 + (int) (Math.random() * 420) * 5;
                    int y = -300 - (int) (Math.random() * 70) * 10;
                    Acorn acron = new Acorn(x, y, 820, false, GraphicLoader.acornImage);
                    MapAndPlayerHandler.acorns.push(acron);
                }
            } else {
                rain = false;
                rainTimer = 0;
            }
        }
    }

    public static void render(Graphics2D g2d) {
        synchronized (icons) {
            for (MysteryBoxIcon icon : icons) {
                icon.renderIcon(g2d);
            }
        }
    }

    private void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
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
                acronRain();
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
