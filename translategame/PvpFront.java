/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import de.looksgood.ani.Ani;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
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
    public PvpRear rear;
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
    boolean control;
    boolean MIPKGINIT = false;
    PFont font;
    PFont clock;
    Career career;
    public serialize.Character hero;
    public serialize.Character enemy;
    ArrayList<Door> doors;
    ArrayList<RevivePoint> revPt;
    ArrayList<Baron> barons;
    ArrayList<Mushroom> mushrooms;
    ArrayList<Snow> snows;
    ArrayList<Dinosaur> dinosaurs;
    MonsterInfoPkg monsterInfoPkg;
    HandleMonster HM;
    Random random = new Random();
    String time = "90";
    boolean win = false;
    int count = 0;

    Minim minim;
    AudioPlayer song;

    PvpFront(TranslateGame parent, PvpRear rear, boolean control) {
        this.parent = parent;
        this.rear = rear;
        loadProgress = new LoadProgress(this);
        this.control = control;
    }

    public void initial() {
        // create object
        clock = this.createFont("src/material/Courier Prime Sans.ttf", 64, true);
        font = this.createFont("src/material/Tekton.otf", 64, true);
        textFont(font);
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
        magicMan = new MagicMan(this, magicManSelectX, SelectY, map, control);
        magicMan.setReverse();
        archer = new ArchMan(this, archManSelectX, SelectY, map, control);
        archer.setReverse();
        swordMan = new SwordMan(this, swordManSelectX, SelectY, map, control);
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

        monsterInfoPkg = new MonsterInfoPkg();
        if (control) {
            initPkg();
        }

        minim = new Minim(this);
        song = minim.loadFile("src/material/output.mp3");
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
            if (!song.isPlaying()) {
                song.play();
            }
        } else if (gameStage == Stage.START) {
            map.display();

            doors.stream().forEach((door) -> {
                door.display();
            });

            revPt.stream().forEach((revpt) -> {
                revpt.display();
            });

            /*barons.stream().forEach((baron) -> {
                baron.display();
            });*/
            for (int i = 0; i < 10; ++i) {
                Mushroom mushroom = mushrooms.get(i);
                mushroom.display();
                if (control) {
                    mushroom.random();
                    monsterInfoPkg.mushroomInfos.get(i).setInfo(mushroom);
                }
            }

            if (this.hero.getInvincible() == false) {
                for (Mushroom mushroom : mushrooms) {
                    if (mushroom.isCollision(hero)) {
                        break;
                    }
                }
                for (Snow snow : snows) {
                    if (snow.isCollision(hero)) {
                        break;
                    }
                }
                for (Dinosaur dinosaur : dinosaurs) {
                    if (dinosaur.isCollision(hero)) {
                        break;
                    }
                }
            }

            if (control) {
                for (Mushroom mushroom : mushrooms) {
                    if (mushroom.isAttacked(hero)) {
                        break;
                    }
                }

                for (Mushroom mushroom : mushrooms) {
                    if (mushroom.isAttacked(enemy)) {
                        break;
                    }
                }
            }

            for (int i = 0; i < 3; ++i) {
                Snow snow = snows.get(i);
                snow.display();
                if (control) {
                    snow.random();
                    monsterInfoPkg.snowInfos.get(i).setInfo(snow);
                }
            }

            if (control) {
                for (Snow snow : snows) {
                    if (snow.isAttacked(hero)) {
                        break;
                    }
                }

                for (Snow snow : snows) {
                    if (snow.isAttacked(enemy)) {
                        break;
                    }
                }
            }

            for (int i = 0; i < 4; ++i) {
                Dinosaur dinosaur = dinosaurs.get(i);
                dinosaur.display();
                if (control) {
                    dinosaur.random();
                    monsterInfoPkg.dinosaurInfos.get(i).setInfo(dinosaur);
                }
            }

            if (control) {
                for (Dinosaur dinosaur : dinosaurs) {
                    if (dinosaur.isAttacked(hero)) {
                        break;
                    }
                }

                for (Dinosaur dinosaur : dinosaurs) {
                    if (dinosaur.isAttacked(enemy)) {
                        break;
                    }
                }
            }

            this.hero.display();
            this.hero.displayInfo();
            this.enemy.display();
            this.rear.sendInfo(this.hero.getInfo());

            if (control) {
                this.HM.sendInfo(monsterInfoPkg);
            }
            fill(204, 51, 0);
            int min = Integer.parseInt(time) / 60;
            int sec = Integer.parseInt(time) % 60;
            String t = nf(min, 2) + ":" + nf(sec, 2);
            textFont(font, 64);
            text(t, 522, 100);
            textFont(font, 32);
        } else if (gameStage == Stage.PVP) {
            map.display();

            doors.stream().forEach((door) -> {
                door.display();
            });

            revPt.stream().forEach((revpt) -> {
                revpt.display();
            });
            this.hero.display();
            this.hero.displayInfo();
            this.enemy.display();
            this.rear.sendInfo(this.hero.getInfo());

            if (this.enemy.lose) {
                this.win = true;
                this.end();
            }
        } else if (gameStage == Stage.END) {
            if (++count % 3 == 0) {
                this.rear.sendInfo(this.hero.getInfo());
            }
            if (win) {
                text("win", 522, 200);
            } else {
                text("lose", 522, 200);
            }
        }
    }

    @Override
    public void keyPressed() {
        if (gameStage == Stage.START || gameStage == Stage.PVP) {
            if (key == 's') {
            } else if (key == 'c') {
                this.hero.setClimb();
            } else if (key == 'm') {
                this.hero.curMp -= 50;
            } else if (key == 'h') {
                //this.hero.setHit(0, true);
                this.hero.curMp += 50;
            } else if (key == 'r') {
            } else if (key == 'a') {
            } else if (keyCode == LEFT && this.hero.isDroping() == false) {
                this.hero.setLeft(map);
                this.hero.setMoving(true);
            } else if (keyCode == RIGHT && this.hero.isDroping() == false) {
                this.hero.setRight(map);
                this.hero.setMoving(true);
            } else if (keyCode == ALT) {
                this.hero.jump();
            } else if (keyCode == UP) {
                for (Door door : doors) {
                    door.trnasport(this.hero);
                }
            } else if (keyCode == CONTROL) {
                if (control) {
                    this.hero.attack();
                } else {
                    this.HM.sendAttackRequest();
                    this.hero.attack();
                }
            }
        }
    }

    @Override
    public void keyReleased() {
        if (gameStage == Stage.START || gameStage == Stage.PVP) {
            this.hero.setMoving(false);
        }
    }

    @Override
    public void mouseMoved() {
        if (gameStage == Stage.SELECT && (career == Career.UNCHOOSE)) {
            frameRate(60);
            if (dist(this.swordManSelectX + this.selectModifyX, this.SelectY + this.selectModifyY, mouseX, mouseY) <= radius) {
                this.swordMan.setAction(Action.ATTACK);
                this.swordMan.introduction();
            } else if (dist(this.magicManSelectX + this.selectModifyX, this.SelectY + this.selectModifyY, mouseX, mouseY) <= radius) {
                this.magicMan.setAction(Action.ATTACK);
                this.magicMan.introduction();
            } else if (dist(this.archManSelectX + this.selectModifyX, this.SelectY + this.selectModifyY, mouseX, mouseY) <= radius) {
                this.archer.setAction(Action.ATTACK);
                this.archer.introduction();
            } else {
                this.swordMan.setAction(Action.STAND);
                this.swordMan.hideIntroduction();
                this.magicMan.setAction(Action.STAND);
                this.magicMan.hideIntroduction();
                this.archer.setAction(Action.STAND);
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
        enemy.dump = true;
        //enemy.hideIntroduction();
        hero.hideIntroduction();
        hero.setAction(Action.STAND);
        hero.setTransPort(hero.x, 0);
        this.rear.sendInfo(this.hero.getInfo());

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
        this.gameStage = Stage.START;
    }

    public Stage getStage() {
        return this.gameStage;
    }

    public synchronized void setMonsterInfo(serialize.MonsterInfoPkg pkg) throws IOException {
        if (!control) {
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
            if (!this.MIPKGINIT) {
                this.MIPKGINIT = true;
            }
        }
    }

    public void setHM(HandleMonster hm) {
        this.HM = hm;
    }

    final void initPkg() {

        for (int i = 0; i < 10; ++i) {
            monsterInfoPkg.addMushroomInfo(new MushroomInfo(random.nextInt(1350) + 20, 590f, false, 0, 12, false, false));
        }

        monsterInfoPkg.addBaronInfo(new BaronInfo(0f, 800f, false, 0, 8000, false, false));

        for (int i = 0; i < 3; ++i) {
            monsterInfoPkg.addSnowInfo(new SnowInfo(random.nextInt(550) + 400, 320f, false, 0, 360, false, false));
        }

        for (int i = 0; i < 2; ++i) {
            monsterInfoPkg.addDinosaurInfo(new DinosaurInfo(random.nextInt(300) + 1, 170f, false, 0, 60, false, false));
        }

        for (int i = 0; i < 2; ++i) {
            monsterInfoPkg.addDinosaurInfo(new DinosaurInfo(random.nextInt(300) + 1100, 170f, false, 0, 60, false, false));
        }

        for (int i = 0; i < monsterInfoPkg.baronInfos.size(); ++i) {
            barons.get(i).setInfo(monsterInfoPkg.baronInfos.get(i));
        }

        for (int i = 0; i < monsterInfoPkg.mushroomInfos.size(); ++i) {
            mushrooms.get(i).setInfo(monsterInfoPkg.mushroomInfos.get(i));
        }

        for (int i = 0; i < monsterInfoPkg.snowInfos.size(); ++i) {
            snows.get(i).setInfo(monsterInfoPkg.snowInfos.get(i));
        }

        for (int i = 0; i < monsterInfoPkg.dinosaurInfos.size(); ++i) {
            dinosaurs.get(i).setInfo(monsterInfoPkg.dinosaurInfos.get(i));
        }
    }

    public void pvp() {
        mushrooms.stream().forEach((mushroom) -> {
            mushroom.die();
        });
        snows.stream().forEach((snow) -> {
            snow.die();
        });
        dinosaurs.stream().forEach((dinosaur) -> {
            dinosaur.die();
        });
        this.hero.die();
        if (control) {
            this.hero.x = 100;
        } else {
            this.hero.x = 1300;
        }
        this.gameStage = Stage.PVP;
    }

    public void end() {
        this.gameStage = Stage.END;
    }
}
