/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obstacles;

import Toolkit.GraphicLoader;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author oscar
 */
public class Eagle extends Enemies {

    private int angle = 0;
    private int attackTimer = 0;
    private int groundTimer = 0;
    private int groundDuration = 0;
    private final int OLD_Y;
    private double speedDown = 13;
    private double attackSpeed = 11;
    private boolean down = false;
    private boolean flyDown = false;
    private boolean allowAttack = true;
    private boolean groundFly = false;
    private boolean flyUp = false;
    private BufferedImage[] falling;

    public Eagle() {
        speed = (int) (18 + Math.random() * 9);
        y = (int) (Math.random() * 8) * 10 + 20;
        x = 2000;
        groundDuration = (int) (10 + Math.random() * 35);
        OLD_Y = y;
        shortenList = 4;
        lives = 5;
        spriteDelay = 7;
        postDeathEvent = true;
        isEagle = true;
        rect = new Rectangle(x + 30, y + 50, 230, 130);
        image = GraphicLoader.eagleImage;
        dead = GraphicLoader.deadEagleImage;
        falling = GraphicLoader.fallingEagleImage;
        updateCollisionRectangle(x, y, 30, 50, 230, 130);
    }

    @Override
    public void update() {
        super.update();
        if (hits > lives) {
            down = true;
            flyUp = false;
            flyDown = false;
            groundFly = false;
            allowAttack = false;
        }
        if (y > 660) {
            down = false;
            killed = true;
        }
        if (down) {
            speedDown *= 1.02;
            y += speedDown;
        }
        if (flyDown && angle > -40 && x < 1700 && !groundFly) {
            angle -= 2;
            attackSpeed *= 1.03;
            y += attackSpeed;
        } else if (flyDown && x < 1700 && !groundFly) {
            attackSpeed *= 1.03;
            y += attackSpeed;
        }
        if (y < OLD_Y && flyUp) {
            angle = 0;
            flyUp = false;
            flyDown = false;
            groundFly = false;
        }
        updateCollisionRectangle(x, y, 30, 50, 230, 130);
        invertRotation();
        attack();
    }

    public void attack() {
        attackTimer++;
        if (attackTimer > 20 && allowAttack) {
            Random rand = new Random();
            attackTimer = 0;
            if (rand.nextBoolean()) {
                flyDown = true;
                allowAttack = false;
            }
        }
    }

    public void invertRotation() {
        if (y > 600 && flyDown) {
            flyDown = false;
            groundFly = true;
            angle = 0;
        }
        if (groundFly) {
            groundTimer++;
            if (groundTimer > groundDuration) {
                flyUp = true;
                if (angle < 30) {
                    angle += 3;
                }
                y -= 13;
            }
        }
    }

    public void rotate(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        double centerX = x + 300 / 2;
        double centerY = y + 200 / 2;
        AffineTransform transform = new AffineTransform();
        transform.translate(centerX, centerY);
        transform.rotate(Math.toRadians(angle));
        transform.translate(-image[3].getWidth(null) / 2.0, -image[3].getHeight(null) / 2.0);
        g2d.setTransform(transform);
        g2d.drawImage(image[3], 0, 0, null);
        g2d.setTransform(oldTransform);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        if (!killed && !down && alive && !flyDown && !flyUp) {
            g2d.drawImage(image[currentImage], x, y, null);
        } else if (down && !flyDown && !flyUp) {
            g2d.drawImage(falling[0], x, y, null);
        } else if (killed && !flyDown && !flyUp) {
            g2d.drawImage(dead[0], x, y, null);
        } else if (flyDown && alive && !flyUp) {
            rotate(g2d);
        } else if (flyUp && alive) {
            rotate(g2d);
        }
    }
}
