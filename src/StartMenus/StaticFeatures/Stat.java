/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.StaticFeatures;

import Toolkit.StatSaver;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class Stat {

    private int x;
    private int y;
    public static boolean active = false;
    private BufferedImage[] label;

    public Stat(int x, int y, BufferedImage[] label) {
        this.x = x;
        this.y = y;
        this.label = label;
    }

    private void createText(int textX, int textY, Graphics2D g2d, String text, int size, double value, Color color, boolean percentage, boolean use) {
        Font font = new Font("1", Font.PLAIN, size);
        g2d.setFont(font);
        g2d.setColor(color);
        String calculated = "";
        if (!percentage && use) {
            if (value < 1000) {
                calculated = String.format("%.0f", value);
            } else if (value >= 1000 && value < 1000000) {
                value /= 1000;
                calculated = String.format("%.2f", value) + "K";
            } else if (value >= 1000000 && value < 1000000000) {
                value /= 1000000;
                calculated = String.format("%.4f", value) + "M";
            } else if (value >= 1000000000) {
                value /= 1000000000;
                calculated = String.format("%.6f", value) + "B";
            }
            String newSum = text + calculated;
            g2d.drawString(newSum, textX, textY);
        } else if (percentage && !use) {
            calculated = String.format("%.0f", value);
            String newSum = text + calculated;
            g2d.drawString(newSum + "%", textX, textY);
        } else if (!percentage && !use) {
            g2d.drawString(text, textX, textY);
        }
    }

    private String convertTime(int timeInSeconds) {
        String time = "0s ";
        int seconds = timeInSeconds % 60;
        int minutes = (timeInSeconds % 3600) / 60;
        int hours = timeInSeconds / 3600;
        if (seconds < 60 && minutes == 0 && hours == 0) {
            time = String.valueOf(seconds) + "s ";
        } else if (seconds > 0 && minutes > 0 && hours == 0) {
            time = String.valueOf(minutes) + "m " + String.valueOf(seconds) + "s ";
        } else if (minutes > 0 && hours > 0) {
            time = String.valueOf(hours) + "h " + String.valueOf(minutes) + "m " + String.valueOf(seconds) + "s ";
        }
        return time;
    }

    public void renderStat(Graphics2D g2d) {
        if (active) {
            g2d.drawImage(label[0], x, y, null);
            createText(760, 240, g2d, "Best score: ", 30, StatSaver.bestScore, Color.white, true, false);
            createText(760, 340, g2d, "Kills: ", 30, StatSaver.kills, Color.white, false, true);
            createText(760, 440, g2d, "Airtime: " + convertTime(StatSaver.totalSeconds), 30, 20, Color.white, false, false);
            createText(760, 540, g2d, "Attempts: ", 30, StatSaver.attempts, Color.white, false, true);
            createText(760, 640, g2d, "Earned nuts: ", 30, StatSaver.totalNuts, Color.white, false, true);
            createText(760, 740, g2d, "Earned berries: ", 30, StatSaver.totalBerries, Color.white, false, true);
            createText(760, 840, g2d, "Mysteryboxes collected: ", 30, StatSaver.boxes, Color.white, false, true);
            createText(760, 940, g2d, "Acron used: ", 30, StatSaver.usedAcrons, Color.white, false, true);
        }
    }
}
