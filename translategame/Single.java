package translategame;

import java.util.ArrayList;
import java.util.Random;

import controlP5.ControlP5;
import java.util.Timer;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import serialize.StoryMap;
import single.*;

public class Single extends PApplet {

    PImage bgImg, character1, character2;
    PFont font;
    TranslateGame parent;
    ControlP5 cp5;
    float buttonOffsetX;
    float buttonOffsetY;
    int buttonWidth;
    int buttonHeight;
    int buttonSpace;
    int buttonSpacex;
    ArrayList<String> buttonLabel, unit_kind;
    int chara1_blood, chara2_blood, chara1_blood_max, chara2_blood_max;
    int chara1_spell_remove_flag, chara2_spell_remove_flag;
    int unit_kind_amount, cooldown_value, cooldown_cur, cooldown_state;
    ArrayList<unit> my_minions, emermy_minions;
    ArrayList<Monster> myMinion, enemyMinion;
    int clk, clk_limit;
    int init_money, cur_money;
    Baron baron1, baron2;
    public StoryMap map;
    Timer timer = new Timer();

    Single(TranslateGame parent) {
        this.parent = parent;
    }

    public void setup() {
        clk = 0;
        unit_kind_amount = 2;
        clk_limit = 2;
        size(1134, 704);
        smooth();

        bgImg = loadImage("src/material/map/selectBG.png");
        //character1 = loadImage("src/material/monster/Baron/BaronStandR000.png");
        //character2 = loadImage("src/material/monster/Baron/BaronStand000.png");

        map = new StoryMap(this, 0, 0);
        baron1 = new Baron(this, 360, 640, map);
        baron1.active = true;
        baron1.setReverse();
        baron2 = new Baron(this, 1130, 640, map);
        baron2.active = true;

        unit_kind = new ArrayList<String>();

        unit_kind.add("monlin");
        unit_kind.add("dino");

        cooldown_value = 200;
        cooldown_cur = cooldown_value;
        cooldown_state = 0;
        //my_minions = new HashMap<String, ArrayList<unit>>();
        //emermy_minions = new HashMap<String, ArrayList<unit>>();
        my_minions = new ArrayList<unit>();
        emermy_minions = new ArrayList<unit>();
        myMinion = new ArrayList<Monster>();
        enemyMinion = new ArrayList<Monster>();
        /*for(int i = 0; i < unit_kind_amount; i++)
        {
        	emermy_minions.put(unit_kind.get(i), new ArrayList<unit>());
        	my_minions.put(unit_kind.get(i), new ArrayList<unit>());
        }*/

        font = this.createFont("src/material/Tekton.otf", 64, true);
        textFont(font);

        buttonOffsetX = 50f;
        buttonOffsetY = 150f;
        buttonWidth = 200;
        buttonHeight = 50;

        chara1_blood_max = 500;
        chara2_blood_max = 500;
        chara1_blood = chara1_blood_max;
        chara2_blood = chara2_blood_max;

        cp5 = new ControlP5(this);
        cp5.setFont(font, 40);
        cp5.setColorActive(unhex("DDFF6666")).setColorForeground(unhex("DDF5E8FF")).setColorBackground(unhex("0148D1CC")).setColorCaptionLabel(unhex("0048D1CC"));
        buttonLabel = new ArrayList<>();
        buttonLabel.add("chara1_atk");
        buttonLabel.add("chara2_atk");
        buttonSpace = 80;
        buttonSpacex = 700;
        chara1_spell_remove_flag = 0;
        chara2_spell_remove_flag = 0;
        for (int i = 0; i < buttonLabel.size(); ++i) {
            String label = buttonLabel.get(i);
            cp5.addButton(label.toLowerCase()).setLabel("minion").setPosition(buttonOffsetX + buttonSpacex * i, buttonOffsetY).setSize(buttonWidth, buttonHeight);
        }
    }

    public void draw() {
        image(bgImg, 0, -20);
        fill(51, 102, 255, 200);
        rect(200, 20, (float) chara1_blood / (float) chara1_blood_max * 200, 50);
        rect(900, 20, (float) chara2_blood / (float) chara2_blood_max * 200, 50);
        text(itoa(chara1_blood), 50, 70);
        text(itoa(chara2_blood), 750, 70);
        baron1.display();
        baron2.display();
        //image(character1, 5, 230);
        //image(character2, 770, 230);

        Random ran = new Random();
        int gen_flag1, gen_flag2, kind;
        gen_flag1 = (int) (Math.random() * 200);
        gen_flag2 = ran.nextInt(200);
        kind = (int) (Math.random() * 3);
        //System.out.println(gen_flag1 + " xcxcx " + gen_flag2);
        if (gen_flag1 == gen_flag2) {
            if (kind == 0) {
                emermy_minions.add(new unit("dino", 1));
                Dinosaur di = new Dinosaur(this, 1100, 640, map);
                di.active = true;
                enemyMinion.add(di);
            } else if (kind == 1) {
                Mushroom mu = new Mushroom(this, 1100, 640, map);
                mu.active = true;
                enemyMinion.add(mu);
                emermy_minions.add(new unit("mush", 1));
            } else if (kind == 2) {
                emermy_minions.add(new unit("snowgila", 1));
                Snow sn = new Snow(this, 1100, 640, map);
                sn.active = true;
                enemyMinion.add(sn);
            }
        }

        if (chara1_spell_remove_flag == 1) {
            chara1_spell_remove_flag = 0;
            cp5.remove("chara1_spell1");
            cp5.remove("chara1_spell2");
            cp5.remove("chara1_spell3");
        }
        if (chara2_spell_remove_flag == 1) {
            chara2_spell_remove_flag = 0;
            cp5.remove("chara2_spell1");
            cp5.remove("chara2_spell2");
            cp5.remove("chara2_spell3");
        }

        if (cooldown_cur <= 0) {
            cooldown_state = 0;
            cooldown_cur = cooldown_value;
        }
        if (cooldown_state == 1) {
            cooldown_cur--;
        }

        // my
        for (int i = 0; i < my_minions.size(); i++) {
            if (clk >= clk_limit) {
                if (emermy_minions.size() > 0 && abs(emermy_minions.get(0).x - (my_minions.get(i).atk_range + my_minions.get(i).x)) < emermy_minions.get(0).cosion_size) {
                    emermy_minions.get(0).cur_blood -= my_minions.get(i).atk_value;
                    System.out.println('a');
                    if (abs(my_minions.get(0).x - emermy_minions.get(0).x) > my_minions.get(0).cosion_size) {
                        my_minions.get(0).x += (float) 3.0;
                        System.out.println('b');
                    }
                } else if (i > 0 && abs(my_minions.get(i).x - my_minions.get(i - 1).x) < my_minions.get(i).cosion_size + my_minions.get(i - 1).cosion_size) {
                    if (my_minions.get(i).x >= 900) {
                        my_minions.get(i).x = 900;
                        chara2_blood--;
                    }
                } else {
                    my_minions.get(i).x += (float) 3.0;
                    if (my_minions.get(i).x >= 900) {
                        my_minions.get(i).x = 900;
                        baron2.setAttack();
                        chara2_blood--;
                        my_minions.get(i).cur_blood--;
                    }
                }
            }
            //fill(51, 102, 100, 200);
            //rect(my_minions.get(i).x, 450, my_minions.get(i).cur_blood / my_minions.get(i).total_blood * 100, 30);
            //image(my_minions.get(i).image, my_minions.get(i).x, my_minions.get(i).y);
            //myMinion.get(i).setPos(my_minions.get(i).x, my_minions.get(i).y);
            if (myMinion.size() > 0) {
                myMinion.get(i).x = my_minions.get(i).x;
                myMinion.get(i).curHp = (int) my_minions.get(i).cur_blood;
                if (myMinion.get(i).curHp < 0) {
                    myMinion.get(i).curHp = 0;
                }
                myMinion.get(i).maxHp = (int) my_minions.get(i).total_blood;
                myMinion.get(i).display();
            }
            try {
                if (my_minions.get(i).cur_blood <= 0 && !myMinion.get(i).die) {
                    this.myMinion.get(i).die();
                    RemoveTask rt = new RemoveTask(my_minions, myMinion, i);
                    timer.schedule(rt, 800);
                }
            } catch (java.lang.IndexOutOfBoundsException ex) {
                System.out.println("fuck your bound");
            }
        }
        // enermy
        for (int i = 0; i < emermy_minions.size(); i++) {
            if (clk >= clk_limit) {
                if (my_minions.size() > 0 && abs(my_minions.get(0).x - (emermy_minions.get(i).x) - emermy_minions.get(i).atk_range) < my_minions.get(0).cosion_size) {
                    my_minions.get(0).cur_blood -= emermy_minions.get(i).atk_value;

                    System.out.println('c');
                    if (abs(emermy_minions.get(0).x - my_minions.get(0).x) > emermy_minions.get(0).cosion_size) {
                        emermy_minions.get(0).x -= (float) 3.0;
                        System.out.println('d');
                    }
                } else if (i > 0 && abs(emermy_minions.get(i).x - emermy_minions.get(i - 1).x) < emermy_minions.get(i).cosion_size + emermy_minions.get(i - 1).cosion_size) {
                    if (emermy_minions.get(i).x <= 100) {
                        emermy_minions.get(i).x = 100;
                        chara1_blood--;
                    }
                } else {
                    emermy_minions.get(i).x -= (float) 3.0;
                    if (emermy_minions.get(i).x <= 100) {
                        emermy_minions.get(i).x = 100;
                        baron1.setAttack();
                        chara1_blood--;
                        emermy_minions.get(i).cur_blood--;
                    }
                }
            }
            //fill(51, 52, 100, 200);
            //rect(emermy_minions.get(i).x, 450, emermy_minions.get(i).cur_blood / emermy_minions.get(i).total_blood * 100, 30);
            //image(emermy_minions.get(i).image, emermy_minions.get(i).x, emermy_minions.get(i).y);
            //enemyMinion.get(i).setPos(emermy_minions.get(i).x, emermy_minions.get(i).y);
            if (enemyMinion.size() > 0) {
                enemyMinion.get(i).x = emermy_minions.get(i).x;
                enemyMinion.get(i).curHp = (int) emermy_minions.get(i).cur_blood;
                if (enemyMinion.get(i).curHp < 0) {
                    enemyMinion.get(i).curHp = 0;
                }
                enemyMinion.get(i).maxHp = (int) emermy_minions.get(i).total_blood;
                //enemyMinion.get(i).display();
            }
            try {
                if (emermy_minions.get(i).cur_blood <= 0 && !this.enemyMinion.get(i).die ) {
                    if (emermy_minions.get(i).type == "mush") {
                        Mushroom mu = new Mushroom(this, emermy_minions.get(i).x, emermy_minions.get(i).y, map);
                        mu.active = true;
                    } else if (emermy_minions.get(i).type == "dino") {
                        Dinosaur di = new Dinosaur(this, emermy_minions.get(i).x, emermy_minions.get(i).y, map);
                        di.active = true;
                    } else if (emermy_minions.get(i).type == "snowgila") {

                    }
                    this.enemyMinion.get(i).die();
                    RemoveTask rt = new RemoveTask(emermy_minions, enemyMinion, i);
                    timer.schedule(rt, 800);
                }
                enemyMinion.get(i).display();
            } catch (java.lang.IndexOutOfBoundsException ex) {
                System.out.println("fuck your bound");
            }

        }

        if (clk >= clk_limit) {
            clk = 0;
        } else {
            clk++;
        }
    }

    public void chara1_atk() {
        cp5.addButton("chara1_spell1").setLabel("DINO").setPosition(200, 60).setSize(200, 50);
        cp5.addButton("chara1_spell2").setLabel("MUSH").setPosition(200, 110).setSize(200, 50);
        cp5.addButton("chara1_spell3").setLabel("SNOW").setPosition(200, 160).setSize(200, 50);
    }

    public void chara2_atk() {
        cp5.addButton("chara2_spell1").setLabel("DINO").setPosition(900, 60).setSize(200, 50);
        cp5.addButton("chara2_spell2").setLabel("MUSH").setPosition(900, 110).setSize(200, 50);
        cp5.addButton("chara2_spell3").setLabel("SNOW").setPosition(900, 160).setSize(200, 50);
    }

    public void chara1_spell1() {
        if (cooldown_state == 0) {
            my_minions.add(new unit("dino", 0));
            Dinosaur di = new Dinosaur(this, 30, 640, map);
            di.active = true;
            di.setReverse();
            myMinion.add(di);
            chara1_spell_remove_flag = 1;
            cooldown_state = 1;
        }
    }

    public void chara1_spell2() {
        my_minions.add(new unit("mush", 0));
        Mushroom mu = new Mushroom(this, 30, 640, map);
        mu.active = true;
        mu.setReverse();
        myMinion.add(mu);
        chara1_spell_remove_flag = 1;
    }

    public void chara1_spell3() {
        my_minions.add(new unit("snowgila", 0));
        Snow sn = new Snow(this, 30, 640, map);
        sn.active = true;
        sn.setReverse();
        myMinion.add(sn);
        chara1_spell_remove_flag = 1;
    }

    public void chara2_spell1() {
        emermy_minions.add(new unit("dino", 1));
        Dinosaur di = new Dinosaur(this, 1100, 640, map);
        di.active = true;
        enemyMinion.add(di);
        chara2_spell_remove_flag = 1;
    }

    public void chara2_spell2() {
        emermy_minions.add(new unit("mush", 1));
        Mushroom mu = new Mushroom(this, 1100, 640, map);
        mu.active = true;
        enemyMinion.add(mu);
        chara2_spell_remove_flag = 1;
    }

    public void chara2_spell3() {
        emermy_minions.add(new unit("snowgila", 1));
        Snow sn = new Snow(this, 1100, 640, map);
        sn.active = true;
        enemyMinion.add(sn);
        chara2_spell_remove_flag = 1;
    }

    public String itoa(int in) {
        String str = "", str_rev = "";
        while (in > 0) {
            str += (char) (in % 10 + '0');
            in /= 10;
        }
        for (int i = 0; i < str.length(); i++) {
            str_rev += str.charAt(str.length() - i - 1);
        }
        return str_rev;
    }
}
