/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import processing.core.PApplet;
import processing.core.PFont;
/**
 *
 * @author zlsh80826
 */
public class WaitRoomFront extends PApplet{
    private String time;
    TranslateGame parent;
    WaitRoomRear rear;
    PFont font;
    
    WaitRoomFront(TranslateGame parent, WaitRoomRear rear){
        this.parent = parent;
        this.rear = rear;
    }
    
    @Override
    public void setup(){
        size(1134, 704);
        smooth();
        background(255);
        time = "";
        font = this.createFont("src/material/Tekton.otf", 64, true);
        textFont(font);
    }
    
    @Override
    public void draw(){
        background(255);
        fill(0);
        text(time, 200, 200);
    }
    
    public void setTime(String newTime){
        time = newTime;
    }
}
