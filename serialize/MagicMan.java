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
public class MagicMan extends Character implements Serializable {

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
    // stand 
    // move
    // hit
    // attack
    // climb

    public MagicMan(PvpFront parent, float x, float y, StoryMap map) {
        this.frame = new ArrayList<Integer>();
        for (int i = 0; i < 10; ++i) {
            frame.add(0);
        }
        this.imageCount = new ArrayList<Integer>();
        this.imageCount.add(3);
        this.imageCount.add(3);
        this.imageCount.add(4);
        this.imageCount.add(4);
        this.imageCount.add(3);
        this.imageCount.add(3);
        this.imageCount.add(3);
        this.imageCount.add(3);
        this.imageCount.add(2);
        this.imageCount.add(2);

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
        this.fileName.add("Attack");
        this.fileName.add("AttackR");
        this.fileName.add("Climb");
        this.fileName.add("ClimbR");

        this.reverse = false;

        String imagePrefix = "material/character/Magic/Magic";
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

        this.offset = new ArrayList<Integer>();
        for (int i = 0; i < 10; ++i) {
            if (i % 2 == 1) {
                offset.add(images.get(i).get(0).width);
            } else {
                offset.add(0);
            }
        }

        this.count = 0;
        this.action = 0;
        this.map = map;
    }

    @Override
    public void display() {
        if (reverse == true) {
            parent.image(images.get(this.getAction()).get(frame.get(this.getAction())),
                    this.x - getWidth() + offset.get(this.getAction()) + map.getX(),
                    this.y - getHeight() + map.getY());
        } else {
            parent.image(images.get(this.getAction()).get(frame.get(this.getAction())), this.x + map.getX(), this.y - getHeight() + map.getY());
        }
        if (this.parent.getStage() == Stage.START) {
            if (map.checkOnGround(this)) {

            } else {
                Ani.to(this, 0.015f, "y", y + 10, Ani.EXPO_IN);
            }
        }

        if (++count % 12 == 0) {
            count = 0;
            int temp = (frame.get(this.getAction()) + 1) % (imageCount.get(this.getAction()));
            frame.set(this.getAction(), temp);
        }

        if (revealIntroducion) {
            parent.text("MagicMan", 574, 200);
        }
    }

    @Override
    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setStand() {
        action = 0;
    }

    @Override
    public void setMove() {
        action = 2;
    }

    @Override
    public void setHit() {
        action = 4;
    }

    @Override
    public void setAttack() {
        action = 6;
    }

    @Override
    public void setReverse() {
        reverse = !reverse;
    }

    @Override
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

    @Override
    public void introduction() {
        revealIntroducion = true;
    }

    @Override
    public void hideIntroduction() {
        revealIntroducion = false;
    }

    @Override
    public Info getInfo() {
        return new Info(action, x, y, reverse, Career.MagicMan);
    }

    @Override
    public void setInfo(Info info) {
        this.action = info.action;
        this.x = info.x;
        this.y = info.y;
        this.reverse = info.reverse;
    }

    @Override
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

    @Override
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

    @Override
    public void jump() {
        this.jumping = true;
        Ani ani = Ani.to(this, 0.2f, "y", y - 50, Ani.QUINT_OUT);

    }
}
