package com.neet.GameState;

import com.neet.Entity.Enemies.Pterodactyl;
import com.neet.Entity.Enemies.Turtle;
import com.neet.Entity.*;
import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;
import com.neet.TileMap.Background;
import com.neet.TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;



public class Level1BState extends GameState {
	
	private Background temple;
	
	private Player player;
	private TileMap tileMap;
	private ArrayList<Enemy> enemies;

	
	private HUD hud;
	
	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;
	
	public Level1BState(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		
		// backgrounds
		temple = new Background("Resource2/Backgrounds/temple.gif", 0.5, 0);
		
		// tilemap
		tileMap = new TileMap(35);
		tileMap.loadTiles("Resource2/Tilesets/");
		tileMap.loadMap("Resources/Maps/State2");
		tileMap.setPosition(140, 0);
		tileMap.setTween(1);
		
		// player
		player = new Player(tileMap);
		player.setPosition(300, 131);
		player.setHealth(PlayerSave.getHealth());
		player.setLives(PlayerSave.getLives());
		player.setTime(PlayerSave.getTime());
		
		// enemies
		enemies = new ArrayList<Enemy>();
		populateEnemies();
		
		
		player.init(enemies);
		

		
		// hud
		hud = new HUD(player);
		
		
		// start event
		eventStart = true;
		tb = new ArrayList<Rectangle>();
		eventStart();
		
		
	}
	
	private void populateEnemies() {
		enemies.clear();
		Turtle tt;
		Pterodactyl g;
		
		tt = new Turtle(tileMap, player);
		tt.setPosition(750, 100);
		enemies.add(tt);
		tt = new Turtle(tileMap, player);
		tt.setPosition(900, 150);
		enemies.add(tt);
		tt = new Turtle(tileMap, player);
		tt.setPosition(1320, 250);
		enemies.add(tt);
		tt = new Turtle(tileMap, player);
		tt.setPosition(1570, 160);
		enemies.add(tt);
		tt = new Turtle(tileMap, player);
		tt.setPosition(1590, 160);
		enemies.add(tt);
		tt = new Turtle(tileMap, player);
		tt.setPosition(2600, 370);
		enemies.add(tt);
		tt = new Turtle(tileMap, player);
		tt.setPosition(2620, 370);
		enemies.add(tt);
		tt = new Turtle(tileMap, player);
		tt.setPosition(2640, 370);
		enemies.add(tt);
		
		g = new Pterodactyl(tileMap);
		g.setPosition(904, 130);
		enemies.add(g);
		g = new Pterodactyl(tileMap);
		g.setPosition(1080, 270);
		enemies.add(g);
		g = new Pterodactyl(tileMap);
		g.setPosition(1200, 270);
		enemies.add(g);
		g = new Pterodactyl(tileMap);
		g.setPosition(1704, 300);
		enemies.add(g);
		
		
	}
	
	public void update() {
		
		// check keys
		handleInput();

		
		// check if end of level event should start
		if(player.getx() >= 2850) {
			eventFinish = blockInput = true;
		}
		
		// play events
		if(eventStart) eventStart();
		if(eventDead) eventDead();
		if(eventFinish) eventFinish();
		

		
		// move backgrounds
		temple.setPosition(tileMap.getx(), tileMap.gety());
		
		// update player
		player.update();
		if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}
		
		// update tilemap
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		tileMap.fixBounds();
		
		// update enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
			}
		}

	}
	
	public void draw(Graphics2D g) {
		
		// draw background
		temple.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		// draw player
		player.draw(g);
		

		
		// draw hud
		hud.draw(g);
		
		
		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}
		
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
		if(blockInput || player.getHealth() == 0) return;
		player.setLeft(Keys.keyState[Keys.LEFT]);
		player.setRight(Keys.keyState[Keys.RIGHT]);
		player.setJumping(Keys.keyState[Keys.SPACE]);
		if(Keys.isPressed(Keys.BUTTON3)) player.setAttacking();
		if(Keys.isPressed(Keys.BUTTON4)) player.setCharging();
	}

///////////////////////////////////////////////////////
//////////////////// EVENTS
///////////////////////////////////////////////////////
	
	// reset level
	private void reset() {
		player.reset();
		player.setPosition(300, 131);
		populateEnemies();
		blockInput = true;
		eventCount = 0;
		eventStart = true;
		eventStart();

	}

	// level started
	private void eventStart() {
		eventStart = blockInput = false;
	}

	// player has died
	private void eventDead() {
		if(player.getLives() <= 0) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
		else {
			eventDead = blockInput = false;
			eventCount = 0;
			player.loseLife();
                        reset();
		}
	}

	// finished level
	private void eventFinish() {
		PlayerSave.setHealth(player.getHealth());
		PlayerSave.setLives(player.getLives());
		PlayerSave.setTime(player.getTime());
		gsm.setState(GameStateManager.MENUSTATE);
	}

}