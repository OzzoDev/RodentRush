/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Enemies;

import StartMenus.Main.ButtonHandler;
import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class EnemyHandler {

    public static Stack<EnemyItem> items = new Stack<>();
    public static Stack<Integer> xPos = new Stack<>();
    public static Stack<Integer> yPos = new Stack<>();
    public static boolean active = false;
    private BufferedImage[] item;

    public EnemyHandler() {
        createEnemyHub();
    }

    public static void createEnemyHub() {
        int x = -1556;
        int y = 300;
        int index = 0;
        for (int i = 1; i <= 10; i++) {
            EnemyItem enemyItem = new EnemyItem(x, y, 360, 360, 389, index);
            items.push(enemyItem);
            x += 389;
            if (i < 7) {
                y += 29;
            } else {
                y -= 29;
            }
            index += 20;
            xPos.push(x);
            yPos.push(y);
        }
    }

    public void check() {

    }

    public static void changeItemPosition() {
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < xPos.size(); j++) {
                if (items.get(i).getX() == xPos.get(j)) {
                    items.get(i).setY((int) yPos.get(j));
                }
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (active) {
            for (EnemyItem ite : items) {
                ite.mouseMoved(e);
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        if (active) {
            for (EnemyItem ite : items) {
                ite.mousePressed(e);
            }
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int scroll = e.getWheelRotation();
        if (active) {
            if (scroll < 0) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getX() == xPos.lastElement() + 389) {
                        items.get(i).setX(xPos.get(0));
                    }
                }
                for (EnemyItem ite : items) {
                    ite.moveOneStep(true);
                }
                changeItemPosition();
            } else {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getX() == xPos.get(0) - 389) {
                        items.get(i).setX(xPos.lastElement());
                    }
                }
                for (EnemyItem ite : items) {
                    ite.moveOneStep(false);
                }
                changeItemPosition();
            }
        }
    }

    public void render(Graphics2D g2d) {
        if (active) {
            synchronized (items) {
                for (EnemyItem ite : items) {
                    ite.renderItem(g2d);
                }
            }
            ButtonHandler.buttons.get(ButtonHandler.buttonIndex).renderButton(g2d);
        }
    }
}
