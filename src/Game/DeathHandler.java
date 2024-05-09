/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import GameAbilities.OptionDisplay;
import GameAbilities.GameAbilityHandler;
import GameAbilities.Perks;
import StartMenus.Main.StartMenu;
import GameMenus.MenuAndStatsHandler;
import Toolkit.StatSaver;
import GameMenus.Revive;
import MysteryBoxes.LootHandler;
import MysteryBoxes.MysteryBoxGenerator;
import Nuts.NutGenerator;
import Obstacles.EnemyGenerator;
import Player.FlyCondition;
import Player.Player;
import PrizeWheel.Loot;
import PrizeWheel.PrizeDisplayer;
import PrizeWheel.Wheel;
import PrizeWheel.WheelHandler;
import StartMenus.Main.ButtonHandler;

/**
 *
 * @author oscar
 */
public class DeathHandler {

    private Revive revive;
    public static boolean start = false;
    public static boolean run = false;
    public static boolean active = true;
    private static boolean allow = true;

    public DeathHandler() {
        revive = new Revive(800, 400);
    }

    public void check() {
        revive.check();
        if (MenuAndStatsHandler.lives.isEmpty() && revive.getAllowActive() && start) {
            run = false;
            Revive.active = true;
            Player.pause();
            Player.angle = 0;
            revive.setAllowActive(false);
            DarknessFilter.darken = false;
            DarknessFilter.timer = 0;
            Perks.waterCollision = false;
            Perks.waterTimer = 0;
            CollisionEffects.startMudAnimation = false;
            LootHandler.whenDead(false, false, false, 0, 0);
            clearStacks();
        }
        if (!revive.getActive() && run) {
            revive.setAllowActive(true);
        }
    }

    public static void activateWheel() {
        run = false;
        DarknessFilter.darken = false;
        DarknessFilter.timer = 0;
        if (WheelHandler.confetti.isEmpty()) {
            WheelHandler.allowConfetti = true;
        }
        WheelHandler.active = true;
        WheelHandler.changeLines(false, false);
        Loot.allowMaxMulti = true;
    }

    public static void returnToMainMenu() {
        run = false;
        start = false;
        clearStacks();
        StartMenu.active = true;
        EnemyGenerator.progress = 0;
        EnemyGenerator.firtEnemy = true;
        EnemyGenerator.generateTimer = 0; 
        Loot.closeDelay = false;
        Wheel.numberOfSpins = 0;
        WheelHandler.confetti.clear();
        WheelHandler.active = false;
        WheelHandler.allowSpin = false;
        WheelHandler.resetButtons();
        WheelHandler.ensureCashIn();
        GameAbilityHandler.closeOnReturn();
        PrizeDisplayer.reset();
        DarknessFilter.darken = false;
        DarknessFilter.timer = 0;
        StatSaver.acronMagazine = StatSaver.actualMag;
        MenuAndStatsHandler.closeMenu();
        GameAbilityHandler.reset();
        Revive.reset();
        ButtonHandler.reset();
        FlyCondition.resetCondition();
    }

    public static void clearStacks() {
        CollisionEffects.drawBubbles = false; 
        MysteryBoxGenerator.boxes.clear();
        MysteryBoxGenerator.confetti.clear();
        MapAndPlayerHandler.acorns.clear();
        EnemyGenerator.layerOne.clear();
        EnemyGenerator.mud.clear();
        EnemyGenerator.ene.clear();
        LootHandler.icons.clear();
        MenuAndStatsHandler.icons.clear();
        NutGenerator.nuts.clear();
        NutGenerator.berries.clear();
        MenuAndStatsHandler.lives.clear();
    }
}
