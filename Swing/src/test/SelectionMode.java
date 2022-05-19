/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.swing.*;
import javax.swing.event.*;


/**
 *
 * @author Saif Khan Faysal
 */
public class SelectionMode extends JFrame
{
    SelectionMode()
    {
        setBounds(100,100,500,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        String s[] = {"one", "two", "three", "four"};
        JList list = new JList(s);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        getContentPane().add(list);
        
        pack();
        
    }
    
    public static void main(String...s)
    {
        new SelectionMode().setVisible(true);
    }
}
