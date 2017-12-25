package com.jappa.Handlers;

import java.awt.event.KeyEvent;

// this class contains a boolean array of current and previous key states
// for the 10 keys that are used for this game.
// a key k is down when keyState[k] is true.

public class Keys {
	
	public static final int NUM_KEYS = 6;
	
	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];
	
	public static int LEFT = 0;
	public static int RIGHT = 1;
	public static int SPACE = 2;
	public static int BUTTON3 = 3;
	public static int BUTTON4 = 4;
	public static int ESCAPE = 5;
        
        public static void init() {
            for (int i = 0; i < NUM_KEYS; i++) {
                keyState[i] = false;
            }
        }
	
	public static void keySet(int i, boolean b) {
		if(i == KeyEvent.VK_LEFT) keyState[LEFT] = b;
		else if(i == KeyEvent.VK_RIGHT) keyState[RIGHT] = b;
		else if(i == KeyEvent.VK_SPACE) keyState[SPACE] = b;
		else if(i == KeyEvent.VK_R) keyState[BUTTON3] = b;
		else if(i == KeyEvent.VK_F) keyState[BUTTON4] = b;
		else if(i == KeyEvent.VK_ESCAPE) keyState[ESCAPE] = b;
	}
	
	public static void update() {
		for(int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}
	
	public static boolean isPressed(int i) {
		return keyState[i] && !prevKeyState[i];
	}
        

	
}
