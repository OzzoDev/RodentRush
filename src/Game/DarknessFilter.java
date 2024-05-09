/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

/**
 *
 * @author OscBurT21
 */
import java.awt.*;

public class DarknessFilter {

    private int alphaTimer = 0;
    public static int timer = 0;
    public static float alpha = 0.2F;
    public static final float MAX_ALPHA = 0.78F;
    private final float MIN_ALPHA = 0.2F;
    private boolean increase = true;
    private boolean decrease = false;
    public static boolean darken = false;

    public void update() {
        if (darken) {
            timer++;
            alphaTimer++;
            if (alphaTimer > 16) {
                alphaLimit();
                if (increase) {
                    alpha += 0.05F;
                } else if (decrease) {
                    alpha -= 0.05F;
                }
                alphaTimer = 0;
            }
            if (timer > 700) {
                decrease = true;
            }

            if (alpha == 0.2F && decrease) {
                darken = false;
                timer = 0;
            }

            if (!darken) {
                alpha = MIN_ALPHA;
                increase = true;
                decrease = false;
            }
        }
    }

    public void alphaLimit() {
        if (alpha >= MAX_ALPHA && increase) {
            increase = false;
            alpha = MAX_ALPHA;
        } else if (alpha <= MIN_ALPHA && decrease) {
            decrease = false;
            alpha = MIN_ALPHA;
        }
    }

    public void renderFilter(Graphics2D g2d) {
        if (darken) {
            g2d.setColor(Color.black);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.fillRect(-20, -20, 2020, 1120);
        }
    }
}
