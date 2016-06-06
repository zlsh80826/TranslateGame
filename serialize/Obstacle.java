/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import processing.core.PApplet;

/**
 *
 * @author zlsh80826
 */
public class Obstacle {

    float x;
    float y;
    float width;
    float height;
    StoryMap map;
    PApplet parent;

    public Obstacle(PApplet parent, float x, float y, float width, float height, StoryMap map) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.map = map;
        this.parent = parent;
    }

    public void display() {
        /*parent.noFill();
        parent.stroke(200, 153, 126, 250);
        parent.strokeWeight(3);
        parent.rect(x + map.getX(), y + map.getY(), width, height);*/
    }
    
    public boolean onGround(Character ch){
        if((ch.x >= x) && (ch.x <= x+width) && (ch.y >= y) && (ch.y <= y+height)){
            ch.setPos(ch.x, this.y);
            return true;
        }
        return false;
    }
}
