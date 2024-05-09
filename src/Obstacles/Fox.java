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
public class Fox extends Enemies {

    public Fox() {
        y = 700;
        x = 2000;
        speed = (int) (13 + Math.random() * 19);
        shortenList = 1;
        lives = 5;
        isStaticObject = false;
        isFox = true;
        running = true;
        image = GraphicLoader.foxImage; 
        dead = GraphicLoader.deadFoxImage; 
        rect = updateCollisionRectangle(x, y, 30, 70, 220, 100);
    }

    @Override
    public void update() {
        super.update();
        rect = updateCollisionRectangle(x, y, 30, 70, 220, 100);
    }
}
