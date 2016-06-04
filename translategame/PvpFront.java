/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import de.looksgood.ani.Ani;
import java.util.ArrayList;
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
    float swordManSelectX = 240f;
    float SelectY = 640f;
    float magicManSelectX = 540f;
    float archManSelectX = 840f;
    float radius = 100f;
    float selectModifyX = -35f;
    float selectModifyY = -50f;
    float bgOffsetX;
    float bgOffsetY;
    Career career;
    serialize.Character hero;
    serialize.Character enemy;
    ArrayList<Door> doors;
    ArrayList<RevivePoint> revPt;
    ArrayList<Obstacle> obstacles;

    PvpFront(TranslateGame parent, PvpRear rear) {
        this.parent = parent;
        this.rear = rear;
        loadProgress = new LoadProgress(this);
    }

    public void initial() {
        // create object

        // map
        this.bgOffsetX = 0f;
        this.bgOffsetY = 0f;
        map = new StoryMap(this, bgOffsetX, bgOffsetY);
        selectBg = loadImage("material/map/selectBG.png");

        doors = new ArrayList<Door>();
        doors.add(new Door(this, 400, 590, map));
        doors.add(new Door(this, 1220, 590, map));

        revPt = new ArrayList<RevivePoint>();
        revPt.add(new RevivePoint(this, 180, 595, map));
        revPt.add(new RevivePoint(this, 1500, 595, map));
        
        obstacles = new ArrayList<Obstacle>();
        obstacles.add(new Obstacle(this, 0f, 700f, 1500f, 50f, map));
        obstacles.add(new Obstacle(this, 0f, 200f, 400f, 50f, map));
        obstacles.add(new Obstacle(this, 1300f, 1500f, 400f, 50f, map));
        obstacles.add(new Obstacle(this, 500f, 500f, 600f, 50f, map));

        // character
        magicMan = new MagicMan(this, magicManSelectX, SelectY, map);
        magicMan.setReverse();
        archer = new ArchMan(this, archManSelectX, SelectY, map);
        archer.setReverse();
        swordMan = new SwordMan(this, swordManSelectX, SelectY, map);
        swordMan.setReverse();
        career = Career.UNCHOOSE;

        // monster
        baron = new Baron(this, 800f, 300f);

        // complete protocaol
        rear.sendLoadComplete();
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
            this.hero.display();
            this.enemy.display();

            doors.stream().forEach((door) -> {
                door.display();
            });

            revPt.stream().forEach((revpt) -> {
                revpt.display();
            });
        }
    }

    @Override
    public void keyPressed() {
        if (gameStage == Stage.START) {
            if (key == 's') {
                this.hero.setStand();
            }
            if (key == 'c') {
                this.hero.setClimb();
            }
            if (key == 'm') {
                this.hero.setMove();
            }
            if (key == 'h') {
                this.hero.setHit();
            }
            if (key == 'r') {
                this.hero.setReverse();
            }
            if (key == 'a') {
                this.hero.setAttack();
            }
            if (keyCode == LEFT) {
                this.hero.setLeft(map);
                this.hero.setMove();
            }
            if (keyCode == RIGHT) {
                this.hero.setRight(map);
                this.hero.setMove();
            }
            this.rear.sendInfo(this.hero.getInfo());
        }
    }

    @Override
    public void keyReleased() {

    }

    @Override
    public void mouseMoved() {
        if (gameStage == Stage.SELECT && (career == Career.UNCHOOSE)) {
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
        if (gameStage == Stage.SELECT && (career == Career.UNCHOOSE)) {
            frameRate(60);
            if (dist(this.swordManSelectX + this.selectModifyX, this.SelectY + this.selectModifyY, mouseX, mouseY) <= radius) {
                this.career = Career.SwordMan;
                //this.rear.sendSelectComplete(career);
                this.rear.sendSelectComplete(this.swordMan.getInfo());
                hero = this.swordMan;
            } else if (dist(this.magicManSelectX + this.selectModifyX, this.SelectY + this.selectModifyY, mouseX, mouseY) <= radius) {
                this.career = Career.MagicMan;
                //this.rear.sendSelectComplete(career);
                this.rear.sendSelectComplete(this.magicMan.getInfo());
                hero = this.magicMan;
            } else if (dist(this.archManSelectX + this.selectModifyX, this.SelectY + this.selectModifyY, mouseX, mouseY) <= radius) {
                this.career = Career.Archer;
                //this.rear.sendSelectComplete(career);
                this.rear.sendSelectComplete(this.archer.getInfo());
                hero = this.archer;
            } else {
                this.career = Career.UNCHOOSE;
            }
        }
    }

    public void startGame(Info info) {
        System.out.println("StartGame");
        if (info.career == Career.SwordMan) {
            enemy = this.swordMan;
        } else if (info.career == Career.MagicMan) {
            enemy = this.magicMan;
        } else if (info.career == Career.Archer) {
            enemy = this.archer;
        }
        enemy.setInfo(info);
        enemy.hideIntroduction();
        hero.hideIntroduction();
        this.gameStage = Stage.START;
    }

}
