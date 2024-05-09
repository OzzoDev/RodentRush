/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameAbilities;

import java.util.Stack;

/**
 *
 * @author oscar
 */
public class Perks {

    public static int waterTimer = 0;
    private int waterDelay = 370;
    public static int nutBonus = 0;
    public static int factor = 1;
    public static Stack<Integer> acronBouns = new Stack<>();
    public static boolean waterCollision = false;

    public Perks() {
        acronBouns.clear();
        acronBouns.push(10);
        acronBouns.push(20);
        acronBouns.push(30);
        acronBouns.push(40);
        acronBouns.push(50);
        acronBouns.push(100);
    }

    public static void changeCollect(int x, boolean reset) {
        if (!reset) {
            for (int i = 0; i < acronBouns.size(); i++) {
                int updatedValue = acronBouns.get(i) + x;
                acronBouns.set(i, updatedValue);
            }
            factor += x;
        } else {
            acronBouns.clear();
            acronBouns.push(10);
            acronBouns.push(20);
            acronBouns.push(30);
            acronBouns.push(40);
            acronBouns.push(50);
            acronBouns.push(100);
            factor = 1;
        }
    }

    public void check() {
        if (waterCollision) {
            waterTimer++;
            if (waterTimer > waterDelay) {
                waterCollision = false;
                waterTimer = 0;
            }
        }
    }
}
