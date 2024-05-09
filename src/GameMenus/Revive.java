/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameMenus;

import Game.DeathHandler;
import Player.Player;
import Toolkit.*;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class Revive {

    private static int x;
    private static int y;
    private static int clicks = 0;
    private static int cost = 10000;
    private static int newSize = 80;
    private static int lengthLeft = 140;
    private static int lengthDown = 135;
    private static int lengthRight = 135;
    private static int lengthUp = 140;
    private static int lengthManger = 275;
    private static int change = 2;
    public static boolean active = false;
    public static boolean draw = true;
    private static boolean pressed = true;
    private static boolean lower = true;
    private static boolean higher = false;
    private static boolean allowActive = true;
    private static BufferedImage[] stats;
    private static BufferedImage[] label;
    private static Rectangle rect;

    public Revive(int x, int y) {
        this.x = x;
        this.y = y;
        label = GraphicLoader.reviveLabel; 
        stats = GraphicLoader.gameStats;
        rect = new Rectangle(x + 55, y + 15, newSize, newSize);
    }

    public static void reset() {
        clicks = 0;
        cost = 10000;
        newSize = 80;
        lengthLeft = 140;
        lengthDown = 135;
        lengthRight = 135;
        lengthUp = 140;
        lengthManger = 275;
        active = false;
        change = 2;
        active = false;
        draw = true;
        pressed = true;
        lower = true;
        higher = false;
        allowActive = true;
    }

    public void check() {
        if (active) {
            limit();
            if (StatSaver.nuts - cost >= 0) {
                pressed = true;
            } else {
                pressed = false;
            }
            if (lower) {
                newSize--;
            } else if (higher) {
                newSize++;
            }
            int heartX = x + (200 - newSize) / 2;
            rect = new Rectangle(heartX + 10, y + 30, newSize - 20, newSize - 20);
            duration();
        } else {
            lengthLeft = 140;
            lengthDown = 135;
            lengthRight = 135;
            lengthUp = 140;
            lengthManger = 275;
            draw = true;
        }
    }

    public void limit() {
        if (newSize <= 60 && lower) {
            lower = false;
            higher = true;
        } else if (newSize >= 80 && higher) {
            lower = true;
            higher = false;
        }
    }

    public void duration() {
        if (!CloseLabel.active) {
            if (lengthManger <= 0) {
                if (StatSaver.berries > 0) {
                    DeathHandler.activateWheel();
                } else {
                    DeathHandler.returnToMainMenu();
                }
                active = false;
            }
            if (active) {
                lengthManger -= change;
                if (lengthManger >= 140) {
                    lengthDown -= change;
                    lengthRight -= change;
                } else {
                    draw = false;
                    lengthUp -= change;
                    lengthLeft -= change;
                }
            }
        }
    }

    private static void calculateCost(int clicks) {
        if (clicks <= 7) {
            switch (clicks) {
                case 0:
                    cost = 10000;
                    break;
                case 1:
                    cost = 75000;
                    break;
                case 2:
                    cost = 200000;
                    break;
                case 3:
                    cost = 300000;
                    break;
                case 4:
                    cost = 500000;
                    break;
                case 5:
                    cost = 1000000;
                    break;
                case 6:
                    cost = 2000000;
                    break;
                case 7:
                    cost = 5000000;
                    break;
                default:
                    break;
            }
        } else {
            cost = 10000000;
        }
    }

    public static void mousePressed(MouseEvent e) {
        if (active && pressed && rect.contains(e.getX(), e.getY())) {
            if (StatSaver.nuts - cost > 0) {
                InGameStats inGameStats = new InGameStats(1665, 10, 0);
                MenuAndStatsHandler.lives.push(inGameStats);
                StatSaver.nuts -= cost;
                DeathHandler.run = true;
                active = false;
            }
            clicks++;
            calculateCost(clicks);
        }
    }

    public boolean getActive() {
        return active;
    }

    public boolean getAllowActive() {
        return allowActive;
    }

    public void setAllowActive(boolean allowActive) {
        Revive.allowActive = allowActive;
    }

    public static void alterCost(Graphics2D g2d) {
        Font font = new Font("", Font.PLAIN, 30);
        g2d.setFont(font);
        g2d.setColor(Color.white);
        int newValue = cost;
        String calculated;
        if (newValue > 0) {
            if (newValue < 1000) {
                calculated = String.valueOf(newValue);
                FontMetrics fontMetrics = g2d.getFontMetrics();
                int stringWidth = fontMetrics.stringWidth(calculated);
                int centerX = x + 90 - stringWidth / 2;
                g2d.drawString(calculated, centerX, y + 150);
            } else if (newValue == 1000) {
                calculated = "1K";
                FontMetrics fontMetrics = g2d.getFontMetrics();
                int stringWidth = fontMetrics.stringWidth(calculated);
                int centerX = x + 90 - stringWidth / 2;
                g2d.drawString(calculated, centerX, y + 150);
            } else if (newValue < 1000000) {
                newValue /= 1000;
                calculated = String.valueOf(newValue);
                FontMetrics fontMetrics = g2d.getFontMetrics();
                int stringWidth = fontMetrics.stringWidth(calculated);
                int centerX = x + 90 - stringWidth / 2;
                g2d.drawString(calculated + "K", centerX, y + 150);
            } else if (newValue == 1000000) {
                calculated = "1";
                FontMetrics fontMetrics = g2d.getFontMetrics();
                int stringWidth = fontMetrics.stringWidth(calculated);
                int centerX = x + 90 - stringWidth / 2;
                g2d.drawString(calculated + "M", centerX, y + 150);
            } else if (newValue > 1000000) {
                newValue /= 1000000;
                calculated = String.valueOf(newValue);
                FontMetrics fontMetrics = g2d.getFontMetrics();
                int stringWidth = fontMetrics.stringWidth(calculated);
                int centerX = x + 90 - stringWidth / 2;
                g2d.drawString(calculated + "M", centerX, y + 150);
            }
        }
    }

    public static void render(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        if (active) {
            g2d.setColor(Color.magenta);
            g2d.fillRect(x + 1, y + 60, 7, lengthLeft);
            if (draw) {
                g2d.fillRect(x + 6, y + 190, lengthDown, 10);
                g2d.fillRect(x + 189, y + 7, 10, lengthRight);
            }
            g2d.fillRect(x + 59, y, lengthUp, 7);
            g2d.drawImage(label[0], x, y, null);
            g2d.drawImage(stats[1], x + 25, y + 119,42,42, null);
            int heartX = x + (200 - newSize) / 2;
            g2d.drawImage(stats[0], heartX, y + 20, newSize, newSize, null);
            alterCost(g2d);
        }
    }
}
