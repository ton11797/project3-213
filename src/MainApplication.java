/**
 * Created by Denice on 21/12/2560.
 */
import ModeGame.Pass;
import com.neet.Main.GamePanel;
import javax.swing.*;
public class MainApplication {
    public static void main(String[] args) {
        boolean exit = false;
        JFrame t;
        JFrame window=null;
        do {
            System.out.println(Pass.getValue());
            if(Pass.getValue() !=4) {
                JFrame frame = new GUI();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
            while (Pass.getValue() == 0 ||Pass.getValue() ==4) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (Pass.getValue() == 1) {
                window = new JFrame("JAPPA GAME");
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
            while (Pass.getValue() == 5) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(Pass.getValue() == 0&& window != null)window.dispose();
        }while(!exit);


    }
    
    
}
