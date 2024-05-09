/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Player;

import Game.CollisionEffects;
import Game.DeathHandler;
import Game.MapAndPlayerHandler;
import GameAbilities.GameAbilityHandler;
import GameMenus.CloseLabel;
import GameMenus.MenuAndStatsHandler;
import Toolkit.StatSaver;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 *
 * @author OscBurT21
 */
public class Player {

    public static int x;
    public static int y;
    private int width;
    private int height;
    private final int MIN_Y = -70;
    public static int maxY;
    public static int angle = 0;
    private static int timer = 0;
    private static int smokeTimer = 0;
    private static int currentImage = 0;
    private static int minIndex = 0;
    private static int maxIndex = 6;
    public static double speedY;
    public static double orgSpeedY;
    public static boolean up;
    public static boolean down;
    private static boolean decreaseAngle = false;
    private static boolean increaseAngle = false;
    private static boolean pressed;
    private static boolean allowAngleChange = true;
    private static boolean draw = true;
    public static boolean colWithWater = false;
    public static boolean blinded = false;
    private static boolean boost = true;
    private BufferedImage[] player;
    private BufferedImage[] smokeImage;
    public static Rectangle rect;
    public static Rectangle waterRect;
    private static Stack<Smoke> smoke = new Stack<>();

    public Player(int x, int y, int width, int height, double speedY, BufferedImage[] player, BufferedImage[] smokeImage) {
        Player.x = x;
        Player.y = y;
        Player.speedY = speedY;
        orgSpeedY = speedY;
        this.width = width;
        this.height = height;
        maxY = y;
        this.player = player;
        this.smokeImage = smokeImage;
        rect = new Rectangle(x + 30, y + 90, width - 50, height - 130);
        waterRect = new Rectangle(x + 3 * (width / 4), y + 90, width / 4, height - 130);
    }

    private void createSmoke() {
        Smoke tempSmoke;
        if (up || y == MIN_Y || y == 300) {
            smokeTimer++;
            if (smokeTimer > 6) {
                int yOffset = 10 + (int) (Math.random() * 3) * 10;
                int xOffset = 70 + (int) (Math.random() * 4) * 10;
                tempSmoke = new Smoke(x - xOffset, y - yOffset, 300, smokeImage);
                smoke.push(tempSmoke);
                smokeTimer = 0;
            }
        }
    }

    private void animateImage() {
        timer++;
        if (timer > 4) {
            currentImage++;
            if (currentImage == maxIndex) {
                currentImage = minIndex;
            }
            timer = 0;
        }
    }

    private void rotate() {
        if (allowAngleChange) {
            if (up || y == 300) {
                if (angle <= -45) {
                    decreaseAngle = false;
                    increaseAngle = true;
                }
                if (angle > 0 && increaseAngle) {
                    decreaseAngle = false;
                    increaseAngle = false;
                    allowAngleChange = false;
                }
            }
        }
        if (increaseAngle) {
            angle += 2;
        } else if (decreaseAngle) {
            angle -= 2;
        }

    }

    public void update() {
        if (DeathHandler.run && !CloseLabel.active) {
            if (CollisionEffects.startMudAnimation) {
                speedY = 1;
            }
            if (!FlyCondition.block()) {
                if (StatSaver.acronMagazine > 0) {
                    if (up) {
                        speedY *= 1.02;
                        y -= speedY;
                    } else if (down) {
                        speedY *= 1.04;
                        y += speedY;
                    }
                } else {
                    if (y < maxY) {
                        down = true;
                    }
                    if (down) {
                        speedY *= 1.04;
                        y += speedY;
                    }
                }
                if (y <= MIN_Y) {
                    draw = false;
                    y = MIN_Y;
                    draw = true;
                    speedY = orgSpeedY;
                    up = false;
                } else if (y > maxY && down) {
                    draw = false;
                    y = maxY;
                    draw = true;
                    speedY = orgSpeedY;
                    up = false;
                    down = false;
                    currentImage = 0;
                    timer = 0;
                    minIndex = 0;
                    maxIndex = 6;
                    angle = 0;
                    increaseAngle = false;
                    decreaseAngle = false;
                }
            } else {
                if (boost) {
                    up = true;
                    currentImage = 7;
                    timer = 0;
                    minIndex = 7;
                    maxIndex = 9;
                    smokeTimer = 0;
                    decreaseAngle = true;
                    increaseAngle = false;
                    allowAngleChange = true;
                    boost = false;
                }
                if (y < 300) {
                    up = false;
                    y = 300;
                    speedY = orgSpeedY;
                } else if (MenuAndStatsHandler.meters >= GameAbilityHandler.optionDisplays.get(1).boostMax) {
                    down = true;
                    currentImage = 10;
                    timer = 0;
                    minIndex = 10;
                    maxIndex = 12;
                    angle = 0;
                } else if (y > maxY && down) {
                    down = false;
                }
                if (up) {
                    speedY *= 1.02;
                    y -= speedY;
                } else if (down) {
                    speedY *= 1.02;
                    y += speedY;
                }
            }
            rect = new Rectangle(x + 30, y + 90, width - 50, height - 130);
            waterRect = new Rectangle(x + 3 * (width / 4), y + 90, width / 4, height - 130);
            rotate();
            createSmoke();
            animateImage();
            for (int i = 0; i < smoke.size(); i++) {
                if (smoke.get(i).remove) {
                    smoke.remove(i);
                } else {
                    smoke.get(i).update();
                }
            }
        }
    }

    public static void pause() {
        increaseAngle = false;
        decreaseAngle = false;
        up = false;
        down = true;
        increaseAngle = false;
        decreaseAngle = false;
        angle = 0;
        pressed = false;
        colWithWater = false;
        CollisionEffects.drawBubbles = false;
        CollisionEffects.blinded = false;
        MapAndPlayerHandler.pressed = false;
    }

    public static void reset() {
        increaseAngle = false;
        decreaseAngle = false;
        up = false;
        down = false;
        y = maxY;
        draw = true;
        speedY = orgSpeedY;
        currentImage = 0;
        timer = 0;
        minIndex = 0;
        maxIndex = 6;
        angle = 0;
        smoke.clear();
        pressed = false;
        boost = true;
        MapAndPlayerHandler.pressed = false;
    }

    public void keyPressed(KeyEvent e) {
        if (!colWithWater && e.getKeyCode() == KeyEvent.VK_SPACE && StatSaver.acronMagazine > 0 && !CloseLabel.active) {
            up = true;
            down = false;
            if (!pressed) {
                if (y == maxY) {
                    allowAngleChange = true;
                }
                if (!CollisionEffects.startMudAnimation) {
                    speedY = orgSpeedY;
                } else {
                    speedY = 1;
                }
                currentImage = 7;
                timer = 0;
                minIndex = 7;
                maxIndex = 9;
                smokeTimer = 0;
                if (allowAngleChange) {
                    decreaseAngle = true;
                    increaseAngle = false;
                }
                int yOffset = 10 + (int) (Math.random() * 3) * 10;
                int xOffset = 70 + (int) (Math.random() * 4) * 10;
                Smoke tempSmoke = new Smoke(x - xOffset, y - yOffset, 300, smokeImage);
                smoke.push(tempSmoke);
                smokeTimer = 0;
            }
            pressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (!colWithWater && e.getKeyCode() == KeyEvent.VK_SPACE && StatSaver.acronMagazine > 0) {
            up = false;
            down = true;
            if (pressed && y != maxY) {
                allowAngleChange = false;
                if (!CollisionEffects.startMudAnimation) {
                    speedY = orgSpeedY;
                } else {
                    speedY = 1;
                }
                currentImage = 10;
                timer = 0;
                minIndex = 10;
                maxIndex = 12;
                angle = 0;
            }
            increaseAngle = false;
            decreaseAngle = false;
            pressed = false;
        }
    }

    public void renderPlayer(Graphics2D g2d) {
        if (draw && DeathHandler.start) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            synchronized (smoke) {
                for (Smoke smok : smoke) {
                    smok.renderSmoke(g2d);
                }
            }
            AffineTransform oldTransform = g2d.getTransform();
            AffineTransform transform = new AffineTransform();
            transform.translate(x + width / 2, y + height / 2);
            transform.rotate(Math.toRadians(angle));
            transform.translate(-width / 2, -height / 2);
            g2d.setTransform(transform);
            g2d.drawImage(player[currentImage], 0, 0, null);
            g2d.setTransform(oldTransform);
        }
    }
}
