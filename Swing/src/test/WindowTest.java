/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.swing.JFrame;
import javax.swing.JWindow;

/**
 *
 * @author Saif Khan Faysal
 */
public class WindowTest extends JFrame
{
    
    WindowTest()
    {
        setBounds(200,200,500,400);
        setVisible(true);
        JWindow w = new JWindow(this);
        w.setBounds(100,100,300,200);
        w.setVisible(true);
        
                
    }
    
    public static void main(String...s)
    {
        new WindowTest();
    }
    
    
}
