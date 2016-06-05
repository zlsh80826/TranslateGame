/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author zlsh80826
 */
public abstract class Monster {

    PApplet parent;
    ArrayList<ArrayList<PImage>> images;
    ArrayList<Integer> imageCount;
    ArrayList<String> fileName;
    ArrayList<Integer> frame;
    int count;
    int action;
    float x;
    float y;
    boolean reverse;
    public boolean active;
    public int curHp;
    public int maxHp;
    StoryMap map;
    
    public float width;
    public float height;


    public void setReverse() {
        reverse = !reverse;
    }

    int getReverse() {
        if (reverse == true) {
            return 1;
        }
        return 0;
    }

    int getAction() {
        return action + this.getReverse();
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setInfo(MonsterInfo info) {
        this.x = info.x;
        this.y = info.y;
        this.reverse = info.reverse;
        this.curHp = info.curHp;
        this.reverse = info.reverse;
    }
    
    public void isCollision(){
        
    }
}
