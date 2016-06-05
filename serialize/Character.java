/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import de.looksgood.ani.Ani;
import java.util.ArrayList;
import static processing.core.PApplet.nf;
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
    public int curHp;
    public int MaxHp;
    public int LV;
    public int exp;
    public int MaxMp;
    public int curMp;
    
    int[] expTable = {15, 34, 57, 92, 135, 372, 560, 840, 1242, 1490, 2145, 3088, 4446, 6402};
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

    public void display() {
        if(parent.getStage() == Stage.START){
            float green = 80 * curHp / MaxHp;
            float red = 80 - green;
            //parent.noStroke();
            parent.stroke(0);
            parent.strokeWeight(2);
            parent.fill(77, 255, 77);
            parent.rect(this.x + 10 + map.getX(), this.y - 108, green, 8);
            parent.fill(255, 77, 77);
            parent.rect(this.x + 10 + map.getX() + green, this.y - 108, red, 8);

            float blue = 80 * curMp / MaxMp;
            float nblue = 80 - blue;
            parent.fill(77, 77, 255);
            parent.rect(this.x + 10 + map.getX(), this.y - 100, blue, 8);
            parent.fill(10, 10, 10, 200);
            parent.rect(this.x + 10 + map.getX() + blue, this.y - 100, nblue, 8);
        }
    }

    public void displayInfo() {
        String lvStr = "Lv: " + nf(LV, 2);
        String hpStr = "Hp: " + curHp + " / " + MaxHp;
        String mpStr = "Mp: " + curMp + " / " + MaxMp;
        parent.fill(0);
        parent.stroke(77, 255, 77);
        parent.strokeWeight(3);
        parent.text(lvStr, 900, 50);
        parent.text(hpStr, 900, 100);
        parent.text(mpStr, 900, 150);
    }

    public void setPos(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    public void setTransPort(float newX, float newY) {
        if (newX > 1040) {
            map.setX(-340);
        } else if (newX < 150) {
            map.setX(0);
        }
        this.x = newX;
        this.y = newY;
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

    public synchronized void setInfo(Info info) {
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
            map.setX(map.getX() - 25);
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
