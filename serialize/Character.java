/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import de.looksgood.ani.Ani;
import java.util.ArrayList;
import processing.core.PImage;
import translategame.PvpFront;

/**
 *
 * @author zlsh80826
 */
public abstract class Character {
    public float x;
    public float y;
    public boolean jumping;
    public boolean isDroping;
    PvpFront parent;
    ArrayList<ArrayList<PImage>> images;
    ArrayList<Integer> imageCount;
    ArrayList<String> fileName;
    ArrayList<Integer> frame;
    ArrayList<Integer> offset;
    int count;
    int action;
    boolean reverse;
    boolean revealIntroducion;
    StoryMap map;

    public Character() {
        this.frame = new ArrayList<Integer>();
        for (int i = 0; i < 10; ++i) {
            frame.add(0);
        }
        this.reverse = false;
        this.jumping = false;
        this.isDroping = false;

        this.count = 0;
        this.action = 0;
    }

    public abstract void display();
    
    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setStand() {
        action = 0;
    }

    public void setMove() {
        action = 2;
    }

    public void setHit() {
        action = 4;
    }

    public void setAttack() {
        action = 6;
    }

    public void setReverse() {
        reverse = !reverse;
    }

    public void setClimb() {
        action = 8;
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

    public void introduction() {
        revealIntroducion = true;
    }

    public void hideIntroduction() {
        revealIntroducion = false;
    }

    public abstract Info getInfo();

    public void setInfo(Info info) {
        this.action = info.action;
        this.x = info.x;
        this.y = info.y;
        this.reverse = info.reverse;
    }

    public void setLeft(StoryMap map) {
        reverse = false;
        if (x < 20) {
            return;
        }
        Ani.to(this, 0.015f, "x", x - 15, Ani.LINEAR);
        if (x <= 500 && map.getX() < 0) {
            map.setX(map.getX() + 20);
        }
    }

    public synchronized void setRight(StoryMap map) {
        reverse = true;
        if (x > 1400) {
            return;
        }
        Ani.to(this, 0.015f, "x", x + 15, Ani.LINEAR);
        if (x > 1000 && x < 1440) {
            map.setX(map.getX() - 20);
        }
    }

    public int getWidth() {
        return images.get(this.getAction()).get(frame.get(this.getAction())).width;
    }

    public int getHeight() {
        return images.get(this.getAction()).get(frame.get(this.getAction())).height;
    }

    public void jump() {
        this.jumping = true;
        this.y = 0;
    }
}
