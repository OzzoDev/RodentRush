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
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class AbilityItem {

    private int x;
    private int y;
    private int width;
    private int height;
    private int space;
    public int index;
    private int oldIndex;
    private int buttonIndex = -1;
    private int angle = 0;
    private int angleSpeed = 6;
    private int cost;
    public int numberOfItems = 1;
    public int key;
    public int buttonLevel = 0;
    private float alpha = 1;
    private String middle = "Default: ";
    private String lower = "Level 1";
    private boolean allowSpin = false;
    private boolean mouseIntersection = false;
    private boolean moveUp;
    private boolean moveDown;
    private boolean allowMove = false;
    private boolean showItem = true;
    private boolean allowGenerate = true;
    private boolean allowButtonGenerate = true;
    private BufferedImage[] item;
    private BufferedImage[] subItem;
    private BufferedImage[] priceDisplayer;
    private BufferedImage[] mouseMovedAnimation;
    private BufferedImage[] itemOption;
    private BufferedImage[] circle;
    private BufferedImage[] button;
    public Rectangle rect;
    public Stack<SubItem> subItems = new Stack<>();
    public Stack<Button> buttons = new Stack<>();

    public AbilityItem(int x, int y, int width, int height, int space, int index, int oldIndex, int cost, BufferedImage[] item, BufferedImage[] subItem, BufferedImage[] priceDisplayer, BufferedImage[] mouseMovedAnimation, BufferedImage[] circle, BufferedImage[] itemOption, BufferedImage[] button) {
        this.x = x;
        this.y = y;
        this.space = space;
        this.width = width;
        this.height = height;
        this.index = index;
        this.oldIndex = oldIndex;
        this.cost = cost;
        this.item = item;
        this.subItem = subItem;
        this.priceDisplayer = priceDisplayer;
        this.mouseMovedAnimation = mouseMovedAnimation;
        this.circle = circle;
        this.itemOption = itemOption;
        this.button = button;
        this.key = index + 1;
        rect = new Rectangle(x, y, width, height);
        if (AbilityHandler.numbers.get(oldIndex).buttonConfiguration.get(0) > 0) {
            setOneButton(AbilityHandler.numbers.get(oldIndex).buttonConfiguration.get(0) - 1);
            changeStringValues(AbilityHandler.numbers.get(oldIndex).buttonConfiguration.get(0));
        }
    }

    private void generateSubHub() {
        if (allowGenerate) {
            SubItem sub;
            int direction = 0;
            int subIndex = 0;
            for (int i = 0; i < 3; i++) {
                sub = new SubItem(x + 160, y + 100, 200, direction, index, subIndex, oldIndex, cost, subItem, priceDisplayer, mouseMovedAnimation, circle);
                subItems.push(sub);
                direction++;
                subIndex++;
            }
            allowGenerate = false;
        }
    }

    private void generateButtons() {
        if (allowButtonGenerate) {
            Button but;
            int x = 100;
            int y = 1020;
            int level = 1;
            for (int i = 0; i < 3; i++) {
                but = new Button(x, y, 120, 50, level, button);
                buttons.push(but);
                level++;
                x += 140;
            }
            setOneButton(buttonLevel);
            allowButtonGenerate = false;
        }
    }

    public void check() {
        if (x == 778) {
            alpha = 1;
        } else {
            alpha = 0.4f;
        }
        if (x == 778 && y < 474) {
            generateSubHub();
        }
        if (x == 778 && y == 300) {
            generateButtons();
        }
        rotate();
        UpperAndLower();
        rect = new Rectangle(x, y, width, height);
        for (SubItem subItem : subItems) {
            subItem.update();
        }
    }

    public void rotate() {
        if (allowSpin) {
            if (angle >= 360) {
                angle = 0;
                if (showItem) {
                    showItem = false;
                    AbilityHandler.openAbilityItem();
                    ScrollHelp.reset();
                } else {
                    showItem = true;
                    ScrollHelp.reset();
                    AbilityHandler.itemOpened = false;
                }
                if (y == 474) {
                    moveUp = true;
                    moveDown = false;
                    ScrollHelp.activate(true,true);
                    ScrollHelp.setText("hover high");
                    for (int i = 0; i < subItems.size(); i++) {
                        subItems.get(i).setAllowMovement(true);
                    }
                }
                if (y == 300) {
                    moveDown = true;
                    moveUp = false;
                    ScrollHelp.activate(true,true);
                    ScrollHelp.setText("hover low");
                }
                allowSpin = false;
                mouseIntersection = false;
            } else {
                angle += angleSpeed;
            }
        }
    }

    public void UpperAndLower() {
        if (x == 778) {
            if (y <= 320 && subItems.get(0).getX() != 200) {
                for (int i = 0; i < subItems.size(); i++) {
                    subItems.get(i).setActive(true);
                }
            }
            if (moveUp) {
                y -= 5;
            } else if (moveDown) {
                y += 5;
            }
            if (y < 300 && moveUp) {
                moveUp = false;
                y = 300;
            } else if (y > 474 && moveDown) {
                moveDown = false;
                y = 474;
                allowGenerate = true;
                allowButtonGenerate = true;
                AbilityHandler.openAllitems();
                subItems.clear();
                buttons.clear();
                OptionHandler.buttonActive = true;
            }
        }
    }

    public void reset() {
        moveUp = false;
        moveDown = false;
        subItems.clear();
        buttons.clear();
        allowSpin = false;
        allowGenerate = true;
        allowButtonGenerate = true;
        angle = 0;
        for (int i = 0; i < subItems.size(); i++) {
            subItems.get(i).clear();
        }
        OptionHandler.buttonActive = true;
    }

    public void mouseMoved(MouseEvent e) {
        if (!OptionHandler.active) {
            if (y == 474 || y == 300) {
                if (rect.contains(e.getX(), e.getY())) {
                    if (!mouseIntersection) {
                        if (y == 300 && !subItems.get(0).getInMovement()) {
                            for (int i = 0; i < subItems.size(); i++) {
                                subItems.get(i).moveBack();
                                subItems.get(i).buyAnimation.clear();
                                subItems.get(i).onMouseMoved.clear();
                            }
                        }
                        if (y == 474) {
                            AbilityHandler.itemOpened = true;
                            OptionHandler.buttonActive = false;
                        }
                        allowSpin = true;
                        ScrollHelp.reset();
                    }
                    mouseIntersection = true;
                } else if (!rect.contains(e.getX(), e.getY()) && angle == 0) {
                    mouseIntersection = false;
                }
            }
            for (SubItem sub : subItems) {
                sub.mouseMoved(e);
            }
            findButtonIndex(e);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (!OptionHandler.active) {
            for (SubItem sub : subItems) {
                sub.mousePressed(e);
            }
            if (y == 300) {
                buttonMonitor(e);
                resetPressed();
            }
        }
    }

    public void ensureItemExistens() {
        for (int i = 0; i < AbilityHandler.numbers.get(oldIndex).itemsBought.size(); i++) {
            if (AbilityHandler.numbers.get(oldIndex).itemsBought.get(i) == 0 && AbilityHandler.numbers.get(oldIndex).buttonConfiguration.get(0) == i) {
                for (int j = 0; j < AbilityHandler.numbers.get(oldIndex).itemsBought.size(); j++) {
                    if (AbilityHandler.numbers.get(oldIndex).itemsBought.get(j) != 0 && j > 0) {
                        AbilityHandler.numbers.get(oldIndex).buttonConfiguration.set(0, j);
                        setOneButton(AbilityHandler.numbers.get(oldIndex).buttonConfiguration.get(0) - 1);
                        changeStringValues(AbilityHandler.numbers.get(oldIndex).buttonConfiguration.get(0));
                    }
                }
            }
        }
    }

    public void buttonMonitor(MouseEvent e) {
        if (buttonIndex != -1 && subItems.get(buttonIndex).numberOfItems > 0 && !buttons.isEmpty() && buttons.get(buttonIndex).getRect().contains(e.getX(), e.getY())) {
            for (int i = 0; i < buttons.size(); i++) {
                if (i == buttonIndex && subItems.get(i).numberOfItems > 0) {
                    buttons.get(i).setPressed(true);
                    buttons.get(i).setAlpha(1);
                    buttons.get(i).setIndex(1);
                    buttons.get(i).setMove(false);
                    changeStringValues(buttons.get(buttonIndex).getLevel());
                    AbilityHandler.numbers.get(oldIndex).buttonConfiguration.set(0, buttons.get(i).getLevel());
                } else if (i != buttonIndex && checkButtonPressed() && buttons.get(i).getPressed()) {
                    buttons.get(i).setPressed(false);
                    buttons.get(i).setAlpha(0.7f);
                    buttons.get(i).setIndex(0);
                }
            }
        }
    }

    public void findButtonIndex(MouseEvent e) {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getRect().contains(e.getX(), e.getY())) {
                buttonIndex = i;
            }
            if (buttons.get(i).getRect().contains(e.getX(), e.getY()) && subItems.get(i).numberOfItems > 0 && !buttons.get(i).getPressed()) {
                buttons.get(i).setMove(true);
            } else {
                buttons.get(i).setMove(false);
            }
        }
    }

    public boolean checkButtonPressed() {
        boolean pressed = false;
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getPressed()) {
                pressed = true;
                break;
            }
        }
        return pressed;
    }

    private void setOneButton(int solidIndex) {
        for (int i = 0; i < buttons.size(); i++) {
            if (i == solidIndex) {
                buttons.get(i).setPressed(true);
                buttons.get(i).setAlpha(1);
                buttons.get(i).setIndex(1);
                buttons.get(i).setMove(false);
            } else {
                buttons.get(i).setPressed(false);
                buttons.get(i).setAlpha(0.7f);
                buttons.get(i).setIndex(0);
            }
        }
    }

    public void resetPressed() {
        for (int i = 0; i < subItems.size(); i++) {
            if (subItems.get(i).pressed) {
                subItems.get(i).pressed = false;
                break;
            }
        }
    }

    public void moveOneStep(boolean right) {
        if (right) {
            x += space;
        } else {
            x -= space;
        }
        rect = new Rectangle(x, y, width, height);
    }

    public void changeStringValues(int level) {
        middle = "Chosen: ";
        lower = "Level " + level;
        this.buttonLevel = level - 1;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getIndex() {
        return index;
    }

    public boolean getShowItem() {
        return showItem;
    }

    public void setShowItem(boolean showItem) {
        this.showItem = showItem;
    }

    public void renderAnimation(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        double centerX = x + width / 2;
        double centerY = y + height / 2;
        AffineTransform transform = new AffineTransform();
        transform.translate(centerX, centerY);
        transform.rotate(Math.toRadians(angle));
        transform.translate(-width / 2.0, -height / 2.0);
        g2d.setTransform(transform);
        g2d.drawImage(item[index], 0, 0, null);
        g2d.setTransform(oldTransform);
    }

    public void renderItem(Graphics2D g2d) {
        if (x % 389 == 0) {
            if (showItem || x == 778) {
                synchronized (subItems) {
                    for (SubItem subItem : subItems) {
                        subItem.renderSubItem(g2d);
                    }
                }
                if (!mouseIntersection) {
                    if (x == 778) {
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                    } else {
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                    }
                    g2d.drawImage(item[index], x, y, null);
                    if (x == 778) {
                        if (AbilityHandler.numbers.get(index).itemsBought.get(0) > 0) {
                            TextEditor.centerWithItem(g2d, String.valueOf(AbilityHandler.numbers.get(index).itemsBought.get(0)), x + 310, y + 330, 40, Color.white, false, 0, 0, 0);
                        }
                    }
                } else if (mouseIntersection && angle <= 360 && x == 778) {
                    renderAnimation(g2d);
                }
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
                if (!subItems.isEmpty()) {
                    if (subItems.get(0).getX() == 200) {
                        g2d.drawImage(itemOption[0], 50, 920, null);
                        synchronized (buttons) {
                            for (Button but : buttons) {
                                but.renderButton(g2d);
                            }
                        }
                        TextEditor.setFontAndColor(g2d, 30, Color.white);
                        TextEditor.centerWithItem(g2d, "Chose which item to activate", 300, 960, 30, Color.white, false, 0, 0, 0);
                        TextEditor.centerWithItem(g2d, middle + " " + lower, 300, 1000, 30, Color.white, false, 0, 0, 0);
                    }
                }
            }
        }
    }
}
