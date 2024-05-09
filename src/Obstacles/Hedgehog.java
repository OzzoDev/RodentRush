/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obstacles;

import Game.CollisionEffects;
import GameMenus.MenuAndStatsHandler;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Stack;
import Player.Player;
import Toolkit.GraphicLoader;

/**
 *
 * @author oscar
 */
public class Hedgehog extends Enemies {

    private int directionTimer = 0;
    private int projectileTimer = 0;
    private boolean left = true;
    private boolean right = false;
    private BufferedImage[] rightImage;
    private BufferedImage[] rightDead;
    private Stack<Projectile> projectiles = new Stack<>();

    public Hedgehog() {
        y = 750;
        x = 2000;
        lives = 10;
        speed = (int) (13 + Math.random() * 3);
        shortenList = 1;
        isStaticObject = false;
        isHedgehog = true;
        running = true;
        isdubbleSided = true;
        rect = new Rectangle(x + 10, y + 30, 160, 60);
        image = GraphicLoader.hedgehogImage; 
        rightImage = GraphicLoader.rightHedgehogImage;  
        dead = GraphicLoader.deadHedgehogImage; 
        rightDead = GraphicLoader.rightDeadHedgehogImage; 
        rect = updateCollisionRectangle(x, y, 10, 30, 160, 60);

    }

    @Override
    public void update() {
        super.update();
        rect = updateCollisionRectangle(x, y, 10, 30, 160, 60);
        movement();
        generateProjectiles();
        projectileCollision();
    }

    public void movement() {
        directionTimer++;
        Random rand = new Random();

        if (directionTimer > 50 && alive) {
            if (rand.nextBoolean()) {
                left = true;
                right = false;
            } else {
                left = false;
                right = true;
            }
            directionTimer = 0;
        }
        if (x > 0 && alive) {
            if (left && noMud && alive) {
                x -= speed;
            } else if (right && noMud && alive) {
                x += 2;
            } else if (right || left && !noMud) {
                x += 0;
            }
        } else if (alive) {
            x -= speed;
        } else if (killed && left || right) {
            setSpeed(10);
            x -= speed;
        }
    }

    public void generateProjectiles() {
        projectileTimer++;
        if (projectileTimer > 70 && alive) {
            int maxAmount = (int) (3 + Math.random() * 7);
            int angle = 20;
            for (int i = 0; i < maxAmount; i++) {
                Projectile projectile = new Projectile(angle, x + 100, y + 10);
                angle += 3;
                projectiles.push(projectile);
            }
            projectileTimer = 0;
        }
        for (Projectile projectile : projectiles) {
            projectile.update();
        }
    }

    public Stack getStack() {
        return projectiles;
    }

    public void projectileCollision() {
        Rectangle rect = Player.rect;
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).getCollisionRectangle().intersects(rect) && projectiles.get(i).getAllowCol()) {
                MenuAndStatsHandler.hit = true;
                projectiles.get(i).setAllowCol(false);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (!CollisionEffects.startMudAnimation) {
            for (Projectile projectile : projectiles) {
                projectile.render(g2d);
            }
        }
        super.draw(g2d);
        if (alive && left) {
            g2d.drawImage(image[currentImage], x, y, null);
        } else if (alive && right) {
            g2d.drawImage(rightImage[currentImage], x, y, null);
        } else if (killed && left) {
            g2d.drawImage(dead[0], x, y, null);
        } else if (killed && right) {
            g2d.drawImage(rightDead[0], x, y, null);
        }
    }

}
