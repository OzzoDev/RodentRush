/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Obstacles;

import Toolkit.GraphicLoader;

/**
 *
 * @author oscar
 */
public class Water extends Enemies {

    public Water(int x) {
        speed = 10;
        y = 780;
        this.x = x;
        spriteDelay = 9;
        isStaticObject = true;
        isWater = true;
        image = GraphicLoader.waterImage;
        rect = updateCollisionRectangle(x, y, 0, 0, 502, 72);
    }

    @Override
    public void update() {
        super.update();
        rect = updateCollisionRectangle(x, y, 0, 0, 502, 72);
    }
}
