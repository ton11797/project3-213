package com.neet.Entity;

import ModeGame.ModeGame;
import com.neet.TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Player extends MapObject {
	
	// references
	private ArrayList<Enemy> enemies;
	
	// player stuff
	private int lives;
	private int health;
	private int maxHealth;
	private int damage;
	private int chargeDamage;
	private boolean knockback;
	private boolean flinching;
	private long flinchCount;
	private boolean doubleJump;
	private boolean alreadyDoubleJump;
	private long time;
	
	// actions
	private boolean dashing;
	private boolean attacking;
	private boolean upattacking;
	private boolean charging;
	private int chargingTick;
	
	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] NUMFRAMES = {
		1, 6, 4, 2, 2, 4, 4, 6, 1, 1, 1
	};
	private final int[] FRAMEWIDTHS = {
		75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75
	};
	private final int[] FRAMEHEIGHTS = {
		52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52
	};
	private final int[] SPRITEDELAYS = {
		-1, 3, 4, 6, 5, 2, 2, 2, 1, -1, 1
	};
	
	private Rectangle ar;
	private Rectangle aur;
	private Rectangle cr;
	
	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int ATTACKING = 2;
	private static final int JUMPING = 3;
	private static final int FALLING = 4;
	private static final int UPATTACKING = 5;
	private static final int CHARGING = 6;
	private static final int DASHING = 7;
	private static final int KNOCKBACK = 8;
	private static final int DEAD = 9;

	// emotes
	private BufferedImage confused;
	private BufferedImage surprised;
	public static final int NONE = 0;
	public static final int CONFUSED = 1;
	public static final int SURPRISED = 2;
	private int emote = NONE;

	public Player(TileMap tm) {
		
		super(tm);
		
		ar = new Rectangle(0, 0, 0, 0);
		ar.width = 30;
		ar.height = 20;
		aur = new Rectangle((int)x - 15, (int)y - 45, 50, 50);
		cr = new Rectangle(0, 0, 0, 0);
		cr.width = 50;
		cr.height = 40;
		
		width = 30;
		height = 30;
		cwidth = 15;
		cheight = 38;
		
		moveSpeed = 1.0;
		maxSpeed =  ModeGame.maxspeed;
		stopSpeed = 10.5;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		
		damage = 2;
		chargeDamage = 1;
		
		facingRight = true;
		
		lives = 10;
		health = maxHealth = 10;
		
		// load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(
					new File("Resources/Sprites/Player/PlayerSprites"+ModeGame.character+".gif")
			);
			
			int count = 0;
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < NUMFRAMES.length; i++) {
				BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
				for(int j = 0; j < NUMFRAMES[i]; j++) {
					bi[j] = spritesheet.getSubimage(
						j * FRAMEWIDTHS[i],
						count,
						FRAMEWIDTHS[i],
						FRAMEHEIGHTS[i]
					);
				}
				sprites.add(bi);
				count += FRAMEHEIGHTS[i];
			}
			
			// emotes
			spritesheet = ImageIO.read(new File("Resources/HUD/Emotes.gif"));
			confused = spritesheet.getSubimage(
				0, 0, 14, 17
			);
			surprised = spritesheet.getSubimage(
				14, 0, 14, 17
			);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}


		setAnimation(IDLE);

	}
	
	public void init(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}
	
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	
	public void setEmote(int i) {
		emote = i;
	}

	
	public void setJumping(boolean b) {
		if(knockback) return;
		if(b && !jumping && falling && !alreadyDoubleJump) {
			doubleJump = true;
		}
		jumping = b;
	}
	public void setAttacking() {
		if(knockback) return;
		if(charging) return;
		if(up && !attacking) upattacking = true;
		else attacking = true;
	}
	public void setCharging() {
		if(knockback) return;
		if(!attacking && !upattacking && !charging) {
			charging = true;
			chargingTick = 0;
		}
	}
	public void setDashing(boolean b) {
		if(!b) dashing = false;
		else if(b && !falling) {
			dashing = true;
		}
	}
	
	public void setDead() {
		health = 0;
		stop();
	}
	
	public String getTimeToString() {
		int minutes = (int) (time / 3600);
		int seconds = (int) ((time % 3600) / 60);
		return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
	}
	public long getTime() { return time; }
	public void setTime(long t) { time = t; }
	public void setHealth(int i) { health = i; }
	public void setLives(int i) { lives = i; }
	public void gainLife() { lives++; }
	public void loseLife() { lives--; }
	public int getLives() { return lives; }
	
	public void hit(int damage) {
		if(flinching) return;
		stop();
		health -= damage;
		if(health < 0) health = 0;
		flinching = true;
		flinchCount = 0;
		if(facingRight) dx = -1;
		else dx = 1;
		dy = -3;
		knockback = true;
		falling = true;
		jumping = false;
	}
	
	public void reset() {
		health = maxHealth;
		facingRight = true;
		currentAction = -1;
		stop();
	}
	
	public void stop() {
		left = right = up = down = flinching = 
			dashing = jumping = attacking = upattacking = charging = false;
	}
	
	private void getNextPosition() {
		
		if(knockback) {
			dy += fallSpeed * 2;
			if(!falling) knockback = false;
			return;
		}
		
		double maxSpeed = this.maxSpeed;
		if(dashing) maxSpeed *= 1.75;
		
		// movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		// cannot move while attacking, except in air
		if((attacking || upattacking || charging) &&
			!(jumping || falling)) {
			dx = 0;
		}
		
		// charging
		if(charging) {
			chargingTick++;
			if(facingRight) dx = moveSpeed * (3 - chargingTick * 0.07);
			else dx = -moveSpeed * (3 - chargingTick * 0.07);
		}
		
		// jumping
		if(jumping && !falling) {
			dy = -5.5;
			//dy = jumpStart;
			falling = true;
		}
		
		if(doubleJump) {
			dy = -5.5;
			alreadyDoubleJump = true;
			doubleJump = false;
		}
		
		if(!falling) alreadyDoubleJump = false;
		
		// falling
		if(falling) {
			dy += fallSpeed;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}

		
	}
	
	private void setAnimation(int i) {
		currentAction = i;
		animation.setFrames(sprites.get(currentAction));
		animation.setDelay(SPRITEDELAYS[currentAction]);
		width = FRAMEWIDTHS[currentAction];
		height = FRAMEHEIGHTS[currentAction];
	}
	
	public void update() {
		
		time++;
		

		
		// update position
		boolean isFalling = falling;
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		if(isFalling && !falling) {
		}
		if(dx == 0) x = (int)x;
		
		// check done flinching
		if(flinching) {
			flinchCount++;
			if(flinchCount > 120) {
				flinching = false;
			}
		}
		
		
		// check attack finished
		if(currentAction == ATTACKING ||
			currentAction == UPATTACKING) {
			if(animation.hasPlayedOnce()) {
				attacking = false;
				upattacking = false;
			}
		}
		if(currentAction == CHARGING) {
			if(animation.hasPlayed(5)) {
				charging = false;
			}
			cr.y = (int)y - 20;
			if(facingRight) cr.x = (int)x - 15;
			else cr.x = (int)x - 35;
			if(facingRight){;}
		}
		
		// check enemy interaction
		for(int i = 0; i < enemies.size(); i++) {
			
			Enemy e = enemies.get(i);
			
			// check attack
			if(currentAction == ATTACKING &&
					animation.getFrame() == 3 && animation.getCount() == 0) {
				if(e.intersects(ar)) {
					e.hit(damage);
				}
			}
			
			// check upward attack
			if(currentAction == UPATTACKING &&
					animation.getFrame() == 3 && animation.getCount() == 0) {
				if(e.intersects(aur)) {
					e.hit(damage);
				}
			}
			
			// check charging attack
			if(currentAction == CHARGING) {
				if(animation.getCount() == 0) {
					if(e.intersects(cr)) {
						e.hit(chargeDamage);
					}
				}
			}
			
			// collision with enemy
			if(!e.isDead() && intersects(e) && !charging) {
				hit(e.getDamage());
			}
			
			if(e.isDead()) {
			}
			
		}
		
		// set animation, ordered by priority
		if(knockback) {
			if(currentAction != KNOCKBACK) {
				setAnimation(KNOCKBACK);
			}
		}
		else if(health == 0) {
			if(currentAction != DEAD) {
				setAnimation(DEAD);
			}
		}
		else if(upattacking) {
			if(currentAction != UPATTACKING) {
				setAnimation(UPATTACKING);
				aur.x = (int)x - 15;
				aur.y = (int)y - 50;
			}
		}
		else if(attacking) {
			if(currentAction != ATTACKING) {
				setAnimation(ATTACKING);
				ar.y = (int)y - 6;
				if(facingRight) ar.x = (int)x + 10;
				else ar.x = (int)x - 40;
			}
		}
		else if(charging) {
			if(currentAction != CHARGING) {
				setAnimation(CHARGING);
			}
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				setAnimation(JUMPING);
			}
		}
		else if(dy > 0) {
			if(currentAction != FALLING) {
				setAnimation(FALLING);
			}
		}
		else if(dashing && (left || right)) {
			if(currentAction != DASHING) {
				setAnimation(DASHING);
			}
		}
		else if(left || right) {
			if(currentAction != WALKING) {
				setAnimation(WALKING);
			}
		}
		else if(currentAction != IDLE) {
			setAnimation(IDLE);
		}
		
		animation.update();
		
		// set direction
		if(!attacking && !upattacking && !charging && !knockback) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw emote
		if(emote == CONFUSED) {
			g.drawImage(confused, (int)(x + xmap - cwidth / 2), (int)(y + ymap - 40), null);
		}
		else if(emote == SURPRISED) {
			g.drawImage(surprised, (int)(x + xmap - cwidth / 2), (int)(y + ymap - 40), null);
		}
		
		
		// flinch
		if(flinching && !knockback) {
			if(flinchCount % 10 < 5) return;
		}
		
		super.draw(g);
		
	}
	
}