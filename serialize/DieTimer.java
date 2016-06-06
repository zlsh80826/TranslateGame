/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PApplet;

/**
 *
 * @author zlsh80826
 */
public class DieTimer extends Thread{
    PApplet applet;
    Character ch;
    DieTimer(PApplet applet, Character ch){
        this.applet = applet;
        this.ch = ch;
    } 
    
    @Override
    public void run(){
        while(ch.freezeTime > 0){
            try {
                Thread.sleep(1000);
                ch.freezeTime -= 1000;
            } catch (InterruptedException ex) {
                Logger.getLogger(DieTimer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
