/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import GameAbilities.Perks;
import Toolkit.StatSaver;
import MysteryBoxes.LootHandler;
import Obstacles.EnemyGenerator;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Stack;
import Player.Acorn;
import Player.FlyCondition;
import Player.Player;
import Toolkit.GraphicLoader;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author oscar
 */
public class MapAndPlayerHandler {

    public static Player player;
    private Map map;
    public static Stack<Acorn> acorns = new Stack<>();
    public static Stack<Map> maps = new Stack<>();
    private Stack<Long> comparedTime = new Stack<>();
    private int timer = 0;
    private int index;
    private int mapCounter = 0;
    private int mapAmount;
    private int totalSeconds = 0;
    public static int textX = 900;
    public static boolean pressed = false;
    private Rectangle drawRect = new Rectangle(-20, -10, 1980, 910);

    public MapAndPlayerHandler() {
        player = new Player(350, 630, 250, 200, 5, GraphicLoader.playerImage, GraphicLoader.smokeImage);
        index = (int) (Math.random() * 2);
        mapAmount = (int) (9 + Math.random() * 7);
        map = new Map(0, 0, index);
        generateMap();
    }

    public void update() {
        timer++;
        if (textX > -2000 && DeathHandler.run && DeathHandler.start) {
            textX -= maps.get(0).mapSpeed;
        }
        for (Map map1 : maps) {
            map1.update();
        }
        if (DeathHandler.run) {
            for (int i = 0; i < acorns.size(); i++) {
                if (acorns.get(i).remove) {
                    acorns.remove(i);
                } else {
                    acorns.get(i).update();
                }
            }
            if (pressed && StatSaver.acronMagazine > 0 && !Perks.waterCollision) {
                generateAcrons();
            }
            removeFirstPartOfMap();
            player.update();
            calculateAirTime();
        }
    }

    public static void setMapSpeedAndGenerate(int speed, boolean gene) {
        for (int i = 0; i < maps.size(); i++) {
            maps.get(i).setSpeed(speed);
        }
        EnemyGenerator.generate = gene;
        for (int i = 0; i < EnemyGenerator.ene.size(); i++) {
            EnemyGenerator.ene.get(i).setMud(gene);
        }
    }

    public void calculateAirTime() {
        if (Player.up || Player.down) {
            long currentTime = System.currentTimeMillis();
            if (comparedTime.isEmpty()) {
                comparedTime.push(currentTime);
            } else {
                long startTime = comparedTime.peek();
                if (currentTime - startTime >= 1000) {
                    StatSaver.totalSeconds++;
                    comparedTime.clear();
                }
            }
        }
    }

    public void MPHHandler() {
        update();
        player.update();
    }

    public final void generateMap() {
        int x = 0;
        for (int i = 0; i < 7; i++) {
            map = new Map(x, 0, index);
            maps.push(map);
            x += 900;
            mapCounter++;
        }
    }

    public void removeFirstPartOfMap() {
        if (maps.firstElement().getX() < -1000) {
            Map map = new Map(maps.lastElement().getX() + 900, 0, index);
            maps.push(map);
            maps.remove(maps.firstElement());
            mapCounter++;
            if (mapCounter > mapAmount) {
                index = (int) (Math.random() * 2);
                mapAmount = (int) (9 + Math.random() * 7);
                mapCounter = 0;
            }
        }
    }

    public void generateAcrons() {
        if (pressed) {
            for (int i = 0; i < 3; i++) {
                int y = (int) (150 + Math.random() * 60);
                int x = (int) (20 + Math.random() * 20);
                Acorn acron = new Acorn(Player.x + x, Player.y + y, 800, true, GraphicLoader.acornImage);
                acorns.push(acron);
            }
            if (!LootHandler.unlimitedArcon) {
                StatSaver.acronMagazine--;
                StatSaver.usedAcrons++;
            }
        }
    }

    public void renderMap(Graphics2D g2d) {
        for (int i = 0; i < maps.size(); i++) {
            if (maps.get(i).getRect().intersects(drawRect)) {
                if (DeathHandler.start) {
                    synchronized (maps) {
                        maps.get(i).drawMap(g2d);
                    }
                }
            }
        }
        Font font = new Font("1", Font.PLAIN, 200);
        g2d.setFont(font);
        g2d.setColor(Color.white);
        if (textX > -2000) {
            g2d.drawString("Attempt " + StatSaver.attempts, textX, 300);
        }
    }

    public void renderCover(Graphics2D g2d) {
        for (int i = 0; i < maps.size(); i++) {
            if (maps.get(i).getRect().intersects(drawRect)) {
                if (DeathHandler.start) {
                    synchronized (maps) {
                        maps.get(i).renderCover(g2d);
                    }
                }
            }
        }
    }

    public void renderPlayer(Graphics2D g2d) {
        if (DeathHandler.start) {
            if (!CollisionEffects.startMudAnimation) {
                synchronized (acorns) {
                    for (Acorn acron : acorns) {
                        acron.drawAcrons(g2d);
                    }
                }
                EnemyGenerator.renderMud(g2d);
                player.renderPlayer(g2d);
            } else {
                player.renderPlayer(g2d);
                EnemyGenerator.renderMud(g2d);
                synchronized (acorns) {
                    for (Acorn acron : acorns) {
                        acron.drawAcrons(g2d);
                    }
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !FlyCondition.block() && !FlyCondition.blockAtOptionVisable()) {
            pressed = false;
            if (DeathHandler.run) {
                player.keyReleased(e);
            }

        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && DeathHandler.run && DeathHandler.start && !FlyCondition.block() && !FlyCondition.blockAtOptionVisable()) {
            player.keyPressed(e);
            pressed = true;
        }
    }

}
