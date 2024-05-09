/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obstacles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import Player.Player;
import Toolkit.GraphicLoader;

/**
 *
 * @author oscar
 */
public class Bear extends Enemies {

    private int currentImageStanding = 4;
    private int spriteTimer = 0;
    private int spriteDelay = 12;
    private int maxXStanding;
    private int minXStanding;
    private int newY;
    private final int OLDSPEED;
    private boolean stopAnimation = false;
    private boolean onlyOnce = true;
    private boolean calledUpon = true;
    private BufferedImage[] standing;

    public Bear() {
        speed = (int) (12 + Math.random() * 16);
        OLDSPEED = speed;
        y = 620;
        x = 2000;
        newY = y - 360;
        shortenList = 2;
        lives = 7;
        maxXStanding = (int) (1 + Math.random() * 10) * 100 + 800;
        minXStanding = (int) (1 + Math.random() * 7) * 100;
        isStaticObject = false;
        isBear = true;
        running = true;
        image = GraphicLoader.bearImage;
        dead = GraphicLoader.deadBearImage;
        standing = GraphicLoader.standingBearImage;
        rect = updateCollisionRectangle(x, y, 20, 40, 320, 190);
    }

    public void changeToStanding() {
        int distanceToPlayer = x - Player.x;
        if (distanceToPlayer < maxXStanding && distanceToPlayer > minXStanding) {
            specialEvent = true;
            setSpeed(10);
        } else if (x < minXStanding) {
            changeBackToRunning();
        }
    }

    public void changeBackToRunning() {
        if (calledUpon) {
            spriteDelay = 8;
            spriteTimer = 0;
            calledUpon = false;
        }
        if (onlyOnce) {
            spriteTimer++;
            if (spriteTimer > spriteDelay) {
                currentImageStanding++;
                spriteDelay -= 2;
                if (currentImageStanding == 4) {
                    stopAnimation = false;
                    specialEvent = false;
                    setSpeed(OLDSPEED);
                    onlyOnce = false;
                }
                spriteTimer = 0;
            }

        }
    }

    @Override
    public void update() {
        super.update();
        if (specialEvent && stopAnimation) {
            rect = updateCollisionRectangle(x + 60, y - 150, 20, 40, 220, 320);
        } else {
            rect = updateCollisionRectangle(x, y, 20, 40, 320, 190);
        }
        changeToStanding();
        spriteTimer++;
        if (spriteTimer > spriteDelay && specialEvent && !stopAnimation) {
            currentImageStanding--;
            spriteDelay += 2;
            if (currentImageStanding == 0) {
                stopAnimation = true;
                currentImageStanding = 0;
            }
            spriteTimer = 0;
        }
        if (speed <= OLDSPEED && !specialEvent) {
            speed += 1;
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (specialEvent) {
            g2d.drawImage(standing[currentImageStanding], x, newY, null);
        }
        super.draw(g2d);
    }
}
