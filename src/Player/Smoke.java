/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Player;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class Smoke {

    private int x;
    private int y;
    private int height;
    private int timer = 0;
    private int speedX;
    private int index = 0;
    public boolean remove;
    private BufferedImage[] smoke;

    public Smoke(int x, int y, int height, BufferedImage[] smoke) {
        this.x = x;
        if (y + height < 860) {
            this.y = y;
        } else {
            this.y = 860 - height;
        }
        this.height = height;
        this.smoke = smoke;
        index = (int) (Math.random() * 3);
        speedX = (int) (14 + Math.random() * 2);
    }

    public void update() {
        if (x < -700) {
            remove = true;
        } else {
            x -= speedX;
        }
        timer++;
        if (timer > 6) {
            index++;
            if (index >= smoke.length) {
                index = 0;
            }
            timer = 0;
        }
    }

    public void renderSmoke(Graphics2D g2d) {
        g2d.drawImage(smoke[index], x, y, null);
    }
}
