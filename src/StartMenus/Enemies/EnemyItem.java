/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class EnemyItem {

    private int x;
    private int y;
    private int width;
    private int height;
    private int space;
    private int index;
    private BufferedImage[] item;
    private Rectangle rect;

    public EnemyItem(int x, int y, int width, int height, int space, int index) {
        this.x = x;
        this.y = y;
        this.space = space;
        this.width = width;
        this.height = height;
        this.index = index;
        this.item = item;
        rect = new Rectangle(x, y, width, height);
    }

    public void check() {

    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int scroll = e.getWheelRotation();
        System.out.println("Scroll: " + scroll);
    }

    public void moveOneStep(boolean right) {
        if (right) {
            x += space;
        } else {
            x -= space;
        }
        rect = new Rectangle(x, y, width, height);
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void renderItem(Graphics2D g2d) {
        g2d.setColor(new Color(250, index, 20));
        g2d.fillRect(x, y, width, height);
    }
}
