/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Main;

import StartMenus.StaticFeatures.Stat;
import Game.MapAndPlayerHandler;
import GameMenus.MenuAndStatsHandler;
import Toolkit.StatSaver;
import Obstacles.EnemyGenerator;
import StartMenus.Abilities.AbilityHandler;
import StartMenus.Abilities.OptionHandler;
import StartMenus.Achievements.AchievementHandler;
import StartMenus.Console.Console;
import StartMenus.Enemies.EnemyHandler;
import StartMenus.Play.Play;
import StartMenus.StaticFeatures.ScrollHelp;
import StartMenus.Shop.ShopHandler;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class ButtonHandler {
    
    public static int buttonIndex = -1;
    public static boolean allowOpen = true;
    public static Stack<Button> buttons = new Stack<>();
    private Stack<String> text = new Stack<>();
    
    public ButtonHandler() {
        generateButtons();
    }
    
    private void fillText() {
        text.push("STORE");
        text.push("ABILITIES");
        text.push("BUDDIES");
        text.push("ACHIEVEMENTS");
        text.push("ENEMIES");
        text.push("SKINS");
        text.push("SOUNDTRACKS");
        text.push("CONTROLS");
        text.push("STATS");
        text.push("SCOREBOARD");
        text.push("USERS");
        text.push("PLAY");
    }
    
    private void generateButtons() {
        fillText();
        int x = 1620;
        int y = 0;
        int index = 0;
        int pressedIndex = 0;
        int stringIndex = 0;
        for (int i = 0; i < 12; i++) {
            Button button = new Button(x, y, index, pressedIndex, 600, 90, "button.png", text.get(stringIndex));
            buttons.push(button);
            y += 90;
            index++;
            stringIndex++;
            pressedIndex++;
            if (index > 1) {
                index = 0;
            }
        }
    }
    
    public void restart() {
        EnemyGenerator.progress = 0;
        MenuAndStatsHandler.pixels = 0;
        MenuAndStatsHandler.meters = 0; 
        MenuAndStatsHandler.createHearts(3);
        StatSaver.acronMagazine = StatSaver.actualMag;
        StatSaver.attempts++;
        MapAndPlayerHandler.textX = 900;
        Console.active = false;
        Console.timer = 0;
        
    }
    
    public static void reset() {
        allowOpen = true;        
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setPressed(false);
            buttons.get(i).setMove(false);
            buttons.get(i).setAlpha(1);
            buttons.get(i).setFilter(false);
        }
    }
    
    public void openButton(MouseEvent e) {
        if (allowOpen && buttonIndex != -1 && buttons.get(buttonIndex).getOpenRect().contains(e.getX(), e.getY()) && !buttons.get(buttonIndex).getPressed()) {
            switch (buttonIndex) {
                case 0:
                    ShopHandler.active = true;
                    ScrollHelp.activate(true, false);
                    break;
                case 1:
                    AbilityHandler.active = true;
                    AbilityHandler.resetAllItems();
                    AbilityHandler.recreateHub();
                    if (AbilityHandler.items.size() > 1) {
                        ScrollHelp.activate(true,true);
                        ScrollHelp.setText("hover low");
                    }
                    OptionHandler.buttonActive = true;
                    break;
                case 2:
                    break;
                case 3:
                    AchievementHandler.active = true;
                    ScrollHelp.activate(true,true);
                    break;
                case 4:
                    EnemyHandler.active = true;
                    ScrollHelp.activate(true,true);
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    Stat.active = true;
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    restart();
                    Play.startGame();
                    break;
                default:
                    break;
            }
            buttons.get(buttonIndex).setFilter(true);
            buttons.get(buttonIndex).setPressed(true);
            buttons.get(buttonIndex).setMove(false);
            buttons.get(buttonIndex).setAlpha(1);
            allowOpen = false;            
        }
    }
    
    public void closeButton(MouseEvent e) {
        int tempIndex = -1;
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getPressed()) {
                tempIndex = i;
                buttonIndex = i;
            }
        }
        if (tempIndex != -1 && buttons.get(tempIndex).getCloseRect().contains(e.getX(), e.getY())) {
            doOnClose(tempIndex);
        }
    }
    
    public static void doOnClose(int tempIndex) {
        switch (tempIndex) {
            case 0:
                ShopHandler.active = false;
                ShopHandler.resetBuyAnimation();
                ShopHandler.numberOfItems = 0;
                break;
            case 1:
                AbilityHandler.active = false;
                AbilityHandler.resetAllItems();
                OptionHandler.close();
                break;
            case 2:
                break;
            case 3:
                AchievementHandler.active = false;
                break;
            case 4:
                EnemyHandler.active = false;
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                Stat.active = false;
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            default:
                break;
        }
        allowOpen = true;
        ScrollHelp.reset();
        buttons.get(tempIndex).setFilter(false);
        buttons.get(tempIndex).setPressed(false);
    }
    
    public void findMouse(MouseEvent e) {
        if (allowOpen) {
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getOpenRect().contains(e.getX(), e.getY())) {
                    buttonIndex = i;
                }
            }
        }
    }
    
    public static void backToMain(int tempIndex) {
        doOnClose(buttonIndex);
    }
    
    public void mouseMoved(MouseEvent e) {
        findMouse(e);
        for (Button button : buttons) {
            button.mouseMoved(e);
        }
    }
    
    public void mousePressed(MouseEvent e) {
        openButton(e);
        closeButton(e);
    }
    
    public void renderButtons(Graphics2D g2d) {
        if (StartMenu.active) {
            synchronized (buttons) {
                for (Button button : buttons) {
                    button.renderButton(g2d);
                }
            }
            synchronized (buttons) {
                for (Button button : buttons) {
                    button.renderFilter(g2d);
                }
            }
        }
    }
}
