/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import controlP5.CColor;
import controlP5.ControlP5;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

/**
 *
 * @author zlsh80826
 */
public class LoginPaint extends PApplet{
    PImage bgImg;
    PFont titleFont;
    TranslateGame console;
    ControlP5 cp5;
    float buttonOffsetX;
    float buttonOffsetY;
    int buttonWidth;
    int buttonHeight;
    int buttonSpace;
    ArrayList<String> buttonLabel;
    
    
    public LoginPaint(TranslateGame console){
        this.console = console;
    }
    
    @Override
    public void setup() {
        smooth();
        buttonOffsetX = 800f;
        buttonOffsetY = 200f;
        buttonWidth = 200;
        buttonHeight = 50;
        bgImg = loadImage("src/material/bg.jpg");
        size(bgImg.width, bgImg.height);
        titleFont = this.createFont("src/material/Tekton.otf", 64, true);
        buttonInit();
    }

    @Override
    public void draw() {
        background(bgImg);
        textFont(titleFont);
        fill(51, 102, 255, 50);
        text("To see is to believe", 80, 130, -100);
        fill(51, 102, 255, 200);
        text("To see is to believe", 50, 150);
    }    
    
    private void buttonInit(){
        cp5 = new ControlP5(this);
        cp5.setFont(titleFont, 40);
        cp5.setColorActive(unhex("DDFF6666")).setColorForeground(unhex("DDF5E8FF")).setColorBackground(unhex("0148D1CC"))
           .setColorCaptionLabel(unhex("0048D1CC"));
        buttonLabel = new ArrayList<>();
        buttonLabel.add("Single");
        buttonLabel.add("Multiple");
        buttonLabel.add("Setting");
        buttonLabel.add("About");
        buttonSpace = 80;
        for(int i=0; i<buttonLabel.size(); ++i){
            String label = buttonLabel.get(i);
            cp5.addButton(label.toLowerCase()).setLabel(label).setPosition(buttonOffsetX, buttonOffsetY + buttonSpace*i).setSize(buttonWidth, buttonHeight);
        }        
    }
}
