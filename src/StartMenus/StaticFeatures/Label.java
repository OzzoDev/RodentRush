/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.StaticFeatures;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class Label {

    private int x;
    private int y;
    private BufferedImage[] label;

    public Label(int x, int y, BufferedImage[] label) {
        this.x = x;
        this.y = y;
        this.label = label;
    }

    public void renderLabel(Graphics2D g2d) {
        g2d.drawImage(label[0], x, y, null);
    }
}
