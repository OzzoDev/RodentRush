/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Shop;

import static StartMenus.Abilities.AbilityHandler.items;
import static StartMenus.Abilities.AbilityHandler.xPos;
import Toolkit.TextEditor;
import Toolkit.StatSaver;
import Toolkit.GraphicLoader;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class ShopHandler {

    public static int numberOfItems = 0;
    public static boolean active = false;
    private static BufferedImage[] shopItem;
    public static BufferedImage[] nutDisplayer;
    public static BufferedImage[] stats;
    public static BufferedImage[] chart;
    public static Stack<ShopItem> items = new Stack<>();
    public static Stack<Integer> xPos = new Stack<>();
    public static Stack<Integer> yPos = new Stack<>();
    public static Rectangle centerRect = new Rectangle(860, 440, 200, 200);
    public static Stack<Rectangle> itemPositionRectangles = new Stack<>();
    private StoredText stroedText = new StoredText();
    private static Chart cha;

    public ShopHandler() {
        shopItem = GraphicLoader.shopItem;
        chart = GraphicLoader.shopChart;
        nutDisplayer = GraphicLoader.shopNutDisplayer;
        stats = GraphicLoader.gameStats;
        cha = new Chart(50, 850, 12, chart);
        createShop();
    }

    public void check() {
        if (active) {
            for (ShopItem shop : items) {
                shop.check();
            }
            cha.update();
        }
    }

    private void createItemPosition() {
        xPos.clear();
        for (int i = 0; i < items.size(); i++) {
            xPos.push(items.get(i).getX());
        }
        PriorityQueue<Integer> sortedX = new PriorityQueue<>(xPos);
        xPos.clear();
        while (!sortedX.isEmpty()) {
            xPos.push(sortedX.poll());
        }
        sortedX.clear();
    }

    private void createShop() {
        int x = -1556;
        int y = 300;
        int index = 0;
        for (int i = 1; i <= 11; i++) {
            ShopItem item = new ShopItem(x, y, 360, 360, 389, index, shopItem);
            items.push(item);
            x += 389;
            if (i < 7) {
                y += 29;
            } else {
                y -= 29;
            }
            index++;
            createItemPosition();
            Rectangle rect = new Rectangle(x, y, 360, 360);
            itemPositionRectangles.push(rect);
        }
    }

    public static void resetBuyAnimation() {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).resetAnimation();
        }
    }

    public static boolean lockMove() {
        int index = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getY() == 474) {
                index = i;
                break;
            }
        }
        return items.get(index).getBought();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int scroll = e.getWheelRotation();
        if (active && !lockMove()) {
            if (scroll < 0) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getX() == xPos.lastElement()) {
                        items.get(i).setX(xPos.get(0) - 389);
                    }
                }
                for (ShopItem ite : items) {
                    ite.moveOneStep(true);
                }
            } else {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getX() == xPos.get(0)) {
                        items.get(i).setX(xPos.lastElement() + 389);
                    }
                }
                for (ShopItem ite : items) {
                    ite.moveOneStep(false);
                }
            }
            for (int i = 0; i < itemPositionRectangles.size(); i++) {
                for (int j = 0; j < items.size(); j++) {
                    if (itemPositionRectangles.get(i).intersects(items.get(j).rect)) {
                        items.get(j).setY((int) itemPositionRectangles.get(i).getY());
                    }
                }
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (active) {
            for (ShopItem shop : items) {
                shop.mouseMoved(e);
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        if (active) {
            for (ShopItem shop : items) {
                shop.mousePressed(e);
            }
        }
    }

    public void renderShop(Graphics2D g2d) {
        if (active) {
            synchronized (items) {
                for (ShopItem shop : items) {
                    shop.renderItem(g2d);
                }
            }
            TextEditor.setFontAndColor(g2d, 60, Color.white);
            g2d.drawImage(nutDisplayer[0], 710, 174, null);
            TextEditor.centerWithItem(g2d, TextEditor.createText("", StatSaver.nuts, true), 960, 274, 60, Color.white, true, 1, 221, 60);
            cha.renderChart(g2d);
            TextEditor.centerWithItem(g2d, String.valueOf(numberOfItems), 170, 945, 75, Color.white, false, 0, 0, 0);
        }
    }
}
