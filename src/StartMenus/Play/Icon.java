/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Play;

import Toolkit.TextEditor;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class Icon {

    private int x;
    private int y;
    private int index;
    public int value = 1;
    private BufferedImage[] icon;
    private BufferedImage[] iconPadding;

    public Icon(int x, int y, int index, BufferedImage[] icon, BufferedImage[] iconPadding) {
        this.x = x;
        this.y = y;
        this.index = index;
        this.icon = icon;
        this.iconPadding = iconPadding;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void renderIcon(Graphics2D g2d) {
        g2d.drawImage(iconPadding[0], x - 5, y - 5, null);
        g2d.drawImage(icon[index], x, y, null);
        TextEditor.centerString(g2d, String.valueOf(value) + "x", x + 30, y + 55, 20, Color.white);
    }
}
