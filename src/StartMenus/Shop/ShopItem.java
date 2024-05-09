/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Shop;

import Toolkit.StatSaver;
import StartMenus.Abilities.AbilityHandler;
import Toolkit.TextEditor;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class ShopItem {

    private int x;
    private int y;
    private int fakeY;
    private int width;
    private int height;
    private int space;
    private int index;
//    private int textIndex;
    private double speed = 5;
    private float alpha = 1;
    private int cost;
    private boolean mouseIntercetion = false;
    private boolean bought = false;
    private BufferedImage[] item;
    public Rectangle rect;

    public ShopItem(int x, int y, int width, int height, int space, int index, BufferedImage[] item) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.space = space;
        this.index = index;
        this.item = item;
        fakeY = y;
        switch (index) {
            case 0:
                cost = 500000;
                break;
            case 1:
                cost = 500;
                break;
            case 2:
                cost = 250;
                break;
            case 3:
                cost = 3000;
                break;
            case 4:
                cost = 750;
                break;
            case 5:
                cost = 1000;
                break;
            case 6:
                cost = 1500;
                break;
            case 7:
                cost = 1000;
                break;
            case 8:
                cost = 2000;
                break;
            case 9:
                cost = 750;
                break;
            case 10:
                cost = 1000;
                break;
            default:
                break;
        }
        rect = new Rectangle(x, y, width, height);
    }

    public void check() {
        if (y == 474) {
            alpha = 1f;
        } else {
            alpha = 0.4f;
        }
        if (bought) {
            if (fakeY < -400) {
                bought = false;
                fakeY = y;
                speed = 5;
            } else {
                speed *= 1.07;
                fakeY -= speed;
            }
        } else {
            fakeY = y;
        }
        rect = new Rectangle(x, y, width, height);
    }

    public void moveOneStep(boolean right) {
        if (right) {
            x += space;
        } else {
            x -= space;
        }
        rect = new Rectangle(x, y, width, height);
    }

    public void mousePressed(MouseEvent e) {
        if (rect.contains(e.getX(), e.getY()) && y == 474 && StatSaver.nuts - cost >= 0 && !bought) {
            bought = true;
            StatSaver.nuts -= cost;
            ShopHandler.numberOfItems++;
            Chart.resetAnimation(0, 0);
            if (index > 0) {
                AbilityHandler.addAbility(index - 1, true, true);
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (rect.contains(e.getX(), e.getY()) && y == 474) {
            mouseIntercetion = true;
        } else {
            mouseIntercetion = false;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXAndWidth() {
        return x + width;
    }

    public void setXandY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean getBought() {
        return bought;
    }

    public int getCost() {
        return cost;
    }

    public void resetAnimation() {
        bought = false;
        fakeY = y;
        speed = 5;
    }

    public void renderBuyAnimation(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        TextEditor.setFontAndColor(g2d, 35, Color.white);
        g2d.drawImage(item[index], x, fakeY, null);
        TextEditor.centerWithItem(g2d, TextEditor.createText("", cost, true), x + width / 2, fakeY + 85, 30, Color.white, true, 1, fakeY + 55, 40);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    public void renderItem(Graphics2D g2d) {
        if (x % 389 == 0) {
            if (mouseIntercetion && y == 474) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
                g2d.setColor(Color.white);
                g2d.fillRect(x - 10, y - 10, width + 20, height + 20);
            } else {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            }
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.drawImage(item[index], x, y, null);
            TextEditor.centerWithItem(g2d, TextEditor.createText("", cost, true), x + width / 2, y + 85, 30, Color.white, true, 1, y + 55, 40);
            renderBuyAnimation(g2d);
            if (x == 778) {
                StoredText.orderText(g2d, y + height + 20, x + width / 2, index, 30);
            }
        }
    }
}
