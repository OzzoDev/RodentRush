/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Abilities;

import Toolkit.StatSaver;
import StartMenus.Shop.ShopHandler;
import Toolkit.TextEditor;
import Toolkit.GraphicLoader;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class AbilityHandler {

    public static int leftCounter = 0;
    public static int rightCounter = 0;
    public static int oldIndex = 0;
    public static int colX = 9;
    public static boolean active = false;
    public static boolean itemOpened = false;
    public static Stack<AbilityItem> items = new Stack<>();
    public static Stack<Integer> xPos = new Stack<>();
    public static Stack<Integer> typeOfItem = new Stack<>();
    public static Stack<Rectangle> itemPositionRectangles = new Stack<>();
    public static Stack<NumberHandler> numbers = new Stack<>();
    private static BufferedImage[] item;
    private static BufferedImage[] subItem;
    private static BufferedImage[] priceDisplayer;
    private static BufferedImage[] mouseMovedAnimation;
    private static BufferedImage[] itemOption;
    private static BufferedImage[] circle;
    private static BufferedImage[] button;
    private static BufferedImage[] optionDisplay;
    private static BufferedImage[] optionOpenButton;
    private static BufferedImage[] closeXImage;
    private static BufferedImage[] backButton;
    private static BufferedImage[] smallItem;
    private static BufferedImage[] choseButton;
    private static BufferedImage[] limitLabel;
    private StoredText text = new StoredText();
    private OptionHandler optionHandler;
    private static NumberHandler numberHandler;

    public AbilityHandler() {
        item = GraphicLoader.abilityItem;
        subItem = GraphicLoader.abilitySubItem;
        priceDisplayer = GraphicLoader.abilityPriceDisplayer;
        mouseMovedAnimation = GraphicLoader.abilityMouseMovedAnimation;
        circle = GraphicLoader.abilityCircle;
        itemOption = GraphicLoader.abilityItemOption;
        button = GraphicLoader.abilityButton;
        optionOpenButton = GraphicLoader.abilityOptionOpenButton;
        optionDisplay = GraphicLoader.abilityOptionDisplay;
        backButton = GraphicLoader.abilityBackButton;
        smallItem = GraphicLoader.abilitySmallItem;
        choseButton = GraphicLoader.abilityChoseButton;
        limitLabel = GraphicLoader.abilityLimitLabel;
        closeXImage = GraphicLoader.xToCloseLabel;
        optionHandler = new OptionHandler(optionDisplay, optionOpenButton, closeXImage, backButton, smallItem, choseButton, limitLabel);
    }

    public static void print() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(numbers.get(i).itemsBought.get(j) + " ");
            }
            System.out.print(numbers.get(i).buttonConfiguration.get(0) + " ");
            System.out.print(numbers.get(i).buttonConfiguration.get(1) + " ");
            System.out.println();
        }
    }

    public static void fillNumbers() {
        for (int i = 0; i < colX; i++) {
            numberHandler = new NumberHandler();
            numbers.push(numberHandler);
        }
    }

    public void check() {
        if (active) {
            for (AbilityItem ite : items) {
                ite.check();
            }
            optionHandler.check();
        }
    }

    public static void recreateHub() {
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).itemsBought.get(0) > 0) {
                for (int j = 0; j < numbers.get(i).itemsBought.get(0); j++) {
                    addAbility(i, false, false);
                }
            }
        }
    }

    public static boolean checkImageItem(int index) {
        boolean allowNew = true;
        if (!typeOfItem.isEmpty()) {
            for (int i = 0; i < typeOfItem.size(); i++) {
                if (index == typeOfItem.get(i)) {
                    allowNew = false;
                    break;
                }
            }
        }
        if (allowNew) {
            typeOfItem.push(index);
        }
        return allowNew;
    }

    public static void createItemPosition() {
        xPos.clear();
        for (int i = 0; i < items.size(); i++) {
            xPos.push(items.get(i).getX());
        }
        PriorityQueue<Integer> sortedX = new PriorityQueue<>(xPos);
        xPos.clear();
        while (!sortedX.isEmpty()) {
            xPos.push(sortedX.poll());
        }
        sortedX.clear();
    }

    public static void addAbility(int shopItem, boolean fromShop, boolean allowValueChange) {
        int x = 0;
        int y = 0;
        AbilityItem abilityItem;
        if (checkImageItem(shopItem)) {
            if (items.isEmpty()) {
                x = 778;
                y = 474;
            } else {
                if (items.size() % 2 == 0) {
                    leftCounter++;
                    y = 474 - (29 * leftCounter);
                    x = 778 - 389 * (leftCounter);
                } else if (!items.isEmpty() || items.size() == 1) {
                    rightCounter++;
                    y = 474 - (29 * rightCounter);
                    x = 778 + (389 * rightCounter);
                }
            }
            Rectangle rect = new Rectangle(x, y, 360, 360);
            itemPositionRectangles.push(rect);
            if (fromShop && allowValueChange) {
                AbilityHandler.numbers.get(shopItem).itemsBought.set(0, 1);
                AbilityHandler.numbers.get(shopItem).itemsBought.set(1, 1);
                abilityItem = new AbilityItem(x, y, 360, 360, 389, shopItem, shopItem, ShopHandler.items.get(shopItem + 1).getCost(), item, subItem, priceDisplayer, mouseMovedAnimation, circle, itemOption, button);
            } else {
                if (allowValueChange) {
                    AbilityHandler.numbers.get(shopItem).itemsBought.set(0, 1);
                }
                abilityItem = new AbilityItem(x, y, 360, 360, 389, shopItem, shopItem, ShopHandler.items.get(shopItem + 1).getCost(), item, subItem, priceDisplayer, mouseMovedAnimation, circle, itemOption, button);
            }
            items.push(abilityItem);
            createItemPosition();
            oldIndex++;
            if (shopItem != 6 && shopItem != 7) {
                OptionHandler.addItem(shopItem);
            }
        } else if (!checkImageItem(shopItem)) {
            if (fromShop && allowValueChange) {
                AbilityHandler.numbers.get(shopItem).itemsBought.set(0, AbilityHandler.numbers.get(shopItem).itemsBought.get(0) + 1);
                AbilityHandler.numbers.get(shopItem).itemsBought.set(1, AbilityHandler.numbers.get(shopItem).itemsBought.get(1) + 1);
            } else if (allowValueChange) {
                AbilityHandler.numbers.get(shopItem).itemsBought.set(0, AbilityHandler.numbers.get(shopItem).itemsBought.get(0) + 1);
            }
        }
    }

    public static void openAbilityItem() {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setShowItem(false);
        }
    }

    public static void openAllitems() {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setShowItem(true);
        }
    }

    public static void resetAllItems() {
        for (int i = 0; i < itemPositionRectangles.size(); i++) {
            for (int j = 0; j < items.size(); j++) {
                if (itemPositionRectangles.get(i).intersects(items.get(j).rect)) {
                    items.get(j).setY((int) itemPositionRectangles.get(i).getY());
                    items.get(j).reset();
                }
            }
        }
        itemOpened = false;
        typeOfItem.clear();
        items.clear();
        leftCounter = 0;
        rightCounter = 0;
        oldIndex = 0;
        itemPositionRectangles.clear();
        xPos.clear();
        OptionHandler.options.clear();
        OptionHandler.chosenCounter = 0;
        OptionHandler.sequenceIndex = 0;
        openAllitems();
    }

    public void mouseMoved(MouseEvent e) {
        if (active) {
            for (AbilityItem ite : items) {
                ite.mouseMoved(e);
            }
            optionHandler.mouseMoved(e);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (active) {
            for (AbilityItem ite : items) {
                ite.mousePressed(e);
            }
            optionHandler.mousePressed(e);
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int scroll = e.getWheelRotation();
        if (active && !itemOpened && !OptionHandler.active) {
            if (scroll < 0) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getX() == xPos.lastElement()) {
                        items.get(i).setX(xPos.get(0) - 389);
                    }
                }
                for (AbilityItem ite : items) {
                    ite.moveOneStep(true);
                }
            } else {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getX() == xPos.get(0)) {
                        items.get(i).setX(xPos.lastElement() + 389);
                    }
                }
                for (AbilityItem ite : items) {
                    ite.moveOneStep(false);
                }
            }
            for (int i = 0; i < itemPositionRectangles.size(); i++) {
                for (int j = 0; j < items.size(); j++) {
                    if (itemPositionRectangles.get(i).intersects(items.get(j).rect)) {
                        items.get(j).setY((int) itemPositionRectangles.get(i).getY());
                    }
                }
            }
        }
        optionHandler.mouseWheelMoved(e);
    }

    public int findCenterItem() {
        int i;
        for (i = 0; i < items.size(); i++) {
            if (items.get(i).getX() == 778) {
                break;
            }
        }
        return i;
    }

    public void render(Graphics2D g2d) {
        if (active) {
            synchronized (items) {
                for (AbilityItem ite : items) {
                    ite.renderItem(g2d);
                }
            }
            TextEditor.setFontAndColor(g2d, 60, Color.white);
            if (!items.isEmpty()) {
                g2d.drawImage(ShopHandler.nutDisplayer[0], 710, items.get(findCenterItem()).getY() - 300, null);
            } else {
                g2d.drawImage(ShopHandler.nutDisplayer[0], 710, 174, null);
            }
            if (!items.isEmpty()) {
                TextEditor.centerWithItem(g2d, TextEditor.createText("", StatSaver.nuts, true), 960, items.get(findCenterItem()).getY() - 200, 60, Color.white, true, 1, items.get(findCenterItem()).getY() - 253, 60);
            } else {
                TextEditor.setFontAndColor(g2d, 30, Color.white);
                g2d.drawString("NO ABILITIES AVAILABLE", 780, 230);
                g2d.drawString("GO TO STORE TO PURCHASE", 750, 270);
            }
            optionHandler.render(g2d);
        }
    }
}
