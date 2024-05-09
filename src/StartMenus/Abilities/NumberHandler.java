/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Abilities;

import java.util.Stack;

/**
 *
 * @author oscar
 */
public class NumberHandler {

    public Stack<Integer> itemsBought = new Stack<>();
    public Stack<Integer> buttonConfiguration = new Stack<>();

    public NumberHandler() {
        for (int i = 0; i < 4; i++) {
            itemsBought.push(0);
        }
        for (int i = 0; i < 2; i++) {
            buttonConfiguration.push(0);
        }
    }
}
