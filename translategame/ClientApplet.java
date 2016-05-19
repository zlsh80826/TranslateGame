/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import processing.core.PApplet;

/**
 *
 * @author zlsh80826
 */
public class ClientApplet extends PApplet{
    
    @Override
    public void setup(){
        setSize(600, 800);
    }
    
    @Override
    public void draw(){
        background(255);
        smooth();
        stroke(200, 153, 126, 250);
        strokeWeight(5);
        line(0, 300, 600, 300);
       
    }
}
