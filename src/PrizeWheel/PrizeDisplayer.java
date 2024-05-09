/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrizeWheel;

import GameMenus.CloseLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class PrizeDisplayer {

    private int x = 535;
    private static int y = 800;
    private int timer = 0;
    private static int prize;
    private static int index;
    private static String rightText = "";
    private static String leftText = "";
    public static boolean down = true;
    private static boolean up = false;
    private static boolean delay = false;
    public static boolean display = false;
    private BufferedImage[] displayer;
    private static BufferedImage[] stats;

    public PrizeDisplayer(BufferedImage[] displayer, BufferedImage[] stats) {
        PrizeDisplayer.stats = stats;
        this.displayer = displayer;
    }

    public void update() {
        if (display && !CloseLabel.active && Wheel.numberOfSpins > 0) {
            if (y > 830 && down) {
                down = false;
                delay = true;
                timer = 0;
            } else if (y < 700 && up) {
                down = true;
                up = false;
                display = false;
                WheelHandler.allowButtonPress = true;
            }
            if (up) {
                y -= 2;
            }
            if (down) {
                y += 2;
            }
            if (delay) {
                timer++;
                if (timer > 50) {
                    delay = false;
                    up = true;
                }
            }
        } else if (Wheel.numberOfSpins == 0) {
            y = 800;
            down = true;
            up = false;
            delay = false;
            display = false;
        }
    }

    public static void reset() {
        y = 800;
        down = true;
        up = false;
        delay = false;
        display = false;
        WheelHandler.allowButtonPress = true;
    }

    public static void setPrize(String leftText, String rightText, int index) {
        PrizeDisplayer.leftText = leftText;
        PrizeDisplayer.rightText = rightText;
        PrizeDisplayer.index = index;
    }

    public void renderPrize(Graphics2D g2d, int textY) {
        int width = 70;
        int height = 70;
        int offsetY = 50;
        int textX; 
        switch (index) {
            case 0:
                width = 60;
                height = 60;
                offsetY = 50;
                break;
            case 1:
                width = 70;
                height = 70;
                offsetY = 55;
                break;
            case 2:
                width = 60;
                height = 60;
                offsetY = 55;
                break;
            default:
                break;
        }
        if(rightText.equalsIgnoreCase("")){
            textX = 770; 
        }else{
            textX = 635; 
        }
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth("PRIZE: " + leftText);
        g2d.drawImage(stats[index], textX + stringWidth - 5, textY - offsetY, width, height, null);
        g2d.drawString("PRIZE: " + leftText, textX, textY);
        g2d.drawString(rightText, textX + stringWidth + 10 + stats[index].getWidth(), textY);
    }

    private void setFontAndColor(Graphics2D g2d, int size, Color color) {
        Font font = new Font("1", Font.PLAIN, size);
        g2d.setFont(font);
        g2d.setColor(color);
    }

    public void render(Graphics2D g2d) {
        if (display) {
            setFontAndColor(g2d, 50, Color.white);
            g2d.drawImage(displayer[0], x, y, null);
            renderPrize(g2d, y + 100);
        }
    }

}
