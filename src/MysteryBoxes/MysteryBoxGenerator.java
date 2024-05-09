/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MysteryBoxes;

import Game.DeathHandler;
import GameAbilities.GameAbilityHandler;
import Toolkit.StatSaver;
import GameMenus.Revive;
import Player.Player;
import Toolkit.GraphicLoader;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class MysteryBoxGenerator {

    private LootHandler lootHandler;
    private int conX;
    private int conY;
    private int timer;
    private int delay = 100;
    private boolean allowConfetti = false;
    public static Stack<Confetti> confetti = new Stack<>();
    public static Stack<MysteryBox> boxes = new Stack<>();
    private BufferedImage[] conf;

    public MysteryBoxGenerator() {
        lootHandler = new LootHandler();
    }

    public void update() {
        if (DeathHandler.run && !GameAbilityHandler.boosterInUse()) {
            for (MysteryBox boxe : boxes) {
                boxe.update();
            }
            synchronized (confetti) {
                for (Confetti c : confetti) {
                    if (!Revive.active) {
                        c.update();
                    }
                }
            }
            removeConfetti();
            lootHandler.check();
            remove();
        }
    }

    public void updateGenerate() {
        if (DeathHandler.run&& !GameAbilityHandler.boosterInUse()) {
            timer++;
            generateConfetti();
            if (timer > delay) {
                timer = 0;
                MysteryBox box = new MysteryBox();
                boxes.push(box);
                delay = (int) (190 + Math.random() * 110);
            }
        }
    }

    public void remove() {
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).getRect().intersects(Player.rect)) {
                lootHandler.getLoot();
                StatSaver.boxes++;
                setXY(boxes.get(i).getX() + 40, boxes.get(i).getY() + 40);
                allowConfetti = true;
                boxes.remove(i);
            } else if (boxes.get(i).getX() < -200) {
                boxes.remove(i);
            }
        }
    }

    private void generateConfetti() {
        if (allowConfetti && !Revive.active) {
            int max = (int) (320 + Math.random() * 130);
            for (int i = 0; i < max; i++) {
                Confetti c = new Confetti(conX, conY, GraphicLoader.prizeWheelConfetti);
                confetti.push(c);
            }
            allowConfetti = false;
        }
    }

    public void removeConfetti() {
        for (int i = 0; i < confetti.size(); i++) {
            if (confetti.get(i).getRemove()) {
                confetti.remove(i);
            }
        }
    }

    private void setXY(int x, int y) {
        this.conX = x;
        this.conY = y;
    }

    public void renderBoxes(Graphics2D g2d) {
        if (!Revive.active) {
            synchronized (confetti) {
                for (Confetti c : confetti) {
                    c.renderConfetti(g2d);
                }
            }
        }
        synchronized (boxes) {
            for (MysteryBox boxe : boxes) {
                boxe.renderBox(g2d);
            }
            lootHandler.render(g2d);
        }
    }
}
