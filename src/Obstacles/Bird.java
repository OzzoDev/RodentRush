/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obstacles;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Stack;
import Game.CollisionEffects;
import Game.DarknessFilter;
import Player.Player;
import Toolkit.GraphicLoader;

/**
 *
 * @author oscar
 */
public class Bird extends Enemies {

    private int speedY;
    private int clock = 0;
    private boolean down = false;
    private BufferedImage[] falling;
    private Stack<BirdPoop> poo = new Stack<>();

    public Bird() {
        speed = (int) (18 + Math.random() * 9);
        y = (int) ((Math.random() * 30) * 15) + 50;
        x = 2000;
        speedY = (int) (8 + Math.random() * 3);
        shortenList = 2;
        lives = 2;
        postDeathEvent = true;
        isStaticObject = false;
        isBird = true;
        image = GraphicLoader.birdImage; 
        dead = GraphicLoader.deadBirdImage; 
        falling = GraphicLoader.fallingBirdImage; 
        rect = updateCollisionRectangle(x, y, 30, 60, 150, 80);
    }

    public void generatePoop() {
        BirdPoop poop = new BirdPoop(x, y, speedY);
        poo.push(poop);
    }

    public void playerhit() {
        Rectangle rect = Player.rect;
        for (int i = 0; i < poo.size(); i++) {
            if (rect.intersects(poo.get(i).getCollisionRectangle())) {
                DarknessFilter.darken = true;
                poo.remove(i);
            }
        }
    }

    @Override
    public void update() {
        super.update();
        rect = updateCollisionRectangle(x, y, 30, 60, 150, 80);
        playerhit();
        if (hits >= lives) {
            down = true;
        }
        if (y > 700) {
            killed = true;
            down = false;
        }
        if (down) {
            y += 18;
        }
        clock++;
        if (clock > 3 && !down) {
            generatePoop();
            clock = 0;
        }
        for (BirdPoop birdPoop : poo) {
            birdPoop.updatePoop();
        }
        synchronized (poo) {
            for (int i = 0; i < poo.size(); i++) {
                if (poo.get(i).getY() > 700) {
                    poo.remove(i);
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        if (down) {
            g2d.drawImage(falling[1], x, y, null);
        }
        synchronized (poo) {
            if (!CollisionEffects.startMudAnimation && !down && !killed) {
                for (BirdPoop birdPoop : poo) {
                    birdPoop.draw(g2d);
                }
            }
        }

    }

}
