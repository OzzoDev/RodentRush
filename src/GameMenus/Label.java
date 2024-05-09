/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameMenus;

import Toolkit.GraphicLoader;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class Label {

    private int x;
    private int y;
    private int index;
    private BufferedImage[] inventoryLabel;

    public Label(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
        inventoryLabel = GraphicLoader.gameInventoryIconLabel;
    }

    public void renderLabel(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7F));
        if (index == 0) {
            g2d.drawImage(inventoryLabel[index], x, y, null);
        } else {
            g2d.drawImage(inventoryLabel[index], x, y, 80, 80, null);
        }
    }

}
