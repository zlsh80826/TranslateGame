/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import de.looksgood.ani.Ani;
import java.io.IOException;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;
import serialize.*;

/**
 *
 * @author zlsh80826
 */
public class PvpFront extends PApplet {

    TranslateGame parent;
    PvpRear rear;
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
    PFont font;
    Career career;
    serialize.Character hero;
    serialize.Character enemy;
    ArrayList<Door> doors;
    ArrayList<RevivePoint> revPt;
    ArrayList<Baron> barons;
    ArrayList<Mushroom> mushrooms;
    ArrayList<Snow> snows;
    ArrayList<Dinosaur> dinosaurs;

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
        doors.add(new Door(this, 400, 590, map, 1300, 170));
        doors.add(new Door(this, 1220, 590, map, 100, 170));

        revPt = new ArrayList<RevivePoint>();
        revPt.add(new RevivePoint(this, 180, 595, map));
        revPt.add(new RevivePoint(this, 1500, 595, map));

        // character
        magicMan = new MagicMan(this, magicManSelectX, SelectY, map);
        magicMan.setReverse();
        archer = new ArchMan(this, archManSelectX, SelectY, map);
        archer.setReverse();
        swordMan = new SwordMan(this, swordManSelectX, SelectY, map);
        swordMan.setReverse();
        career = Career.UNCHOOSE;

        // monster
        barons = new ArrayList<Baron>();
        barons.add(new Baron(this, 300f, 300f, map));

        mushrooms = new ArrayList<Mushroom>();
        for (int i = 0; i < 10; ++i) {
            mushrooms.add(new Mushroom(this, 300f, 300f, map));
        }

        snows = new ArrayList<Snow>();
        for (int i = 0; i < 3; ++i) {
            snows.add(new Snow(this, 300f, 300f, map));
        }

        dinosaurs = new ArrayList<Dinosaur>();
        for (int i = 0; i < 4; ++i) {
            dinosaurs.add(new Dinosaur(this, 300f, 300f, map));
        }

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
        font = this.createFont("src/material/Tekton.otf", 64, true);
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
            this.hero.displayInfo();
            this.enemy.display();
            this.rear.sendInfo(this.hero.getInfo());
            
            doors.stream().forEach((door) -> {
                door.display();
            });

            revPt.stream().forEach((revpt) -> {
                revpt.display();
            });

            barons.stream().forEach((baron) -> {
                baron.display();
            });
            
            mushrooms.stream().forEach((mushroom) -> {
                mushroom.display();
                mushroom.isCollision(this.hero);
            });
            
            snows.stream().forEach((snow) -> {
                snow.display();
                snow.isCollision(this.hero);
            });
            
            dinosaurs.stream().forEach((Dinosaur dinosaur) -> {
                dinosaur.display();
                dinosaur.isCollision(this.hero);
            });

        }
    }

    @Override
    public void keyPressed() {
        if (gameStage == Stage.START) {
            if (key == 's') {
                this.hero.setStand();
            } else if (key == 'c') {
                this.hero.setClimb();
            } else if (key == 'm') {
                this.hero.setMove();
                this.hero.curMp -= 50;
            } else if (key == 'h') {
                this.hero.setHit(0);
                this.hero.curMp += 50;
            } else if (key == 'r') {
                this.hero.setReverse();
                this.hero.curHp += 50;
            } else if (key == 'a') {
                this.hero.setAttack();
                this.hero.curHp -= 50;
            } else if (keyCode == LEFT && this.hero.isDroping == false) {
                this.hero.setLeft(map);
                this.hero.setMove();
            } else if (keyCode == RIGHT && this.hero.isDroping == false) {
                this.hero.setRight(map);
                this.hero.setMove();
            } else if (keyCode == ALT) {
                this.hero.jump();
            } else if (keyCode == UP) {
                doors.stream().map((door) -> door.trnasport(this.hero.x, this.hero.y)).filter((point) -> (point != null)).forEach((point) -> {
                    this.hero.setTransPort(point.x, point.y);
                });
            }
        }
    }

    @Override
    public void keyReleased() {
        if (gameStage == Stage.START) {
            this.hero.setStand();
        }
    }

    @Override
    public void mouseMoved() {
        if (gameStage == Stage.SELECT && (career == Career.UNCHOOSE)) {
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
        if (info.career == Career.SwordMan) {
            enemy = this.swordMan;
        } else if (info.career == Career.MagicMan) {
            enemy = this.magicMan;
        } else if (info.career == Career.Archer) {
            enemy = this.archer;
        }
        enemy.setInfo(info);
        //enemy.hideIntroduction();
        hero.hideIntroduction();
        hero.setStand();
        hero.setPos(hero.x, 0);
        this.rear.sendInfo(this.hero.getInfo());
        this.gameStage = Stage.START;
        this.frameRate(60);
        textFont(font, 32);

        barons.stream().forEach((baron) -> {
            baron.active = true;
        });
        mushrooms.stream().forEach((mushroom) -> {
            mushroom.active = true;
        });
        snows.stream().forEach((snow) -> {
            snow.active = true;
        });
        dinosaurs.stream().forEach((Dinosaur dinosaur) -> {
            dinosaur.active = true;
        });
    }

    public Stage getStage() {
        return this.gameStage;
    }

    public synchronized void setMonsterInfo(serialize.MonsterInfoPkg pkg) throws IOException{
        for (int i = 0; i < pkg.baronInfos.size(); ++i) {
            barons.get(i).setInfo(pkg.baronInfos.get(i));
        }

        for (int i = 0; i < pkg.mushroomInfos.size(); ++i) {
            mushrooms.get(i).setInfo(pkg.mushroomInfos.get(i));
        }

        for (int i = 0; i < pkg.snowInfos.size(); ++i) {
            snows.get(i).setInfo(pkg.snowInfos.get(i));
        }

        for (int i = 0; i < pkg.dinosaurInfos.size(); ++i) {
            dinosaurs.get(i).setInfo(pkg.dinosaurInfos.get(i));
        }
    }
}
