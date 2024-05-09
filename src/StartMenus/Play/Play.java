/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Play;

import Game.DeathHandler;
import GameAbilities.GameAbilityHandler;
import GameAbilities.Perks;
import GameMenus.MenuAndStatsHandler;
import Nuts.NutGenerator;
import Player.Player;
import PrizeWheel.Loot;
import StartMenus.Main.StartMenu;
import Toolkit.GraphicLoader;
import Toolkit.StatSaver;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class Play {

    private static Stack<Icon> icons = new Stack<>();
    private static BufferedImage[] iconImage;
    private static BufferedImage[] iconPadding;

    public Play() {
        iconImage = GraphicLoader.mysteryBoxLootIcon;
        iconPadding = GraphicLoader.mysteryBoxFilterIcon;
        setIcons();
    }

    private static void setIcons() {
        Icon icon = new Icon(1710, 980, 5, iconImage, iconPadding);
        icons.push(icon);
        icon = new Icon(1810, 980, 3, iconImage, iconPadding);
        icons.push(icon);
    }

    public void check() {
        icons.get(0).setValue(NutGenerator.factor);
        icons.get(1).setValue(Perks.factor);
    }

    public static void startGame() {
        DeathHandler.start = true;
        DeathHandler.run = true;
        StartMenu.active = false;
        GameAbilityHandler.createIcons();
        Player.reset();
        if (Loot.allowMaxMulti) {
            Loot.setMaxValues();
        } else {
            Perks.changeCollect(1, true);
            NutGenerator.changeCollect(1, true);
        }
        GameAbilityHandler.optionDisplays.get(1).activate();
//        StatSaver.berries =20;
//        MenuAndStatsHandler.lives.clear();
    }

    public void render(Graphics2D g2d) {
        synchronized (icons) {
            for (Icon icon : icons) {
                icon.renderIcon(g2d);
            }
        }
        GameAbilityHandler.optionDisplays.get(1).renderLootAlternative(g2d);
    }
}
