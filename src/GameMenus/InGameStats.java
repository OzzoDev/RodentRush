/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameMenus;

import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class InGameStats {

    private int x;
    private int y;
    private int index;
    private BufferedImage[] gameStats;

    public InGameStats(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
        gameStats = GraphicLoader.gameStats;
    }

    public void renderStat(Graphics2D g2d) {
        switch (index) {
            case 0:
                g2d.drawImage(gameStats[index], x, y, null);
                break;
            case 3:
                g2d.drawImage(gameStats[index], x - 5, y - 5, 40, 40, null);
                break;
            default:
                g2d.drawImage(gameStats[index], x, y, 40, 40, null);
                break;
        }
    }
}
