package com.neet.GameState;

import ModeGame.Pass;
import com.neet.Main.GamePanel;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	private PauseState pauseState;
	private boolean paused;
	
	public static final int NUMGAMESTATES = 16;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1ASTATE = 2;
	public static final int LEVEL1BSTATE = 3;
	
	public GameStateManager() {
		
		
		gameStates = new GameState[NUMGAMESTATES];
		
		pauseState = new PauseState(this);
		paused = false;
		
		currentState = LEVEL1ASTATE;
                loadState(currentState);
		
	}
	
	private void loadState(int state) {
		if(state == LEVEL1ASTATE)
			gameStates[state] = new Level1AState(this);
		else if(state == LEVEL1BSTATE)
			gameStates[state] = new Level1BState(this);
		else if(state == MENUSTATE){
			Pass.setValue(0);
		}

	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	public void setPaused(boolean b) { paused = b; }
	
	public void update() {
		if(paused) {
			pauseState.update();
			return;
		}
		if(gameStates[currentState] != null) gameStates[currentState].update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		if(paused) {
			pauseState.draw(g);
			return;
		}
		if(gameStates[currentState] != null) gameStates[currentState].draw(g);
		else {
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}
	
}