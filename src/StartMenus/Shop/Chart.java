/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Shop;

import GameMenus.CloseLabel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author OscBurT21
 */
public class Chart {

    private int x;
    private int y;
    private static int timer = 0;
    private int delay;
    private static int currentImage = 0;
    private BufferedImage[] chart;

    public Chart(int x, int y, int delay, BufferedImage[] chart) {
        this.x = x;
        this.y = y;
        this.delay = delay;
        this.chart = chart;
    }

    public static void resetAnimation(int timer, int currentImage) {
        Chart.timer = timer;
        Chart.currentImage = currentImage;
    }

    public void update() {
        if (!CloseLabel.active) {
            timer++;
            if (timer > delay) {
                currentImage++;
                if (currentImage >= chart.length) {
                    currentImage = 0;
                }
                timer = 0;
            }
        }
    }

    public void renderChart(Graphics2D g2d) {
            g2d.drawImage(chart[currentImage], x, y, null);
    }

}
