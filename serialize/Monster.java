/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
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
    public boolean moving;
    public boolean attacking;
    public boolean hitting;
    public boolean dying;
    public boolean rest;
    protected int curHp;
    public int maxHp;
    StoryMap map;
    Timer timer = new Timer();

    public float width;
    public float height;
    public int damage;
    Random random = new Random();

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

    public void hit(int dmg) {
        if (curHp < dmg) {
            curHp = 0;
        } else {
            curHp -= dmg;
        }
    }

    public synchronized boolean isAttacked(Character ch) {
        if(!ch.getAttacking())
            return true;
        float thisCenterPointX = this.x + this.width / 2;
        float thisCenterPointY = this.y + this.height / 2;
        float heroCenterPointX = ch.x + ch.width / 2;
        float heroCenterPointY = ch.y + ch.height / 2;
        
        if( (reverse && (thisCenterPointX > heroCenterPointX)) || (!reverse && (thisCenterPointX < heroCenterPointX) ) ){
            if ( (Math.abs(thisCenterPointX - heroCenterPointX) < (this.width + ch.width) / 2 + ch.attackRange) &&
                  Math.abs(thisCenterPointY - heroCenterPointY) < 50 ) {
                this.hit(ch.dmg + random.nextInt(ch.dmg));
                return true;
            }
        }
        return false;
    }

}
