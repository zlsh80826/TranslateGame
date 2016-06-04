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
    
    public Obstacle(PApplet parent, float x, float y, float width, float height, StoryMap map){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.map = map;
        this.parent = parent;
    }
    
    public void display(){
        parent.rect( x+map.getX(), y+map.getY(), width, height);
    }
}
