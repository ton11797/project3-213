package com.jappa.GameState;

import com.jappa.Entity.Enemies.Pterodactyl;
import com.jappa.Entity.Enemies.Turtle;
import com.jappa.Entity.Enemy;
import com.jappa.Entity.HUD;
import com.jappa.Entity.Player;
import com.jappa.Entity.PlayerSave;
import com.jappa.Handlers.Keys;
import com.jappa.Main.GamePanel;
import com.jappa.TileMap.Background;
import com.jappa.TileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;



public class Level2State extends GameState {
	
	private Background temple;
	
	private Player player;
	private TileMap tileMap;
	private ArrayList<Enemy> enemies;

	
	private HUD hud;
	
	// events
	private boolean eventFinish;
	private boolean eventDead;
	
	public Level2State(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		
		// backgrounds
		temple = new Background("Resources/Backgrounds/temple.gif", 0.5, 0);
		
		// tilemap
		tileMap = new TileMap(35);
		tileMap.loadTiles("Resources/Tilesets/");
		tileMap.loadMap("Resources/Maps/State4");
		tileMap.setPosition(140, 0);
		tileMap.setTween(1);
		
		// player
		player = new Player(tileMap);
		player.setPosition(50, 450);
		player.setHealth(PlayerSave.getHealth());
		player.setLives(PlayerSave.getLives());
		player.setTime(PlayerSave.getTime());
		
		// enemies
		enemies = new ArrayList<Enemy>();
		populateEnemies();
		
		
		player.init(enemies);
		
		// hud
		hud = new HUD(player);

		
	}
	
	private void populateEnemies() {
		enemies.clear();
		Turtle gp;
		Pterodactyl g;
		
		gp = new Turtle(tileMap, player);
		gp.setPosition(300, 400);
		enemies.add(gp);
		gp = new Turtle(tileMap, player);
		gp.setPosition(450, 400);
		enemies.add(gp);
		gp = new Turtle(tileMap, player);
		gp.setPosition(700, 400);
		enemies.add(gp);
		gp = new Turtle(tileMap, player);
		gp.setPosition(650, 450);
		enemies.add(gp);
		gp = new Turtle(tileMap, player);
		gp.setPosition(800, 450);
		enemies.add(gp);
		gp = new Turtle(tileMap, player);
		gp.setPosition(900, 460);
		enemies.add(gp);
		gp = new Turtle(tileMap, player);
		gp.setPosition(1000, 370);
		enemies.add(gp);
		gp = new Turtle(tileMap, player);
		gp.setPosition(1520, 300);
		enemies.add(gp);
		
		g = new Pterodactyl(tileMap);
		g.setPosition(1000, 420);
		enemies.add(g);
		g = new Pterodactyl(tileMap);
		g.setPosition(1300, 450);
		enemies.add(g);
		g = new Pterodactyl(tileMap);
		g.setPosition(1400, 350);
		enemies.add(g);
		g = new Pterodactyl(tileMap);
		g.setPosition(1704, 300);
		enemies.add(g);
		
		
	}
	
	public void update() {
		
		// check keys
		handleInput();
		// check if end of level event should start
		if(player.getx() <=1200&& player.gety()<=130) {
			eventFinish = true;
		}
		
		// play events
		if(eventDead) eventDead();
		if(eventFinish) eventFinish();

		// move backgrounds
		temple.setPosition(tileMap.getx(), tileMap.gety());
		
		// update player
		player.update();
		if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = true;
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
		
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
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
		player.setPosition(80, 161);
		populateEnemies();
	}

	// player has died
	private void eventDead() {
		if(player.getLives() <= 0) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
		else {
			eventDead = false;
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