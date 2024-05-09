/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MysteryBoxes;

import Game.CollisionEffects;
import Game.DeathHandler;
import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class MysteryBox {

    private int x = 2000;
    private int y;
    private int spriteTimer = 0;
    private int spriteDelay = 13;
    private int currentImage = 0;
    private final int MAX_Y;
    private final int MIN_Y;
    private boolean up = true;
    private boolean down = false;
    private BufferedImage[] box;
    private Rectangle rect;

    public MysteryBox() {
        y = (int) (Math.random() * 6) * 100 + 100;
        MAX_Y = y + 10;
        MIN_Y = y - 10;
        rect = new Rectangle(x, y, 80, 80);
        box = GraphicLoader.mysteryBox;
    }

    public void update() {
        if (DeathHandler.run && !CollisionEffects.startMudAnimation) {
            x -= 10;
        } else if (!DeathHandler.run || CollisionEffects.startMudAnimation) {
            x -= 0;
        }
        if (DeathHandler.run) {
            spriteTimer++;
            if (spriteTimer > spriteDelay) {
                currentImage++;
                if (currentImage >= box.length - 1) {
                    currentImage = 0;
                }
                spriteTimer = 0;
            }
            limit();
            if (up) {
                y--;
            } else if (down) {
                y++;
            }
            rect = new Rectangle(x, y, 80, 80);
        }
    }

    public void limit() {
        if (y <= MIN_Y) {
            up = false;
            down = true;
        } else if (y >= MAX_Y) {
            up = true;
            down = false;
        }
    }

    public Rectangle getRect() {
        return rect;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void renderBox(Graphics2D g2d) {
        g2d.drawImage(box[currentImage], x, y, null);
    }
}
