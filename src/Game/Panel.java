/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import GameAbilities.GameAbilityHandler;
import GameMenus.Button;
import GameMenus.CloseLabel;
import StartMenus.Console.Console;
import StartMenus.Main.StartMenu;
import GameMenus.MenuAndStatsHandler;
import Toolkit.StatSaver;
import GameMenus.Revive;
import MysteryBoxes.MysteryBoxGenerator;
import Nuts.NutGenerator;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import Obstacles.EnemyGenerator;
import PrizeWheel.WheelHandler;
import Toolkit.TextEditor;
import Toolkit.GraphicLoader;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author oscar
 */
public class Panel extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, Runnable {

    MapAndPlayerHandler mapAndPlayerHandler;
    Collision collision;
    TextEditor graphics;
    EnemyGenerator enemyGenerator;
    CollisionEffects collisionEffects;
    NutGenerator nutGenerator;
    MenuAndStatsHandler menuHandler;
    GameAbilityHandler abilityHandler;
    DeathHandler deathHandler;
    MysteryBoxGenerator mysteryBoxGenerator;
    WheelHandler wheelHandler;
    StartMenu startMenu;
    StatSaver recoverStats;
    private final int FPS_SET = 40;
    private Thread gameThread;
    private boolean start = false;

    public Panel() {
        setFocusable(true);
        this.setPreferredSize(new Dimension(1920, 1080));
        this.setDoubleBuffered(true);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        GraphicLoader.loadGameGraphic();
        startThread();
        startMenu = new StartMenu();
        recoverStats = new StatSaver();
        mapAndPlayerHandler = new MapAndPlayerHandler();
        collision = new Collision();
        graphics = new TextEditor();
        enemyGenerator = new EnemyGenerator();
        collisionEffects = new CollisionEffects();
        nutGenerator = new NutGenerator();
        menuHandler = new MenuAndStatsHandler();
        abilityHandler = new GameAbilityHandler();
        deathHandler = new DeathHandler();
        mysteryBoxGenerator = new MysteryBoxGenerator();
        wheelHandler = new WheelHandler();
        start = true;
    }

    public void generating() {
        if (start) {
            enemyGenerator.updateGenerate();
            mysteryBoxGenerator.updateGenerate();
            WheelHandler.generateConfetti();
        }
    }

    public void update() {
        Window.hideCursor();
        mapAndPlayerHandler.update();
        collision.collisonHandler();
        enemyGenerator.enemiesHandler();
        collisionEffects.effects();
        nutGenerator.update();
        menuHandler.check();
        abilityHandler.check();
        deathHandler.check();
        mysteryBoxGenerator.update();
        wheelHandler.update();
        startMenu.check();
        StatSaver.check();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        mapAndPlayerHandler.renderMap(g2d);
        nutGenerator.renderNuts(g2d);
        mysteryBoxGenerator.renderBoxes(g2d);
        enemyGenerator.renderWaterStone(g2d);
        mapAndPlayerHandler.renderPlayer(g2d);
        collisionEffects.render(g2d);
        enemyGenerator.renderEnemies(g2d);
        nutGenerator.renderBerries(g2d);
        mapAndPlayerHandler.renderCover(g2d);
        collisionEffects.renderFilter(g2d);
        Revive.render(g2d);
        menuHandler.render(g2d);
        abilityHandler.renderIcon(g2d);
        wheelHandler.render(g2d);
        startMenu.renderMainMeny(g2d);
        Console.renderConsole(g2d);
        menuHandler.renderClose(g2d);
        g2d.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        wheelHandler.keyPressed(e);
        abilityHandler.keyPressed(e);
        StartMenu.keyPressed(e);
        mapAndPlayerHandler.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        mapAndPlayerHandler.keyReleased(e);
        StartMenu.KeyReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (Button button : MenuAndStatsHandler.buttons) {
            button.mousePressed(e);
        }
        startMenu.mousePressed(e);
        wheelHandler.mousePressed(e);
        abilityHandler.mousePressed(e);
        CloseLabel.mousePressed(e);
        Revive.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Window.makeCursorVisible(e);
        for (Button button : MenuAndStatsHandler.buttons) {
            button.mouseMoved(e);
        }
        startMenu.mouseMoved(e);
        wheelHandler.mouseMoved(e);
        abilityHandler.mouseMoved(e);
        CloseLabel.mouseMoved(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        startMenu.mouseWheelMoved(e);
    }

    private void startThread() {
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
                generating();
                long end = System.nanoTime();
                long passed = (end - start);
                lastFrame = now;
                frames++;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                frames = 0;
            }
        }
    }
}
