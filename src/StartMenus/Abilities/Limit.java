/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Abilities;

import StartMenus.StaticFeatures.ScrollHelp;
import Toolkit.TextEditor;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class Limit {

    private int x = 810;
    private static int y = 750;
    private final int MAX_Y;
    private static int MIN_Y;
    private int speed = 4;
    private static int timer = 0;
    private static boolean down = true;
    private static boolean up = false;
    public static boolean active = false;
    private BufferedImage[] display;

    public Limit(BufferedImage[] display) {
        this.display = display;
        MIN_Y = y;
        MAX_Y = y + 150;
    }

    public void update() {
        if (active) {
            if (y > MAX_Y && down) {
                down = false;
            } else if (y < MIN_Y && up) {
                y = MIN_Y;
                reset();
                ScrollHelp.reset();
                ScrollHelp.activate(true,true);
            }
            if (!up && !down) {
                timer++;
                if (timer > 120) {
                    down = false;
                    up = true;
                    timer = 0;
                }
            }
            if (down) {
                y += speed;
            } else if (up) {
                y -= speed;
            }
        }
    }

    public static void reset() {
        if (y != MIN_Y) {
            up = false;
            down = true;
            active = false;
            y = MIN_Y;
            timer = 0;
            ScrollHelp.reset();
            ScrollHelp.activate(true,true);
        }
    }

    public void renderDisplay(Graphics2D g2d) {
        if (active) {
            g2d.drawImage(display[0], x, y, null);
            TextEditor.centerString(g2d, "MAXIMUM CHOSEN", 960, y + 50, 25, Color.white);
            TextEditor.centerString(g2d, "ABILITIES REACHED", 960, y + 90, 25, Color.white);
            TextEditor.centerString(g2d, "7", 960, y + 130, 25, Color.white);

        }
    }

}
