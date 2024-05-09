/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrizeWheel;

import GameAbilities.OptionDisplay;
import Toolkit.StatSaver;
import java.awt.AlphaComposite;
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
    private int cost;
    private int currentImage;
    private int timer = 0;
    private int index;
    private float alpha = 1f;
    private boolean pressed = false;
    private boolean allowPress = true;
    private boolean move = false;
    private boolean use;
    private String jackpot;
    private String title = "JACKPOT";
    private Rectangle rect;
    private BufferedImage[] button;
    private BufferedImage[] stats;

    public Button(int x, int y, int width, int height, int index, BufferedImage[] button, BufferedImage[] stats, boolean use) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.index = index;
        this.button = button;
        this.stats = stats;
        this.use = use;
        rect = new Rectangle(x, y, width, height);
        switch (index) {
            case 0:
                cost = 1;
                jackpot = "1K";
                break;
            case 1:
                cost = 3;
                jackpot = "5K";
                break;
            case 2:
                cost = 5;
                jackpot = "10K";
                break;
            case 3:
                cost = 10;
                jackpot = "50K";
                break;
            case 4:
                cost = 1;
                jackpot = "10";
                title = "CASH IN ALL";
                break;
            default:
                break;
        }
    }

    public void check() {
        timer++;
        if (timer > 16 && !pressed) {
            currentImage++;
            if (currentImage >= button.length) {
                currentImage = 0;
            }
            timer = 0;
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (rect.contains(e.getX(), e.getY()) && !pressed) {
            move = true;
        } else {
            move = false;
        }
    }

    public void mousePressed(MouseEvent e) {
        if (rect.contains(e.getX(), e.getY())) {
            System.out.println(index + " index");
            if (index != 4) {
                if (allowPress && StatSaver.berries - cost >= 0) {
                    StatSaver.berries -= cost;
                    alpha = 1f;
                    pressed = true;
                    allowPress = false;
                    WheelHandler.changeLines(true, false);
                    WheelHandler.allowSpin = true;
                }
            } else {
                alpha = 1f;
                pressed = true;
                allowPress = false;
                WheelHandler.changeLines(true, true);
            }
        }
    }

    public void buttonPressed(boolean allowPress, float alpha) {
        this.allowPress = allowPress;
        this.alpha = alpha;
        resetAnimation();
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public boolean getPressed() {
        return pressed;
    }

    public void setAllowPress(boolean allowPress) {
        this.allowPress = allowPress;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public boolean getAllowPress() {
        return allowPress;
    }

    public Rectangle getRect() {
        return rect;
    }

    public int getCost() {
        return cost;
    }

    public int getIndex() {
        return index;
    }

    public void resetAnimation() {
        timer = 0;
        currentImage = 0;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    private String convertValue(String text, double value, boolean use) {
        String calculated = "";
        if (use) {
            if (value < 1000) {
                calculated = String.format("%.0f", value);
            } else if (value >= 1000 && value < 1000000) {
                value /= 1000;
                calculated = String.format("%.1f", value) + "K";
            }
            String newSum = text + calculated;
        } else {
            calculated = text;
        }
        return calculated;
    }

    private void changeFont(Graphics2D g2d, int size) {
        Font font = new Font("1", Font.PLAIN, size);
        g2d.setFont(font);
    }

    public void renderButton(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        changeFont(g2d, 20);
        if (move && allowPress) {
            g2d.drawImage(button[currentImage], x - 10, y, null);
            g2d.drawString(title, x, y + 30);
            changeFont(g2d, 20);
            g2d.drawString("" + cost, x, y + 60);
            g2d.drawString(jackpot, x + 110, y + 60);
            g2d.drawImage(stats[3], x + 25, y + 38, 30, 30, null);
            g2d.drawString("=", x + 60, y + 60);
            g2d.drawImage(stats[1], x + 80, y + 37, 30, 30, null);
        } else {
            g2d.drawImage(button[currentImage], x, y, null);
            g2d.drawString(title, x + 10, y + 30);
            changeFont(g2d, 20);
            g2d.drawString("" + cost, x + 10, y + 60);
            g2d.drawString(jackpot, x + 120, y + 60);
            g2d.drawImage(stats[3], x + 35, y + 38, 30, 30, null);
            g2d.drawString("=", x + 70, y + 60);
            g2d.drawImage(stats[1], x + 90, y + 37, 30, 30, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
    }
}
