/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
/**
 *
 * @author zlsh80826
 */
public class WaitRoomFront extends PApplet{
    private String time;
    TranslateGame parent;
    WaitRoomRear rear;
    PFont font;
    int pos = 100;
    //test variable
    PImage image;
    PImage mr0;
    PImage mr1;
    PImage mr2;
    PImage mr3;
    PImage mr4;
    PImage mr5;
    WaitRoomFront(TranslateGame parent, WaitRoomRear rear){
        this.parent = parent;
        this.rear = rear;
    }
    
    @Override
    public void setup(){
        size(1134, 704);
        smooth();
        background(255);
        time = "";
        font = this.createFont("src/material/Tekton.otf", 64, true);
        textFont(font);
        mr0 = loadImage("jump_r.png");
        mr3 = loadImage("MushroomMoveR000.png");
        mr4 = loadImage("MushroomMoveR001.png");
        mr5 = loadImage("MushroomMoveR002.png");
    }
    
    @Override
    public void draw(){
        background(255);
        fill(0);
        int up = Integer.parseInt(time);
        up = up % 10;
        
        switch(up){
        case 0:
        	pos = pos+5;
        	image(mr3,pos,400);
        	text("L        ", 100, 200+10);
        	text("  o       ", 100, 200);
        	text("    a      ", 100, 200);
        	text("      d     ", 100, 200);
        	text("        i    ", 100, 200);
        	text("         n   ", 100, 200);
        	text("           g  ", 100, 200);
        	text("              : ", 100, 200);
        	break;
        case 1:
        	pos = pos;
        	image(mr3,pos,400);
        	text("L        ", 100, 200);
        	text("  o       ", 100, 200+10);
        	text("    a      ", 100, 200);
        	text("      d     ", 100, 200);
        	text("        i    ", 100, 200);
        	text("         n   ", 100, 200);
        	text("           g  ", 100, 200);
        	text("              : ", 100, 200);
        	break;
        case 2:
        	pos = pos;
        	image(mr4,pos,400);
        	text("L        ", 100, 200);
        	text("  o       ", 100, 200);
        	text("    a      ", 100, 200+10);
        	text("      d     ", 100, 200);
        	text("        i    ", 100, 200);
        	text("         n   ", 100, 200);
        	text("           g  ", 100, 200);
        	text("              : ", 100, 200);
        	break;
        case 3:
        	pos = pos+5;
        	image(mr4,pos,400);
        	text("L        ", 100, 200);
        	text("  o       ", 100, 200);
        	text("    a      ", 100, 200);
        	text("      d     ", 100, 200+10);
        	text("        i    ", 100, 200);
        	text("         n   ", 100, 200);
        	text("           g  ", 100, 200);
        	text("              : ", 100, 200);
        	break;
        case 4:
        	pos = pos;
        	image(mr5,pos,400);
        	text("L        ", 100, 200);
        	text("  o       ", 100, 200);
        	text("    a      ", 100, 200);
        	text("      d     ", 100, 200);
        	text("        i    ", 100, 200+10);
        	text("         n   ", 100, 200);
        	text("           g  ", 100, 200);
        	text("              : ", 100, 200);
        	break;
        case 5:
        	pos = pos + 5;
        	image(mr5,pos,400);
        	text("L        ", 100, 200);
        	text("  o       ", 100, 200);
        	text("    a      ", 100, 200);
        	text("      d     ", 100, 200);
        	text("        i    ", 100, 200);
        	text("         n   ", 100, 200+10);
        	text("           g  ", 100, 200);
        	text("              : ", 100, 200);
        	break;
        case 6:
        	pos = pos;
        	image(mr0,pos,400);
        	text("L        ", 100, 200);
        	text("  o       ", 100, 200);
        	text("    a      ", 100, 200);
        	text("      d     ", 100, 200);
        	text("        i    ", 100, 200);
        	text("         n   ", 100, 200);
        	text("           g  ", 100, 200+10);
        	text("              : ", 100, 200);
        	break;
        case 7:
        	pos = pos;
        	image(mr0,pos,400);
        	text("L        ", 100, 200);
        	text("  o       ", 100, 200);
        	text("    a      ", 100, 200);
        	text("      d     ", 100, 200);
        	text("        i    ", 100, 200);
        	text("         n   ", 100, 200);
        	text("           g  ", 100, 200);
        	text("              : ", 100, 200+10);
        	break;
        case 8:
        	pos = pos;
        	image(mr3,pos,400);
        	text("L        ", 100, 200);
        	text("  o       ", 100, 200);
        	text("    a      ", 100, 200);
        	text("      d     ", 100, 200);
        	text("        i    ", 100, 200);
        	text("         n   ", 100, 200);
        	text("           g  ", 100, 200);
        	text("              : ", 100, 200);
        	break;
        case 9:
        	pos = pos;
        	image(mr0,pos,400);
        	text("L        ", 100, 200);
        	text("  o       ", 100, 200);
        	text("    a      ", 100, 200);
        	text("      d     ", 100, 200);
        	text("        i    ", 100, 200);
        	text("         n   ", 100, 200);
        	text("           g  ", 100, 200);
        	text("              : ", 100, 200);
        	break;
        default:
        	pos = pos;
        	image(mr3,pos,400);
        	text("L        ", 100, 200);
        	text("  o       ", 100, 200);
        	text("    a      ", 100, 200);
        	text("      d     ", 100, 200);
        	text("        i    ", 100, 200);
        	text("         n   ", 100, 200);
        	text("           g  ", 100, 200);
        	text("              : ", 100, 200);
        	break;
        }
        text(time+"%", 350, 200);
        
    }
    
    public void setTime(String newTime){
        time = newTime;
    }
}
