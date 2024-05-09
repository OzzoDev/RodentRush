/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrizeWheel;

import Game.DeathHandler;
import GameAbilities.Perks;
import GameMenus.MenuAndStatsHandler;
import Nuts.NutGenerator;
import Toolkit.StatSaver;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class Loot {

    public static boolean closeDelay = false;
    public static boolean allowMaxMulti = false;
    public static Stack<Integer> multiNuts = new Stack<>();
    public static Stack<Integer> multiAcorns = new Stack<>();

    public static void check() {
        if (closeDelay) {
            if (!PrizeDisplayer.display) {
                reviveFromPrize();
            }
        }
    }

    public static void reviveFromPrize() {
        closeDelay = false;
        DeathHandler.run = true;
        DeathHandler.start = true;
        WheelHandler.active = false;
        WheelHandler.confetti.clear();
        Wheel.numberOfSpins = 0;
        MenuAndStatsHandler.createHearts(1);
        PrizeDisplayer.reset();
    }

    public static void activeDelay() {
        closeDelay = true;
    }

    public static int findMaxMultiNut() {
        int max = Integer.MIN_VALUE;
        for (int element : multiNuts) {
            if (element > max) {
                max = element;
            }
        }
        return max;
    }

    public static int findMaxMultiAcorn() {
        int max = Integer.MIN_VALUE;
        for (int element : multiAcorns) {
            if (element > max) {
                max = element;
            }
        }
        return max;
    }

    public static void setMaxValues() {
        if (allowMaxMulti) {
            if (!multiAcorns.isEmpty()&&findMaxMultiAcorn()>0) {
                Perks.changeCollect(findMaxMultiAcorn(), false);
            }
            if (!multiNuts.isEmpty()&&findMaxMultiNut()>0) {
                NutGenerator.changeCollect(findMaxMultiNut(), false);
            }
            multiNuts.clear();
            multiAcorns.clear();
            allowMaxMulti = false;
        }
    }

    public static void getLoot(int wheelIndex, int topNumber) {
        switch (wheelIndex) {
            case 0:
                switch (topNumber) {
                    case 0:
                        PrizeDisplayer.setPrize("500", "", 1);
                        StatSaver.nuts += 500;
                        break;
                    case 1:
                        PrizeDisplayer.setPrize("200", "", 1);
                        StatSaver.nuts += 200;
                        break;
                    case 2:
                        PrizeDisplayer.setPrize("5", "", 2);
                        StatSaver.actualMag += 5;
                        break;
                    case 3:
                        PrizeDisplayer.setPrize("100", "", 1);
                        StatSaver.nuts += 100;
                        break;
                    case 4:
                        PrizeDisplayer.setPrize("1K", "", 1);
                        StatSaver.nuts += 1000;
                        break;
                    case 5:
                        PrizeDisplayer.setPrize("50", "", 1);
                        StatSaver.nuts += 50;
                        break;
                    case 6:
                        PrizeDisplayer.setPrize("2", "", 2);
                        StatSaver.actualMag += 2;
                        break;
                    case 7:
                        PrizeDisplayer.setPrize("200", "", 1);
                        StatSaver.nuts += 200;
                        break;
                    case 8:
                        PrizeDisplayer.setPrize("100", "", 1);
                        StatSaver.nuts += 100;
                        break;
                    case 9:
                        PrizeDisplayer.setPrize("1", "", 0);
                        activeDelay();
                        setMaxValues();
                        break;
                    case 10:
                        PrizeDisplayer.setPrize("50", "", 1);
                        StatSaver.nuts += 50;
                        break;
                    case 11:
                        PrizeDisplayer.setPrize("2", "", 2);
                        StatSaver.actualMag += 2;
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                switch (topNumber) {
                    case 0:
                        PrizeDisplayer.setPrize("2X", "  FOR NEXT RUN", 1);
                        multiNuts.push(2);
                        break;
                    case 1:
                        PrizeDisplayer.setPrize("1K", "", 1);
                        StatSaver.nuts += 1000;
                        break;
                    case 2:
                        PrizeDisplayer.setPrize("10", "", 2);
                        StatSaver.actualMag += 10;
                        break;
                    case 3:
                        PrizeDisplayer.setPrize("5K", "", 1);
                        StatSaver.nuts += 5000;
                        break;
                    case 4:
                        PrizeDisplayer.setPrize("750", "", 1);
                        StatSaver.nuts += 750;
                        break;
                    case 5:
                        PrizeDisplayer.setPrize("1K", "", 1);
                        StatSaver.nuts += 1000;
                        break;
                    case 6:
                        PrizeDisplayer.setPrize("2X", " FOR NEXT RUN", 2);
                        multiAcorns.push(2);
                        break;
                    case 7:
                        PrizeDisplayer.setPrize("750", "", 1);
                        StatSaver.nuts += 750;
                        break;
                    case 8:
                        PrizeDisplayer.setPrize("500", "", 1);
                        StatSaver.nuts += 500;
                        break;
                    case 9:
                        PrizeDisplayer.setPrize("1", "", 0);
                        activeDelay();
                        setMaxValues();
                        break;
                    case 10:
                        PrizeDisplayer.setPrize("500", "", 1);
                        StatSaver.nuts += 500;
                        break;
                    case 11:
                        PrizeDisplayer.setPrize("20", "", 2);
                        StatSaver.actualMag += 20;
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                switch (topNumber) {
                    case 0:
                        PrizeDisplayer.setPrize("1K", "", 1);
                        StatSaver.nuts += 1000;
                        break;
                    case 1:
                        PrizeDisplayer.setPrize("10K", "", 1);
                        StatSaver.nuts += 10000;
                        break;
                    case 2:
                        PrizeDisplayer.setPrize("5", "", 2);
                        StatSaver.actualMag += 5;
                        break;
                    case 3:
                        PrizeDisplayer.setPrize("750", "", 1);
                        StatSaver.nuts += 750;
                        break;
                    case 4:
                        PrizeDisplayer.setPrize("1K", "", 1);
                        StatSaver.nuts += 1000;
                        break;
                    case 5:
                        PrizeDisplayer.setPrize("5K", "", 1);
                        StatSaver.nuts += 5000;
                        break;
                    case 6:
                        PrizeDisplayer.setPrize("75", "", 2);
                        StatSaver.actualMag += 75;
                        break;
                    case 7:
                        PrizeDisplayer.setPrize("1K", "", 1);
                        StatSaver.nuts += 1000;
                        break;
                    case 8:
                        PrizeDisplayer.setPrize("750", "", 1);
                        StatSaver.nuts += 750;
                        break;
                    case 9:
                        PrizeDisplayer.setPrize("1", "", 0);
                        activeDelay();
                        setMaxValues();
                        break;
                    case 10:
                        PrizeDisplayer.setPrize("750", "", 1);
                        StatSaver.nuts += 750;
                        break;
                    case 11:
                        PrizeDisplayer.setPrize("3X", " FOR NEXT RUN", 2);
                        multiAcorns.push(3);
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                switch (topNumber) {
                    case 0:
                        PrizeDisplayer.setPrize("50K", "", 1);
                        StatSaver.nuts += 50000;
                        break;
                    case 1:
                        PrizeDisplayer.setPrize("5K", "", 1);
                        StatSaver.nuts += 5000;
                        break;
                    case 2:
                        PrizeDisplayer.setPrize("50", "", 2);
                        StatSaver.actualMag += 50;
                        break;
                    case 3:
                        PrizeDisplayer.setPrize("4X", "  FOR NEXT RUN", 1);
                        multiNuts.push(4);
                        break;
                    case 4:
                        PrizeDisplayer.setPrize("1K", "", 1);
                        StatSaver.nuts += 1000;
                        break;
                    case 5:
                        PrizeDisplayer.setPrize("1", "", 0);
                        activeDelay();
                        setMaxValues();
                        break;
                    case 6:
                        PrizeDisplayer.setPrize("4X", " FOR NEXT RUN", 2);
                        multiAcorns.push(4);
                        break;
                    case 7:
                        PrizeDisplayer.setPrize("1K", "", 1);
                        StatSaver.nuts += 1000;
                        break;
                    case 8:
                        PrizeDisplayer.setPrize("2K", "", 1);
                        StatSaver.nuts += 2000;
                        break;
                    case 9:
                        PrizeDisplayer.setPrize("1", "", 0);
                        activeDelay();
                        setMaxValues();
                        break;
                    case 10:
                        PrizeDisplayer.setPrize("1K", "", 1);
                        StatSaver.nuts += 1000;
                        break;
                    case 11:
                        PrizeDisplayer.setPrize("100", "", 2);
                        StatSaver.actualMag += 100;
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

}
