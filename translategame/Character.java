/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import java.util.ArrayList;
import processing.core.PApplet;
import static processing.core.PApplet.nf;
import processing.core.PImage;

/**
 *
 * @author zlsh80826
 */
public class Character {
    PApplet parent;
    //PImage[] images;
    ArrayList<ArrayList<PImage>> images;
    ArrayList<Integer> imageCount;
    ArrayList<String> fileName;
    int frame;
    int count;
    int action;
    float x;
    float y;
    
    Character(PApplet parent, float x, float y, String imagePrefix, String imageSuffix, ArrayList<Integer> imageCount){
        this.imageCount = imageCount;
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.images = new ArrayList<ArrayList<PImage>>();
        this.fileName = new ArrayList<String>();
        this.fileName.add( "Attack" );
        this.fileName.add( "AttackR" );
        this.fileName.add( "Move" );
        this.fileName.add( "MoveR" );
        this.fileName.add( "Hit" );
        this.fileName.add( "HitR" );
        this.fileName.add( "Stand" );
        this.fileName.add( "StandR" );
        this.fileName.add( "Die" );
        this.fileName.add( "DieR" );
        
        for(int j=0; j<10; ++j){ 
            ArrayList<PImage> temp = new ArrayList<PImage>();
            for(int i=0; i< imageCount.get(j); ++i){
                String file_name = imagePrefix + fileName.get(j) + nf(i, 3) + imageSuffix;
                temp.add(parent.loadImage(file_name));
            }
            images.add(temp);
        }
        this.count = 0;
        this.action = 2;
    }
    
    void display(){
        parent.image(images.get(action).get(frame), this.x - images.get(action).get(action).width, this.y);
        if( ++count % 12 == 0){
            count = 0;
            frame = (frame + 1) % imageCount.get(action);
        }
    }   
    
    void setPos(float x, float y){
        this.x = x;
        this.y = y;
    }
    
}

