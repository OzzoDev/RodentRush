/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Abilities;

import Toolkit.StatSaver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class AbilitySaver {

    private static BufferedReader reader;
    private static BufferedWriter writer;

    public static void read() {
        try {
            reader = new BufferedReader(new FileReader(StatSaver.file));
            try {
                loadAbilities();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void write(BufferedWriter writer) {
        try {
            saveSubAbilities(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveSubAbilities(BufferedWriter writer) throws IOException {
        String temp = "";
        Stack<String> tempStrings = new Stack<>();
        for (int i = 0; i < AbilityHandler.numbers.size(); i++) {
            for (int j = 0; j < AbilityHandler.numbers.get(i).itemsBought.size(); j++) {
                temp += AbilityHandler.numbers.get(i).itemsBought.get(j) + " ";
            }
            temp += AbilityHandler.numbers.get(i).buttonConfiguration.get(0) + " ";
            temp += AbilityHandler.numbers.get(i).buttonConfiguration.get(1) + " ";
            tempStrings.push(temp);
            temp = "";
        }
        writer.write("\n\n");
        for (int i = 0; i < tempStrings.size(); i++) {
            System.out.println(tempStrings.get(i));
            writer.write("\n" + tempStrings.get(i) + " ");
        }
    }

    public static boolean isInteger(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        return input.matches("\\-?\\d+");
    }

    public static void loadAbilities() throws IOException {
        AbilityHandler.fillNumbers();
        String line;
        String tempValue = "";
        int tempItemIndex = 0;
        int tempButtonIndex = 0;
        int blankCounter = 0;
        Stack<Integer> tempValues = new Stack<>();
        Stack<Integer> tempButtonConfiguration = new Stack<>();
        boolean tempSkip = true;
        reader = new BufferedReader(new FileReader(StatSaver.file));
        while ((line = reader.readLine()) != null && tempSkip) {
            if (line.trim().isEmpty()) {
                tempSkip = false;
            }
        }
        if (!tempSkip) {
            tempSkip = true;
        }

        for (int i = 0; i < AbilityHandler.numbers.size(); i++) {
            line = reader.readLine();
            blankCounter = 0;
            for (int j = 0; j < line.length(); j++) {
                if (Character.isDigit(line.charAt(j))) {
                    tempValue += line.charAt(j);
                } else {
                    if (isInteger(tempValue) && !tempValue.isEmpty()) {
                        if (blankCounter < 4) {
                            tempValues.push(Integer.valueOf(tempValue));

                        } else {
                            tempButtonConfiguration.push(Integer.valueOf(tempValue));
                        }
                        tempValue = "";
                    }
                }
                if (line.charAt(j) == ' ') {
                    blankCounter++;
                }
            }
        }
        for (int i = 0; i < AbilityHandler.numbers.size(); i++) {
            for (int j = 0; j < AbilityHandler.numbers.get(i).buttonConfiguration.size(); j++) {
                AbilityHandler.numbers.get(i).buttonConfiguration.set(j, tempButtonConfiguration.get(tempButtonIndex));
                tempButtonIndex++;
            }
        }

        for (int i = 0; i < AbilityHandler.numbers.size(); i++) {
            for (int j = 0; j < AbilityHandler.numbers.get(i).itemsBought.size(); j++) {
                AbilityHandler.numbers.get(i).itemsBought.set(j, tempValues.get(tempItemIndex));
                if (j == 0 && tempValues.get(tempItemIndex) != 0) {
                    for (int k = 0; k < tempValues.get(tempItemIndex); k++) {
                        AbilityHandler.addAbility(i, false,true);
                    }
                }
                tempItemIndex++;
            }
        }
        AbilityHandler.resetAllItems();
        AbilityHandler.recreateHub();
//        AbilityHandler.print();
        tempButtonConfiguration.clear();
        tempValues.clear();
    }
}
