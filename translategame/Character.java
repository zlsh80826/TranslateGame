/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import static processing.core.PApplet.nf;
import processing.core.PImage;

/**
 *
 * @author zlsh80826
 */
public class Character {
    PApplet parent;
    PImage[] images;
    int frame;
    int imageCount;
    int count;
    float x;
    float y;
    
    Character(PApplet parent, float x, float y, String imagePrefix, String imageSuffix, int imageCount){
        this.imageCount = imageCount;
        this.parent = parent;
        this.x = x;
        this.y = y;
        images = new PImage[imageCount];
        for(int i=0; i< imageCount; ++i){
            String fileName = imagePrefix + nf(i, 3) + imageSuffix;
            images[i] = parent.loadImage(fileName);
        }
        this.count = 0;
    }
    
    void display(){
        parent.image(images[frame], this.x - images[frame].width, this.y);
        ++ count;
        if(count % 12 == 0){
            count = 0;
            frame = (frame + 1) % imageCount;
            Ani.to(this, 0.2f, "x", this.x - 15.0f, Ani.LINEAR);
            System.out.println(x);
        }
    }   
    
    void setPos(float x, float y){
        this.x = x;
        this.y = y;
    }
}

