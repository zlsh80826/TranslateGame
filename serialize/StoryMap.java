/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import java.io.Serializable;
import processing.core.PApplet;
import static processing.core.PApplet.nf;
import processing.core.PImage;

/**
 *
 * @author zlsh80826
 */
public class StoryMap implements Serializable {
    float x;
    float y;
    int frame;
    int imageCount;
    int controlSeed;
    int offsetX;
    int offsetY;
    PImage[] images;
    PImage bg;
    PApplet parent;
    
    public StoryMap(PApplet parent, float x, float y){
        this.parent = parent;
        this.x = -300;
        this.y = 0;
        this.offsetY = -200;
        this.imageCount = 20;
        this.controlSeed = 0;
        
        String imagePrefix = "material/map/MapleMap_image";
        String imageSuffix = ".png";
        
        this.images = new PImage[imageCount];
        for(int i=0; i<imageCount; ++i){ 
            String file_name = imagePrefix + nf(i, 2) + imageSuffix;
            PImage image = parent.loadImage(file_name);
            images[i] = image;
        }
        bg = parent.loadImage("material/map/bkg_lg.png");
    }
    
    public void display(){
        controlSeed = (controlSeed+1) % 8;
        if(controlSeed == 0)
            frame = (frame + 1) % (imageCount);
        parent.image(bg, x-5, y + offsetY);
        parent.image(images[frame], x, y);
    }     
    
    public void setX(float newX){
        if(newX < -485 || newX > 0)
            return;
        this.x = newX;
    }
    
    public void setY(float newY){
        if( newY > 100 || newY < 0)
            return ;
        this.y = newY;
    }
    
    public float getX(){
        return x;
    }
    
    public float getY(){
        return y;
    }
}
