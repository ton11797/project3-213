/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeGame;

/**
 *
 * @author User
 */
public final class ModeGame {
    
    public static int damage = 2;
    public static double maxspeed = 2.5;
    public static String character = "2";
    public static boolean iron = false;
    public static boolean invisible = false;
    public static boolean immortal = false;

    public static void Set(int i){
          
        if(i == 0){ damage = 1;}
        if(i == 1){ damage = 2;}
        if(i == 2){ damage = 3;} 
        if(i == 3){ damage = 5;} 
        if(i == 4){ damage = 10;} 
    }
    
    public static void SetSpeed(int i){
        maxspeed =  (i/20)*2.5 ;
        if(i < 10) maxspeed = 1;
    }
    
    public static void SetCharacter(int i){
        character = Integer.toString(i);
    }
    
    public static void ResetCheat(){
        iron = invisible =immortal = false;
    }
    
    public static void GodMode(){
        iron = invisible = immortal = true;
    }

    
}
