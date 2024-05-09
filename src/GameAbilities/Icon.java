/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameAbilities;

import StartMenus.Abilities.AbilityHandler;
import Toolkit.TextEditor;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class Icon {

    private int x;
    private int y;
    private int key;
    public int itemIndex;
    public int itemPlace;
    public int itemLevel;
    public int duration = 0;
    public float alpha = 0.5f;
    private String suffix = "";
    private boolean setStartTime = true;
    public boolean remove = false;
    public boolean pause = false;
    private Stack<LocalTime> newTime = new Stack<>();
    private Stack<Integer> timeLimit = new Stack<>();
    private BufferedImage[] icon;
    private BufferedImage[] displayCircle;

    public Icon(int x, int y, int key, int itemIndex, int itemPlace, int itemLevel, BufferedImage[] icon, BufferedImage[] displayCircle) {
        this.x = x;
        this.y = y;
        this.key = key;
        this.itemIndex = itemIndex;
        this.itemPlace = itemPlace;
        this.itemLevel = itemLevel;
        this.icon = icon;
        this.displayCircle = displayCircle;
    }

    public void check() {
        if (AbilityHandler.numbers.get(itemPlace).itemsBought.get(itemLevel) == 0 && alpha != 1) {
            remove = true;
        }
        if (alpha == 1 && setStartTime) {
            newTime.push(LocalTime.now());
            setStartTime = false;
        } else if (!setStartTime&&!pause) {
            if (capturedTime(timeLimit.lastElement()) == -1) {
                alpha = 0.5f;
                setStartTime = true;
                duration = timeLimit.firstElement();
            }
        }
    }

    public void pressed() {
        if (AbilityHandler.numbers.get(itemPlace).itemsBought.get(itemLevel) > 0 && alpha != 1) {
            alpha = 1f;
            AbilityHandler.numbers.get(itemPlace).itemsBought.set(itemLevel, AbilityHandler.numbers.get(itemPlace).itemsBought.get(itemLevel) - 1);
            AbilityHandler.numbers.get(itemPlace).itemsBought.set(0, AbilityHandler.numbers.get(itemPlace).itemsBought.get(0) - 1);
        }
    }

    public long capturedTime(long timeMax) {
        long totalSeconds = 0;
        LocalTime currentTime = LocalTime.now();
        Duration timeDifference = Duration.between(newTime.lastElement(), currentTime);
        if (!pause) {
            totalSeconds = timeDifference.getSeconds();
        }
        if (totalSeconds <= timeMax && !pause) {
            if (!pause) {
                duration = (int) (timeMax - totalSeconds);
            }
            return totalSeconds;
        } else {
            return -1;
        }
    }

    public void pauseTimeCapturing(boolean pause) {
        this.pause = pause;
        if (pause) {
            timeLimit.push(duration);
        } else {
            if(!timeLimit.isEmpty()){
            duration = timeLimit.lastElement();
            }
            newTime.push(LocalTime.now());
        }
    }

    public static boolean isInteger(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        return input.matches("\\-?\\d+");
    }

    public void setDuration(String dura) {
        String temp = "";
        for (int i = 0; i < dura.length(); i++) {
            if (Character.isDigit(dura.charAt(i))) {
                temp += dura.charAt(i);
            } else {
                suffix += dura.charAt(i);
            }
        }
        if (isInteger(temp) && !temp.isEmpty()) {
            this.duration = Integer.parseInt("4");
            timeLimit.push(duration);
        }
    }

    public void renderIcon(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(icon[itemIndex], x, y, null);
        g2d.drawImage(displayCircle[0], x - 5, y - 5, null);
        g2d.drawImage(displayCircle[0], x + 65, y + 70, null);
        TextEditor.centerString(g2d, String.valueOf(key), x + 5, y + 7, 10, Color.white);
        TextEditor.centerString(g2d, String.valueOf(AbilityHandler.numbers.get(itemPlace).itemsBought.get(itemLevel)), x + 75, y + 82, 10, Color.white);
        TextEditor.centerString(g2d, String.valueOf(duration) + suffix, x + 40, y + 100, 15, Color.white);
        TextEditor.centerString(g2d, "Level " + itemLevel, x + 40, y + 17, 10, Color.white);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }
}
