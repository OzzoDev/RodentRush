/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Main;

import StartMenus.StaticFeatures.*;
import GameMenus.CloseLabel;
import Toolkit.StatSaver;
import StartMenus.Abilities.AbilityHandler;
import StartMenus.Achievements.AchievementHandler;
import StartMenus.Console.Console;
import StartMenus.Enemies.EnemyHandler;
import StartMenus.StaticFeatures.ScrollHelp;
import StartMenus.Shop.ShopHandler;
import Toolkit.GraphicLoader;
import Toolkit.TextEditor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class StartMenu {

    private int x = 0;
    private int y = 0;
    public static boolean active = true;
    private BufferedImage[] menu;
    private BufferedImage[] mainLabel;
    private BufferedImage[] statLabel;
    private static BufferedImage[] scrollLabel;
    private Stack<Label> labels = new Stack<>();
    private Console console;
    private Stat stat;
    private ScrollHelp scrollHelp;
    private AchievementHandler achievementHandler;
    private ShopHandler shopHandler;
    private AbilityHandler abilityHandler;
    private EnemyHandler enemyHandler;
    private ButtonHandler buttonHandler;

    public StartMenu() {
        menu = GraphicLoader.mainMenu;
        mainLabel = GraphicLoader.mainMenuLabel;
        statLabel = GraphicLoader.mainStatLabel;
        scrollLabel = GraphicLoader.mainScrollLabel;
        console = new Console(0, 0);
        stat = new Stat(695, 180, statLabel);
        scrollHelp = new ScrollHelp(760, 1090, scrollLabel);
        shopHandler = new ShopHandler();
        abilityHandler = new AbilityHandler();
        achievementHandler = new AchievementHandler();
        enemyHandler = new EnemyHandler();
        buttonHandler = new ButtonHandler();
        generateLabels();
    }

    public void check() {
        if (active && !CloseLabel.active) {
            shopHandler.check();
            abilityHandler.check();
            achievementHandler.check();
            enemyHandler.check();
            scrollHelp.update();
        }
        for (int i = 0; i < AbilityHandler.items.size(); i++) {
            AbilityHandler.items.get(i).ensureItemExistens();
        }
        Console.check();
    }

    private void generateLabels() {
        Label mainLabel = new Label(360, 0, this.mainLabel);
        labels.push(mainLabel);
    }

    public static void keyPressed(KeyEvent e) {
        Console.keyPressed(e);
    }

    public static void KeyReleased(KeyEvent e) {
        Console.keyReleased(e);
    }

    public void mouseMoved(MouseEvent e) {
        if (active && !CloseLabel.active) {
            buttonHandler.mouseMoved(e);
            shopHandler.mouseMoved(e);
            abilityHandler.mouseMoved(e);
            achievementHandler.mouseMoved(e);
            enemyHandler.mouseMoved(e);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (active && !CloseLabel.active) {
            buttonHandler.mousePressed(e);
            shopHandler.mousePressed(e);
            abilityHandler.mousePressed(e);
            achievementHandler.mousePressed(e);
            enemyHandler.mousePressed(e);
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (active && !CloseLabel.active) {
            shopHandler.mouseWheelMoved(e);
            abilityHandler.mouseWheelMoved(e);
            achievementHandler.mouseWheelMoved(e);
            enemyHandler.mouseWheelMoved(e);
            scrollHelp.mouseWheelMoved(e);
        }
    }

    public void renderMainMeny(Graphics2D g2d) {
        if (active) {
            g2d.drawImage(menu[0], x, y, null);
            synchronized (labels) {
                for (Label label : labels) {
                    label.renderLabel(g2d);
                }
            }
            TextEditor.centerWithItem(g2d, TextEditor.createText("", StatSaver.nuts, true), 620, 70, 60, Color.white, true, 1, 20, 60);
            g2d.drawImage(GraphicLoader.gameIcon, 770, 20, 60, 60, null);
            TextEditor.centerWithItem(g2d, TextEditor.createText("", StatSaver.actualMag, true), 1020, 70, 60, Color.white, true, 2, 20, 60);
            buttonHandler.renderButtons(g2d);
            abilityHandler.render(g2d);
            achievementHandler.render(g2d);
            enemyHandler.render(g2d);
            shopHandler.renderShop(g2d);
            stat.renderStat(g2d);
            scrollHelp.renderHelp(g2d);
        }
    }

}
