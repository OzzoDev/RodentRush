/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameAbilities;

import Game.DeathHandler;
import StartMenus.Abilities.AbilityHandler;
import StartMenus.Abilities.StoredText;
import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class GameAbilityHandler {

    private static int rowCounter = 0;
    public static boolean pause = false;
    public static Stack<Icon> icons = new Stack<>();
    private static Stack<Integer> itemList = new Stack<>();
    private static Stack<Integer> indexes = new Stack<>();
    private static Stack<GameAbility> abilities = new Stack<>();
    private Perks perks = new Perks();
    private static Icon icon;
    public static Stack<OptionDisplay> optionDisplays = new Stack<>();

    public GameAbilityHandler() {
        generateOptionsAbilities();
    }

    private void generateOptionsAbilities() {
        OptionDisplay casinoLoot;
        casinoLoot = new OptionDisplay(6,18,true,false,"Chose Loot", "Default 10");
        optionDisplays.push(casinoLoot);
        casinoLoot = new OptionDisplay(4,12,false,true,"Use Jumpbooster", "Close");
        optionDisplays.push(casinoLoot);
    }

    private static void findItemInList() {
        for (int i = 0; i < AbilityHandler.numbers.size(); i++) {
            if (AbilityHandler.numbers.get(i).itemsBought.get(0) > 0 && AbilityHandler.numbers.get(i).buttonConfiguration.get(1) == 1 && i != 6 && i != 7 && i != 4) {
                itemList.push(rowCounter * 3);
            }
            rowCounter++;
        }
    }

    private static void ensureExistens(int index) {
        if (AbilityHandler.numbers.get(index).itemsBought.get(AbilityHandler.numbers.get(index).buttonConfiguration.get(0)) > 0) {
            indexes.push(AbilityHandler.numbers.get(index).buttonConfiguration.get(0));
        } else {
            for (int i = 1; i < AbilityHandler.numbers.get(index).itemsBought.size(); i++) {
                if (AbilityHandler.numbers.get(index).itemsBought.get(i) > 0) {
                    indexes.push(i);
                    break;
                }
            }
        }
    }

    public static void createIcons() {
        findItemInList();
        StoredText.extractDuration();
        int x = 385;
        int y = 940;
        int key = 1;
        for (int i = 0; i < itemList.size(); i++) {
            ensureExistens(itemList.get(i) / 3);
            icon = new Icon(x, y, key, itemList.get(i) + AbilityHandler.numbers.get(itemList.get(i) / 3).buttonConfiguration.get(0) - 1, (itemList.get(i) / 3), indexes.get(i), GraphicLoader.subItemIcon, GraphicLoader.displayCircle);
            icons.push(icon);
            x += 160;
            key++;
        }
        for (int i = 0; i < icons.size(); i++) {
            icons.get(i).setDuration(StoredText.duration.get(itemList.get(i) + AbilityHandler.numbers.get(itemList.get(i) / 3).buttonConfiguration.get(0) - 1));
        }
    }

    public static void closeOnReturn() {
        for (int i = 0; i < optionDisplays.size(); i++) {
            optionDisplays.get(i).closeOnReturn();
        }
    }

    public void check() {
        perks.check();
        for (int i = 0; i < optionDisplays.size(); i++) {
            if (optionDisplays.get(i).close) {
                optionDisplays.get(i).closeDisplay();
            }else{
                optionDisplays.get(i).check();
            }
            optionDisplays.get(i).boost();
        }
        if (DeathHandler.start && !DeathHandler.run && !pause) {
            for (int i = 0; i < icons.size(); i++) {
                icons.get(i).pauseTimeCapturing(true);
            }
            for (int i = 0; i < abilities.size(); i++) {
                abilities.get(i).pauseTimeCapturing(true);
            }
            pause = true;
        }
        if (DeathHandler.run && DeathHandler.start) {
            if (pause) {
                for (int i = 0; i < icons.size(); i++) {
                    icons.get(i).pauseTimeCapturing(false);
                }
                for (int i = 0; i < abilities.size(); i++) {
                    abilities.get(i).pauseTimeCapturing(false);
                }
                pause = false;
            }
            for (int i = 0; i < icons.size(); i++) {
                if (icons.get(i).remove) {
                    reset();
                    createIcons();
                    if (icons.contains(i)) {
                        icons.get(i).remove = false;
                        icons.remove(i);
                    }
                } else {
                    icons.get(i).check();
                }
            }
            for (int i = 0; i < abilities.size(); i++) {
                if (abilities.get(i).remove) {
                    reset();
                    createIcons();
                    if (abilities.contains(i)) {
                        abilities.get(i).remove = false;
                        abilities.remove(i);
                    }
                } else {
                    abilities.get(i).check();
                }
            }
        }
    }

    public static void reset() {
        itemList.clear();
        icons.clear();
        rowCounter = 0;
        indexes.clear();
        abilities.clear();
    }
    
    public static boolean boosterInUse(){
        boolean open = false;
        boolean inUse = false; 
        boolean output = true; 
        for (int i = 0; i < optionDisplays.size(); i++) {
            if(optionDisplays.get(i).isBooster&&optionDisplays.get(i).active){
                output = true; 
            }else if(optionDisplays.get(i).active||!optionDisplays.get(i).boostInUse){
                output = false; 
            }
            if(optionDisplays.get(i).boostInUse){
                output = true; 
            }
        }
        return output;
    }

    public boolean ensureSingleUse(int index) {
        boolean onlyOneInUse = true;
        for (int i = 0; i < abilities.size(); i++) {
            if (index == abilities.get(i).itemIndex) {
                onlyOneInUse = false;
                break;
            }
        }
        return onlyOneInUse;
    }

    public void keyPressed(KeyEvent e) {
        if (DeathHandler.run && DeathHandler.start) {
            int keyCode = e.getKeyCode();
            int tempIndex = -1;
            if (keyCode == KeyEvent.VK_1 && !icons.isEmpty()) {
                tempIndex = 0;
            } else if (keyCode == KeyEvent.VK_2 && icons.size() >= 2) {
                tempIndex = 1;
            } else if (keyCode == KeyEvent.VK_3 && icons.size() >= 3) {
                tempIndex = 2;
            } else if (keyCode == KeyEvent.VK_4 && icons.size() >= 4) {
                tempIndex = 3;
            } else if (keyCode == KeyEvent.VK_5 && icons.size() >= 5) {
                tempIndex = 4;
            } else if (keyCode == KeyEvent.VK_6 && icons.size() >= 6) {
                tempIndex = 5;
            } else if (keyCode == KeyEvent.VK_7 && icons.size() >= 7) {
                tempIndex = 6;
            }
            if (tempIndex != -1 && ensureSingleUse(tempIndex)) {
                icons.get(tempIndex).pressed();
                GameAbility ability = new GameAbility(icons.get(tempIndex).itemPlace, icons.get(tempIndex).itemLevel, icons.get(tempIndex).duration);
                abilities.push(ability);
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
       for (int i = 0; i < optionDisplays.size(); i++) {
            optionDisplays.get(i).mouseMoved(e);
        }
    }

    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < optionDisplays.size(); i++) {
            optionDisplays.get(i).mousePressed(e);
        }
    }

    public void renderIcon(Graphics2D g2d) {
        if (DeathHandler.start) {
            synchronized (icons) {
                for (Icon ico : icons) {
                    ico.renderIcon(g2d);
                }
            }
        }
    }
}
