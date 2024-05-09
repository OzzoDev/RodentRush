/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import GameMenus.CloseLabel;
import GameMenus.MenuAndStatsHandler;
import PrizeWheel.WheelHandler;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author oscar
 */
public class Game implements Runnable {

    private Panel panel;
    private Window window;
    private Thread gameThread;
    private final int FPS_SET = 40;

    public Game() {
        panel = new Panel();
        window = new Window(panel);
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !WheelHandler.lock) {
                    for (int i = 0; i < MenuAndStatsHandler.buttons.size(); i++) {
                        MenuAndStatsHandler.buttons.get(i).setActive(true);
                        CloseLabel.active = true;
                        DeathHandler.run = false;
                    }
                }
            }
        });
        panel.requestFocus();
        startGame();
    }

    private void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();
        int frames = 0;
        long lastCheck = System.currentTimeMillis();
        while (true) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                long start = System.nanoTime();
                panel.update();
                long end = System.nanoTime();
                long passed = (end - start);
                panel.repaint();
                lastFrame = now;
                frames++;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
//                System.out.println("FPS " + frames);
                frames = 0;
            }

        }

    }
}
