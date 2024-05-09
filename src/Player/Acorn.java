/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Player;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author OscBurT21
 */
public class Acorn {

    private double x;
    private int y;
    private int rotationAngle;
    private int angle = 90;
    private int acronNumber;
    private int speedX;
    private int speedY = 12;
    private int delay = 0;
    private int bounceMax;
    private final int Y_MAX;
    public boolean remove;
    public boolean allowCol = true;
    private boolean down = true;
    public boolean bounce = false;
    private boolean bounceDown = false;
    private boolean bounceUp = true;
    private boolean player;
    private boolean draw = true;
    private BufferedImage[] acron;
    public Rectangle rect;

    public Acorn(int x, int y, int ymax, boolean player, BufferedImage[] acorn) {
        this.y = y;
        this.x = x;
        this.player = player;
        this.acron = acorn;
        Y_MAX = ymax;
        speedX = (int) (1 + Math.random() * 2);
        rotationAngle = (int) (1 + Math.random() * 360);
        acronNumber = (int) (1 + Math.random() * 2);
        bounceMax = (int) (1 + Math.random() * 50);
        rect = new Rectangle((int) x, y, 20, 20);
    }

    public void update() {
        if (player) {
            angle -= 2;
            rotationAngle += 7;
            x -= (Math.cos(Math.toRadians(angle)) * 18);
            speedY *= 1.09;
            if (!bounce) {
                y += speedY;
            } else {
                bounceAcorn();
            }
        } else {
            if (!bounce && down) {
                speedY *= 1.09;
                y += speedY;
                rotationAngle += 7;
            } else {
                speedX *= 1.43;
                if (acronNumber == 1) {
                    x -= speedX;
                } else if (acronNumber == 2) {
                    x += speedX;
                }
                bounceAcorn();
            }
        }
        if (y >= Y_MAX) {
            down = false;
            bounce = true;
            draw = false;
            y = Y_MAX;
            draw = true;
            delay++;
            if (delay > 3) {
                remove = true;
            }
            if (y >= Y_MAX&&player) {
                allowCol = false;
            }
        }
        rect = new Rectangle((int) x, y, 20, 20);
    }

    public void bounceAcorn() {
        if (bounce) {
            if (y <= Y_MAX - bounceMax) {
                bounceUp = false;
                bounceDown = true;
            } else if (y >= Y_MAX) {
                bounceDown = false;
                bounceUp = true;
            }
        }
        if (bounceUp) {
            y -= 6;
        } else if (bounceDown) {
            y += 6;
        }
    }

    public void drawAcrons(Graphics2D g2d) {
        if (draw) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            AffineTransform oldTransform = g2d.getTransform();
            AffineTransform transform = new AffineTransform();
            transform.translate(x + 30 / 2, y + 30 / 2);
            transform.rotate(Math.toRadians(rotationAngle));
            transform.translate(-acron[0].getWidth(null) / 2.0, -acron[0].getHeight(null) / 2.0);
            g2d.setTransform(transform);
            g2d.drawImage(acron[0], 0, 0, null);
            g2d.setTransform(oldTransform);
        }
    }
}
