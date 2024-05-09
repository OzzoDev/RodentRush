/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameMenus;

import Game.DeathHandler;
import Player.Player;
import PrizeWheel.WheelHandler;
import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class CloseLabel {

    private int x;
    private int y;
    private static int xX;
    private static int xY;
    public static boolean active = false;
    private static boolean move = false;
    private BufferedImage[] label;
    private BufferedImage[] close;
    public static Rectangle rect;

    public CloseLabel(int x, int y, int width, int height, String filename) {
        this.x = x;
        this.y = y;
        label = GraphicLoader.closeLabel;
        close = GraphicLoader.xToCloseLabel;
        xX = x + width - 60;
        xY = y + 20;
        rect = new Rectangle(xX, xY, 40, 40);
    }

    public static void mouseMoved(MouseEvent e) {
        if (rect.contains(e.getX(), e.getY())) {
            move = true;
        } else {
            move = false;
        }
        rect = new Rectangle(xX, xY, 40, 40);
    }

    public static void mousePressed(MouseEvent e) {
        if (rect.contains(e.getX(), e.getY()) && active) {
            for (int i = 0; i < MenuAndStatsHandler.buttons.size(); i++) {
                MenuAndStatsHandler.buttons.get(i).setActive(false);
            }
            CloseLabel.active = false;
            Player.pause();
            if (!Revive.active && !WheelHandler.active) {
                DeathHandler.run = true;
            }
        }
    }

    public void renderLabel(Graphics2D g2d) {
        if (active) {
            g2d.drawImage(label[0], x, y, null);
            if (move) {
                g2d.drawImage(close[0], xX + 2, xY + 2, null);
            } else {
                g2d.drawImage(close[0], xX, xY, null);
            }
        }
    }
}
