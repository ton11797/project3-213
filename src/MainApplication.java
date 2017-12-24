/**
 * Created by Denice on 21/12/2560.
 */
import com.neet.Main.GamePanel;
import javax.swing.*;
public class MainApplication {
    public static void main(String[] args) {
        boolean exit = false;
        JFrame t;
        Pass start = new Pass(0);
        do {
            if(start.getValue() !=4) {
                JFrame frame = new GUI(start);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
            while (start.getValue() == 0 ||start.getValue() ==4) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (start.getValue() == 1) {
                JFrame window = new JFrame("JAPPA GAME");
                window.add(new GamePanel());
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setResizable(false);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
                exit = true;
            }else {
                exit = true;
            }
        }while(!exit);


    }
    
    
}
