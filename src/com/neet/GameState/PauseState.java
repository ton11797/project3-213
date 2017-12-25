package com.neet.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.neet.Handlers.Keys;
import com.neet.Main.GUI;
import com.neet.Main.GamePanel;
import java.util.Random;
import javax.swing.JFrame;

public class PauseState extends GameState {
	
	private Font font;
        private int movex ,movey , dx,dy;
	
	public PauseState(GameStateManager gsm) {

		super(gsm);
		init();
	}
	
	public void init() {
                //font
                font = new Font("Tahoma", Font.BOLD, 14);
                
                //movement
                movex = movey = 90;
                dx = dy = 2;
        
        }
	
	public void update() {
		handleInput();   
                
                if(movex ==0 || movex == GamePanel.WIDTH-100 )dx = -dx;
                if(movey ==0 || movey == GamePanel.HEIGHT)dy= -dy;
                
                movex+=dx;
                movey+=dy;
                
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Game Paused", movex,movey);        
                
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(false);
	}

}
