package com.jappa.GameState;

import com.jappa.Entity.PlayerSave;
import com.jappa.Main.MainApplication;
import com.jappa.Main.GamePanel;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
        private boolean run;
	
	private PauseState pauseState;
	private boolean paused;
	
	public static final int NUMGAMESTATES = 4;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1 = 2;
	public static final int LEVEL2 = 3;
	
	public GameStateManager() {
		
		gameStates = new GameState[NUMGAMESTATES];
		
		pauseState = new PauseState(this);
		paused = false;
                run= true;
		
		currentState = LEVEL1;
                loadState(currentState);
		
	}
	
	private void loadState(int state) {
		if(state == LEVEL1)
			gameStates[state] = new Level1State(this);
		else if(state == LEVEL2)
			gameStates[state] = new Level2State(this);
		else if(state == MENUSTATE){                   
                    run = false;
                    PlayerSave.init();
                    new MainApplication().setVisible(true);
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
	
	public void setPaused(boolean b) {
            paused = b;
        }
        
        public boolean isRun(){
            return run;
        }
	
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