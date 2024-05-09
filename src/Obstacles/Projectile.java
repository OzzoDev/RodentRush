/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obstacles;

import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class Projectile {

    private double x;
    private double y;
    private double radians;
    private int angle;
    private int speedX;
    private int speedY;
    private BufferedImage[] image;
    private Rectangle rect;
    private boolean allowCol = true;

    public Projectile(int angle, double x, double y) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        radians = Math.toRadians(angle);
        speedX = (int) (22 + Math.random() * 8);
        speedY = 4;
        rect = new Rectangle((int) x + 10, (int) y + 30, 60, 20);
        image = GraphicLoader.hedgehogProjectileImage;
        updateCollisionRectangle();
    }

    public void update() {
        x -= speedX * Math.cos(radians);
        y -= speedY * Math.sin(radians);
        updateCollisionRectangle();
    }

    public Rectangle getCollisionRectangle() {
        return rect;
    }

    public void updateCollisionRectangle() {
        rect = new Rectangle((int) x + 10, (int) y + 30, 60, 20);
    }

    public void setAllowCol(boolean allowCol) {
        this.allowCol = allowCol;
    }

    public boolean getAllowCol() {
        return allowCol;
    }

    public void render(Graphics2D g2d) {
        g2d.drawImage(image[0], (int) x, (int) y, null);

    }

}
