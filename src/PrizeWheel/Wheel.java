/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrizeWheel;

import StartMenus.Console.Console;
import Toolkit.StatSaver;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class Wheel {

    private static int x;
    private static int y;
    private static int width;
    private static int height;
    public static int angle;
    public static int rotationSpeed;
    public static int numberOfSpins = 0;
    public int wheelIndex = 0;
    private int timer = 0;
    private static double centerX;
    private static double centerY;
    private boolean spin = false;
    private BufferedImage[] wheel;
    public static Stack<AngleHandler> angles = new Stack<>();

    public Wheel(int x, int y, int width, int height, BufferedImage[] wheel) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        centerX = x + width / 2;
        centerY = y + height / 2;
        this.wheel = wheel;
        randomizeSpeed();
        generateAngles();
    }

    public static void generateAngles() {
        int number = 0;
        int angle = 0;
        int radius = 200;
        int items = 12;
        int midX = (width / 4) * 3;
        int midY = (height / 5) * 4;
        for (int i = 0; i < items; i++) {
            double angleRad = Math.toRadians(360.0 / items * i);
            int numberX = (int) (midX + radius * Math.cos(angleRad));
            int numberY = (int) (midY + radius * Math.sin(angleRad));
            AngleHandler lo = new AngleHandler(x, y, numberX, numberY, 800, 800, angle, number, centerX, centerY);
            angles.push(lo);
            angle += 30;
            number++;
        }
    }

    public void update() {
        if (spin) {
            if (rotationSpeed <= 0 && numberOfSpins > 0) {
                spin = false;
                Pointer.spin = false;
                Pointer.angle = 0;
                WheelHandler.updateConfetti = true;
                if (StatSaver.berries > 0) {
                    WheelHandler.changeLines(false, false);
                } else {
                    WheelHandler.changeLines(true, true);
                }
                Loot.getLoot(wheelIndex, findTopAngle());
                for (int i = 0; i < WheelHandler.buttons.size(); i++) {
                    WheelHandler.buttons.get(i).setMove(false);
                }
                WheelHandler.resetButtons();
                PrizeDisplayer.display = true;
            }
            angle += rotationSpeed;
            for (int i = 0; i < angles.size(); i++) {
                angles.get(i).setSpeed(rotationSpeed);
                angles.get(i).update();
            }
            Pointer.speed = rotationSpeed;
            timer++;
            if (timer > 2) {
                rotationSpeed *= 0.97;
                timer = 0;
            }
        }
    }

    private int findTopAngle() {
        int numberToTop = -1;
        double diff = Double.MAX_VALUE;
        int topAngle = -17;
        for (int i = 0; i < angles.size(); i++) {
            int anglediff = angles.get(i).getAngle() - topAngle;
            anglediff = (anglediff + 360) % 360;
            if (anglediff < diff) {
                diff = anglediff;
                numberToTop = angles.get(i).getNumber();
            }
        }
        return numberToTop;
    }

    private void randomizeSpeed() {
        rotationSpeed = (int) (35 + Math.random() * 20);
        for (AngleHandler lo : angles) {
            lo.setSpeed(rotationSpeed);
        }
        Pointer.speed = rotationSpeed;
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ENTER && !Console.active && WheelHandler.allowSpin) {
            randomizeSpeed();
            spin = true;
            Pointer.spin = true;
            WheelHandler.allowSpin = false;
            WheelHandler.allowButtonPress = false;
            WheelHandler.changeLines(true, true);
            numberOfSpins++;
        }
    }

    public void renderWheel(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        AffineTransform oldTransform = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.translate(centerX, centerY);
        transform.rotate(Math.toRadians(angle));
        transform.translate(-width / 2.0, -height / 2.0);
        g2d.setTransform(transform);
        g2d.drawImage(wheel[wheelIndex], 0, 0, null);
        synchronized (angles) {
            for (AngleHandler lo : angles) {
                lo.renderNumber(g2d);
            }
        }
        g2d.setTransform(oldTransform);
    }
}
