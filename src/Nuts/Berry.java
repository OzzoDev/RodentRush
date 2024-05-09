/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Nuts;

import Game.CollisionEffects;
import Toolkit.GraphicLoader;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class Berry {

    private int x;
    private int y;
    private int angle = 0;
    private int rotateAngle = 0;
    private int speedX;
    private BufferedImage[] berry;
    private Rectangle rect;

    public Berry() {
        y = 770;
        x = (int) (Math.random() * 10) * 50 + 2000;
        speedX = (int) (13 + Math.random() * 3);
        rect = new Rectangle(x, y, 60, 60);
        berry = GraphicLoader.gameStats;
    }

    public void update() {
        if (!CollisionEffects.startMudAnimation) {
            angle -= 10;
            if (angle > 360.0) {
                angle -= 360.0;
            }
            x -= speedX;
        }
        rect = new Rectangle(x, y, 60, 60);
    }

    public Rectangle getRect() {
        return rect;
    }

    public int getX() {
        return x;
    }

    public void renderBerry(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        AffineTransform oldTransform = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.translate(x + 70 / 2, y + 70 / 2);
        transform.rotate(Math.toRadians(angle));
        transform.translate(-35, -35);
        g2d.setTransform(transform);
        g2d.drawImage(berry[3], 0, 0, 70, 70, null);
        g2d.setTransform(oldTransform);
    }

}
