/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Main;

import GameMenus.CloseLabel;
import StartMenus.Abilities.OptionHandler;
import Toolkit.GraphicLoader;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
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
    private int closeX = 40;
    private int closeY = 40;
    private int index;
    private int pressedIndex;
    private float alpha = 1;
    private boolean move = false;
    private boolean moveX = false;
    private boolean pressed = false;
    private boolean useFilter;
    private boolean filter = false;
    private String text;
    private Rectangle rect;
    private Rectangle closeRect;
    private BufferedImage[] button;
    private BufferedImage[] close;

    public Button(int x, int y, int index, int pressedIndex, int width, int height, String filename, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.index = index;
        this.pressedIndex = pressedIndex;
        this.text = text;
        rect = new Rectangle(x, y, width, height);
        closeRect = new Rectangle(closeX, closeY, 80, 80);
        if (text.equalsIgnoreCase("PLAY")) {
            useFilter = false;
        } else {
            useFilter = true;
        }
        button = GraphicLoader.mainButton;
        close = GraphicLoader.xToCloseLabel;

    }

    public void mouseMoved(MouseEvent e) {
        if (rect.contains(e.getX(), e.getY()) && !CloseLabel.active && ButtonHandler.allowOpen) {
            move = true;
            alpha = 0.85f;
            rect = new Rectangle(x, y, width, height);
        } else {
            move = false;
            alpha = 1;
            rect = new Rectangle(x, y, width, height);
        }
        if (closeRect.contains(e.getX(), e.getY())) {
            moveX = true;
            closeRect = new Rectangle(closeX, closeY, 80, 80);
        } else {
            moveX = false;
            closeRect = new Rectangle(closeX, closeY, 80, 80);
        }
    }

    public String getText() {
        return text;
    }

    public boolean getPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public Rectangle getCloseRect() {
        return closeRect;
    }

    public Rectangle getOpenRect() {
        return rect;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void renderButton(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        Font font = new Font("1", Font.PLAIN, 30);
        g2d.setFont(font);
        g2d.setColor(Color.white);
        if (move) {
            g2d.drawImage(button[index], x - 10, y, null);
            g2d.drawString(text, x + 40, y + 50);
        } else {
            g2d.drawImage(button[index], x, y, null);
            g2d.drawString(text, x + 50, y + 50);
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    public void renderFilter(Graphics2D g2d) {
        if (filter && useFilter) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
            g2d.setColor(Color.black);
            g2d.fillRect(-20, -20, 2000, 1100);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            if (!OptionHandler.active) {
                if (moveX) {
                    g2d.drawImage(close[0], closeX - 5, closeY - 5, 80, 80, null);
                } else {
                    g2d.drawImage(close[0], closeX, closeY, 80, 80, null);
                }
            }
            g2d.setColor(Color.white);
            g2d.drawImage(button[index], x, y, null);
            g2d.drawString(text, x + 50, y + 50);
        }
    }
}
