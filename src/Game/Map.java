/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author OscBurT21
 */
public class Map {

    private int x;
    private int y;
    private int index;
    public int mapSpeed = 10;
    private Rectangle rect;
    private Rectangle drawRect = new Rectangle(-20, -20, 2020, 1020);

    public Map(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
        updateRectangle();
    }

    public void update() {
        if (DeathHandler.run) {
            x -= mapSpeed;
        } else {
            x -= 0;
        }
        updateRectangle();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void updateRectangle() {
        rect = new Rectangle(x, y, 900, 1000);
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setSpeed(int mapSpeed) {
        this.mapSpeed = mapSpeed;
    }

    public void drawMap(Graphics2D g2d) {
        if (rect.intersects(drawRect)) {
            g2d.drawImage(GraphicLoader.map[index], x, y, null);
        }
    }

    public void renderCover(Graphics2D g2d) {
        g2d.drawImage(GraphicLoader.mapCover[0], x, 850, null);
        g2d.drawImage(GraphicLoader.mapCover[0], x + 900, 850, null);
        g2d.drawImage(GraphicLoader.mapCover[0], x + 1800, 850, null);
    }

}
