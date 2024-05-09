/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Console;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author oscar
 */
public class ConsoleChar {

    private int x;
    private int y;
    private String letter;
    private Font font;

    public ConsoleChar(int x, int y, String letter) {
        this.x = x;
        this.y = y;
        this.letter = letter;
        this.font = new Font("Arial", Font.PLAIN, 30);
    }

    public int getX() {
        return x;
    }
    
    public String getLetter(){
        return letter; 
    }

    public int getCharLength() {
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Rectangle2D bounds = font.getStringBounds(letter, frc);
        int charLängd = (int) bounds.getWidth();
        return charLängd;
    }

    public void renderChar(Graphics2D g2d) {
        g2d.setFont(font);
        g2d.setColor(Color.white);
        g2d.drawString(letter, x, y);
    }

}
