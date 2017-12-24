import javax.swing.*;

/**
 * Created by Denice on 23/12/2560.
 */
public class Pass {
    private int value;
    public JFrame O;
    public Pass(int v){
        value=v;
    }
    public void setoF(JFrame o){
        O=o;
    }
    public synchronized void setValue(int in){
        value=in;
    }
    public synchronized int getValue(){
        return value;
    }
}
