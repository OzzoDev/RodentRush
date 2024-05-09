/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Abilities;

import Toolkit.TextEditor;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class Option {

    private int x;
    private int y;
    private int buttonX;
    private int buttonY;
    private int width;
    private int height;
    private int index;
    private int buttonIndex = 0;
    private int space;
    private boolean pressed = false;
    private BufferedImage[] item;
    private BufferedImage[] button;
    private Rectangle rect;
    private Rectangle buttonRect;

    public Option(int x, int y, int width, int height, int index, int space, BufferedImage[] item, BufferedImage[] button) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.index = index;
        this.space = space;
        this.item = item;
        this.button = button;
        buttonX = x + 150;
        buttonY = y + 40;
        rect = new Rectangle(x, y, width, height);
        buttonRect = new Rectangle(buttonX, buttonY, button[0].getWidth(), button[0].getHeight());
        
    }

    public void moveOneStep(boolean up) {
        if (up) {
            y -= space;
            buttonY -= space;
        } else {
            y += space;
            buttonY += space;
        }
        rect = new Rectangle(x, y, width, height);
        buttonRect = new Rectangle(buttonX, buttonY, button[0].getWidth(), button[0].getHeight());
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        buttonY = y + 40;
    }

    public int getButtonIndex() {
        return buttonIndex;
    }

    public boolean getPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public void setButtonIndex(int buttonIndex) {
        this.buttonIndex = buttonIndex;
    }

    public Rectangle getButtonRect() {
        return buttonRect;
    }
    
    public int getIndex(){
        return index; 
    }

    public void renderItem(Graphics2D g2d) {
        if (rect.intersects(OptionHandler.drawRect)) {
            g2d.drawImage(item[index], x, y,null);
            g2d.drawImage(button[buttonIndex], buttonX, buttonY, null);
        }
    }
}
