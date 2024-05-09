/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrizeWheel;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class Pointer {

    private int x;
    private int y;
    public static int angle = 0;
    public static int speed;
    private boolean upper = true;
    private boolean lower = false;
    public static boolean spin = false;
    private BufferedImage[] pointer;

    public Pointer(int x, int y, BufferedImage[] pointer) {
        this.x = x;
        this.y = y;
        this.pointer = pointer;
    }

    public void update() {
        if (spin) {
            if (angle >= 45) {
                upper = false;
                lower = true;
            } else if (angle <= -45) {
                upper = true;
                lower = false;
            }
            if (upper) {
                angle += speed;
            } else if (lower) {
                angle -= speed;
            }
        }
    }

    public void renderPointer(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        double centerX = x + pointer[0].getWidth() / 2;
        double centerY = y + pointer[0].getHeight() / 2;
        AffineTransform transform = new AffineTransform();
        transform.translate(centerX, centerY);
        transform.rotate(Math.toRadians(angle));
        transform.translate(-pointer[0].getWidth(null) / 2.0, -pointer[0].getHeight(null) / 2.0);
        g2d.setTransform(transform);
        g2d.drawImage(pointer[0], 0, 0, null);
        g2d.setTransform(oldTransform);
    }
}
