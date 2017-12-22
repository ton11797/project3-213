/**
 * Created by Denice on 23/12/2560.
 */
public class Pass {
    private int value;
    public Pass(int v){
        value=v;
    }
    public synchronized void setValue(int in){
        value=in;
        System.out.print("set");
    }
    public synchronized int getValue(){
        return value;
    }
}
