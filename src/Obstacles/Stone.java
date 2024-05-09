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
public class Stone extends Enemies {

    public Stone() {
        speed = 10;
        x = 2000;
        y = 710;
        animate = false;
        isStaticObject = true;
        isStone = true;
        image = GraphicLoader.stoneImage;
        rect = updateCollisionRectangle(x, y, 20, 40, 80, 100);
    }

    @Override
    public void update() {
        super.update();
        rect = updateCollisionRectangle(x, y, 20, 40, 80, 100);
    }

}
