/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameMenus;

import Game.CollisionEffects;
import Game.DarknessFilter;
import Game.DeathHandler;
import MysteryBoxes.LootHandler;
import Obstacles.EnemyGenerator;
import Player.Player;
import StartMenus.Play.Play;
import Toolkit.*;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class MenuAndStatsHandler {

    public static int pixels = 0;
    public static int meters = 0;
    private boolean setWaterIcon = true;
    private boolean setDownIcon = true;
    private boolean setDarkIcon = true;
    public static boolean run = true;
    public static boolean hit = false;
    private Stack<InGameMeny> inGameMenys = new Stack<>();
    private Stack<InGameStats> gameStats = new Stack<>();
    private Stack<Label> labels = new Stack<>();
    public static Stack<InventoryIcon> icons = new Stack<>();
    public static Stack<InGameStats> lives = new Stack<>();
    public static Stack<Button> buttons = new Stack<>();
    private CloseLabel closeLabel;
    private Play play;
    private GameInventory gameInventory;

    public MenuAndStatsHandler() {
        create();
        createLabels();
        createStats();
        createButtons();
        gameInventory = new GameInventory(0, 900);
        closeLabel = new CloseLabel(740, 300, 500, 500, "inGameCloseLabel.png");
        play = new Play();
    }

    private void create() {
        InGameMeny inGameMeny = new InGameMeny(0, 0, 0);
        inGameMenys.push(inGameMeny);
        inGameMeny = new InGameMeny(1620, 0, 1);
        inGameMenys.push(inGameMeny);
    }

    public static void createHearts(int amount) {
        int x = 1665;
        for (int i = 0; i < amount; i++) {
            InGameStats inGameStats = new InGameStats(x, 10, 0);
            lives.push(inGameStats);
            x += 90;
        }
    }

    public void removeLives() {
        if (hit && !lives.isEmpty()) {
            synchronized (lives) {
                lives.pop();
                hit = false;
            }
        }
    }

    private void createStats() {
        InGameStats inGameStats = new InGameStats(-5, 10, 1);
        gameStats.push(inGameStats);
        inGameStats = new InGameStats(5, 60, 2);
        gameStats.push(inGameStats);
        inGameStats = new InGameStats(180, 20, 3);
        gameStats.push(inGameStats);
    }

    public void check() {
        if (StatSaver.acronMagazine <= 0) {
            StatSaver.acronMagazine = 0;
        }
        if (!run) {
            createLabels();
        }
        removeLives();
        if (Player.colWithWater && setWaterIcon) {
            iconPlace(1.1, 40, 940, 0, "water");
            setWaterIcon = false;
        } else if (CollisionEffects.startMudAnimation && setDownIcon) {
            iconPlace(0, 40, 940, 1, "down");
            setDownIcon = false;
        } else if (DarknessFilter.darken && setDarkIcon) {
            iconPlace(0.44, 40, 940, 2, "dark");
            setDarkIcon = false;
        }
        if (!Player.colWithWater) {
            setWaterIcon = true;
        }
        if (!CollisionEffects.startMudAnimation) {
            setDownIcon = true;
        }
        if (!DarknessFilter.darken) {
            setDarkIcon = true;
        }
        checkIcons();
        if (DeathHandler.run) {
            pixels++;
            if (pixels > 40) {
                pixels = 0;
                meters++;
            }
        }
        play.check();
    }

    private void iconPlace(double speed, int x, int y, int iconIndex, String name) {
        InventoryIcon icon;
        switch (icons.size()) {
            case 0:
                icon = new InventoryIcon(speed, x, y, iconIndex, name);
                icons.push(icon);
                break;
            case 1:
                icon = new InventoryIcon(speed, x + 100, y, iconIndex, name);
                icons.push(icon);
                break;
            case 2:
                icon = new InventoryIcon(speed, x + 200, y, iconIndex, name);
                icons.push(icon);
                break;
            default:
                break;
        }
    }

    public void checkIcons() {
        for (int i = 0; i < icons.size(); i++) {
            icons.get(i).check();
            if (!icons.get(i).getActive()) {
                icons.remove(i);
            } else if (icons.get(i).getName().equalsIgnoreCase("down") && !CollisionEffects.startMudAnimation) {
                icons.remove(i);
            } else if (icons.get(i).getName().equalsIgnoreCase("dark") && !DarknessFilter.darken) {
                icons.remove(i);
            }
        }
    }

    private void createLabels() {
        Label label = new Label(30, 930, 1);
        labels.push(label);
        label = new Label(130, 930, 1);
        labels.push(label);
        label = new Label(230, 930, 1);
        labels.push(label);
        label = new Label(1500, 970, 2);
        labels.push(label);
        label = new Label(1600, 970, 2);
        labels.push(label);
        label = new Label(1700, 970, 2);
        labels.push(label);
        label = new Label(1800, 970, 2);
        labels.push(label);
        label = new Label(365, 930, 0);
        labels.push(label);
        label = new Label(525, 930, 0);
        labels.push(label);
        label = new Label(685, 930, 0);
        labels.push(label);
        label = new Label(845, 930, 0);
        labels.push(label);
        label = new Label(1005, 930, 0);
        labels.push(label);
        label = new Label(1165, 930, 0);
        labels.push(label);
        label = new Label(1325, 930, 0);
        labels.push(label);
    }

    private void createButtons() {
        Button button = new Button(790, 370, 400, 100, 0, GraphicLoader.gameMenuButton, "exit");
        buttons.push(button);
        button = new Button(790, 500, 400, 100, 1, GraphicLoader.gameMenuButton, "return");
        buttons.push(button);
    }

    public static void closeMenu() {
        CloseLabel.active = false;
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setActive(false);
        }
    }

    public void calculateNewValuesToDisplay(Graphics2D g2d, int textX, int textY, double value) {
        g2d.setColor(Color.white);
        Font font = new Font("1", Font.PLAIN, 30);
        g2d.setFont(font);
        if (value < 1000) {
            g2d.drawString(String.format("%.0f", value), textX, textY);
        } else if (value < 1000000) {
            value /= 1000;
            g2d.drawString(String.format("%.2f", value) + "K", textX, textY);
        } else {
            value /= 1000000;
            g2d.drawString(String.format("%.2f", value) + "M", textX, textY);
        }
    }

    public void render(Graphics2D g2d) {
        if (DeathHandler.start) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            if (run) {
                synchronized (inGameMenys) {
                    for (InGameMeny inGameMen : inGameMenys) {
                        inGameMen.renderMeny(g2d);
                    }
                }
                synchronized (gameStats) {
                    for (InGameStats gameStat : gameStats) {
                        gameStat.renderStat(g2d);
                    }
                }
                synchronized (gameStats) {
                    for (InGameStats gameStat : lives) {
                        gameStat.renderStat(g2d);
                    }
                }
                calculateNewValuesToDisplay(g2d, 35, 45, StatSaver.nuts);
                calculateNewValuesToDisplay(g2d, 215, 45, StatSaver.berries);
                if (!LootHandler.unlimitedArcon) {
                    calculateNewValuesToDisplay(g2d, 45, 95, StatSaver.acronMagazine);
                } else {
                    g2d.drawString("∞", 45, 95);
                }
                g2d.setColor(Color.white);
                Font font = new Font("1", Font.PLAIN, 40);
                g2d.setFont(font);
                if (meters == 0) {
                    TextEditor.centerString(g2d, "0m", 1790, 115, 40, Color.white);
                } else {
                    TextEditor.centerString(g2d, TextEditor.createText("", meters, true)+"m", 1790, 115, 40, Color.white);
                }
                font = new Font("1", Font.PLAIN, 30);
                g2d.setFont(font);
                gameInventory.renderInventory(g2d);
                synchronized (labels) {
                    for (Label label : labels) {
                        label.renderLabel(g2d);
                    }
                }
                LootHandler.render(g2d);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
                synchronized (icons) {
                    for (InventoryIcon icon : icons) {
                        icon.render(g2d);
                    }
                }

            }
        }
        play.render(g2d);
    }

    public void renderClose(Graphics2D g2d) {
        closeLabel.renderLabel(g2d);
        synchronized (buttons) {
            for (Button button : buttons) {
                button.renderButtons(g2d);
            }
        }
    }
}
