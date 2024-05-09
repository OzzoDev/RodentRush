/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrizeWheel;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class Nut {

    private int x;
    private int y;
    private int speed;
    private int rotationSpeed;
    private int angle;
    private BufferedImage nut;

    public Nut(BufferedImage nut) {
        this.nut = nut;
        x = (int) (Math.random() * 26) * 30 + 570;
        y = (int) (Math.random() * 7) * 10 + 140;
        speed = (int) (Math.random() * 20) + 7;
        rotationSpeed = (int) (Math.random() * 4) * 3 + 10;
        angle = (int) (Math.random() * 18) * 20;
    }

    public void update() {
        y += speed;
        angle += rotationSpeed;
    }

    public int getY() {
        return y;
    }

    public void render(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.translate(x+15, y+15);
        transform.rotate(Math.toRadians(angle));
        transform.translate(-15, -15);
        g2d.setTransform(transform);
        g2d.drawImage(nut, 0, 0, 30,30,null);
        g2d.setTransform(oldTransform);
    }
}
