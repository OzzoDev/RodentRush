/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameMenus;

import Game.DeathHandler;
import Player.Player;
import Toolkit.GraphicLoader;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class InventoryIcon {

    private int x;
    private int y;
    private int timer = 0;
    private int index;
    private double length = 50;
    private double speed;
    private String iconName;
    private boolean active = true;
    private BufferedImage[] imageIcon;
    private BufferedImage[] iconPadding;

    public InventoryIcon(double speed, int x, int y, int index, String iconName) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.index = index;
        this.iconName = iconName;
        imageIcon = GraphicLoader.gameInventoryIcon;
        iconPadding = GraphicLoader.mysteryBoxFilterIcon;
    }

    public void check() {
        if (DeathHandler.run) {
            timer++;
            if (timer > 7 && length >= 0) {
                length -= speed;
                timer = 0;
            }

            if (length <= 0) {
                active = false;
                if (iconName.equalsIgnoreCase("water")) {
                    Player.colWithWater = false;
                }
            }
        }
    }

    public boolean getActive() {
        return active;
    }

    public String getName() {
        return iconName;
    }

    public void render(Graphics2D g2d) {
        if (active) {
            g2d.drawImage(iconPadding[1], x - 5, y - 5, null);
            g2d.drawImage(imageIcon[index], x, y, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            g2d.setColor(Color.magenta);
            if (iconName.equalsIgnoreCase("water") || iconName.equalsIgnoreCase("dark")) {
                g2d.fillRect(x + 5, y + 43, (int) length, 10);
            }
        }
    }
}
