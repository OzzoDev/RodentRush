/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StartMenus.Abilities;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author oscar
 */
public class BuyAnimation {
 
    private int x; 
    private int y; 
    private int width; 
    private int height; 
    private int angle = 0; 
    private double angleSpeed = 4; 
    private boolean remove = false; 
    private BufferedImage[] circle; 
    
    public BuyAnimation(int x, int y, int width, int height,BufferedImage[] circle){
        this.x=x; 
        this.y=y; 
        this.width=width; 
        this.height=height; 
        this.circle=circle; 
    }
    
    public void update(){
        angleSpeed*=1.03; 
        angle+=angleSpeed;  
        if(angle>=360){
            remove = true;
        }
    }
    
    public boolean getRemove(){
        return remove; 
    }
    
    public void renderAnimation(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();
        double centerX = x + width / 2;
        double centerY = y + height / 2;
        AffineTransform transform = new AffineTransform();
        transform.translate(centerX, centerY);
        transform.rotate(Math.toRadians(angle));
        transform.translate(-width / 2.0, -height / 2.0);
        g2d.setTransform(transform);
        g2d.drawImage(circle[0], 0, 0, null);
        g2d.setTransform(oldTransform);
    }
    
}
