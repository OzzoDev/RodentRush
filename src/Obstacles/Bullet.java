/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obstacles;

import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import Player.Player;

/**
 *
 * @author oscar
 */
public class Bullet {

    private int x;
    private int y;
    private int angle;
    private int flashTimer = 0;
    private int velocityX;
    private int velocityY;
    private boolean flash = false;
    private boolean allowCol = true;
    private Rectangle rect;
    private BufferedImage[] image;
    private BufferedImage[] flame;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        rect = new Rectangle(x, y, 30, 10);
        image = GraphicLoader.bulletImage; 
        flame = GraphicLoader.bulletFlameImage; 
    }

    public void update() {
        follow();
        rect = new Rectangle(x, y, 30, 10);
        if (flash) {
            flashTimer++;
            if (flashTimer > 4) {
                flashTimer = 0;
                flash = false;
            }
        }
    }

    public void follow() {
        int playerX = Player.x;
        int playerY = (int) Player.y + 110;
        double dx = playerX - x;
        double dy = playerY - y;
        angle = (int) Math.toDegrees(Math.atan2(dy, dx));
        double velocity = 35;
        if (x > playerX) {
            velocityX = (int) (velocity * Math.cos(Math.toRadians(angle)));
            velocityY = (int) (velocity * Math.sin(Math.toRadians(angle)));
            x += velocityX;
            y += velocityY;
        } else {
            x -= 35;
            y += 5;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getCollisionRectangle() {
        return rect;
    }

    public void setFlash(boolean flash) {
        this.flash = flash;
    }

    public boolean getAllowCol() {
        return allowCol;
    }

    public void setAllowCol(boolean allowCol) {
        this.allowCol = allowCol;
    }

    public void render(Graphics2D g2d, int xt, int yt) {
        if (flash) {
            g2d.drawImage(flame[0], xt - 10, yt + 180, null);
        }
        g2d.drawImage(image[0], x, y, null);
    }
}
