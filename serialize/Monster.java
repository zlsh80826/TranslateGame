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
import translategame.PvpFront;

/**
 *
 * @author zlsh80826
 */
public abstract class Monster {

    PvpFront parent;
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
    public boolean die = false;
    Random random = new Random();

    public void display() {
        /*float thisCenterPointX = this.x + this.width / 2;
        float thisCenterPointY = this.y - this.width / 2;
        parent.fill(255, 102, 255);
        parent.ellipse(thisCenterPointX, thisCenterPointY, 10, 10);*/
    }

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

    private synchronized boolean hit(int dmg) {
        if (curHp <= dmg) {
            curHp = 0;
            die();
            return true;
        } else {
            curHp -= dmg;
            this.action = 4;
            MonsterTimer mt = new MonsterTimer(this, Action.HIT);
            timer.schedule(mt, 200);
            return false;
        }
    }

    public synchronized boolean isAttacked(Character ch) {
        if (!ch.getAttacking() || ch.cooldown) {
            return true;
        }
        if (this.die) {
            return false;
        }
        float thisCenterPointX = this.x + this.width / 2;
        float thisCenterPointY = this.y - this.height / 2;
        float heroCenterPointX = ch.x + ch.width / 2;
        float heroCenterPointY = ch.y - ch.height / 2;

        if ((ch.reverse && (thisCenterPointX > heroCenterPointX)) || (!ch.reverse && (thisCenterPointX < heroCenterPointX))) {
            if ((Math.abs(thisCenterPointX - heroCenterPointX) < (this.width + ch.width) / 2 + ch.attackRange)
                    && Math.abs(thisCenterPointY - heroCenterPointY) < 50) {
                if(this.hit(ch.dmg + random.nextInt((int) (1 + (ch.dmg * 0.2))))){
                    if(ch == this.parent.hero)
                        this.sendExp(ch);
                    else
                        this.parent.rear.sendExpRequest(maxHp);
                }
                ch.cooldown = true;
                ActionTimer at = new ActionTimer(ch, Action.COOLDOWN);
                timer.schedule(at, 400);
                return true;
            }
        }
        return false;
    }

    public void die() {
        action = 6;
        this.die = true;
        this.dying = true;
        MonsterTimer mt = new MonsterTimer(this, Action.DIE);
        MonsterTimer mt2 = new MonsterTimer(this, Action.DYING);
        timer.schedule(mt, 5000);
        timer.schedule(mt2, 700);
    }

    public void setInfo(MonsterInfo info) {
        this.x = info.x;
        this.y = info.y;
        this.curHp = info.curHp;
        this.reverse = info.reverse;
        this.action = info.action;
        this.rest = info.rest;
        this.die = info.die;
        this.dying = info.dying;
    }
    
    public abstract void sendExp(Character ch);
    
    public abstract int getExp();
}
