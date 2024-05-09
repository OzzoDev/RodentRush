/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import GameMenus.CloseLabel;
import GameMenus.MenuAndStatsHandler;
import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import Player.Player;
import Obstacles.EnemyGenerator;

/**
 *
 * @author oscar
 */
public class CollisionEffects {

    private int currentImageBubbels = 0;
    private int spriteTimer = 0;
    private int spriteDelay = 5;
    private int bubbleTimer = 0;
    private int waterCollisionIndex = -1;
    public static int index;
    public static boolean drawBubbles = false;
    private boolean startTimer = false;
    public static boolean blinded = false;
    public static boolean startMudAnimation = false;
    private DarknessFilter filter = new DarknessFilter();

    public void updateSpritesSheets() {
        spriteTimer++;
        if (spriteTimer > spriteDelay) {
            currentImageBubbels++;
            if (currentImageBubbels >= GraphicLoader.bubbles.length - 1) {
                currentImageBubbels = 0;
            }
            spriteTimer = 0;
        }
    }

    public void duration() {
        for (int i = 0; i < EnemyGenerator.ene.size(); i++) {
            if (EnemyGenerator.ene.get(i).isWater) {
                if (Player.waterRect.intersects(EnemyGenerator.ene.get(i).getCollisionRectangle())) {
                    drawBubbles = true;
                    if (waterCollisionIndex == -1) {
                        waterCollisionIndex = i;
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < EnemyGenerator.ene.size(); i++) {
            if (waterCollisionIndex != -1 && !Player.waterRect.intersects(EnemyGenerator.ene.get(waterCollisionIndex).getCollisionRectangle())) {
                drawBubbles = false;
                waterCollisionIndex = -1;
                break; 
            }
        }
        if (startMudAnimation) {
            if (Player.y > 770) {
                MenuAndStatsHandler.lives.clear();
            }
        }
    }

    public void updateEffects() {
        filter.update();
    }

    public void sinkAnimation() {
        if (startMudAnimation && !CloseLabel.active) {
            if (EnemyGenerator.ene.contains(index)) {
                EnemyGenerator.ene.get(index).setMudAni(true);
            }
            Player.down = false;
            if (!Player.up) {
                Player.y += 1;
            }
           MapAndPlayerHandler.setMapSpeedAndGenerate(0, false);
            if (startMudAnimation && Player.y < 610) {
                startMudAnimation = false;
                MapAndPlayerHandler.setMapSpeedAndGenerate(10, true);
                EnemyGenerator.ene.get(index).setAnimate(false);
                EnemyGenerator.ene.get(index).setMudAni(false);
            }
        }
        if (DeathHandler.run && !startMudAnimation) {
            MapAndPlayerHandler.setMapSpeedAndGenerate(10, true);
        }
    }

   

    public void effects() {
        if (DeathHandler.run && !CloseLabel.active) {
            updateSpritesSheets();
            duration();
            sinkAnimation();
            updateEffects();
        }
    }

    public void render(Graphics2D g2d) {
        if (drawBubbles && !startMudAnimation && !Player.up) {
            g2d.drawImage(GraphicLoader.bubbles[currentImageBubbels], 390, 780, null);
        }
    }

    public void renderFilter(Graphics2D g2d) {
        filter.renderFilter(g2d);
    }

}
