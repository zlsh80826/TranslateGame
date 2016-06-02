/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import de.looksgood.ani.Ani;
import processing.core.PApplet;

/**
 *
 * @author zlsh80826
 */
public class PvpFront extends PApplet{
    TranslateGame parent;
    PvpRear rear;
    Character character;
    PvpFront(TranslateGame parent, PvpRear rear){
        this.parent = parent;
        this.rear = rear;
        //this.character = new Character(this, 800f, 0f, "material/monster/Baron/BaronAttack", ".png", 27);
        this.character = new Character(this, 800f, 300f, "material/monster/Mushroom/MushroomMove", ".png", 3);
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
        this.character.display();
    }
}
