/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import de.looksgood.ani.Ani;
import java.io.Serializable;
import java.util.ArrayList;
import processing.core.PApplet;
import static processing.core.PApplet.nf;
import processing.core.PImage;
import translategame.PvpFront;

/**
 *
 * @author zlsh80826
 */
public class Mushroom extends Monster implements Serializable {

    // stand 
    // move
    // hit 
    // die
    public Mushroom(PvpFront parent, float x, float y, StoryMap map) {
        this.frame = new ArrayList<Integer>();
        for (int i = 0; i < 8; ++i) {
            frame.add(0);
        }
        this.imageCount = new ArrayList<Integer>();
        this.imageCount.add(2);
        this.imageCount.add(2);
        this.imageCount.add(3);
        this.imageCount.add(3);
        this.imageCount.add(1);
        this.imageCount.add(1);
        this.imageCount.add(3);
        this.imageCount.add(3);
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.fileName = new ArrayList<String>();
        this.fileName.add("Stand");
        this.fileName.add("StandR");
        this.fileName.add("Move");
        this.fileName.add("MoveR");
        this.fileName.add("Hit");
        this.fileName.add("HitR");
        this.fileName.add("Die");
        this.fileName.add("DieR");
        this.reverse = false;
        this.map = map;

        String imagePrefix = "material/monster/Mushroom/Mushroom";
        String imageSuffix = ".png";

        this.images = new ArrayList<ArrayList<PImage>>();
        for (int j = 0; j < fileName.size(); ++j) {
            ArrayList<PImage> temp = new ArrayList<PImage>();
            for (int i = 0; i < imageCount.get(j); ++i) {
                String file_name = imagePrefix + fileName.get(j) + nf(i, 3) + imageSuffix;
                temp.add(parent.loadImage(file_name));
            }
            images.add(temp);
        }
        this.count = 0;
        this.action = 0;
        this.active = false;
        this.curHp = 12;
        this.maxHp = 12;
        this.width = images.get(0).get(0).width;
        this.height = images.get(0).get(0).height;
        this.rest = true;
    }

    public void display() {
        if (!die || dying) {
            parent.image(images.get(this.getAction()).get(frame.get(this.getAction())),
                    this.x - images.get(this.getAction()).get(frame.get(this.getAction())).width + map.getX() + images.get(0).get(0).width,
                    this.y - images.get(this.getAction()).get(frame.get(this.getAction())).height + map.getY());
            if (++count % 12 == 0) {
                count = 0;
                int temp = (frame.get(this.getAction()) + 1) % (imageCount.get(this.getAction()));
                frame.set(this.getAction(), temp);
            }

            float green = 70 * curHp / maxHp;
            float red = 70 - green;
            //parent.noStroke();
            parent.stroke(0);
            parent.strokeWeight(2);
            parent.fill(77, 255, 77);
            parent.rect(this.x - 5 + map.getX(), this.y - 70, green, 8);
            parent.fill(255, 77, 77);
            parent.rect(this.x - 5 + green + map.getX(), this.y - 70, red, 8);

            /*// collision detect
            parent.noFill();
            parent.strokeWeight(3);
            parent.stroke(0);
            parent.rect(this.x + map.getX(), this.y + map.getY() - height, width, height);
            parent.text(Integer.toString(curHp), this.x + map.getX(), this.y + map.getY() - height - 20);*/
        }
        super.display();
    }

    public synchronized boolean isCollision(Character ch) {
        if (this.die) {
            return false;
        }
        float thisCenterPointX = this.x + this.width / 2;
        float thisCenterPointY = this.y - this.height / 2;
        float heroCenterPointX = ch.x + ch.width / 2;
        float heroCenterPointY = ch.y - ch.height / 2;

        if (PApplet.dist(thisCenterPointX, thisCenterPointY, heroCenterPointX, heroCenterPointY) < (this.width + ch.width) / 2) {
            if (ch.getInvincible() == false) {
                if (thisCenterPointX > heroCenterPointX) {
                    ch.setHit(random.nextInt(3) + 2, true);
                } else {
                    ch.setHit(random.nextInt(3) + 2, false);
                }
            }
            return true;
        }
        return false;
    }

    public void setInfo(MonsterInfo info) {
        this.x = info.x;
        this.y = info.y;
        this.curHp = info.curHp;
        this.reverse = info.reverse;
        this.action = info.action;
        this.rest = info.rest;
    }

    public synchronized void random() {
        if (this.die) {
            return;
        }
        if (this.curHp <= this.maxHp && this.action == 0 && rest) {
            this.action = 2;
            this.reverse = random.nextBoolean();
            this.rest = false;
            MonsterTimer mt = new MonsterTimer(this, Action.MOVE);
            int dis = random.nextInt(200) + 1;
            int time = dis * 20;
            if (reverse && this.x + dis < 1400) {
                Ani.to(this, time / 1000f, "x", x + dis, Ani.LINEAR);
            } else if (!reverse && this.x - dis > 0) {
                Ani.to(this, time / 1000f, "x", this.x - dis, Ani.LINEAR);
            }
            timer.schedule(mt, time);
        } else if (this.action == 0 && !rest) {
            MonsterTimer mt = new MonsterTimer(this, Action.STAND);
            timer.schedule(mt, 1000 + random.nextInt(3000));
        }
    }

    @Override
    public void sendExp(Character ch) {
        ch.pulseExp(10);
    }

    @Override
    public int getExp() {
        return 10;
    }
}
