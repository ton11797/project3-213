import javax.swing.*;
import java.awt.*;

import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.UIManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class GUI extends JFrame {
    private Pass s;
    
    
    
        public void SetPreview(int i) {
        String type1 ="" ,type2 = "";
        if(i == 0){type1 = "nimbus";  type2 = "Nimbus" ;}
        if(i == 1){type1 = "metal"; type2 =  "Metal";}
        
        try {
            
                UIManager.setLookAndFeel("com.sun.java.swing.plaf."+ type1 +"."+ type2 + "LookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    public GUI(Pass in) {
        s = in;
        SetPreview(0);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabBar = new JPanel();
        X = new JLabel();
        minus = new JLabel();
        jLabel3 = new JLabel();
        MainFrames = new JPanel();
        MenuFrame = new JPanel();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        TabBar.setBackground(java.awt.Color.orange);
        TabBar.setForeground(java.awt.Color.darkGray);
        TabBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                TabBarMouseDragged(evt);
            }
        });
        TabBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TabBarMousePressed(evt);
            }
        });

        X.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        X.setForeground(new java.awt.Color(255, 255, 255));
        X.setText("X");
        X.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                XMouseClicked(evt);
            }
        });

        minus.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        minus.setForeground(new java.awt.Color(255, 255, 255));
        minus.setText("-");
        minus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minusMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("JAPPA GAME");

        GroupLayout TabBarLayout = new GroupLayout(TabBar);
        TabBar.setLayout(TabBarLayout);
        TabBarLayout.setHorizontalGroup(
            TabBarLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(TabBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(minus)
                .addGap(18, 18, 18)
                .addComponent(X)
                .addContainerGap())
        );
        TabBarLayout.setVerticalGroup(
            TabBarLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, TabBarLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(TabBarLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(X, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
                    .addComponent(minus, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        MainFrames.setBackground(java.awt.Color.darkGray);

        MenuFrame.setBackground(java.awt.Color.darkGray);

        jButton1.setBackground(java.awt.Color.orange);
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("OPTION");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(java.awt.Color.orange);
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("START");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(java.awt.Color.orange);
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("EXIT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        GroupLayout MenuFrameLayout = new GroupLayout(MenuFrame);
        MenuFrame.setLayout(MenuFrameLayout);
        MenuFrameLayout.setHorizontalGroup(
            MenuFrameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(MenuFrameLayout.createSequentialGroup()
                .addGap(266, 266, 266)
                .addGroup(MenuFrameLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(283, Short.MAX_VALUE))
        );
        MenuFrameLayout.setVerticalGroup(
            MenuFrameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, MenuFrameLayout.createSequentialGroup()
                .addContainerGap(267, Short.MAX_VALUE)
                .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButton3, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );

        GroupLayout MainFramesLayout = new GroupLayout(MainFrames);
        MainFrames.setLayout(MainFramesLayout);
        MainFramesLayout.setHorizontalGroup(
            MainFramesLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, MainFramesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MenuFrame, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        MainFramesLayout.setVerticalGroup(
            MainFramesLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(MainFramesLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(MenuFrame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(TabBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MainFrames, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(TabBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MainFrames, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void XMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_XMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_XMouseClicked

    private void minusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minusMouseClicked
        // TODO add your handling code here:
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_minusMouseClicked

    private void TabBarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabBarMouseDragged
        // TODO add your handling code here:
                
        int x = this.getLocation().x ;
        int y = this.getLocation().y ;
            x += evt.getX() - point.x;
            y += evt.getY() - point.y;
                this.setLocation(x,y);
            
    }//GEN-LAST:event_TabBarMouseDragged
    Point point;
    
    private void TabBarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabBarMousePressed
        // TODO add your handling code here:
        point = evt.getPoint();
    }//GEN-LAST:event_TabBarMousePressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        s.setValue(1);
        this.dispose();
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        s.setValue(2);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.UIManager.LookAndFeelInfo info : javax.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new GUI().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel MainFrames;
    private JPanel MenuFrame;
    private JPanel TabBar;
    private JLabel X;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JLabel jLabel3;
    private JLabel minus;
    // End of variables declaration//GEN-END:variables
}
