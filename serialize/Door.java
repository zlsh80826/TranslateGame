/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import processing.core.PApplet;
import static processing.core.PApplet.nf;
import processing.core.PImage;

/**
 *
 * @author zlsh80826
 */
public class Door {

    float x;
    float y;
    int frame;
    int imageCount;
    int controlSeed;
    int offsetX;
    int offsetY;
    PImage[] images;
    PApplet parent;
    StoryMap map;

    public Door(PApplet parent, float x, float y, StoryMap map) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.offsetY = -200;
        this.imageCount = 14;
        this.controlSeed = 0;
        this.map = map;

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
        parent.image(images[frame], x-images[frame].width + map.getX(), y-images[frame].height + map.getY());
    }    
}
