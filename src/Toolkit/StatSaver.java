/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Toolkit;

import GameMenus.MenuAndStatsHandler;
import StartMenus.Abilities.AbilitySaver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.Serializable;

/**
 *
 * @author oscar
 */
public class StatSaver {

    public static int nuts;
    public static int acronMagazine;
    public static int berries;
    public static int attempts;
    public static int actualMag;
    public static int bestScore;
    public static int kills;
    public static int boxes;
    public static int totalSeconds;
    public static long totalNuts;
    public static long usedAcrons;
    public static long totalBerries;
    public static BufferedReader reader;
    public static BufferedWriter writer;
    public static File file;

    public StatSaver() {
        file = new File("stats.txt");
        read();
        AbilitySaver.read();
    }

    public static void check() {
        if (MenuAndStatsHandler.meters > bestScore) {
            bestScore = MenuAndStatsHandler.meters;
        }
    }

    public void read() {
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            if (line != null) {
                nuts = Integer.parseInt(line);
            }
            line = reader.readLine();
            if (line != null) {
                acronMagazine = Integer.parseInt(line);
                actualMag = acronMagazine;
            }
            line = reader.readLine();
            if (line != null) {
                berries = Integer.parseInt(line);
            }
            line = reader.readLine();
            if (line != null) {
                attempts = Integer.parseInt(line);
            }
            line = reader.readLine();
            if (line != null) {
                bestScore = Integer.parseInt(line);
            }
            line = reader.readLine();
            if (line != null) {
                kills = Integer.parseInt(line);
            }
            line = reader.readLine();
            if (line != null) {
                boxes = Integer.parseInt(line);
            }
            line = reader.readLine();
            if (line != null) {
                totalSeconds = Integer.parseInt(line);
            }
            line = reader.readLine();
            if (line != null) {
                usedAcrons = Long.parseLong(line);
            }
            line = reader.readLine();
            if (line != null) {
                totalNuts = Long.parseLong(line);
            }
            line = reader.readLine();
            if (line != null) {
                totalBerries = Long.parseLong(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write() {
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(String.valueOf(nuts));
            writer.write("\n" + String.valueOf(acronMagazine));
            writer.write("\n" + String.valueOf(berries));
            writer.write("\n" + String.valueOf(attempts));
            writer.write("\n" + String.valueOf(bestScore));
            writer.write("\n" + String.valueOf(kills));
            writer.write("\n" + String.valueOf(boxes));
            writer.write("\n" + String.valueOf(totalSeconds));
            writer.write("\n" + String.valueOf(usedAcrons));
            writer.write("\n" + String.valueOf(totalNuts));
            writer.write("\n" + String.valueOf(totalBerries));
            AbilitySaver.write(writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
