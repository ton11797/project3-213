/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neet.Handlers;

import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

/**
 *
 * @author User
 */
public class Mouse {
    
    public Point point;
    
    public Mouse(Point p){
        point = p;
    }
    
    public void DragDrop(JFrame frame,MouseEvent e){
        
        int x = frame.getLocation().x;
        int y = frame.getLocation().y;
        x += e.getX() - point.x;
        y += e.getY() - point.y;
        frame.setLocation(x, y);
        
    }
    
}
