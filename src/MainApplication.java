/**
 * Created by Denice on 21/12/2560.
 */

import com.neet.Main.GamePanel;

import javax.swing.*;
public class MainApplication {
    private static JFrame frame;
    public static void main(String[] args) {
        boolean exit = false;
        Pass start = new Pass(0);
        do {
            if(start.getValue() !=4) {
                frame = new GUI(start);
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
               // start.setValue(0);
                exit = true;
            } else if (start.getValue() == 2) {
                start.setValue(0);
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new OptionFrame(start).setVisible(true);
                    }
                });
                while (start.getValue() == 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                start.setValue(4);
            } else {
                exit = true;
            }
        }while(!exit);


    }
    
    
}
