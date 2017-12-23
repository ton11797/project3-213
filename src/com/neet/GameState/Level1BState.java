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
	private boolean eventQuake;
	
	public Level1BState(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		
		// backgrounds
		temple = new Background("Resource2/Backgrounds/grass2.png", 0.5, 0);
		
		// tilemap
		tileMap = new TileMap(30);
		tileMap.loadTiles("Resources/Tilesets/ruinstileset.gif");
		tileMap.loadMap("Resources/Maps/level1b.map");
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
		
		// title and subtitle
		try {

			hageonText = ImageIO.read(
					new File("Resources/HUD/HageonTemple.gif")
			);
			title = new Title(hageonText.getSubimage(0, 0, 178, 20));
			title.sety(60);
			subtitle = new Title(hageonText.getSubimage(0, 33, 91, 13));
			subtitle.sety(85);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		// start event
		eventStart = true;
		tb = new ArrayList<Rectangle>();
		eventStart();
		
		// sfx
		//JukeBox.load("/SFX/teleport.mp3", "teleport");
		//JukeBox.load("/SFX/explode.mp3", "explode");
		//JukeBox.load("/SFX/enemyhit.mp3", "enemyhit");
		
	}
	
	private void populateEnemies() {
		enemies.clear();
		Turtle gp;
		Pterodactyl g;
		
		gp = new Turtle(tileMap, player);
		gp.setPosition(750, 100);
		enemies.add(gp);
		gp = new Turtle(tileMap, player);
		gp.setPosition(900, 150);
		enemies.add(gp);
		gp = new Turtle(tileMap, player);
		gp.setPosition(1320, 250);
		enemies.add(gp);
		gp = new Turtle(tileMap, player);
		gp.setPosition(1570, 160);
		enemies.add(gp);
		gp = new Turtle(tileMap, player);
		gp.setPosition(1590, 160);
		enemies.add(gp);
		gp = new Turtle(tileMap, player);
		gp.setPosition(2600, 370);
		enemies.add(gp);
		gp = new Turtle(tileMap, player);
		gp.setPosition(2620, 370);
		enemies.add(gp);
		gp = new Turtle(tileMap, player);
		gp.setPosition(2640, 370);
		enemies.add(gp);
		
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
		
		// check if quake event should start
		if(player.getx() > 2175 && !tileMap.isShaking()) {
			eventQuake = blockInput = true;
		}
		
		// check if end of level event should start
		if(player.getx() == 2850) {
			eventFinish = blockInput = true;
		}
		
		// play events
		if(eventStart) eventStart();
		if(eventDead) eventDead();
		if(eventQuake) eventQuake();
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
		player.loseLife();
		player.reset();
		player.setPosition(300, 131);
		populateEnemies();
		blockInput = true;
		eventCount = 0;
		tileMap.setShaking(false, 0);
		eventStart = true;
		eventStart();
		title = new Title(hageonText.getSubimage(0, 0, 178, 20));
		title.sety(60);
		subtitle = new Title(hageonText.getSubimage(0, 33, 91, 13));
		subtitle.sety(85);
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
		if(eventCount == 30) title.begin();
		if(eventCount == 60) {
			eventStart = blockInput = false;
			eventCount = 0;
			subtitle.begin();
			tb.clear();
		}
	}
	
	// player has died
	private void eventDead() {
		eventCount++;
		if(eventCount == 1) player.setDead();
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
				reset();
			}
		}
	}
	
	// earthquake
	private void eventQuake() {
		eventCount++;
		if(eventCount == 1) {
			player.stop();
			player.setPosition(2175, player.gety());
		}
		if(eventCount == 60) {
			player.setEmote(Player.CONFUSED);
		}
		if(eventCount == 120) player.setEmote(Player.NONE);
		if(eventCount == 150) tileMap.setShaking(true, 10);
		if(eventCount == 180) player.setEmote(Player.SURPRISED);
		if(eventCount == 300) {
			player.setEmote(Player.NONE);
			eventQuake = blockInput = false;
			eventCount = 0;
		}
	}
	
	// finished level
	private void eventFinish() {
		eventCount++;
		if(eventCount == 1) {
			player.stop();
		}
		else if(eventCount == 120) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 120) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if(eventCount == 180) {
			PlayerSave.setHealth(player.getHealth());
			PlayerSave.setLives(player.getLives());
			PlayerSave.setTime(player.getTime());
			gsm.setState(GameStateManager.LEVEL1CSTATE);
		}
		
	}

}