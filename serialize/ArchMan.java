/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import de.looksgood.ani.Ani;
import de.looksgood.ani.AniSequence;
import java.io.Serializable;
import java.util.ArrayList;
import static processing.core.PApplet.nf;
import processing.core.PImage;
import translategame.PvpFront;

/**
 *
 * @author zlsh80826
 */
public class ArchMan extends Character implements Serializable {

    public ArchMan(PvpFront parent, float x, float y, StoryMap map, boolean control) {
        super();
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
        this.imageCount.add(1);
        this.imageCount.add(1);

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
        this.fileName.add("Jump");
        this.fileName.add("JumpR");

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

        this.offset = new ArrayList<Integer>();
        for (int i = 0; i < 12; ++i) {
            if (i % 2 == 1) {
                offset.add(images.get(i).get(0).width);
            } else {
                offset.add(0);
            }
        }

        this.map = map;
        this.LV = 1;
        this.MaxHp = (int) (25 * Math.pow(1.25, LV));
        this.curHp = MaxHp;
        this.MaxMp = (int) (300 * Math.pow(1.1, LV));
        this.dmg = (int) (3 * Math.pow(1.28, LV));
        this.curMp = MaxMp;
        this.width = images.get(0).get(0).width;
        this.height = images.get(0).get(0).height;
        this.aniseq = new AniSequence(this.parent);
        this.attackRange = 300;
        this.control = control;
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
        if (revealIntroducion) {
            parent.stroke(0);
            parent.strokeWeight(3);
            parent.fill(200, 153, 126, 150);
            parent.rect(750, 100, 300, 400, 25);
            parent.fill(0);
            parent.text("Archer", 765, 180);
        }
        super.display();
    }

    @Override
    public Info getInfo() {
        return new Info(action, x, y, reverse, Career.Archer, this.curHp, this.LV, 0, this.curMp, this.getInvincible(), this.MaxHp, this.MaxMp, this.lose, 0, "info");
    }

    @Override
    public void updateInfo() {
        this.MaxHp = (int) (25 * Math.pow(1.25, LV));
        this.curHp = MaxHp;
        this.MaxMp = (int) (300 * Math.pow(1.1, LV));
        this.dmg = (int) (3 * Math.pow(1.28, LV));
        this.curMp = MaxMp;
    }
}
