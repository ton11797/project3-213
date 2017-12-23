package com.neet.GameState;

import com.neet.Entity.Enemies.Pterodactyl;
import com.neet.Entity.Enemies.Turtle;
import com.neet.Entity.*;
import com.neet.Handlers.Keys;
import com.neet.Main.GamePanel;
import com.neet.TileMap.Background;
import com.neet.TileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Level1AState extends GameState {
	
	private Background sky;
	private Background clouds;
	private Background mountains;
	
	private Player player;
	private TileMap tileMap;
	private ArrayList<Enemy> enemies;
	private ArrayList<EnemyProjectile> eprojectiles;
	
	private HUD hud;
	private BufferedImage hageonText;
	private Title title;
	private Title subtitle;
	
	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;
	
	public Level1AState(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		
		// backgrounds
		sky = new Background("Resource2/Backgrounds/bg.png", 0);
		clouds = new Background("Resources/Backgrounds/clouds.gif", 0.5);
		//mountains =new Background("Resources/Backgrounds/mountains.gif", 1);
		
		// tilemap
		tileMap = new TileMap(35);
		tileMap.loadTiles("Resource2/Tilesets/");
		tileMap.loadMap("Resource2/state1");
		tileMap.setPosition(140, 0);
		tileMap.setBounds(
			tileMap.getWidth() - 1 * tileMap.getTileSize(),
			tileMap.getHeight() + 1 *tileMap.getTileSize() ,
			0, 0
		);
		tileMap.setTween(1);
		
		// player
		player = new Player(tileMap);
		player.setPosition(80, 100);
		player.setHealth(PlayerSave.getHealth());
		player.setLives(PlayerSave.getLives());
		player.setTime(PlayerSave.getTime());
		
		// enemies
		enemies = new ArrayList<Enemy>();
		eprojectiles = new ArrayList<EnemyProjectile>();
		populateEnemies();
		
		
		// init player
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
		Turtle gp;
		Pterodactyl g;
		
		gp = new Turtle (tileMap, player);
		gp.setPosition(350, 300);
		enemies.add(gp);
		gp = new Turtle (tileMap, player);
		gp.setPosition(550, 300);
		enemies.add(gp);
		gp = new Turtle (tileMap, player);
		gp.setPosition(560, 300);
		enemies.add(gp);
		gp = new Turtle (tileMap, player);
		gp.setPosition(1660, 100);
		enemies.add(gp);
		gp = new Turtle (tileMap, player);
		gp.setPosition(1700, 100);
		enemies.add(gp);
		gp = new Turtle (tileMap, player);
		gp.setPosition(2177, 100);
		enemies.add(gp);
		gp = new Turtle (tileMap, player);
		gp.setPosition(2960, 100);
		enemies.add(gp);
		gp = new Turtle (tileMap, player);
		gp.setPosition(2980, 100);
		enemies.add(gp);
		gp = new Turtle (tileMap, player);
		gp.setPosition(3000, 100);
		enemies.add(gp);
		
		g = new Pterodactyl(tileMap);
		g.setPosition(300, 100);
		enemies.add(g);
		g = new Pterodactyl(tileMap);
		g.setPosition(500, 100);
		enemies.add(g);
	}
	
	public void update() {
		
		// check keys
		handleInput();
		
		// check if end of level event should start
		System.out.println(player.getx()+" "+player.gety());
		if(player.getx() == 1400 ) {
			eventFinish = blockInput = true;
		}
		
		// check if player dead event should start
		if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}
		
		// play events
		if(eventStart) eventStart();
		if(eventDead) eventDead();
		if(eventFinish) eventFinish();
		
		// move title and subtitle
		if(title != null) {
			title.update();
			if(title.shouldRemove()) title = null;
		}
		if(subtitle != null) {
			subtitle.update();
			if(subtitle.shouldRemove()) subtitle = null;
		}
		
		// move backgrounds
		clouds.setPosition(tileMap.getx(), tileMap.gety());
		//mountains.setPosition(tileMap.getx(), tileMap.gety());
		
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
			if(e.isDead()) {
				enemies.remove(i);
				i--;
			}
		}

	}
	
	public void draw(Graphics2D g) {
		
		// draw background
		sky.draw(g);
		clouds.draw(g);
		//mountains.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		// draw enemy projectiles
		for(int i = 0; i < eprojectiles.size(); i++) {
			eprojectiles.get(i).draw(g);
		}
		
		// draw player
		player.draw(g);
		
		
		// draw hud
		hud.draw(g);
		
		// draw title
		if(title != null) title.draw(g);
		if(subtitle != null) subtitle.draw(g);
		
		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
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
		player.setPosition(80, 161);
		populateEnemies();
		blockInput = true;
		eventCount = 0;
		tileMap.setShaking(false, 0);
		eventStart = true;
		eventStart();
	}
	
	// level started
	private void eventStart() {
		eventStart = blockInput = false;
	}
	
	// player has died
	private void eventDead() {
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
	
	// finished level
	private void eventFinish() {
		PlayerSave.setHealth(player.getHealth());
		PlayerSave.setLives(player.getLives());
		PlayerSave.setTime(player.getTime());
		gsm.setState(GameStateManager.LEVEL1BSTATE);
	}

}