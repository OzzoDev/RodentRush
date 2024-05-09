/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Player;

import GameAbilities.GameAbilityHandler;
import GameMenus.MenuAndStatsHandler;

/**
 *
 * @author OscBurT21
 */
public class FlyCondition {

    public static boolean up = true;
    public static boolean down;
    public static int solidY = 500;

//    public static void update() {
//        if (Player.y <= solidY) {
//            up = false;
//            System.out.println(Player.y);
//            Player.y=solidY;
//            Player.y = (int) Player.orgSpeedY;
//        } else if (MenuAndStatsHandler.meters >= GameAbilityHandler.optionDisplays.get(1).boostMax) {
//            down = true;
//        } else {
//            Player.y = solidY;
//        }
//        if (up&&Player.y<=solidY) {
//            Player.speedY *= 1.02;
//            Player.y -= Player.speedY;
//        } 
//        if (down) {
//            Player.speedY *= 1.02;
//            Player.y += Player.speedY;
//        }
//    }
    public static void resetCondition() {
        up = true;
        down = false;
    }

    public static boolean block() {
        boolean stop;
        if (GameAbilityHandler.optionDisplays.get(1).boostInUse) {
            stop = true;
        } else {
            stop = false;
        }
        return stop;
    }

    public static boolean blockAtOptionVisable() {
        boolean stop;
        if (GameAbilityHandler.optionDisplays.get(1).active) {
            stop = true;
        } else {
            stop = false;
        }
        return stop;
    }
}
