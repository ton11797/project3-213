package com.neet.GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.neet.Entity.Enemy;
import com.neet.Entity.HUD;
import com.neet.Entity.Player;
import com.neet.Entity.PlayerSave;
import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;
import com.neet.TileMap.Background;
import com.neet.TileMap.TileMap;

public class Level1CState extends GameState {
	
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
	private boolean eventPortal;
	private boolean flash;
	private boolean eventBossDead;
	
	public Level1CState(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		
		// backgrounds
		temple = new Background("/Backgrounds/temple.gif", 0.5, 0);
		
		// tilemap
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/ruinstileset.gif");
		tileMap.loadMap("/Maps/level1c.map");
		tileMap.setPosition(140, 0);
		tileMap.setTween(1);
		
		// player
		player = new Player(tileMap);
		player.setPosition(50, 190);
		player.setHealth(PlayerSave.getHealth());
		player.setLives(PlayerSave.getLives());
		player.setTime(PlayerSave.getTime());
		

		
		// enemies
		enemies = new ArrayList<Enemy>();
		populateEnemies();
		
		
		// init player
		player.init(enemies);
		
		// hud
		hud = new HUD(player);
		

		
		
		// start event
		eventStart = blockInput = true;
		tb = new ArrayList<Rectangle>();
		eventStart();
		
		
	}
	
	private void populateEnemies() {
		enemies.clear();
	}
	
	public void update() {
		
		// check keys
		handleInput();
		

		
		// check if player dead event should start
		if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}
		
		// play events
		if(eventStart) eventStart();
		if(eventDead) eventDead();
		if(eventFinish) eventFinish();
		if(eventPortal) eventPortal();
		if(eventBossDead) eventBossDead();
		
		// move backgrounds
		temple.setPosition(tileMap.getx(), tileMap.gety());
		
		// update player
		player.update();
		
		// update tilemap
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		tileMap.update();
		tileMap.fixBounds();
		
		// update enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead() || e.shouldRemove()) {
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
		
		// flash
		if(flash) {
			g.setColor(java.awt.Color.WHITE);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
		
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
		if(blockInput || player.getHealth() == 0) return;
		player.setUp(Keys.keyState[Keys.UP]);
		player.setLeft(Keys.keyState[Keys.LEFT]);
		player.setDown(Keys.keyState[Keys.DOWN]);
		player.setRight(Keys.keyState[Keys.RIGHT]);
		player.setJumping(Keys.keyState[Keys.BUTTON1]);
		player.setDashing(Keys.keyState[Keys.BUTTON2]);
		if(Keys.isPressed(Keys.BUTTON3)) player.setAttacking();
		if(Keys.isPressed(Keys.BUTTON4)) player.setCharging();
	}

///////////////////////////////////////////////////////
//////////////////// EVENTS
///////////////////////////////////////////////////////
	
	// reset level
	private void reset() {
		player.reset();
		player.setPosition(50, 190);
		populateEnemies();
		eventStart = blockInput = true;
		eventCount = 0;
		eventStart();
	}
	
	// level started
	private void eventStart() {
		eventCount++;
		if(eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
		}
		if(eventCount > 1 && eventCount < 60) {
			tb.get(0).height -= 4;
			tb.get(1).width -= 6;
			tb.get(2).y += 4;
			tb.get(3).x += 6;
		}
		if(eventCount == 60) {
			eventStart = blockInput = false;
			eventCount = 0;
			eventPortal = blockInput = true;
			tb.clear();
			
		}
	}
	
	// player has died
	private void eventDead() {
		eventCount++;
		if(eventCount == 1) {
			player.setDead();
			player.stop();
		}
		if(eventCount == 60) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 60) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if(eventCount >= 120) {
			if(player.getLives() == 0) {
				gsm.setState(GameStateManager.MENUSTATE);
			}
			else {
				eventDead = blockInput = false;
				eventCount = 0;
				player.loseLife();
				reset();
			}
		}
	}
	
	// finished level
	private void eventFinish() {
		eventCount++;
		if(eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 1) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if(eventCount == 60) {
			PlayerSave.setHealth(player.getHealth());
			PlayerSave.setLives(player.getLives());
			PlayerSave.setTime(player.getTime());
			gsm.setState(GameStateManager.ACIDSTATE);
		}
		
	}
	
	private void eventPortal() {
		eventCount++;
		if(eventCount == 1) {

		}
		if(eventCount >= 160 && eventCount <= 180) {
			if(eventCount % 4 == 0 || eventCount % 4 == 1) flash = true;
			else flash = false;
		}


		if(eventCount == 300) {
			player.setEmote(Player.NONE);
		}
		if(eventCount == 360) {
			flash = true;

		}
		if(eventCount == 362) {
			flash = false;

		}
		if(eventCount == 420) {
			eventPortal = blockInput = false;
			eventCount = 0;

		}
		
	}
	
	public void eventBossDead() {
		eventCount++;
		if(eventCount == 1) {
			player.stop();
			enemies.clear();
		}
		if(eventCount <= 120 && eventCount % 15 == 0) {
		}
		if(eventCount == 180) {
		}
		if(eventCount == 390) {
			eventBossDead = false;
			eventCount = 0;
			eventFinish = true;
		}
	}

}