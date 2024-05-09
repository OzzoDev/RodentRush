/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameMenus;

import StartMenus.Main.ButtonHandler; 
import Game.DeathHandler;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import Game.Window;
import java.awt.Graphics2D;

/**
 *
 * @author oscar
 */
public class Button {

    private int x;
    private int y;
    private int width;
    private int height;
    private int speed = 5;
    private int index;
    private String name;
    private boolean active = false;
    private boolean move = false;
    private BufferedImage[] button;
    private Rectangle rect;

    public Button(int x, int y, int width, int height, int index, BufferedImage[] button, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        this.index = index;
        this.button=button;
        rect = new Rectangle(x, y, width, height);
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && active) {
            if (rect.contains(e.getX(), e.getY()) && name.equalsIgnoreCase("exit")) {
                Window.close();
            } else if (rect.contains(e.getX(), e.getY()) && name.equalsIgnoreCase("return")) {
                DeathHandler.returnToMainMenu();
                ButtonHandler.backToMain(ButtonHandler.buttonIndex);
            }
            move = false; 
        }
        rect = new Rectangle(x, y, width - 10, height);
    }

    public void mouseMoved(MouseEvent e) {
        if (rect.contains(e.getX(), e.getY()) && active) {
            move = true;
        } else if (!rect.contains(e.getX(), e.getY()) && active) {
            move = false;
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    public void renderButtons(Graphics2D g2d) {
        if (active && !move) {
            g2d.drawImage(button[index], x, y, null);
        } else if (active && move) {
            g2d.drawImage(button[index], x + speed, y + speed, null);
        }
    }

}
