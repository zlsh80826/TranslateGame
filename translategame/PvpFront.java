/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PFont;
import serialize.*;

/**
 *
 * @author zlsh80826
 */
public class PvpFront extends PApplet {

    TranslateGame parent;
    PvpRear rear;
    Mushroom mushroom;
    Baron baron;
    Stage gameStage;
    LoadProgress loadProgress;
    StoryMap map;

    PvpFront(TranslateGame parent, PvpRear rear) {
        this.parent = parent;
        this.rear = rear;
        loadProgress = new LoadProgress(this);
    }

    public void initial() {
        baron = new Baron(this, 800f, 300f);
        map = new StoryMap(this, 0f, 0f);
        rear.sendLoadComplete();
        System.out.println("loadComplete");
        gameStage = Stage.SELECT;
    }

    @Override
    public void setup() {
        size(1134, 704);
        smooth();
        Ani.init(this);
        gameStage = Stage.INIT;
        PFont font = this.createFont("src/material/Tekton.otf", 64, true);
        textFont(font);
    }

    @Override
    public void draw() {
        background(255);
        if (gameStage == Stage.INIT) {
            loadProgress.display();
        } else if (gameStage == Stage.SELECT) {
            map.display();
            if (mouseX < 20) {
                map.setX(map.getX() + 5);
            } else if (mouseX > 1114) {
                map.setX(map.getX() - 5);
            }

            if (mouseY < 20) {
                map.setY(map.getY() + 5);
            } else if (mouseY > 600) {
                map.setY(map.getY() - 5);
            }
        }

        //this.character.display();
        //this.mushroom.display();
        //this.baron.display();
    }

    @Override
    public void keyPressed() {
        /*if(key == 's'){
            this.baron.setStand();
        }
        if(key == 'd'){
            this.baron.setDie();
        }
        if(key == 'm'){
            this.baron.setMove();
        }
        if(key == 'h'){
            this.baron.setHit();
        }
        if(key == 'r'){
            this.baron.setReverse();
        }
        if(key == 'a'){
            this.baron.setAttack();
        }*/
    }

    @Override
    public void keyReleased() {

    }

    @Override
    public void mouseMoved() {

    }

}
