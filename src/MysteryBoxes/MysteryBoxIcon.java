/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MysteryBoxes;

import Game.DeathHandler;
import Toolkit.GraphicLoader;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class MysteryBoxIcon {

    private int x;
    private int y;
    private int duationTimer = 0;
    private int duationDelay;
    private double value;
    private boolean active = true;
    private boolean string;
    private String text;
    private BufferedImage icon;
    private BufferedImage[] filterIcon;

    public MysteryBoxIcon(int x, int y, int duationDelay, BufferedImage icon, double value, boolean string, String text) {
        this.x = x;
        this.y = y;
        this.duationDelay = duationDelay;
        this.icon = icon;
        this.value = value;
        this.string = string;
        this.text = text;
        filterIcon = GraphicLoader.mysteryBoxFilterIcon; 
    }

    public void duration() {
        if (DeathHandler.run) {
            duationTimer++;
            if (duationTimer > duationDelay) {
                active = false;
            }
        }
    }

    public boolean getActive() {
        return active;
    }

    public void renderIcon(Graphics2D g2d) {
        g2d.drawImage(filterIcon[0], x - 5, y - 5, null);
        g2d.drawImage(icon, x, y, null);
        Font font = new Font("Arial", Font.PLAIN, 20);
        g2d.setFont(font);
        g2d.setColor(Color.white);
        if (!string) {
            double newValue = value;
            String calculated;
            if (newValue > 0) {
                if (newValue < 1000) {
                    calculated = String.format("%.0f", newValue);
                    FontMetrics fontMetrics = g2d.getFontMetrics();
                    int stringWidth = fontMetrics.stringWidth(calculated);
                    int centerX = x + 30 - stringWidth / 2;
                    g2d.drawString(calculated, centerX, y + 55);
                } else if (newValue == 1000) {
                    g2d.drawString("1K", x + 15, y + 55);
                } else if (newValue < 1000000) {
                    newValue /= 1000;
                    calculated = String.format("%.1fK", newValue);
                    FontMetrics fontMetrics = g2d.getFontMetrics();
                    int stringWidth = fontMetrics.stringWidth(calculated);
                    int centerX = x + 30 - stringWidth / 2;
                    g2d.drawString(calculated, centerX, y + 55);
                }
            } else {
                if (newValue > -1000) {
                    calculated = String.format("%.0f", newValue);
                    FontMetrics fontMetrics = g2d.getFontMetrics();
                    int stringWidth = fontMetrics.stringWidth(calculated);
                    int centerX = x + 30 - stringWidth / 2;
                    g2d.drawString(calculated, centerX, y + 55);
                } else if (newValue == -1000) {
                    g2d.drawString("-1K", x + 10, y + 55);
                } else if (newValue > -1000000) {
                    newValue /= 1000;
                    calculated = String.format("%.1fK", newValue);
                    FontMetrics fontMetrics = g2d.getFontMetrics();
                    int stringWidth = fontMetrics.stringWidth(calculated);
                    int centerX = x + 30 - stringWidth / 2;
                    g2d.drawString(calculated, centerX, y + 55);
                }
            }
        } else {
            g2d.drawString(text, x + 15, y + 55);
        }
    }

}
