/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obstacles;

import Game.CollisionEffects;
import Game.DarknessFilter;
import Game.DeathHandler;
import GameAbilities.GameAbilityHandler;
import MysteryBoxes.LootHandler;
import Nuts.NutGenerator;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Collections;
import java.util.Stack;

/**
 *
 * @author OscBurT21
 */
public class EnemyGenerator {

    public static int progress = 0;
    public static int generateTimer = 0;
    private static int generateDelay = 150;
    private static int lightningStromTimer = 0;
    private static int lower = 700;
    private static int difficultyIncreaser = 0;
    public static boolean generate = true;
    public static boolean change = true;
    private static boolean allow = true;
    public static boolean firtEnemy = true; 
    public static Stack<Enemies> ene = new Stack<>();
    public static Stack<Enemies> mud = new Stack<>();
    public static Stack<Enemies> layerOne = new Stack<>();
    private static Stack<Integer> probability = new Stack<>();
    private static Rectangle drawRect = new Rectangle(-20, -10, 1980, 910);

    public EnemyGenerator() {
        probabilityChange(2, 3, 3, 2, 6, 12, 0, 0, 3, 5, 0, 0);
//        probabilityChange(5, 5, 5, 10, 0, 0, 0, 0, 0, 0, 0, 0);

    }

    public static void probabilityChange(int oneMax, int twoMax, int threeMax, int fourMax, int fiveMax, int sixMax, int sevenMax, int eightMax, int nineMax, int tenMax, int elevenMax, int tweleMax) {
        if (change) {
            probability.clear();
            for (int i = 0; i < oneMax; i++) {
                probability.add(1);
            }
            for (int i = 0; i < twoMax; i++) {
                probability.add(2);
            }
            for (int i = 0; i < threeMax; i++) {
                probability.add(3);
            }
            for (int i = 0; i < fourMax; i++) {
                probability.add(4);
            }
            for (int i = 0; i < fiveMax; i++) {
                probability.add(5);
            }
            for (int i = 0; i < sixMax; i++) {
                probability.add(6);
            }
            for (int i = 0; i < sevenMax; i++) {
                probability.add(7);
            }
            for (int i = 0; i < eightMax; i++) {
                probability.add(8);
            }
            for (int i = 0; i < nineMax; i++) {
                probability.add(9);
            }
            for (int i = 0; i < tenMax; i++) {
                probability.add(10);
            }
            for (int i = 0; i < elevenMax; i++) {
                probability.add(11);
            }
            for (int i = 0; i < tweleMax; i++) {
                probability.add(12);
            }
            Collections.shuffle(probability);
            change = false;
        }
    }

    public static void update() {
        if (DeathHandler.run && DeathHandler.start&& !GameAbilityHandler.boosterInUse()) {
            difficulty();
            synchronized (ene) {
                for (Enemies enemies : ene) {
                    enemies.update();
                }
            }
            if (!DeathHandler.run && generate) {
                ene.clear();
                mud.clear();
                layerOne.clear();
            }
            if (DarknessFilter.darken) {
                lightningStromTimer++;
                if (lightningStromTimer > 20 && DarknessFilter.alpha == DarknessFilter.MAX_ALPHA) {
                    Enemies lightning = new Lightning();
                    ene.push(lightning);
                    lightningStromTimer = 0;
                }
            }
        }
    }

    public void updateGenerate() {
        if (generate && DeathHandler.run && !GameAbilityHandler.boosterInUse()) {
            generateTimer++;
            if (!probability.isEmpty()) {
                if (firtEnemy||generateTimer > generateDelay) {
                    generateTimer = 0;
                    firtEnemy = false; 
                    try {
                        create();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    public static void difficulty() {
        if (!CollisionEffects.startMudAnimation) {
            progress++;
        }
        if (progress % lower == 0 && generateDelay > 30) {
            lower -= 30;
            generateDelay -= 5;
        }
        if (progress % 2200 == 0) {
            difficultyIncreaser++;
            change = true;
            lower = 700;
            switch (difficultyIncreaser) {
                case 1:
                    probabilityChange(4, 6, 2, 4, 2, 6, 5, 4, 9, 4, 2, 1);
                    NutGenerator.colRowChange(8, 5, 2, 5);
                    LootHandler.fillLoot(15, 100, 15, 50, 5, 1);
                    LootHandler.probabilityChange(10, 8, 6, 2, 1, 1, 1, 2, 1);
                    generateDelay = 130;
                    break;
                case 2:
                    probabilityChange(6, 3, 2, 7, 1, 4, 6, 6, 7, 3, 4, 2);
                    NutGenerator.colRowChange(10, 7, 3, 5);
                    LootHandler.fillLoot(15, 100, 15, 50, 7, 1);
                    LootHandler.probabilityChange(8, 8, 8, 1, 1, 2, 2, 2, 2);
                    generateDelay = 120;
                    break;
                case 3:
                    probabilityChange(7, 4, 2, 6, 2, 3, 7, 9, 8, 3, 7, 4);
                    NutGenerator.colRowChange(12, 8, 4, 5);
                    LootHandler.fillLoot(20, 200, 15, 70, 9, 1);
                    LootHandler.probabilityChange(7, 7, 7, 2, 2, 2, 2, 1, 1);
                    generateDelay = 110;
                    break;
                case 4:
                    generateDelay = 100;
                    probabilityChange(9, 2, 2, 8, 1, 2, 8, 13, 8, 2, 10, 6);
                    NutGenerator.colRowChange(15, 8, 5, 6);
                    LootHandler.fillLoot(30, 400, 10, 150, 7, 2);
                    LootHandler.probabilityChange(12, 9, 3, 2, 2, 2, 2, 2, 2);
                    break;
                case 5:
                    DeathHandler.run = false;
                    NutGenerator.berries.clear();
                    NutGenerator.nuts.clear();
                    break;
                default:
                    break;
            }
        }
    }

    public static void create() throws IOException {
        if (!probability.isEmpty()) {
            Collections.shuffle(probability);
            switch (probability.get(0)) {
                case 1:
                    Enemies bird = new Bird();
                    ene.push(bird);
                    break;
                case 2:
                    Enemies bear = new Bear();
                    ene.push(bear);
                    break;
                case 3:
                    Enemies fox = new Fox();
                    ene.push(fox);
                    break;
                case 4:
                    Enemies water = new Water(2000);
                    ene.push(water);
                    break;
                case 5:
                    Enemies stone = new Stone();
                    ene.push(stone);
                    break;
                case 6:
                    Enemies mus = new Musquito();
                    ene.push(mus);
                    break;
                case 7:
                    Enemies mud = new Mud();
                    ene.push(mud);
                    break;
                case 8:
                    Enemies hedgehog = new Hedgehog();
                    ene.push(hedgehog);
                    break;
                case 9:
                    Enemies eagle = new Eagle();
                    ene.push(eagle);
                    break;
                case 10:
                    Enemies rabbit = new Rabbit();
                    ene.push(rabbit);
                    break;
                case 11:
                    Enemies tower = new Tower();
                    ene.push(tower);
                    break;
                case 12:
                    Enemies lightning = new Lightning();
                    ene.push(lightning);
                    break;
                default:
                    break;
            }
        }
    }

    public void enemiesHandler() {
        update();
        removeObstacles();
        overlay();
    }

    public static Rectangle getRectangle(int i) {
        return ene.get(i).getCollisionRectangle();
    }

    public static void removeObstacles() {
        for (int i = 0; i < ene.size(); i++) {
            if (ene.get(i).getX() < -1400) {
                ene.remove(i);
            }
        }
    }

    public void overlay() {
        for (int i = 0; i < ene.size(); i++) {
            if (ene.get(i).isMud) {
                mud.push(ene.get(i));
            } else if (ene.get(i).isWater || ene.get(i).isStone || ene.get(i).isTower) {
                layerOne.push(ene.get(i));
            }
        }
    }

    public void renderEnemies(Graphics2D g2d) {
        synchronized (ene) {
            for (int i = 0; i < ene.size(); i++) {
                if (ene.get(i).getCollisionRectangle().intersects(drawRect) && !ene.get(i).isBird && !ene.get(i).isMud && !ene.get(i).isWater && !ene.get(i).isStone && !ene.get(i).isTower) {
                    ene.get(i).draw(g2d);
                } else if (ene.get(i).isBird) {
                    ene.get(i).draw(g2d);
                }
            }
        }
    }

    public static void renderMud(Graphics2D g2d) {
        synchronized (mud) {
            for (Enemies enemies : mud) {
                enemies.draw(g2d);
            }
        }
    }

    public void renderWaterStone(Graphics2D g2d) {
        synchronized (layerOne) {
            for (Enemies enemies : layerOne) {
                enemies.draw(g2d);
            }
        }
    }
}
