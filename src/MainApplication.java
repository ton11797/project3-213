/**
 * Created by Denice on 21/12/2560.
 */
import javax.swing.*;
public class MainApplication {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setBounds(200, 200, 600, 300);
        frame.setContentPane(new Menu());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);

    }
}
