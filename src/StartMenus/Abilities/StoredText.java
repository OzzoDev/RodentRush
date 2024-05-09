/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Abilities;

import Toolkit.TextEditor;
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
    public static Stack<String> duration = new Stack<>();

    public StoredText() {
        generateText();
    }

    private void generateText() {
        infoText.push("Nut collecting help  Duration: 10s  Number of bees: 1  Combined range: 5m  .");
        infoText.push("Nut collecting help  Duration: 15s  Number of bees: 2  Combined range: 10m  .");
        infoText.push("Nut collecting help  Duration: 25s  Number of bees: 3  Combined range: 20m  .");

        infoText.push("More acorns when  killing enemies  Duration: 10s  Effect: 2X  .");
        infoText.push("More acorns when  killing enemies  Duration: 20s  Effect: 3X  .");
        infoText.push("More acorns when  killing enemies  Duration: 40s  Effect: 4X  .");

        infoText.push("Extra protection  Duration: 20s  Equal to one life  .");
        infoText.push("Extra protection  Duration: 25s  Equal to one life  .");
        infoText.push("Extra protection  Duration: 40s  Equal to two lives  .");

        infoText.push("Fall down slowly  Duration: 10s  .");
        infoText.push("Fall down slowly  Duration: 15s  .");
        infoText.push("Fall down slowly  Duration: 25s  .");

        infoText.push("Get a speedy start  Range: 250m  .");
        infoText.push("Get a speedy start  Range: 750m  .");
        infoText.push("Get a speedy start  Range: 2500m  .");

        infoText.push("Fly at any altitude  no acorns required  Duration: 15s  .");
        infoText.push("Fly at any altitude  no acorns required  Duration: 25s  .");
        infoText.push("Fly at any altitude  no acorns required  Duration: 40s  .");

        infoText.push("Loot per berry when  redeeming nuts at  the casino  Effect: 100 nuts per berry  .");
        infoText.push("Loot per berry when  redeeming nuts at  the casino  Effect: 250 nuts per berry  .");
        infoText.push("Loot per berry when  redeeming nuts at  the casino  Effect: 500 nuts per berry  .");

        infoText.push("Increase start magazine  Effect: 10 more acorns  .");
        infoText.push("Increase start magazine  Effect: 15 more acorns  .");
        infoText.push("Increase start magazine  Effect: 25 more acorns  .");

        infoText.push("Multi nuts  Duration: 15s  Effect: 2X  .");
        infoText.push("Multi nuts  Duration: 25s  Effect: 3X  .");
        infoText.push("Multi nuts  Duration: 40s  Effect: 4X  .");
    }

    public static void extractDuration() {
        String temp = "";
        boolean skip = false;
        boolean addSpace = true; 
        duration.clear();
        for (int i = 0; i < infoText.size(); i++) {
            for (int j = 0; j < infoText.get(i).length(); j++) {
                if (!skip) {
                    if (Character.isDigit(infoText.get(i).charAt(j))) {
                        temp += infoText.get(i).charAt(j);
                        if (infoText.get(i).charAt(j + 1) == 's') {
                            duration.push(temp + "s");
                            skip = true;
                            addSpace = false; 
                        } else if (infoText.get(i).charAt(j + 1) == 'm') {
                            duration.push(temp + "m");
                            skip = true;
                            addSpace = false; 
                        }
                    } else {
                        temp = "";
                    }
                }
            }
            if(addSpace){
                duration.push(" "); 
            }
            skip = false;
            addSpace = true; 
        }
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
                    renderText(g2d, text.get(i), startX, y += 30);
                } else {
                    renderText(g2d, text.get(i), startX - getSpaceLength(TextEditor.font, " "), y += 30);
                }
            }
        }
    }

    public static void renderText(Graphics2D g2d, String text, int x, int y) {
        g2d.drawString(text, x, y);
    }

    public static int getSpaceLength(Font font, String character) {
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Rectangle2D bounds = font.getStringBounds(character, frc);
        int length = (int) bounds.getWidth();
        return length;
    }

}
