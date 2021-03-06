/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import de.looksgood.ani.Ani;
import de.looksgood.ani.AniSequence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public boolean droping;
    private boolean isHitting;
    private boolean invincible;
    private boolean attacking;
    private boolean moving;
    public boolean dump;
    public boolean cooldown = false;
    public boolean control;
    public boolean freeze;
    public int freezeTime;
    public boolean lose = false;

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
    protected int curHp;
    public int MaxHp;
    public int LV;
    public int exp;
    public int MaxMp;
    public int curMp;
    public int width;
    public int height;
    public Timer timer;
    public int dmg;
    public float attackRange;
    public int heal = 0;
    AniSequence aniseq;

    int[] expTable = {15, 34, 57, 92, 135, 372, 560, 840, 1242, 1490, 2145, 3088, 4446, 6402};
    StoryMap map;

    public Character() {
        this.frame = new ArrayList<Integer>();
        for (int i = 0; i < 12; ++i) {
            frame.add(0);
        }
        this.reverse = false;
        this.jumping = false;
        this.droping = false;
        this.isHitting = false;
        this.attacking = false;
        this.invincible = false;
        this.moving = false;
        this.dump = false;

        this.count = 0;
        this.action = 0;
        this.timer = new Timer();
    }

    public void display() {
        
        if( ++heal % 60 == 0){
            if( (control && this.x < 300 && this.y > 500) ||
                (!control && this.x > 1000 && this.y > 500)){
                this.setCurHp(-MaxHp/10);
            }
        }
        
        // gravity
        if ( ( (this.parent.getStage() == Stage.PVP) ||  (this.parent.getStage() == Stage.START) ) && this.dump == false ) {
            if (map.checkOnGround(this)) {
                if (this.droping) {
                    this.setAction(Action.HIT);
                    if(!freeze)
                        this.setInvincible(false);
                }
                this.setDroping(false);
            } else if (this.jumping == false) {
                this.drop();
            }
        }

        if (++count % 12 == 0) {
            count = 0;
            int temp = (frame.get(this.getAction()) + 1) % (imageCount.get(this.getAction()));
            frame.set(this.getAction(), temp);
        }

        if (parent.getStage() == Stage.START || parent.getStage() == Stage.PVP) {
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

            /*// collision detect
            parent.noFill();
            parent.strokeWeight(3);
            parent.stroke(255, 0, 0);
            parent.rect(this.x + map.getX(), this.y + map.getY() - height, width, height);*/

            /*parent.fill(255, 0, 0);
            parent.ellipse(this.x + map.getX() + width / 2, this.y + map.getY() - height / 2, 10, 10);*/

            /*if (reverse) {
                parent.noFill();
                parent.strokeWeight(3);
                parent.stroke(0, 255, 0);
                parent.rect(this.x + map.getX() + width, this.y + map.getY() - height, attackRange, height);
            } else {
                parent.noFill();
                parent.strokeWeight(3);
                parent.stroke(0, 255, 0);
                parent.rect(this.x + map.getX() - attackRange, this.y + map.getY() - height, attackRange, height);
            }*/
        }
    }

    public void displayInfo() {
        String lvStr = "Lv: " + nf(LV, 2);
        String hpStr = "Hp: " + curHp + " / " + MaxHp;
        String mpStr = "Mp: " + curMp + " / " + MaxMp;
        parent.fill(255, 255, 255, 200);
        parent.stroke(255, 255, 255);
        parent.strokeWeight(3);
        parent.rect(880, 15, 240, 150, 20);
        parent.fill(0);
        parent.text(lvStr, 900, 50);
        parent.text(hpStr, 900, 100);
        parent.text(mpStr, 900, 150);

    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
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

    public void setAction(Action action) {
        if (action == Action.ATTACK) {
            this.action = 6;
        } else if (action == Action.HIT) {
            this.action = 4;
        } else if (action == Action.MOVE) {
            this.action = 2;
        } else if (action == Action.ONRAP) {
            this.action = 8;
        } else if (action == Action.STAND) {
            this.action = 0;
        } else if (action == Action.JUMP) {
            this.action = 10;
        }
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
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
        this.curHp = info.curHp;
        this.LV = info.LV;
        this.curMp = info.curMp;
        this.MaxHp = info.maxHp;
        this.MaxMp = info.maxMp;
        this.lose = info.lose;
    }

    public void setLeft(StoryMap map) {
        if (!attacking && !jumping && !isHitting && !droping) {
            this.setAction(Action.MOVE);
        }
        if (!isHitting) {
            reverse = false;
            if (x < 20) {
                return;
            }
            Ani.to(this, 0.015f, "x", x - 15, Ani.LINEAR);
            if (x <= 500 && map.getX() < 0) {
                map.setX(map.getX() + 20);
            }
        }
    }

    public synchronized void setRight(StoryMap map) {
        if (!attacking && !jumping && !isHitting && !droping) {
            this.setAction(Action.MOVE);
        }
        if (!isHitting) {
            reverse = true;
            if (x > 1400) {
                return;
            }
            Ani.to(this, 0.015f, "x", x + 15, Ani.LINEAR);
            if (x > 1000 && x < 1440) {
                map.setX(map.getX() - 25);
            }
        }
    }

    public int getWidth() {
        return images.get(this.getAction()).get(frame.get(this.getAction())).width;
    }

    public int getHeight() {
        return images.get(this.getAction()).get(frame.get(this.getAction())).height;
    }

    public void jump() {
        if (this.jumping || this.isHitting) {
            return;
        }
        this.setJumping(true);
        this.setAction(Action.JUMP);
        Ani.to(this, 0.2f, "y", y - 50, Ani.SINE_OUT);
        SettingTimer st = new SettingTimer(this, Action.JUMP, false);
        ActionTimer at = new ActionTimer(this, Action.JUMP);
        timer.schedule(st, 200);
        timer.schedule(at, 400);
    }

    public synchronized void setHit(boolean value) {
        this.isHitting = value;
    }

    public synchronized boolean getHit() {
        return this.isHitting;
    }

    public synchronized void attack() {
        if (this.attacking || this.isHitting) {
            return;
        }
        if( (this.parent.enemy.x > this.x) && this.reverse && (this.parent.enemy.x < this.x + this.attackRange) )
            this.parent.rear.sendDmgRequest(this.dmg);
        else if((this.parent.enemy.x < this.x) && !this.reverse && (this.parent.enemy.x > this.x - this.attackRange))
            this.parent.rear.sendDmgRequest(this.dmg);
        this.attacking = true;
        this.setAction(Action.ATTACK);
        SettingTimer st = new SettingTimer(this, Action.ATTACK, false);
        ActionTimer at = new ActionTimer(this, Action.ATTACK);
        timer.schedule(st, 600);
        timer.schedule(at, 600);
    }

    public synchronized void setInvincible(boolean value) {
        this.invincible = value;
    }

    public synchronized boolean getInvincible() {
        return this.invincible;
    }

    public synchronized void setHit(int damage, boolean dir) {
        if (this.getInvincible()) {
            return;
        }
        this.setHit(true);
        this.setInvincible(true);
        this.setAction(Action.HIT);
        this.setCurHp(damage);
        if (dir) {
            Ani.to(this, 0.3f, "x", x - 20);
        } else {
            Ani.to(this, 0.3f, "x", x + 20);
        }
        SettingTimer st = new SettingTimer(this, Action.HIT, false);
        ActionTimer at = new ActionTimer(this, Action.HIT);
        timer.schedule(st, 300);
        timer.schedule(at, 1500);
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void drop() {
        if (!this.isDroping()) {
            this.setDroping(true);
            this.setInvincible(true);
            this.setAction(Action.JUMP);
        }
        Ani.to(this, 0.015f, "y", y + 27, Ani.LINEAR);
    }

    public synchronized boolean isDroping() {
        return this.droping;
    }

    public synchronized void setDroping(boolean value) {
        this.droping = value;
    }

    public synchronized boolean getAttacking() {
        return this.attacking;
    }
    
    public void setCurHp(int dmg){
        if(this.curHp < dmg){
            this.curHp = 0;
            this.die();
        }else if(this.curHp - dmg > this.MaxHp){
            this.curHp = MaxHp;
        }else{
            this.curHp -= dmg;
        }        
    }
    
    public synchronized void die(){
        if(this.parent.getStage() == Stage.PVP ){
            this.lose = true;
            this.parent.end();
            return ;
        }
        if(control){
            this.setTransPort(100, 590);
            this.reverse = true;
        }else{
            this.setTransPort(1350, 590);
            this.reverse = false;
        }
        this.parent.rear.sendExpRequest(LV * LV * 15);
        freezeTime = 3000 * LV;
        this.freeze = true;
        this.setInvincible(true);
        this.curHp = MaxHp;
        this.setAction(Action.STAND);
        ActionTimer at = new ActionTimer(this, Action.DIE);
        timer.schedule(at, freezeTime);
    }
    
    public synchronized void pulseExp(int getExp){
        exp += getExp;
        if(exp >= this.expTable[this.LV-1]){
            //System.out.println(exp);
            this.exp -= this.expTable[this.LV-1];
            //System.out.println(exp);
            ++this.LV;
            this.updateInfo();
            //System.out.println(LV);
        }
    }
    
    abstract public void updateInfo();
}
