/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrizeWheel;

import Game.DeathHandler;
import GameAbilities.OptionDisplay;
import GameAbilities.GameAbilityHandler;
import GameMenus.CloseLabel;
import StartMenus.Abilities.AbilityHandler;
import Toolkit.StatSaver;
import Toolkit.GraphicLoader;
import Toolkit.TextEditor;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class WheelHandler {

    private static String upperLine = "CHOSE";
    private static String lowerLine = "BUTTON";
    public static boolean lock = false;
    public static boolean active = false;
    public static boolean allowSpin = false;
    public static boolean allowConfetti = true;
    public static boolean updateConfetti = false;
    public static boolean allowButtonPress = true;
    private static boolean close = false;
    private BufferedImage[] prizeWheel;
    private BufferedImage[] background;
    private BufferedImage[] wheelHolder;
    private BufferedImage[] pointerTriangle;
    private BufferedImage[] button;
    private BufferedImage[] holder;
    private BufferedImage[] displayer;
    private static BufferedImage[] stats;
    private static BufferedImage[] prizeConfetti;
    private static Stack<Nut> nuts = new Stack<>();
    public static Stack<Confetti> confetti = new Stack<>();
    public static Stack<Button> buttons = new Stack<>();
    private Wheel wheel;
    private Pointer pointer;
    private PrizeDisplayer prizeDisplayer;

    public WheelHandler() {
        prizeWheel = GraphicLoader.prizeWheel;
        background = GraphicLoader.prizeWheelBackground;
        wheelHolder = GraphicLoader.prizeWheelHolder;
        pointerTriangle = GraphicLoader.prizeWheelPointerTriangle;
        button = GraphicLoader.prizeWheelButton;
        displayer = GraphicLoader.prizeWheelDisplay;
        holder = GraphicLoader.prizeWheelStatsDisplay;
        stats = GraphicLoader.gameStats;
        prizeConfetti = GraphicLoader.prizeWheelConfetti;
        wheel = new Wheel(560, 300, 500, 500, prizeWheel);
        pointer = new Pointer(775, 250, pointerTriangle);
        prizeDisplayer = new PrizeDisplayer(displayer, stats);
        generateButtons();
    }

    public void update() {
        if (active && !CloseLabel.active) {
            if (StatSaver.berries == 0 && allowButtonPress && Wheel.rotationSpeed <= 0 && close) {
                DeathHandler.returnToMainMenu();
                close = false;
            }
            wheel.update();
            pointer.update();
            for (Button button : buttons) {
                button.check();
            }
            if (lock) {
                if (nuts.isEmpty()) {
                    lock = false;
                    active = false;
                    nuts.clear();
                    DeathHandler.returnToMainMenu();
                }
            }
            if (updateConfetti && !CloseLabel.active) {
                for (int i = 0; i < confetti.size(); i++) {
                    if (confetti.get(i).getRemove()) {
                        confetti.remove(i);
                    } else {
                        confetti.get(i).update();
                    }
                }
                if (confetti.isEmpty()) {
                    if (StatSaver.berries == 0) {
                        close = true;
                    }
                    updateConfetti = false;
                    allowConfetti = true;
                }
            }
            updateNuts();
            prizeDisplayer.update();
            Loot.check();
        }
    }

    public static void generateConfetti() {
        if (allowConfetti) {
            int angle = 0;
            int max = (int) (2000 + Math.random() * 500);
            for (int i = 0; i < max; i++) {
                Confetti conf = new Confetti(790, 540, prizeConfetti);
                confetti.push(conf);
                if (angle > 360) {
                    angle = 0;
                }
                angle++;
            }
            allowConfetti = false;
        }
    }

    public static void generateNutRain() {
        int max = (int) (500 + Math.random() * 100);
        for (int i = 0; i < max; i++) {
            Nut nut = new Nut(stats[1]);
            nuts.push(nut);
        }
    }

    public void updateNuts() {
        for (int i = 0; i < nuts.size(); i++) {
            if (nuts.get(i).getY() > 790) {
                nuts.remove(i);
            } else {
                nuts.get(i).update();
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (active) {
            wheel.keyPressed(e);
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (active) {
            for (Button button : buttons) {
                button.mouseMoved(e);
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        if (active && !CloseLabel.active && allowButtonPress) {
            int chosenIndex = -1;
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getRect().contains(e.getX(), e.getY())) {
                    chosenIndex = i;
                    break;
                }
            }
            if (chosenIndex != -1) {
                if (buttons.get(chosenIndex).getAllowPress() && StatSaver.berries - buttons.get(chosenIndex).getCost() >= 0) {
                    for (int i = 0; i < buttons.size(); i++) {
                        if (i != chosenIndex) {
                            buttons.get(i).buttonPressed(false, 0.4f);
                        } else {
                            if (chosenIndex != 4) {
                                buttons.get(chosenIndex).buttonPressed(false, 1);
                                wheel.wheelIndex = chosenIndex;
                                allowSpin = true;
                                changeLines(true, false);
                                StatSaver.berries -= buttons.get(chosenIndex).getCost();
                            } else {
                                if (AbilityHandler.numbers.get(6).itemsBought.get(0) > 0) {
                                    GameAbilityHandler.optionDisplays.get(1).closeOnReturn();
                                    GameAbilityHandler.optionDisplays.get(0).activate();
                                } else {
                                    cashIn(0);
                                }
                                changeLines(true, true);
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateButtons() {
        int x = 1180;
        int y = 300;
        int buttonIndex = 0;
        Button bu;
        for (int i = 0; i < 5; i++) {
            if (i == 4) {
                bu = new Button(x, y, 170, 80, buttonIndex, button, stats, false);
            } else {
                bu = new Button(x, y, 170, 80, buttonIndex, button, stats, true);
            }
            buttons.push(bu);
            buttonIndex++;
            y += 80;
        }
    }

    public static void cashIn(int factor) {
        int amount = StatSaver.berries * factor;
        StatSaver.nuts += amount;
        Wheel.numberOfSpins = 0;
        Loot.closeDelay = false;
        lock = true;
        StatSaver.berries = 0;
        generateNutRain();
    }

    public static void ensureCashIn() {
        int amount = StatSaver.berries * 10;
        StatSaver.nuts += amount;
        StatSaver.berries = 0;
    }

    public static void resetButtons() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setAllowPress(true);
            buttons.get(i).setPressed(false);
            buttons.get(i).setAlpha(1f);
            buttons.get(i).resetAnimation();
            buttons.get(i).setMove(false);
        }
    }

    public static void changeLines(boolean chosen, boolean clear) {
        if (!clear) {
            if (chosen) {
                upperLine = "PRESS ENTER";
                lowerLine = "TO SPIN";
            } else {
                upperLine = "CHOSE";
                lowerLine = "BUTTON";
            }
        } else {
            upperLine = "";
            lowerLine = "";
        }
    }

    public void render(Graphics2D g2d) {
        if (active) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.20f));
            g2d.fillRect(500, 140, 920, 720);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            prizeDisplayer.render(g2d);
            g2d.drawImage(background[0], 510, 150, null);
            g2d.drawImage(background[1], 510, 150, null);
            synchronized (nuts) {
                for (Nut nut : nuts) {
                    nut.render(g2d);
                }
            }
            TextEditor.setFontAndColor(g2d, 25, Color.white);
            g2d.drawString(TextEditor.createText(upperLine, 0, false), 1150, 230);
            g2d.drawString(TextEditor.createText(lowerLine, 0, false), 1150, 260);
            g2d.drawImage(wheelHolder[0], 710, 690, null);
            g2d.drawImage(holder[0], 1000, 730, null);
            TextEditor.centerWithItem(g2d, TextEditor.createText("", StatSaver.berries, true), 1050, 782, 20, Color.white, active, 3, 760, 30);
            TextEditor.centerWithItem(g2d, TextEditor.createText("", StatSaver.nuts, true), 1140, 782, 20, Color.white, active, 1, 760, 30);
            TextEditor.centerWithItem(g2d, TextEditor.createText("", StatSaver.acronMagazine, true), 1250, 782, 20, Color.white, active, 2, 757, 30);
            synchronized (confetti) {
                for (Confetti conf : confetti) {
                    conf.renderConfetti(g2d);
                }
            }
            wheel.renderWheel(g2d);
            pointer.renderPointer(g2d);
            synchronized (buttons) {
                for (Button bu : buttons) {
                    bu.renderButton(g2d);
                }
            }
            GameAbilityHandler.optionDisplays.get(0).renderLootAlternative(g2d);
        }
    }
}
