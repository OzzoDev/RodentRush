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
public class Confetti {

    private int x;
    private int y;
    private int startX;
    private int startY;
    private int timer = 0;
    private int delay;
    private int newSize;
    private int minSize;
    private int index = 0;
    private int spriteTimer = 0;
    private int spriteDelay;
    private int rotateAngle;
    private double angle;
    private double radius;
    private double radiusSpeed;
    private float alpha = 1f;
    private boolean remove = false;
    private BufferedImage[] confetti;

    public Confetti(int startX, int startY, BufferedImage[] confetti) {
        this.startX = startX;
        this.startY = startY;
        this.confetti = confetti;
        x = startX;
        y = startY;
        index = (int) (Math.random() * 4);
        angle = Math.random() * 360;
        rotateAngle = (int) (Math.random() * 360);
        radius = 100;
        radiusSpeed = 2+Math.random() * 6;
        delay = (int) (1 + Math.random() * 3);
        spriteDelay = (int) (2 + Math.random() * 4);
        newSize = 20;
        minSize = (int) (1 + Math.random() * 5);
    }

    public void update() {
        double radians = (Math.toRadians(angle));
        radius += radiusSpeed;
        int newX = startX + (int) (radius * Math.cos(radians));
        int newY = startY + (int) (radius * Math.sin(radians));
        x = newX;
        y = newY;
        rotateAngle++;
        timer++;
        if (timer > delay) {
            newSize--; 
            timer = 0;
        }
        spriteTimer++;
        if (spriteTimer > spriteDelay) {
            index++;
            if (index >= confetti.length - 1) {
                index = 0;
            }
            spriteTimer = 0;
        }
        if (alpha < 0.1) {
            alpha = 0.1f;
        } else {
            alpha -= 0.01f;
        }
        if (newSize < minSize) {
            remove = true;
        }
    }

    public boolean getRemove() {
        return remove;
    }

    public void renderConfetti(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        double centerX = x + newSize / 2;
        double centerY = y + newSize / 2;
        transform.translate(centerX, centerY);
        transform.rotate(Math.toRadians(rotateAngle));
        transform.translate(-confetti[index].getWidth(null) / 2.0, -confetti[index].getHeight(null) / 2.0);
        g2d.setTransform(transform);
        g2d.drawImage(confetti[index], 0, 0, newSize, newSize, null);
        g2d.setTransform(oldTransform);
    }

}
