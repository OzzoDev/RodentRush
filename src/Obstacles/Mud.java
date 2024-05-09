/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obstacles;

import Toolkit.GraphicLoader;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author oscar
 */
public class Mud extends Enemies {

    public Mud() {
        speed = 10;
        y = 780;
        x = 2000;
        spriteDelay = 10;
        isStaticObject = true;
        isMud = true;
        rect = new Rectangle(x + 110, y + 10, 270, 60);
        image = GraphicLoader.mudImage;
        rect = updateCollisionRectangle(x, y, 270, -10, 270, 60);
    }

    @Override
    public void update() {
        super.update();
        rect = updateCollisionRectangle(x, y, 270, -10, 270, 60);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        if (animateMud && alive) {
            g2d.drawImage(image[currentImage], x + 50, y, null);
        } else {
            g2d.drawImage(image[0], x + 50, y, null);
        }
    }
}
