/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obstacles;

import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

/**
 *
 * @author oscar
 */
public class Rabbit extends Enemies {

    private int jumpTimer = 0;
    private int jumpDelay;
    private int jumpHeight;
    private int angle = 0;
    private boolean allowJump = true;
    private boolean down = false;
    private boolean up = true;
    private double jumpSpeed = 3;
    private final int OLDSPEED;

    public Rabbit() {
        y = 710;
        x = 2000;
        speed = (int) (12 + Math.random() * 14);
        OLDSPEED = speed;
        shortenList = 1;
        lives = 3;
        jumpDelay = (int) (15 + Math.random() * 10);
        jumpHeight = (int) (Math.random() * 10) * 10 + 70;
        isStaticObject = false;
        isRabbit = true;
        running = true;
        rect = new Rectangle(x + 20, y + 20, 160, 100);
        image = GraphicLoader.rabbitImage; 
        dead = GraphicLoader.deadRabbitImage; 
        rect = updateCollisionRectangle(x, y, 20, 20, 160, 100);
    }

    @Override
    public void update() {
        super.update();
        rect = updateCollisionRectangle(x, y, 20, 20, 160, 100);
        jumpTimer++;
        if (jumpTimer > jumpDelay && allowJump) {
            jumpTimer = 0;
            allowJump = false;
        }
        if (!allowJump && alive) {
            jump();
            speed = 13;
        } else if (allowJump && alive) {
            speed = OLDSPEED;
        }
        if (killed) {
            down = false;
            up = false;
        }
    }

    public void jump() {
        jumpLimit();
        if (up) {
            jumpSpeed *= 1.11;
            y -= jumpSpeed;
            if (angle < 45) {
                angle++;
            }
        } else if (down) {
            jumpSpeed *= 1.06;
            y += jumpSpeed;
            if (angle > -45) {
                angle -= 2;
            }
        }
    }

    public void jumpLimit() {
        if (y < 680 - jumpHeight && up) {
            up = false;
            down = true;
            jumpSpeed = 5;
            angle = 0;
        } else if (y > 710 && down) {
            down = false;
            up = true;
            allowJump = true;
            jumpTimer = 0;
            jumpDelay += 9;
            jumpHeight = (int) (Math.random() * 10) * 10 + 70;
            angle = 0;
            jumpSpeed = 3;
        }
    }

    public void rotate(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        double centerX = x + 200 / 2;
        double centerY = y + 150 / 2;
        AffineTransform transform = new AffineTransform();
        transform.translate(centerX, centerY);
        transform.rotate(Math.toRadians(angle));
        transform.translate(-image[4].getWidth(null) / 2.0, -image[4].getHeight(null) / 2.0);
        g2d.setTransform(transform);
        g2d.drawImage(image[4], 0, 0, null);
        g2d.setTransform(oldTransform);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (alive && allowJump) {
            g2d.drawImage(image[currentImage], x, y, null);
        } else if (alive && !allowJump) {
            rotate(g2d);
        } else if (killed) {
            g2d.drawImage(dead[0], x, 720, null);
        }
    }

}
