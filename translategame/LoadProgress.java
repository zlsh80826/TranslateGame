/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import processing.core.PApplet;

/**
 *
 * @author zlsh80826
 */
public class LoadProgress {

    PApplet parent;
    int frame;
    float centerX;
    float centerY;
    float radius;
    float stringX;
    float stringY;
    int count;
    int textCount;
    String[] loadText = {"L", "o", "a", "d", "i", "n", "g", ".", ".", "."};

    LoadProgress(PApplet parent) {
        this.parent = parent;
        this.centerX = 567f;
        this.centerY = 352f;
        this.radius = 200f;
        this.count = 0;
        this.textCount = 0;
        this.stringX = 850;
        this.stringY = 650;
    }

    public void display() {
        parent.frameRate(24);
        for (int j = 0; j < 20; ++j) {
            int i = (j + count) % 20;
            float angle = (360f / 20f) * j;
            float offsetX = (float) (radius * Math.cos(Math.toRadians(angle)));
            float offsetY = (float) (radius * Math.sin(Math.toRadians(angle)));
            parent.noStroke();
            parent.fill((255f / 20f) * (19 - i), (255f / 10f) * Math.abs(10 - i), (255f / 19f) * i, 150);
            parent.ellipse(centerX + offsetX, centerY + offsetY, 50, 50);
        }
        
        parent.fill(0);
        for (int i = 0; i < 10; ++i) {
            if (i == (textCount % 10)) {
                parent.text(loadText[i], stringX + 25 * i, stringY - 20);
            } else {
                parent.text(loadText[i], stringX + 25 * i, stringY);
            }
        }
        
        if(count % 2 ==0 )
            ++textCount;
        
        if (--count < 0) {
            count = 20;
        }
    }
}
