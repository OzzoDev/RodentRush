/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obstacles;

import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author OscBurT21
 */
public class BirdPoop {

    private int x;
    private int y;
    private int speedX;
    private int speedY;
    private int index;
    private BufferedImage[] image;
    private boolean allowCol=true;
    private Rectangle rect;

    public BirdPoop(int x, int y, int speedY) {
        this.x = x + 7;
        this.y = y + 40;
        this.speedY = speedY;
        speedX = (int) (1 + Math.random() * 2);
        index = (int) (Math.random() * 2);
        updateCollisionRectangle();
        image = GraphicLoader.birdPoopImage;
    }

    public void updatePoop() {
        x += speedX;
        y += speedY;
        updateCollisionRectangle();
    }

    public int getY() {
        return y;
    }

    private void updateCollisionRectangle() {
        rect = new Rectangle(x + 155, y + 40, 30, 30);
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

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image[index], x + 150, y + 40, 50, 30, null);
    }
}
