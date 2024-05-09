/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obstacles;

import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author oscar
 */
public class Musquito extends Enemies {

    private final int MIN_Y;
    private final int MAX_Y;
    private boolean down = false;
    private boolean up = true;

    public Musquito() {
        speed = (int) (11 + Math.random() * 3);
        lives = 1;
        shortenList = 2;
        y = (int) (Math.random() * 10) * 20 + 520;
        x = (int) (Math.random() * 10) * 100 + 2000;
        MIN_Y = y - 30;
        MAX_Y = y + 30;
        isStaticObject = false;
        isMusquito = true;
        rect = new Rectangle(x + 25, y + 30, 50, 35);
        image = GraphicLoader.musquitoImage;
        dead = GraphicLoader.deadMusquitoImage;
        rect = updateCollisionRectangle(x, y, 25, 30, 50, 35);
    }

    @Override
    public void update() {
        super.update();
        upAndDown();
        afterDeath();
        if (up && !specialEvent) {
            y--;
        } else if (down && !specialEvent) {
            y++;
        }

        if (specialEvent) {
            y += 10;
        }
        rect = updateCollisionRectangle(x, y, 25, 30, 50, 35);
    }

    public void upAndDown() {
        if (!specialEvent) {
            if (y >= MAX_Y) {
                up = true;
                down = false;
            } else if (y <= MIN_Y) {
                up = false;
                down = true;
            }
        }
    }

    public void afterDeath() {
        if (hits > lives) {
            specialEvent = true;
        }

        if (y >= 740 && specialEvent) {
            killed = true;
            specialEvent = false;
            up = false;
            down = false;
        }

    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        if (specialEvent) {
            g2d.drawImage(dead[0], x, y, null);
        }
    }

}
