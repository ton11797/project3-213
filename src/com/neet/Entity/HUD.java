package com.neet.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class HUD {
	
	private Player player;
	
	private BufferedImage heart;
	private BufferedImage life;
	
	public HUD(Player p) {
		player = p;
		try {
			heart =  ImageIO.read(new File("Resource2/HUD/meat.png"));
			life = ImageIO.read(new File("Resource2/HUD/egg.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		for(int i = 0; i < player.getHealth(); i++) {
			g.drawImage(heart, 10 + i * 15, 10, null);
		}
		for(int i = 0; i < player.getLives(); i++) {
			g.drawImage(life, 10 + i * 15, 25, null);
		}
		g.setColor(java.awt.Color.WHITE);
		g.drawString(player.getTimeToString(), 290, 15);
	}
	
}













