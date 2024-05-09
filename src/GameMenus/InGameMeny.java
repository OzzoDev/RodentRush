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
public class InGameMeny {

    private int x;
    private int y;
    private int index;
    private BufferedImage[] inGameMenu;

    public InGameMeny(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
        inGameMenu = GraphicLoader.gameMenu;
    }

    public void renderMeny(Graphics2D g2d) {
        g2d.drawImage(inGameMenu[index], x, y, null);
    }

}
