/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Console;

import GameMenus.CloseLabel;
import Toolkit.StatSaver;
import Toolkit.GraphicLoader;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class Console {

    private static int x;
    private static int y;
    public static int timer;
    private static int flashTimer;
    private static boolean shiftPressed = false;
    private static boolean tabPressed = false;
    public static boolean active = false;
    private static boolean flashMark = false;
    private static BufferedImage[] console;
    private static BufferedImage[] shadow;
    private static Stack<ConsoleChar> letters = new Stack<>();

    public Console(int x, int y) {
        this.x = x;
        this.y = y;
        console = GraphicLoader.console; 
        shadow = GraphicLoader.consoleShadow;
    }

    public static void check() {
        if (active) {
            timer++;
            if (timer > 700) {
                active = false;
                timer = 0;
                letters.clear();
            }
            flashTimer++;
            if (flashTimer > 10) {
                flashMark = !flashMark;
                flashTimer = 0;
            }
        }
    }

    private static void executeCommand(String command, int value) {
        String target = getTarget(command);
        if (target.equals("nut")) {
            StatSaver.nuts = value;
        } else if (target.equals("acorn")) {
            StatSaver.actualMag = value;
        } else if (target.equals("berries")) {
            StatSaver.berries = value;
        }
    }

    private static String getTarget(String command) {
        String target = "";
        if (command.charAt(0) == '/' && command.charAt(1) == '/') {
            for (int i = 2; i < command.length(); i++) {
                if (Character.isLetter(command.charAt(i))) {
                    target += command.charAt(i);
                } else {
                    break;
                }
            }
        }
        return target;
    }

    private static boolean commandVaild(String command, int endIndex) {
        boolean vaild = false;
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(0) == '/' && command.charAt(1) == '/') {
                if (command.charAt(i) == '=' && command.charAt(endIndex + 1) == '/' && command.charAt(i) == '=' && command.charAt(endIndex + 2) == '/') {
                    vaild = true;
                }
            }
        }
        return vaild;
    }

    private static void commandChange(Stack<ConsoleChar> com) {
        StringBuilder sb = new StringBuilder();
        String value = "";
        int change;
        int startIndex = -1;
        int endIndex = -1;
        int tempEnd;
        for (int i = 0; i < com.size(); i++) {
            sb.append(com.get(i).getLetter());
        }
        String command = sb.toString();
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == '(') {
                startIndex = i + 1;
            } else if (command.charAt(i) == ')') {
                endIndex = i;
            }
        }
        tempEnd = endIndex + 1;
        if (command.length() - tempEnd == 2) {
            if (endIndex != -1 && startIndex != -1 && commandVaild(command, endIndex)) {
                for (int i = startIndex; i < endIndex; i++) {
                    value += command.charAt(i);
                }
                change = Integer.parseInt(value);
                executeCommand(command, change);
            }
        }
    }

    public static void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (!CloseLabel.active) {
            if (keyCode == KeyEvent.VK_SHIFT) {
                shiftPressed = true;
            } else if (keyCode == KeyEvent.VK_ALT) {
                tabPressed = true;
            }

            if (shiftPressed && tabPressed) {
                active = true;
                timer = 0;
            }
            if (active) {
                ConsoleChar consoleChar;
                if (Character.isDigit(keyCode) || Character.isLetter(keyCode) || keyCode == '/' || keyCode == '(' || keyCode == ')' || keyCode == '=') {
                    if (letters.isEmpty()) {
                        consoleChar = new ConsoleChar(5, 40, "" + e.getKeyChar());
                        letters.push(consoleChar);
                    } else {
                        consoleChar = new ConsoleChar(letters.lastElement().getX() + letters.lastElement().getCharLength(), 40, "" + e.getKeyChar());
                        if (letters.lastElement().getX() < 300) {
                            letters.push(consoleChar);
                        }
                    }

                } else if (keyCode == KeyEvent.VK_BACK_SPACE && !letters.isEmpty()) {
                    letters.pop();
                } else if (keyCode == KeyEvent.VK_ENTER) {
                    commandChange(letters);
                    letters.clear();
                    active = false;
                }
            }
        }
    }

    public static void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        } else if (keyCode == KeyEvent.VK_ALT) {
            tabPressed = false;
        }
    }

    private static void setFontAndColor(Graphics2D g2d, int size, Color color) {
        Font font = new Font("1", Font.PLAIN, size);
        g2d.setFont(font);
        g2d.setColor(color);
    }

    public static void renderConsole(Graphics2D g2d) {
        if (active) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
            g2d.drawImage(shadow[0], x, y - 5, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
            g2d.drawImage(console[0], x, y, null);
            setFontAndColor(g2d,25,Color.white);
            if (flashMark&&letters.isEmpty()) {
                g2d.drawString("|", 5, 37);
            }
            synchronized (letters) {
                for (ConsoleChar letter : letters) {
                    letter.renderChar(g2d);
                }
            }
        }
    }

}
