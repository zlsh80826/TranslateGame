/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import java.io.Serializable;
import java.util.ArrayList;
import processing.core.PApplet;
import static processing.core.PApplet.nf;
import processing.core.PImage;

/**
 *
 * @author zlsh80826
 */
public class ArchMan extends Character implements Serializable {

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
    boolean revealIntroducion;
    // stand 
    // move
    // hit
    // attack
    // climb

    public ArchMan(PApplet parent, float x, float y) {
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

        String imagePrefix = "material/character/Bow/Bow";
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
    }

    @Override
    public void display() {
        parent.image(images.get(this.getAction()).get(frame.get(this.getAction())), this.x - images.get(this.getAction()).get(frame.get(this.getAction())).width,
                this.y - images.get(this.getAction()).get(frame.get(this.getAction())).height);
        if (++count % 12 == 0) {
            count = 0;
            int temp = (frame.get(this.getAction()) + 1) % (imageCount.get(this.getAction()));
            frame.set(this.getAction(), temp);
        }

        if (revealIntroducion) {
            parent.text("Archer", 574, 200);
        }
    }

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
}
