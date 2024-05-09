/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Abilities;

import StartMenus.StaticFeatures.ScrollHelp;
import Toolkit.TextEditor;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class OptionHandler {

    private static int x = 760;
    private static int y = 10;
    private int closeX = 40;
    private int closeY = 40;
    private int backX = 800;
    private int backY = 780;
    private int buttonX = 1400;
    private int buttonY = 900;
    public static int sequenceIndex = 0;
    public static int chosenCounter = 0;
    private static boolean moveOpenButton = false;
    private static boolean moveBackButton = false;
    private static boolean moveX = false;
    public static boolean active = false;
    public static boolean buttonActive = true;
    public static boolean showLimit = true;
    private BufferedImage[] display;
    private BufferedImage[] openButton;
    private BufferedImage[] closeXImage;
    private BufferedImage[] backButton;
    private static BufferedImage[] abilityItem;
    private static BufferedImage[] choseButton;
    private BufferedImage[] limitLabel;
    private Rectangle openRect;
    private Rectangle closeRect;
    private Rectangle backRect;
    private static Limit limit;
    public static Rectangle drawRect = new Rectangle(800, 50, 300, 720);
    public static Stack<Option> options = new Stack<>();
    private static Stack<Rectangle> itemPosition = new Stack<>();

    public OptionHandler(BufferedImage[] display, BufferedImage[] openButton, BufferedImage[] closeXImage, BufferedImage[] backButton, BufferedImage[] abilityItem, BufferedImage[] choseButton, BufferedImage[] limitLabel) {
        this.display = display;
        this.openButton = openButton;
        this.closeXImage = closeXImage;
        this.backButton = backButton;
        this.abilityItem = abilityItem;
        this.choseButton = choseButton;
        this.limitLabel = limitLabel;
        openRect = new Rectangle(buttonX, buttonY, openButton[0].getWidth(), openButton[0].getHeight());
        closeRect = new Rectangle(closeX, closeY, closeXImage[0].getWidth(), closeXImage[0].getHeight());
        backRect = new Rectangle(backX, backY, backButton[0].getWidth(), backButton[0].getHeight());
        limit = new Limit(limitLabel);
    }

    public void check() {
        if (active) {
            limit.update();
        }
    }

    public static void addItem(int newIndex) {
        Option option;
        Rectangle tempRect;
        int tempX = x + 80;
        int tempY = y + 40;
        if (options.isEmpty()) {
            option = new Option(tempX, tempY, 120, 120, newIndex, 149, abilityItem, choseButton);
            options.push(option);
            tempRect = new Rectangle(tempX, tempY, 120, 120);
            itemPosition.push(tempRect);
        } else {
            tempY = options.lastElement().getY() + 149;
            option = new Option(tempX, tempY, 120, 120, newIndex, 149, abilityItem, choseButton);
            options.push(option);
            tempRect = new Rectangle(tempX, tempY, 120, 120);
            itemPosition.push(tempRect);
        }
        if (AbilityHandler.numbers.get(newIndex).buttonConfiguration.get(1) > 0) {
            options.get(sequenceIndex).setPressed(true);
            options.get(sequenceIndex).setButtonIndex(1);
            chosenCounter++;
        }
        sequenceIndex++;
    }

    public void mouseMoved(MouseEvent e) {
        if (active) {
            if (backRect.contains(e.getX(), e.getY())) {
                moveBackButton = true;
            } else {
                moveBackButton = false;
            }
            if (closeRect.contains(e.getX(), e.getY())) {
                moveX = true;
            } else {
                moveX = false;
            }
        }
        if (buttonActive && !active && !AbilityHandler.items.isEmpty()) {
            if (openRect.contains(e.getX(), e.getY())) {
                moveOpenButton = true;
            } else {
                moveOpenButton = false;
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        if (active) {
            if (backRect.contains(e.getX(), e.getY())) {
                close();
            }
            ItemsChosen(e);
        }
        if (buttonActive && !AbilityHandler.items.isEmpty()) {
            if (openRect.contains(e.getX(), e.getY())) {
                open();
            }
        }
    }

    private void ItemsChosen(MouseEvent e) {
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getButtonRect().contains(e.getX(), e.getY())) {
                if (!options.get(i).getPressed() && getNumberOfChosenItems() < 7) {
                    options.get(i).setPressed(true);
                    options.get(i).setButtonIndex(1);
                    chosenCounter++;
                    AbilityHandler.numbers.get(options.get(i).getIndex()).buttonConfiguration.set(1, 1);
                } else if (options.get(i).getPressed()) {
                    options.get(i).setPressed(false);
                    options.get(i).setButtonIndex(0);
                    Limit.reset();
                    chosenCounter--;
                    AbilityHandler.numbers.get(options.get(i).getIndex()).buttonConfiguration.set(1, 0);
                }
                if (chosenCounter == 7) {
                    ScrollHelp.down = true;
                    ScrollHelp.up = false;
                    Limit.active = true;
                }
            }
        }
    }

    public int getNumberOfChosenItems() {
        int tempCounter = 0;
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getPressed()) {
                tempCounter++;
            }
        }
        return tempCounter;
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (active && options.size() > 5) {
            int scroll = e.getWheelRotation();
            if (scroll < 0) {
                for (int i = 0; i < options.size(); i++) {
                    if (options.get(i).getY() == itemPosition.firstElement().getY()) {
                        options.get(i).setY((int) (itemPosition.lastElement().getY() + 149));
                    }
                }
                for (int i = 0; i < options.size(); i++) {
                    options.get(i).moveOneStep(true);
                }
            } else {
                for (int i = 0; i < options.size(); i++) {
                    if (options.get(i).getY() == itemPosition.lastElement().getY()) {
                        options.get(i).setY((int) (itemPosition.firstElement().getY() - 149));
                    }
                }
                for (int i = 0; i < options.size(); i++) {
                    options.get(i).moveOneStep(false);
                }
            }
        }
    }

    public static void open() {
        active = true;
        moveOpenButton = false;
        ScrollHelp.reset();
        if (options.size() > 5) {
            ScrollHelp.activate(true,true);
        }
        moveX = false;
    }

    public static void close() {
        active = false;
        moveBackButton = false;
        buttonActive = true;
        ScrollHelp.setText("hover low");
        ScrollHelp.activate(true,true);
        Limit.reset();
    }

    public void render(Graphics2D g2d) {
        if (active) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            g2d.setColor(Color.black);
            g2d.fillRect(-20, -20, 2000, 1100);
            g2d.setColor(Color.white);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
        if (buttonActive && !AbilityHandler.items.isEmpty()) {
            if (moveOpenButton) {
                g2d.drawImage(openButton[0], buttonX, buttonY - 10, null);
                TextEditor.centerWithItem(g2d, "Chose abilities", 1550, 940, 30, Color.white, false, 0, 0, 0);
                TextEditor.centerWithItem(g2d, "to use in game", 1550, 980, 30, Color.white, false, 0, 0, 0);
            } else {
                g2d.drawImage(openButton[0], buttonX, buttonY, null);
                TextEditor.centerWithItem(g2d, "Chose abilities", 1550, 950, 30, Color.white, false, 0, 0, 0);
                TextEditor.centerWithItem(g2d, "to use in game", 1550, 990, 30, Color.white, false, 0, 0, 0);
            }
        }
        if (active) {
            limit.renderDisplay(g2d);
            g2d.drawImage(display[0], 760, 10, null);
            TextEditor.centerString(g2d, "" + chosenCounter, 1060, 840, 50, Color.white);
            synchronized (options) {
                for (Option option : options) {
                    option.renderItem(g2d);
                }
            }
            if (moveX) {
                g2d.drawImage(closeXImage[0], closeX - 5, closeY - 5,80,80, null);
            } else {
                g2d.drawImage(closeXImage[0], closeX, closeY,80,80, null);
            }
            if (moveBackButton) {
                g2d.drawImage(backButton[0], backX, backY + 10, null);
                TextEditor.centerWithItem(g2d, "BACK", backX + 130, backY + 65, 40, Color.white, false, 0, 0, 0);
            } else {
                g2d.drawImage(backButton[0], backX, backY, null);
                TextEditor.centerWithItem(g2d, "BACK", backX + 130, backY + 55, 40, Color.white, false, 0, 0, 0);
            }
        }
    }

}
