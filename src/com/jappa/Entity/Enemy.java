package com.jappa.Entity;

import com.jappa.TileMap.TileMap;

public class Enemy extends MapObject {
	
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	protected boolean remove;
	
	protected boolean flinching;
	protected long flinchCount;
	
	public Enemy(TileMap tm) {
		super(tm);
		remove = false;
	}
	
	public boolean isDead() { return dead; }
	
	public int getDamage() { return damage; }
	
	public void hit(int damage) {
		if(dead || flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		if(dead) remove = true;
		flinching = true;
		flinchCount = 0;
	}
	
	public void update() {}
	
}














