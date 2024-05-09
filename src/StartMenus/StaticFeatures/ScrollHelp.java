/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.StaticFeatures;

import Toolkit.TextEditor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class ScrollHelp {

    private int x;
    private static int y;
    private static int index;
    private static String text = "Scroll";
    public static boolean up = true;
    public static boolean down = false;
    private static boolean active = false;
    private static boolean low;
    private BufferedImage[] help;

    public ScrollHelp(int x, int y, BufferedImage[] help) {
        this.x = x;
        this.y = y;
        this.help = help;
    }

    public static void activate(boolean active, boolean low) {
        ScrollHelp.active = active;
        if (active) {
            ScrollHelp.low = low;
            if (low) {
                y = 1090;
                index = 0;
                up = true; 
                down = false; 
            } else {
                y = -160;
                index = 1;
                up = false; 
                down = true; 
            }
        }
    }

    public void update() {
        if (active) {
            if (low) {
                if (y < 950) {
                    up = false;
                }
                if (y > 1090 && down) {
                    active = false;
                    down = false;
                    up = true;
                }
            } else {
                if (y > -10&&down) {
                    down = false;
                }
                if (y < -160 && up) {
                    active = false;
                    down = true;
                    up = false;
                }
            }
            if (up) {
                y -= 4;
            } else if (down) {
                y += 4;
            }
        }
    }

    public static void reset() {
        if (low) {
            up = true;
            down = false;
            active = false;
            y = 1090;
            index = 0;
        } else {
            up = false;
            down = true;
            active = false;
            y = -160;
            index = 1;
        }
        text = "Scroll";
    }

    public static void setText(String text) {
        ScrollHelp.text = text;
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (active) {
            if (low) {
                down = true;
                up = false;
            } else {
                down = false;
                up = true;
            }
        }
    }

    private void setFontAndColor(Graphics2D g2d, int size, Color color) {
        Font font = new Font("1", Font.PLAIN, size);
        g2d.setFont(font);
        g2d.setColor(color);
    }

    public void renderHelp(Graphics2D g2d) {
        setFontAndColor(g2d, 30, Color.white);
        g2d.drawImage(help[index], x, y, null);
        if (text.equalsIgnoreCase("hover low")) {
            TextEditor.centerString(g2d, "SCROLL TO MOVE OR", 960, y + 40, 30, Color.white);
            TextEditor.centerString(g2d, "HOVER OVER ITEM", 960, y + 80, 30, Color.white);
            TextEditor.centerString(g2d, "TO OPEN", 960, y + 120, 30, Color.white);
        } else if (text.equalsIgnoreCase("hover high")) {
            TextEditor.centerString(g2d, "HOVER OVER ITEM", 960, y + 60, 30, Color.white);
            TextEditor.centerString(g2d, "TO CLOSE", 960, y + 100, 30, Color.white);
        } else {
            TextEditor.centerString(g2d, "SCROLL TO MOVE", 960, y + 90, 30, Color.white);
        }
    }
}
