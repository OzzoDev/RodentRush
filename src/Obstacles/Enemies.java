/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obstacles;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class Enemies {

    public int x;
    public int y;
    public int speed;
    public int hits = 0;
    public int lives;
    public int currentImage = 0;
    public int hitTimer = 0;
    public int hitDelay = 1;
    public int spriteTimer = 0;
    public int spriteDelay = 5;
    public int shortenList;
    public int removeTimer = 0;
    public int bubbleX;
    public int bubbleY;
    public int mudTimer = 0;

    public boolean alive = true;
    public boolean killed = false;
    public boolean collectDeath = true;
    public boolean postDeathEvent = false;
    public boolean allowHit = true;
    public boolean allowCol = true;
    public boolean isdubbleSided = false;
    public boolean lightningStrike = true; 

    public boolean isBird = false;
    public boolean isBear = false;
    public boolean isFox = false;
    public boolean isWater = false;
    public boolean isStone = false;
    public boolean isMusquito = false;
    public boolean isMud = false;
    public boolean isHedgehog = false;
    public boolean isEagle = false;
    public boolean isRabbit = false;
    public boolean isTower = false; 
    public boolean isLightning = false; 

    public boolean isStaticObject = false;
    public boolean running = false;
    public boolean animate = true;
    public boolean specialEvent = false;
    public boolean removeKilled = false;
    public boolean noMud = true;
    public boolean animateMud = false;
    public boolean allowMud = true;
    public boolean mudUpAnimation = false;
    public boolean allowRun = true;

    public BufferedImage[] image;
    public BufferedImage[] dead;

    public Rectangle rect;
    public Rectangle drawRect = new Rectangle(-20, -20, 1020, 1120);

    public void update() {
        if (noMud && !isHedgehog&&allowRun) {
            x -= speed;
        }
        if (!noMud) {
            x -= 0;
        }
        spriteTimer++;
        if (spriteTimer > spriteDelay && animate) {
            currentImage++;
            if (currentImage >= image.length - shortenList) {
                currentImage = 0;
            }
            spriteTimer = 0;
        }
        hitTimer++;
        if (hitTimer > hitDelay && !isStaticObject) {
            allowHit = true;
            hitTimer = 0;
        }
        if (hits > lives && !isStaticObject) {
            alive = false;
            if (!postDeathEvent) {
                killed = true;
            }
            setSpeed(10);
        }
        if (killed) {
            removeTimer++;
            if (removeTimer > 15) {
                removeKilled = true;
            }
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Rectangle updateCollisionRectangle(int x, int y, int maxX, int maxY, int width, int height) {
        return rect = new Rectangle(x + maxX, y + maxY, width, height);
    }

    public Rectangle getCollisionRectangle() {
        return rect;
    }

    public boolean getAllowCol() {
        return allowCol;
    }

    public void setAllowCol(boolean allowCol) {
        this.allowCol = allowCol;
    }

    public boolean getAllowHit() {
        return allowHit;
    }

    public void setAllowHit(boolean allowHit) {
        this.allowHit = allowHit;
    }

    public void increseHits() {
        hits++;
    }

    public int getX() {
        return x;
    }

    public void setMud(boolean noMud) {
        this.noMud = noMud;
    }

    public void setAnimate(boolean animateMud) {
        this.animateMud = animateMud;
    }

    public void setAllowMud(boolean allowMud) {
        this.allowMud = allowMud;
    }

    public void setMudAni(boolean mudUpAnimation) {
        this.mudUpAnimation = mudUpAnimation;
    }

    public boolean getCollectDeath() {
        return collectDeath;
    }

    public void setCollectDeath(boolean collectDeath) {
        this.collectDeath = collectDeath;
    }
    
    public boolean getStrike(){
        return lightningStrike; 
    }

    public void draw(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        if (alive && !specialEvent && !isdubbleSided) {
            g2d.drawImage(image[currentImage], x, y, null);
        } else if (killed && !specialEvent && !isdubbleSided) {
            g2d.drawImage(dead[0], x, y, null);
        }
//        g2d.setColor(Color.cyan);
//        g2d.draw(rect);
    }
}
