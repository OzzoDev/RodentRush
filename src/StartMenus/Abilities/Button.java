/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Abilities;

import Toolkit.TextEditor;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class Button {

    private int x;
    private int y;
    private int width;
    private int height;
    private int level;
    private int index = 0;
    private float alpha = 0.7f;
    private boolean move = false;
    private boolean pressed = false;
    private BufferedImage[] button;
    private Rectangle rect;

    public Button(int x, int y, int width, int height, int level, BufferedImage[] button) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.level = level;
        this.button = button;
        rect = new Rectangle(x, y, width, height);
    }

    public boolean getPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Rectangle getRect() {
        return rect;
    }

    public int getLevel() {
        return level;
    }
    
    public void renderButton(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        TextEditor.setFontAndColor(g2d, 15, Color.white);
        if (move) {
            g2d.drawImage(button[index], x, y - 5, null);
            TextEditor.centerWithItem(g2d, "Level " + level, x + width / 2, y + 25, 15, Color.white, false, 0, 0, 0);
        } else {
            g2d.drawImage(button[index], x, y, null);
            TextEditor.centerWithItem(g2d, "Level " + level, x + width / 2, y + 30, 15, Color.white, false, 0, 0, 0);
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }
}
