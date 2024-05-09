/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obstacles;

import Game.DarknessFilter;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;
import Player.Player;
import Toolkit.GraphicLoader;

/**
 *
 * @author oscar
 */
public class Lightning extends Enemies {

    private int strikeTimer = 0;

    public Lightning() {
        Random rand = new Random();
        if(!DarknessFilter.darken){
        if (rand.nextBoolean()) {
            x = Player.x + 50;
        } else {
            x = (int) (Math.random() * 3) * 100 + 200;
        }
        }else{
            x = (int)(Math.random()*18)*100+100; 
        }
        y = (int) (Math.random() * 40) * 10;
        y = 0 - y;
        spriteDelay = 1;
        isStaticObject = true;
        isLightning = true;
        rect = new Rectangle(x, y, 100, 500);
        image = GraphicLoader.lightningImage; 
        rect = updateCollisionRectangle(x, y, 0, 0, 150, 3000);
    }

    @Override
    public void update() {
        if (lightningStrike) {
            spriteTimer++;
            strikeTimer++;
            if (spriteTimer > spriteDelay) {
                currentImage++;
                if (currentImage >= image.length) {
                    currentImage = 0;
                }
                spriteTimer = 0;
            }
            if (strikeTimer > 4) {
                lightningStrike = false;
            }
        }
        rect = updateCollisionRectangle(x, y, 0, 0, 150, 3000);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (lightningStrike) {
            g2d.drawImage(image[currentImage], x, y, null);
        }
    }

}
