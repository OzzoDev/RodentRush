/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrizeWheel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *
 * @author oscar
 */
public class AngleHandler {

    private int x;
    private int y;
    private int numberX;
    private int numberY;
    private int width;
    private int height;
    private int number;
    private int angle;
    private int startAngle = 0;
    private double speed;
    private double centerX;
    private double centerY;

    public AngleHandler(int x, int y, int numberX, int numberY, int width, int height, int angle, int number, double centerX, double centerY) {
        this.x = x;
        this.y = y;
        this.number = number;
        this.numberX = numberX;
        this.numberY = numberY;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void update() {
        angle += speed;
        startAngle += speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getAngle() {
        return angle - 270;
    }

    public int getNumber() {
        return number;
    }

    public void renderNumber(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.translate(centerX, centerY);
        transform.rotate(Math.toRadians(startAngle));
        transform.translate(-width / 2.0, -height / 2.0);
        g2d.setTransform(transform);
        g2d.setColor(Color.white);
        Font font = new Font("1", Font.PLAIN, 20);
        g2d.setFont(font);
//        g2d.drawString("" + number + " " + angle, numberX, numberY);
        g2d.setTransform(oldTransform);
    }

}
