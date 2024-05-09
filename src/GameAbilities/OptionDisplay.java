/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameAbilities;

import Game.MapAndPlayerHandler;
import GameMenus.CloseLabel;
import GameMenus.MenuAndStatsHandler;
import Player.Player;
import PrizeWheel.WheelHandler;
import StartMenus.Abilities.AbilityHandler;
import Toolkit.GraphicLoader;
import Toolkit.TextEditor;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class OptionDisplay {

    private int itemIndex;
    private int subIndex;
    private int firstLevelItemIndex;
    private int timer = 0;
    private int autoCloseTimer = 0;
    private int boostFactor;
    public int boostMax;
    private int mapSpeedTimer = 0;
    private int mapSpeed = 10;
    public int tempDistance;
    public boolean active = false;
    public boolean lock = true;
    private boolean moveItem;
    private boolean moveButton;
    private boolean buttonPressed;
    private boolean itemPressed;
    private boolean isCasinoLoot;
    public boolean isBooster;
    public boolean boostInUse;
    public boolean close;
    private boolean increaseMapspeed = true;
    private boolean decreaseMapspeed;
    private String text = "";
    private String textOnCloseButton = "";
    private String helpText = "";
    private BufferedImage[] itemDisplay;
    private BufferedImage[] subItem;
    private BufferedImage[] closeButton;
    private Rectangle itemRect;
    private Rectangle closeRect;

    public OptionDisplay(int itemIndex, int firstLevelItemIndex, boolean isCasinoLoot, boolean isBooster, String text, String textOnCloseButton) {
        this.itemIndex = itemIndex;
        this.firstLevelItemIndex = firstLevelItemIndex;
        this.isCasinoLoot = isCasinoLoot;
        this.isBooster = isBooster;
        this.text = text;
        this.textOnCloseButton = textOnCloseButton;
        itemDisplay = GraphicLoader.optionDisplay;
        subItem = GraphicLoader.abilitySubItem;
        closeButton = GraphicLoader.optionCloseButton;
        itemRect = new Rectangle(860, 370, 180, 180);
        closeRect = new Rectangle(835, 620, 250, 60);
        subIndex = (AbilityHandler.numbers.get(itemIndex).buttonConfiguration.get(0) - 1) + firstLevelItemIndex;
    }

    public void check() {
        if (active && !CloseLabel.active) {
            autoCloseTimer++;
            if (autoCloseTimer > 160 && !isCasinoLoot) {
                close = true;
                autoCloseTimer = 0;
            }
            if (lock) {
                timer++;
                if (timer > 5) {
                    if (buttonPressed && isCasinoLoot) {
                        useItem(0);
                    } else if (buttonPressed && !isCasinoLoot) {
                        closeDisplay();
                    } else {
                        useItem(subIndex);
                    }
                    close = true;
                    timer = 0;
                }
            }
        }
    }

    public void boost() {
        if (boostInUse && !CloseLabel.active) {
            if (mapSpeed >= 100) {
                increaseMapspeed = false;
            }
            if (MenuAndStatsHandler.meters >= boostMax) {
                decreaseMapspeed = true;
            }
            if (mapSpeed <= 10 && decreaseMapspeed) {
                decreaseMapspeed = false;
                increaseMapspeed = false;
                boostInUse = false;
                MapAndPlayerHandler.setMapSpeedAndGenerate(10, true);
            } else {
                MapAndPlayerHandler.setMapSpeedAndGenerate(mapSpeed, false);
                if (MenuAndStatsHandler.meters <= tempDistance) {
                    MenuAndStatsHandler.meters += boostFactor;
                }
            }
            if (increaseMapspeed) {
                mapSpeed += 2;
            }
            if (decreaseMapspeed) {
                mapSpeed -= 2;
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (!lock && active && !CloseLabel.active) {
            if (itemRect.contains(e.getX(), e.getY())) {
                moveItem = true;
                itemRect = new Rectangle(860, 365, 180, 180);
            } else {
                moveItem = false;
                itemRect = new Rectangle(860, 370, 180, 180);
            }
            if (closeRect.contains(e.getX(), e.getY())) {
                moveButton = true;
                closeRect = new Rectangle(835, 615, 250, 60);
            } else {
                moveButton = false;
                closeRect = new Rectangle(835, 620, 250, 60);
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        if (!lock && active && !CloseLabel.active) {
            if (itemRect.contains(e.getX(), e.getY())) {
                itemPressed = true;
                lock = true;
            } else if (closeRect.contains(e.getX(), e.getY())) {
                buttonPressed = true;
                lock = true;
            }
        }
    }

    private void setHelpText() {
        switch (subIndex) {
            case 12:
                helpText = "250m";
                break;
            case 13:
                helpText = "750m";
                break;
            case 14:
                helpText = "2500m";
                break;
            case 18:
                helpText = "100";
                break;
            case 19:
                helpText = "250";
                break;
            case 20:
                helpText = "500";
                break;
            default:
                break;
        }
    }

    public void activate() {
        if (AbilityHandler.numbers.get(itemIndex).itemsBought.get(0) > 0) {
            close = false;
            active = true;
            lock = false;
            increaseMapspeed = true;
            decreaseMapspeed = false;
            boostInUse = false;
            autoCloseTimer = 0;
            subIndex = (AbilityHandler.numbers.get(itemIndex).buttonConfiguration.get(0) - 1) + firstLevelItemIndex;
            setHelpText();
        }
    }

    public void closeOnReturn() {
        active = false;
        lock = false;
        buttonPressed = false;
        itemPressed = false;
        moveButton = false;
        moveItem = false;
        increaseMapspeed = false;
        decreaseMapspeed = false;
        boostInUse = false;
        MapAndPlayerHandler.setMapSpeedAndGenerate(mapSpeed, false);
    }

    public void closeDisplay() {
        active = false;
        lock = false;
        buttonPressed = false;
        itemPressed = false;
        moveButton = false;
        moveItem = false;
    }

    public void useItem(int index) {
        switch (index) {
            case 12:
                tempDistance = MenuAndStatsHandler.meters + 250;
                boostFactor = 1;
                boostMax = MenuAndStatsHandler.meters + 200;
                boostInUse = true;
                break;
            case 13:
                tempDistance = MenuAndStatsHandler.meters + 750;
                boostFactor = 3;
                boostMax = MenuAndStatsHandler.meters + 700;
                boostInUse = true;
                break;
            case 14:
                boostFactor = 15;
                boostMax = MenuAndStatsHandler.meters + 2450;
                tempDistance = MenuAndStatsHandler.meters + 2500;
                boostInUse = true;
                break;
            case 0:
                WheelHandler.cashIn(10);
                WheelHandler.generateNutRain();
                break;
            case 18:
                WheelHandler.cashIn(100);
                break;
            case 19:
                WheelHandler.cashIn(250);
                WheelHandler.generateNutRain();
                break;
            case 20:
                WheelHandler.cashIn(500);
                WheelHandler.generateNutRain();
                break;
            default:
                break;
        }
        if (index != 0) {
            AbilityHandler.numbers.get(itemIndex).itemsBought.set(AbilityHandler.numbers.get(itemIndex).buttonConfiguration.get(0), AbilityHandler.numbers.get(itemIndex).itemsBought.get(AbilityHandler.numbers.get(itemIndex).buttonConfiguration.get(0)) - 1);
            AbilityHandler.numbers.get(itemIndex).itemsBought.set(0, AbilityHandler.numbers.get(itemIndex).itemsBought.get(0) - 1);
        }
    }

    public void renderLootAlternative(Graphics2D g2d) {
        if (active) {
            g2d.drawImage(itemDisplay[0], 810, 250, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
            g2d.setColor(Color.white);
            g2d.fillRect(845, 355, 230, 230);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            if (moveItem) {
                g2d.drawImage(subItem[subIndex], 860, 365, null);
                TextEditor.centerString(g2d, helpText, 960, 535, 20, Color.white);
            } else {
                g2d.drawImage(subItem[subIndex], 860, 370, null);
                TextEditor.centerString(g2d, helpText, 960, 540, 20, Color.white);
            }
            TextEditor.centerString(g2d, text, 960, 300, 30, Color.white);
            if (moveButton) {
                g2d.drawImage(closeButton[0], 835, 615, null);
                TextEditor.centerString(g2d, textOnCloseButton, 960, 655, 30, Color.white);

            } else {
                g2d.drawImage(closeButton[0], 835, 620, null);
                TextEditor.centerString(g2d, textOnCloseButton, 960, 660, 30, Color.white);

            }
        }
    }
}
