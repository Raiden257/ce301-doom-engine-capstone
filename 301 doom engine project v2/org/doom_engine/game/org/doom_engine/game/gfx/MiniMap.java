package org.doom_engine.game.gfx;

import org.doom_engine.game.entities.Player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.doom_engine.game.Level.Level;
import org.doom_engine.game.Game;



public class MiniMap {
	
	public int width;
    public int height;
    public static int[] pixels;
    public Game game;
    public BufferedImage image1 ,image2= new BufferedImage(640, 480,BufferedImage.TYPE_INT_RGB);
    
    
    public MiniMap(int width, int height) {
    	this.width=width;
    	this.height=height;
    	pixels= new int[width*height];
    	 game= new Game();
    	 Graphics2D g2d= image2.createGraphics();
    	 g2d.setColor(Color.YELLOW);
    	 g2d.fillRect(0, 0, 640, 480);
    }
    
   
    public void renderMiniMap() {
    	try {
			image1 = ImageIO.read(Level.class.getResourceAsStream("/levels/level0.png"));
			for(int y=0; y<128; y++) {
				for(int x=0;x<128;x++) {
					pixels[x+y*width]=image1.getRGB(x, y);
				}
			}
			image1.setRGB(111, 21, Color.black.getRGB());
			image1.setRGB(Player.x1, Player.y1, Color.red.getRGB());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

}
