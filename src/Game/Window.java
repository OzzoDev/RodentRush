/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import Toolkit.GraphicLoader;
import Toolkit.StatSaver;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 *
 * @author oscar
 */
public class Window {

    public static JFrame jframe;
    public static int cursorTimer = 0;
    private static boolean mouseIsMoving = true;
    private static BufferedImage mousePointer = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    private static Cursor blankPointer = Toolkit.getDefaultToolkit().createCustomCursor(mousePointer, new Point(0, 0), "invisible");

    public Window(Panel panel) {
        jframe = new JFrame();
        BufferedImage icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        icon.setRGB(0, 0, new Color(0, 0, 0, 0).getRGB());
        jframe.setIconImage(GraphicLoader.gameIcon);
        jframe.setUndecorated(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(true);
        jframe.add(panel);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        jframe.getContentPane().setCursor(blankPointer);
    }

    public static void makeCursorVisible(MouseEvent e) {
        if (!mouseIsMoving) {
            Window.jframe.getContentPane().setCursor(Cursor.getDefaultCursor());
            mouseIsMoving = true;
            cursorTimer = 0;
        }
    }

    public static void hideCursor() {
        if (mouseIsMoving) {
            cursorTimer++;
            if (cursorTimer > 20) {
                Window.jframe.getContentPane().setCursor(blankPointer);
                mouseIsMoving = false;
                cursorTimer = 0;
            }
        }
    }

    public static void close() {
        StatSaver.acronMagazine = StatSaver.actualMag;
        StatSaver.write();
        jframe.dispatchEvent(new WindowEvent(jframe, WindowEvent.WINDOW_CLOSING));
    }

}
