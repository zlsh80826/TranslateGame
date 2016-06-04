/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import java.io.Serializable;
import java.util.ArrayList;
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
    ArrayList<Obstacle> obstacles;

    public StoryMap(PApplet parent, float x, float y) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.offsetY = -200;
        this.imageCount = 20;
        this.controlSeed = 0;

        String imagePrefix = "material/map/MapleMap_image";
        String imageSuffix = ".png";

        this.images = new PImage[imageCount];
        for (int i = 0; i < imageCount; ++i) {
            String file_name = imagePrefix + nf(i, 2) + imageSuffix;
            PImage image = parent.loadImage(file_name);
            images[i] = image;
        }
        bg = parent.loadImage("material/map/bkg_lg.png");

        obstacles = new ArrayList<Obstacle>();
        obstacles.add(new Obstacle(this.parent, 0f, 590f, 1500f, 50f, this));
        obstacles.add(new Obstacle(this.parent, 0f, 170f, 380f, 50f, this));
        obstacles.add(new Obstacle(this.parent, 1140f, 170f, 360f, 50f, this));
        obstacles.add(new Obstacle(this.parent, 470f, 320f, 590f, 50f, this));
    }

    public void display() {
        controlSeed = (controlSeed + 1) % 8;
        if (controlSeed == 0) {
            frame = (frame + 1) % (imageCount);
        }
        parent.image(bg, x - 5, y + offsetY);
        parent.image(images[frame], x, y);
        
        obstacles.stream().forEach((obstacle) -> {
            obstacle.display();
        });
    }

    public void setX(float newX) {
        if (newX < -366 || newX > 0) {
            return;
        }
        this.x = newX;
    }

    public void setY(float newY) {
        this.y = newY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
