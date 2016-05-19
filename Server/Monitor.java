/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author zlsh80826
 */
public class Monitor extends JFrame{
    JTextArea info;
    Monitor(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
        /* Need to implement
        this.setShape(shape);
        this.setIconImage(image);        
        */
        this.setLocation(550, 200);//need to tune
        info = new JTextArea();
        info.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
        info.setText("Server Monitor is starting...");
        info.setEditable(false);
        this.add(info);
    }
    
    void addText(String text){
        this.info.append(text);
    }
}
