/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import processing.core.PApplet;
import static processing.core.PApplet.nf;
import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author zlsh80826
 */
public class Door {

    float x;
    float y;
    float radius;
    int frame;
    int imageCount;
    int controlSeed;
    int offsetX;
    int offsetY;
    PImage[] images;
    PApplet parent;
    StoryMap map;
    final PVector point;

    public Door(PApplet parent, float x, float y, StoryMap map, float pointX, float pointY) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.offsetY = -200;
        this.imageCount = 14;
        this.controlSeed = 0;
        this.map = map;
        this.radius = 30;
        point = new PVector();
        this.point.set(pointX, pointY);

        String imagePrefix = "material/map/Door";
        String imageSuffix = ".png";

        this.images = new PImage[imageCount];
        for (int i = 0; i < imageCount; ++i) {
            String file_name = imagePrefix + nf(i, 2) + imageSuffix;
            PImage image = parent.loadImage(file_name);
            images[i] = image;
        }
    }

    public void display() {
        controlSeed = (controlSeed + 1) % 8;
        if (controlSeed == 0) {
            frame = (frame + 1) % (imageCount);
        }
        parent.image(images[frame], x - images[frame].width + map.getX(), y - images[frame].height + map.getY());
        
        float thisX = x - images[frame].width/2;
        float thisY = y - images[frame].height/2;
        
    }    
    
    public void setPoint(float x, float y){
        point.set(x, y);
    }
    
    public synchronized void trnasport(Character ch){
        float heroX = ch.x + ch.width / 2;
        float heroY = ch.y - ch.height / 2;
        float thisX = x - images[frame].width/2;
        float thisY = y - images[frame].height/2;
        if( PApplet.dist(heroX, heroY, thisX, thisY) < 50 )
            ch.setTransPort(this.point.x, this.point.y);
    }
}
