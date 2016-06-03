/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
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
    MagicMan magicMan;
    ArchMan archer;
    SwordMan swordMan;
    PImage selectBg;
    float swordManSelectX = 290f;
    float SelectY = 640f;
    float magicManSelectX = 590f;
    float archManSelectX = 890f;
    float radius = 100f;
    float selectModifyX = -35f;
    float selectModifyY = -50f;
    Career career;

    PvpFront(TranslateGame parent, PvpRear rear) {
        this.parent = parent;
        this.rear = rear;
        loadProgress = new LoadProgress(this);
    }

    public void initial() {
        baron = new Baron(this, 800f, 300f);
        map = new StoryMap(this, 0f, 0f);
        magicMan = new MagicMan(this, magicManSelectX, SelectY);
        magicMan.setReverse();
        archer = new ArchMan(this, archManSelectX, SelectY);
        archer.setReverse();
        swordMan = new SwordMan(this, swordManSelectX, SelectY);
        swordMan.setReverse();
        selectBg = loadImage("material/map/selectBG.png");
        rear.sendLoadComplete();
        career = Career.UNCHOOSE;
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
            image(selectBg, -100, -26);
            this.magicMan.display();
            this.archer.display();
            this.swordMan.display();
        } else if (gameStage == Stage.START) {
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
        if (gameStage == Stage.SELECT && (career == Career.UNCHOOSE) ) {
            frameRate(60);
            if (dist(this.swordManSelectX + this.selectModifyX, this.SelectY + this.selectModifyY, mouseX, mouseY) <= radius) {
                this.swordMan.setAttack();
                this.swordMan.introduction();
            } else if (dist(this.magicManSelectX + this.selectModifyX, this.SelectY + this.selectModifyY, mouseX, mouseY) <= radius) {
                this.magicMan.setAttack();
                this.magicMan.introduction();
            } else if (dist(this.archManSelectX + this.selectModifyX, this.SelectY + this.selectModifyY, mouseX, mouseY) <= radius) {
                this.archer.setAttack();
                this.archer.introduction();
            } else {
                this.swordMan.setStand();
                this.swordMan.hideIntroduction();
                this.magicMan.setStand();
                this.magicMan.hideIntroduction();
                this.archer.setStand();
                this.archer.hideIntroduction();
            }
        }
    }

    @Override
    public void mouseClicked() {
        if (gameStage == Stage.SELECT && (career == Career.UNCHOOSE) ) {
            frameRate(60);
            if (dist(this.swordManSelectX + this.selectModifyX, this.SelectY + this.selectModifyY, mouseX, mouseY) <= radius) {
                this.career = Career.SwordMan;
            } else if (dist(this.magicManSelectX + this.selectModifyX, this.SelectY + this.selectModifyY, mouseX, mouseY) <= radius) {
                this.career = Career.MagicMan;
            } else if (dist(this.archManSelectX + this.selectModifyX, this.SelectY + this.selectModifyY, mouseX, mouseY) <= radius) {
                this.career = Career.Archer;
            } else {
                this.career = Career.UNCHOOSE;
            }
            this.rear.sendSelectComplete();
        }
    }
    
    public void startGame(){
        this.gameStage = Stage.START;
        System.out.println( "StartGame" );
    }

}
