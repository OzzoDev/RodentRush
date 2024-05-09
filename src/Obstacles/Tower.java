/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obstacles;

import GameMenus.MenuAndStatsHandler;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Stack;
import Player.Player;
import Toolkit.GraphicLoader;

/**
 *
 * @author oscar
 */
public class Tower extends Enemies {

    private int bulletTimer = 0;
    private int bulletDelay;
    private int maxAmount;
    private int bulletCounter = 0;
    private boolean fire = false;
    private boolean launch = true;
    private Stack<Bullet> bullets = new Stack<>();

    public Tower() {
        y = 300;
        x = 2000;
        speed = 10;
        spriteDelay = 20;
        bulletDelay = (int) (3 + Math.random() * 5);
        maxAmount = (int) (4 + Math.random() * 5);
        isStaticObject = true;
        isTower = true;
        rect = new Rectangle(x + 60, y + 60, 230, 500);
        image = GraphicLoader.towerImage;
        rect = updateCollisionRectangle(x, y, 60, 60, 230, 500);
    }

    @Override
    public void update() {
        if (noMud) {
            x -= speed;
        } else if (!noMud) {
            x += 0;
        }
        allowFire();
        if (fire) {
            spriteTimer++;
            if (spriteTimer > spriteDelay) {
                currentImage++;
                if (currentImage >= image.length) {
                    currentImage = 0;
                }
                spriteTimer = 0;
            }
        }
        rect = updateCollisionRectangle(x, y, 60, 60, 230, 500);
        bulletPlayerIntersection();
        generateBullets();
        removeBullets();
        for (Bullet bullet : bullets) {
            bullet.update();
        }
    }

    public void allowFire() {
        int distanceToPlayer = x - Player.x;
        if (distanceToPlayer < 1400) {
            fire = true;
        } else {
            fire = false;
        }
    }

    public void generateBullets() {
        if (launch && fire) {
            if (bulletCounter < maxAmount && launch) {
                bulletTimer++;
                if (bulletTimer > bulletDelay) {
                    Bullet bullet = new Bullet(x + 30, y + 180);
                    bullet.setFlash(true);
                    bullets.push(bullet);
                    bulletCounter++;
                    bulletTimer = 0;
                }
            }
            if (bulletCounter >= maxAmount) {
                launch = false;
            }
        }
    }

    public void removeBullets() {
        if (!bullets.isEmpty()) {
            for (int i = 0; i < bullets.size(); i++) {
                if (bullets.get(i).getY() > 800 || bullets.get(i).getX() < -100 || bullets.get(i).getCollisionRectangle().intersects(Player.rect)) {
                    bullets.remove(i);
                }
            }
        }
    }

    public void bulletPlayerIntersection() {
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getCollisionRectangle().intersects(Player.rect) && bullets.get(i).getAllowCol()) {
                MenuAndStatsHandler.hit = true;
                bullets.get(i).setAllowCol(false);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (fire && launch) {
            g2d.drawImage(image[currentImage], x, y, null);
        } else {
            g2d.drawImage(image[0], x, y, null);
        }
        synchronized (bullets) {
            for (Bullet bullet : bullets) {
                bullet.render(g2d, x, y);
            }
        }
    }
}
