/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Abilities;

import GameAbilities.MagazineButton;
import Toolkit.TextEditor;
import Toolkit.StatSaver;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class SubItem {

    private int x;
    private int y;
    private int size;
    private final int OLD_X;
    private final int MAX_SIZE;
    private double speedY;
    private int speedX = 10;
    private int direction;
    private int index;
    private int oldIndex;
    private int abilityIndex;
    private int updatedIndex;
    private int level;
    private int cost;
    public int numberOfItems = 0;
    private boolean active = false;
    private boolean allowMovement = true;
    private boolean inMovement = false;
    private boolean left;
    private boolean right;
    public boolean pressed = false;
    private Rectangle rect;
    private BufferedImage[] subItem;
    private BufferedImage[] priceDisplayer;
    private BufferedImage[] mouseMovedAnimation;
    private BufferedImage[] circle;
    public Stack<OnMouseMoved> onMouseMoved = new Stack<>();
    public Stack<BuyAnimation> buyAnimation = new Stack<>();
    private MagazineButton magazineButton;

    public SubItem(int x, int y, int size, int direction, int abilityIndex, int index, int oldIndex, int cost, BufferedImage[] subItem, BufferedImage[] priceDisplayer, BufferedImage[] mouseMovedAnimation, BufferedImage[] circle) {
        this.x = x;
        this.y = y;
        this.size = 1;
        OLD_X = x;
        MAX_SIZE = size;
        this.direction = direction;
        this.subItem = subItem;
        this.priceDisplayer = priceDisplayer;
        this.mouseMovedAnimation = mouseMovedAnimation;
        this.circle = circle;
        this.cost = cost;
        this.index = index;
        this.oldIndex = oldIndex;
        this.abilityIndex = abilityIndex;
        if (abilityIndex != 0) {
            index = 3 * abilityIndex;
        } else {
            index = 0;
        }
        switch (direction) {
            case 0:
                left = true;
                speedY = -6;
                updatedIndex = index;
                level = 1;
                this.cost = cost;
                break;
            case 1:
                left = true;
                speedY = -2;
                updatedIndex = index + 1;
                level = 2;
                this.cost *= 2;
                break;
            case 2:
                left = true;
                speedY = 2;
                updatedIndex = index + 2;
                level = 3;
                this.cost *= 4;
                break;
            default:
                break;
        }
        rect = new Rectangle(x, y, size, size);
        numberOfItems = AbilityHandler.numbers.get(oldIndex).itemsBought.get(level);

        if (oldIndex == 7 && level == 1) {
            magazineButton = new MagazineButton(810, 750);
        }
    }

    public void moveBack() {
        switch (direction) {
            case 0:
                right = true;
                speedY = 6;
                break;
            case 1:
                right = true;
                speedY = 3;
                break;
            case 2:
                right = true;
                speedY = -1;
                break;
            default:
                break;
        }
        allowMovement = true;
    }

    public void reset() {
        switch (direction) {
            case 0:
                left = true;
                speedY = -6;
                break;
            case 1:
                left = true;
                speedY = -2;
                break;
            case 2:
                left = true;
                speedY = 2;
                break;
            default:
                break;
        }
        size = 1;
        x = OLD_X;
        allowMovement = false;
        active = false;
    }

    public void clear() {
        buyAnimation.clear();
        onMouseMoved.clear();
    }

    public void limit() {
        if (x < 200 && left) {
            left = false;
            x = 200;
            inMovement = false;
            allowMovement = false;
        } else if (x > OLD_X && right) {
            active = false;
            switch (direction) {
                case 0:
                    left = true;
                    speedY = -7;
                    break;
                case 1:
                    left = true;
                    speedY = -3;
                    break;
                case 2:
                    left = true;
                    speedY = 1;
                    break;
                default:
                    break;
            }
            allowMovement = false;
            inMovement = false;
        }
    }

    public void update() {
        if (active && allowMovement) {
            if (x != 200 && x != OLD_X) {
                inMovement = true;
            } else {
                inMovement = false;
            }
            limit();
            if (left) {
                x -= speedX;
                if (size < MAX_SIZE) {
                    size += 7;
                }
            } else if (right) {
                x += speedX;
                if (size > 1) {
                    size -= 3;
                }
            }
            y += speedY;
            rect = new Rectangle(x, y, size, size);
        }
        if (active && oldIndex == 7 && level == 1&&x==200) {
            magazineButton.check();
        }
        for (int i = 0; i < onMouseMoved.size(); i++) {
            if (onMouseMoved.get(i).getRemove()) {
                onMouseMoved.remove(i);
            } else {
                onMouseMoved.get(i).update();
            }
        }
        for (int i = 0; i < buyAnimation.size(); i++) {
            if (buyAnimation.get(0).getRemove()) {
                buyAnimation.remove(i);
            } else {
                buyAnimation.get(i).update();
            }
        }
    }

    private void generateMouseMovedAnimation() {
        OnMouseMoved onMouse = new OnMouseMoved(x + 45, y, level, false, true, mouseMovedAnimation);
        onMouseMoved.push(onMouse);
        onMouse = new OnMouseMoved(x + 65, y, level, true, false, mouseMovedAnimation);
        onMouseMoved.push(onMouse);
    }

    private void createBuyAnimation() {
        BuyAnimation buy = new BuyAnimation(x, y, 200, 200, circle);
        buyAnimation.push(buy);
    }

    public void mousePressed(MouseEvent e) {
        if (oldIndex == 7 && level == 1&&x==200) {
            magazineButton.mousePressed(e);
        }
        if (rect.contains(e.getX(), e.getY())) {
            if (buyAnimation.isEmpty() && StatSaver.nuts - cost >= 0) {
                StatSaver.nuts -= cost;
                createBuyAnimation();
                numberOfItems++;
                AbilityHandler.numbers.get(oldIndex).itemsBought.set(level, numberOfItems);
                AbilityHandler.numbers.get(oldIndex).itemsBought.set(0, AbilityHandler.numbers.get(oldIndex).itemsBought.get(0) + 1);
                pressed = true;
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (oldIndex == 7 && level == 1&&x==200) {
            magazineButton.mouseMoved(e);
        }
        if (AbilityHandler.numbers.get(oldIndex).itemsBought.get(level) > 0) {
            if (rect.contains(e.getX(), e.getY())) {
                if (onMouseMoved.isEmpty() && x == 200) {
                    generateMouseMovedAnimation();
                }
            } else {
                onMouseMoved.clear();
            }
            for (OnMouseMoved onMouse : onMouseMoved) {
                onMouse.mouseMoved(e);
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getOLD_X() {
        return OLD_X;
    }

    public int getNumber() {
        return numberOfItems;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setAllowMovement(boolean allowMovement) {
        this.allowMovement = allowMovement;
    }

    public boolean getInMovement() {
        return inMovement;
    }

    private void getInfoText(Graphics2D g2d) {
        TextEditor.setFontAndColor(g2d, 25, Color.white);
        StoredText.orderText(g2d, y + 30, x + 220, updatedIndex, 4);

    }

    public void renderSubItem(Graphics2D g2d) {
        if (active) {
            if (AbilityHandler.numbers.get(oldIndex).itemsBought.get(level) > 0) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            } else {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }
            if (numberOfItems > 0) {
                synchronized (onMouseMoved) {
                    for (OnMouseMoved on : onMouseMoved) {
                        on.renderAnimation(g2d);
                    }
                }
            }
            g2d.drawImage(subItem[updatedIndex], x, y, size, size, null);
            synchronized (buyAnimation) {
                for (BuyAnimation buy : buyAnimation) {
                    buy.renderAnimation(g2d);
                }
            }
        }
        TextEditor.setFontAndColor(g2d, 15, Color.white);
        if (x == 200) {
            g2d.drawString("LEVEL " + level, x + 70, y + 40);
            getInfoText(g2d);
            g2d.drawImage(priceDisplayer[0], x - 50, y - 78, null);
            if (cost != 0) {
                TextEditor.centerWithItem(g2d, TextEditor.createText("", cost, true), x + 100, y - 40, 30, Color.white, true, 1, y - 65, 30);
            }
            if (AbilityHandler.numbers.get(oldIndex).itemsBought.get(level) > 0) {
                TextEditor.centerWithItem(g2d, String.valueOf(AbilityHandler.numbers.get(oldIndex).itemsBought.get(level)), x + 170, y + 170, 30, Color.white, false, 0, 0, 0);
            }
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        if (oldIndex == 7 && level == 1&&x==200) {
            magazineButton.renderMagazineUseButton(g2d);
        }
    }
}
