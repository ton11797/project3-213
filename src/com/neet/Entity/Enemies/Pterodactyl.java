package com.neet.Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.neet.Entity.Enemy;
import com.neet.Handlers.Content;
import com.neet.ModeGame.ModeGame;
import com.neet.TileMap.TileMap;


public class Pterodactyl extends Enemy {
	
	private BufferedImage[] idleSprites;
	
	private int tick;
	private double a;
	private double b;
	
	public Pterodactyl(TileMap tm) {
		
		super(tm);
		
		health = maxHealth = 2;
		
		width = 504/8;
		height = 452/8;
		cwidth = 504/10;
		cheight = 452/10;
		
		damage = ModeGame.damage;
		moveSpeed = 5;
		
		idleSprites = Content.Pterodactyl[0];
		
		animation.setFrames(idleSprites);
		animation.setDelay(4);
		
		tick = 0;
		a = Math.random() * 0.06 + 0.07;
		b = Math.random() * 0.06 + 0.07;
		
	}
	
	public void update() {
		
		// check if done flinching
		if(flinching) {
			flinchCount++;
			if(flinchCount == 6) flinching = false;
		}
		
		tick++;
		x = Math.sin(a * tick) + x;
		y = Math.sin(b * tick) + y;
		
		// update animation
		animation.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		if(flinching) {
			if(flinchCount == 0 || flinchCount == 2) return;
		}
		
		super.draw(g);
		
	}
	
}
