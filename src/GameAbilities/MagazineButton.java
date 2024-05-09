/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameAbilities;

import StartMenus.Abilities.AbilityHandler;
import Toolkit.GraphicLoader;
import Toolkit.StatSaver;
import Toolkit.TextEditor;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author OscBurT21
 */
public class MagazineButton {

    private int x;
    private int y;
    private int timer = 0;
    private float alpha = 1;
    private boolean move;
    private boolean pressed;
    private BufferedImage[] button;
    private Rectangle rect;

    public MagazineButton(int x, int y) {
        this.x = x;
        this.y = y;
        button = GraphicLoader.magazineButton;
        rect = new Rectangle(x, y, 300, 100);
    }

    public void check() {
        if (pressed) {
            timer++;
            if (timer > 5) {
                addToMagazine();
                reset();
                timer = 0;
            }
        }
    }

    private void addToMagazine() {
        int indexToUse = AbilityHandler.numbers.get(7).buttonConfiguration.get(0);
                AbilityHandler.print();
        switch (indexToUse) {
            case 1:
                StatSaver.actualMag += 5;
                AbilityHandler.numbers.get(7).itemsBought.set(1,AbilityHandler.numbers.get(7).itemsBought.get(1)-1);
                AbilityHandler.numbers.get(7).itemsBought.set(0,AbilityHandler.numbers.get(7).itemsBought.get(0)-1);
                break;
            case 2:
                StatSaver.actualMag += 12;
                AbilityHandler.numbers.get(7).itemsBought.set(2,AbilityHandler.numbers.get(7).itemsBought.get(2)-1);
                AbilityHandler.numbers.get(7).itemsBought.set(0,AbilityHandler.numbers.get(7).itemsBought.get(0)-1);
                break;
            case 3:
                StatSaver.actualMag += 25;
                AbilityHandler.numbers.get(7).itemsBought.set(3,AbilityHandler.numbers.get(7).itemsBought.get(3)-1);
                AbilityHandler.numbers.get(7).itemsBought.set(0,AbilityHandler.numbers.get(7).itemsBought.get(0)-1);
                break;
            default:
                break;
        }
        System.out.println("--------------------");
        AbilityHandler.print();
    }

    public void mousePressed(MouseEvent e) {
        if (rect.contains(e.getX(), e.getY()) && AbilityHandler.numbers.get(7).itemsBought.get(0) > 0 && !pressed) {
            alpha = 0.5f;
            pressed = true;
            move = false;
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (rect.contains(e.getX(), e.getY())) {
            move = true;
            rect = new Rectangle(x, y - 5, 300, 100);
        } else {
            move = false;
            rect = new Rectangle(x, y, 300, 100);
        }
    }

    public void reset() {
        pressed = false;
        move = false;
        timer = 0;
        alpha = 1f;
    }

    public void renderMagazineUseButton(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        if (move) {
            g2d.drawImage(button[0], x, y - 5, null);
            TextEditor.centerString(g2d, "USE", x + 150, y + 65, 50, Color.white);
        } else {
            g2d.drawImage(button[0], x, y, null);
            TextEditor.centerString(g2d, "USE", x + 150, y + 70, 50, Color.white);
        }
    }
}
