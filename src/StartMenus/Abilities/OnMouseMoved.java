/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Abilities;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author OscBurT21
 */
public class OnMouseMoved {

    private int x;
    private final int START_X;
    private int y;
    private int index;
    private double speed = 5;
    private boolean left;
    private boolean right;
    private boolean remove = false;
    private boolean allowBack = false;
    private final boolean START_DIRECTION;
    private BufferedImage[] animation;

    public OnMouseMoved(int x, int y, int index, boolean right, boolean left, BufferedImage[] animation) {
        this.right = right;
        this.left = left;
        this.START_DIRECTION = right;
        this.x = x;
        START_X = this.x;
        this.y = y;
        this.index = index - 1;
        this.animation = animation;
    }

    public void limit() {
        if (x < 120 && left) {
            left = false;
            allowBack = true;
            speed = 5;
        } else if (x > 370 && right) {
            allowBack = true;
            right = false;
            speed = 5;
        }
        if (x > START_X && !START_DIRECTION) {
            remove = true;
        }
        if (x < START_X && START_DIRECTION) {
            remove = true;
        }
    }

    public void update() {
        if (!remove) {
            limit();
            if (right) {
                x += speed;
            }
            if (left) {
                x -= speed;
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (allowBack) {
            if (!START_DIRECTION) {
                right = true;
            } else {
                left = true;
            }
            allowBack = false; 
        }
    }

    public boolean getRemove() {
        return remove;
    }

    public void renderAnimation(Graphics2D g2d) {
        if (!remove) {
            g2d.drawImage(animation[index], x, y, null);
        }
    }

}
