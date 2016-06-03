/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import serialize.*;

/**
 *
 * @author zlsh80826
 */
public class PvpFront extends PApplet{
    TranslateGame parent;
    PvpRear rear;
    //Character character;
    Mushroom mushroom;
    PvpFront(TranslateGame parent, PvpRear rear){
        this.parent = parent;
        this.rear = rear;
        /*ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.add(0);
        temp.add(0);
        temp.add(3);
        temp.add(3);
        temp.add(0);
        temp.add(0);
        temp.add(2);
        temp.add(2);
        temp.add(3);
        temp.add(3);
        this.character = new Character(this, 800f, 0f, "material/monster/Mushroom/Mushroom", ".png", temp);*/
        //this.character = new Character(this, 800f, 300f, "material/monster/Mushroom/MushroomMove", ".png", 3);
        mushroom = new Mushroom(this, 300f, 300f);
    }
    
    @Override
    public void setup(){
        size(1134, 704);
        smooth();
        Ani.init(this);
    }
    
    @Override
    public void draw(){
        background(255);
        //this.character.display();
        this.mushroom.display();
    }
    
    @Override
    public void keyPressed(){
        if(key == 's'){
            mushroom.setStand();
        }
        if(key == 'd'){
            mushroom.setDie();
        }
        if(key == 'm'){
            mushroom.setMove();
        }
        if(key == 'h'){
            mushroom.setHit();
        }
    }

    @Override
    public void keyReleased(){
        
    }    
    
}
