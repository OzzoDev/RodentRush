/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import GameAbilities.Perks;
import GameMenus.MenuAndStatsHandler;
import Toolkit.StatSaver;
import java.awt.Rectangle;
import Player.Player;
import Obstacles.EnemyGenerator;

/**
 *
 * @author oscar
 */
public class Collision {

    public static void collisionIntersection() {
        Rectangle rect = Player.rect;
        for (int i = 0; i < EnemyGenerator.ene.size(); i++) {
            if (Player.waterRect.intersects(EnemyGenerator.ene.get(i).getCollisionRectangle()) && EnemyGenerator.ene.get(i).getAllowCol() && EnemyGenerator.ene.get(i).alive && EnemyGenerator.ene.get(i).isWater) {
                Player.colWithWater = true;
            }
            if (rect.intersects(EnemyGenerator.ene.get(i).getCollisionRectangle()) && EnemyGenerator.ene.get(i).getAllowCol() && EnemyGenerator.ene.get(i).alive) {
                EnemyGenerator.ene.get(i).setAllowCol(false);
                if (EnemyGenerator.ene.get(i).isBird) {
                    MenuAndStatsHandler.hit = true;
                } else if (EnemyGenerator.ene.get(i).isBear) {
                    MenuAndStatsHandler.hit = true;
                } else if (EnemyGenerator.ene.get(i).isFox) {
                    MenuAndStatsHandler.hit = true;
                } else if (EnemyGenerator.ene.get(i).isWater) {
                    Perks.waterCollision = true;
                } else if (EnemyGenerator.ene.get(i).isStone) {
                    MenuAndStatsHandler.hit = true;
                } else if (EnemyGenerator.ene.get(i).isMusquito) {
                    MenuAndStatsHandler.hit = true;
                    if (StatSaver.acronMagazine > 0) {
                        StatSaver.acronMagazine *= 0.75;
                    }
                } else if (EnemyGenerator.ene.get(i).isMud) {
                    CollisionEffects.startMudAnimation = true;
                    EnemyGenerator.ene.get(i).setAnimate(true);
                    CollisionEffects.index = i;
                } else if (EnemyGenerator.ene.get(i).isHedgehog) {
                    MenuAndStatsHandler.hit = true;
                } else if (EnemyGenerator.ene.get(i).isEagle) {
                    MenuAndStatsHandler.hit = true;
                } else if (EnemyGenerator.ene.get(i).isRabbit) {
                    MenuAndStatsHandler.hit = true;
                } else if (EnemyGenerator.ene.get(i).isTower) {
                    MenuAndStatsHandler.hit = true;
                } else if (EnemyGenerator.ene.get(i).isLightning) {
                    MenuAndStatsHandler.lives.clear();
                }
                if (!CollisionEffects.startMudAnimation) {
                    if (EnemyGenerator.ene.contains(i) && !EnemyGenerator.ene.get(i).isLightning) {
                        EnemyGenerator.ene.get(i).setAnimate(false);
                    }
                }
            }
        }
    }

    public static void acronCollision() {
        for (int i = 0; i < MapAndPlayerHandler.acorns.size(); i++) {
            for (int j = 0; j < EnemyGenerator.ene.size(); j++) {
                if (MapAndPlayerHandler.acorns.get(i).rect.intersects(EnemyGenerator.ene.get(j).getCollisionRectangle()) && !MapAndPlayerHandler.acorns.get(i).bounce && EnemyGenerator.ene.get(j).getAllowHit()) {
                    EnemyGenerator.ene.get(j).increseHits();
                    EnemyGenerator.ene.get(j).setAllowHit(false);
                }

            }
        }
    }

    public static void checkDeath() {
        for (int i = 0; i < EnemyGenerator.ene.size(); i++) {
            if (EnemyGenerator.ene.get(i).killed && EnemyGenerator.ene.get(i).getCollectDeath()) {
                if (EnemyGenerator.ene.get(i).isBird) {
                    StatSaver.acronMagazine += Perks.acronBouns.get(4);
                } else if (EnemyGenerator.ene.get(i).isBear) {
                    StatSaver.acronMagazine += Perks.acronBouns.get(3);
                } else if (EnemyGenerator.ene.get(i).isFox) {
                    StatSaver.acronMagazine += Perks.acronBouns.get(2);
                } else if (EnemyGenerator.ene.get(i).isMusquito) {
                    StatSaver.acronMagazine += Perks.acronBouns.get(0);
                } else if (EnemyGenerator.ene.get(i).isHedgehog) {
                    StatSaver.acronMagazine += Perks.acronBouns.get(5);
                } else if (EnemyGenerator.ene.get(i).isEagle) {
                    StatSaver.acronMagazine += Perks.acronBouns.get(3);
                } else if (EnemyGenerator.ene.get(i).isRabbit) {
                    StatSaver.acronMagazine += Perks.acronBouns.get(2);
                }
                StatSaver.kills++;
                EnemyGenerator.ene.get(i).setCollectDeath(false);
            }
        }
    }

    public static void obstacleOnObstacleCollision() {
        for (int i = 1; i < EnemyGenerator.ene.size(); i++) {
            if (EnemyGenerator.ene.get(i).getCollisionRectangle().intersects(EnemyGenerator.ene.get(i - 1).getCollisionRectangle()) && EnemyGenerator.ene.get(i).isStaticObject && EnemyGenerator.ene.get(i - 1).isStaticObject) {
                EnemyGenerator.ene.remove(i);
            }
        }

        for (int i = 0; i < EnemyGenerator.ene.size(); i++) {
            for (int j = 0; j < EnemyGenerator.ene.size(); j++) {
                if (EnemyGenerator.ene.get(i).getCollisionRectangle().intersects(EnemyGenerator.ene.get(j).getCollisionRectangle())) {
                    if (EnemyGenerator.ene.contains(j) && EnemyGenerator.ene.contains(i) && !EnemyGenerator.ene.get(i).isLightning && EnemyGenerator.ene.get(i).isStaticObject && EnemyGenerator.ene.get(j).removeKilled && !EnemyGenerator.ene.get(j).isStaticObject) {
                        EnemyGenerator.ene.remove(j);
                    }
                }
            }
        }
    }

    public static void remove() {
        for (int i = 0; i < EnemyGenerator.ene.size(); i++) {
            if (EnemyGenerator.ene.get(i).isLightning) {
                if (!EnemyGenerator.ene.get(i).getStrike()) {
                    EnemyGenerator.ene.remove(i);
                }
            }
        }
    }

    public void collisonHandler() {
        if (DeathHandler.run) {
            obstacleOnObstacleCollision();
            collisionIntersection();
            acronCollision();
            checkDeath();
            remove();
        }
    }
}
