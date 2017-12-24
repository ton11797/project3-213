package ModeGame;

import javax.swing.*;

/**
 * Created by Denice on 23/12/2560.
 */
public final class Pass {
    private static int value;
    public static JFrame O;
    public static void setoF(JFrame o){
        O=o;
    }
    public static synchronized void setValue(int in){
        value=in;
        System.out.print("set ");
        System.out.println(value);
    }
    public static synchronized int getValue(){
        return value;
    }
}
