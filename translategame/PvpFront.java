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
public class PvpFront extends PApplet{
    TranslateGame parent;
    PvpRear rear;
    PvpFront(TranslateGame parent, PvpRear rear){
        this.parent = parent;
        this.rear = rear;
    }
    
    @Override
    public void setup(){
        size(1134, 704);
        background(77, 255, 77, 200);
        smooth();
    }
    
    @Override
    public void draw(){
        background(77, 255, 77, 200);
    }
}
