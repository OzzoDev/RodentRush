/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameMenus;

import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class GameInventory {

    private int x;
    private int y;
    private BufferedImage[] inventory;

    public GameInventory(int x, int y) {
        this.x = x;
        this.y = y;
        inventory = GraphicLoader.gameInventory;
    }

    public void renderInventory(Graphics2D g2d) {
        g2d.drawImage(inventory[0], x - 7, y, null);
    }

}
