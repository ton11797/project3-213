/**
 * Created by Denice on 21/12/2560.
 */
import com.neet.Main.GamePanel;
import java.awt.event.WindowAdapter;
import javax.swing.*;
public class MainApplication {
    public static void main(String[] args) {
        Pass start = new Pass(0);
        JFrame frame = new GUI(start);
//        frame.setBounds(200, 200, 600, 300);
//        frame.setContentPane(new GUI());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);

        while(start.getValue()==0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JFrame window = new JFrame("Artifact");
        window.add(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
