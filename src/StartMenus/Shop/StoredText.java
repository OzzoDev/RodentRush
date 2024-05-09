/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Shop;

import Toolkit.TextEditor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class StoredText {

    public static Stack<String> infoText = new Stack<>();
    
    public StoredText() {
        generateText();
    }
    
    private void generateText() {
        infoText.push("HATCH TO GET A  BUDDY THAT HELPS  WITH PROTECTION  .");
        infoText.push("DEPLOY TO GET  HELP WITH  COLLECTING NUTS  .");
        infoText.push("GET TWICE AS MANY  ACORNS WHEN  KILLING ENEMIES  .");
        infoText.push("ACTIVATE TO GET  EXTRA PROTECTION  .");
        infoText.push("USE AT ANY  ALTITUDE TO FALL  DOWN SLOWLY  .");
        infoText.push("ACTIVATE TO GET A  FLYING START  .");
        infoText.push("DEPLOY AT ANY  HEIGHT TO  FLY WITHOUT  USING ACORNS  .");
        infoText.push("BUY TO GET 100 MORE  (DEFAULT 10) NUTS  FOR EACH BERRY WHEN  REDEEMING BERRIES  AT THE CASINO  .");
        infoText.push("INCREASE START  MAGAZINE BY  10 ACORNS  .");
        infoText.push("GET TWICE AS MANY  NUTS WHEN  COLLECTING  .");
        infoText.push("DEPLOY TO START  FURTHER AHEAD  .");
    }
    
    public static void orderText(Graphics2D g2d, int y, int startX, int index, int fontSize) {
        int blankCounter = 0;
        String temp = "";
        Stack<String> text = new Stack<>();
        for (int i = 0; i < infoText.get(index).length(); i++) {
            if (infoText.get(index).charAt(i) == ' ' && infoText.get(index).charAt(i + 1) == ' ') {
                blankCounter++;
                text.push(temp);
                temp = "";
            } else {
                temp += infoText.get(index).charAt(i);
            }
        }
        if (!text.isEmpty()) {
            for (int i = 0; i < text.size(); i++) {
                if (i == 0) {
                    renderText(g2d, text.get(i), startX, y += 35);
                } else {
                    renderText(g2d, text.get(i), startX, y += 35);
                }
            }
        }
    }

    public static void renderText(Graphics2D g2d, String text, int x, int y) {
        TextEditor.centerString(g2d, text, x, y, 30, Color.white);
//        g2d.drawString(text, x, y);
    }
    
     public static int getSpaceLength(Font font, String character) {
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Rectangle2D bounds = font.getStringBounds(character, frc);
        int length = (int) bounds.getWidth();
        return length;
    }
}
