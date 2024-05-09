/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Nuts;

import Game.CollisionEffects;
import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class Nut {

    private int x;
    private int y;
    private int speedX = 10;
    private int speedY = 2;
    private int index;
    private BufferedImage[] nutImage;
    private Rectangle rect;
    private Rectangle drawRect = new Rectangle(-20, -20, 2020, 1120);

    public Nut(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
        rect = new Rectangle(x, y, 40, 40);
        nutImage = GraphicLoader.gameNuts;
    }

    public void update() {
        if (!CollisionEffects.startMudAnimation) {
            x -= speedX;
        } else {
            x -= 0;
        }
        rect = new Rectangle(x, y, 40, 40);

    }

    public Rectangle getCollisionRectangle() {
        return rect;
    }

    public int getIndex() {
        return index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void renderNut(Graphics2D g2d) {
        if (rect.intersects(drawRect)) {
            g2d.drawImage(nutImage[index], x, y, null);
        }
    }

}
