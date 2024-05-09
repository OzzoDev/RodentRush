/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Toolkit;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class TextEditor {

    public static Font font;
    private static BufferedImage[] stats;

    public TextEditor() {
        stats = GraphicLoader.gameStats;
    }

    public static void setFontAndColor(Graphics2D g2d, int size, Color color) {
        font = new Font("1", Font.PLAIN, size);
        g2d.setFont(font);
        g2d.setColor(color);
    }

    public static void centerWithItem(Graphics2D g2d, String text, int x, int y, int size, Color color, boolean drawStat, int index, int itemY, int area) {
        font = new Font("1", Font.PLAIN, size);
        g2d.setFont(font);
        g2d.setColor(color);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(text);
        int centerX = x - stringWidth / 2;
        g2d.drawString(text, centerX, y);
        if (drawStat) {
            renderStatItem(g2d, centerX, itemY, area, index);
        }
    }

    public static void centerString(Graphics2D g2d, String text, int x, int y, int size, Color color) {
        font = new Font("1", Font.PLAIN, size);
        g2d.setFont(font);
        g2d.setColor(color);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(text);
        int centerX = x - stringWidth / 2;
        g2d.drawString(text, centerX, y);
    }

    public static void renderStatItem(Graphics2D g2d, int x, int itemY, int size, int index) {
        int startX = (x - size);
        g2d.drawImage(stats[index], startX, itemY, size, size, null);
    }

    public static String createText(String text, double value, boolean use) {
        String calculated = "";
        if (use) {
            if (value < 1000) {
                calculated = String.format("%.0f", value);
            } else if (value >= 1000 && value < 1000000) {
                value /= 1000;
                calculated = String.format("%.1f", value) + "K";
            } else if (value >= 1000000 && value < 1000000000) {
                value /= 1000000;
                calculated = String.format("%.2f", value) + "M";
            } else if (value >= 1000000000) {
                value /= 1000000000;
                calculated = String.format("%.3f", value) + "B";
            }
            String newSum = text + calculated;
        } else {
            calculated = text;
        }
        return calculated;
    }
}
