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
    

    public static void Set(int i){
          
        if(i == 0){ damage = 1;}
        if(i == 1){ damage = 2;}
        if(i == 2){ damage = 5;} 
    }
    
    public static void SetSpeed(int i){
        maxspeed = (i/50) *2.5 ;
        if(maxspeed < 3) maxspeed = 0.5;
    }

    
}
