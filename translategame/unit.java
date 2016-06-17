package translategame;

import processing.core.PApplet;
import processing.core.PImage;
import serialize.Dinosaur;

public class unit extends PApplet {
	public float cur_blood, total_blood, atk_value;		
	public int side;		//side 0 for player 1 for computer
	public float x, y, cosion_size, atk_range;
	public String type;
	//String img_src;
	public Dinosaur dinosaur;
	public PImage image;
	public int state; // 0 for move, 1 for atk
	public int cooldown_left, cooldown_value, cost;
	int value;
	
	unit(String type, int side) {
		switch (type)
		{
		case "dino":
			this.type = type;
			this.total_blood= 40;
			this.cur_blood = this.total_blood;
			this.atk_range = 10;
			this.cosion_size = 35;
			this.x = 100;
			this.y = 530;
			this.state = 0;
			this.atk_value = 5;
			this.cooldown_value = 5;
			this.cost = 30;
			this.value = 35;
			if(side == 0) {
				this.image = loadImage("src/material/monster/Dinosaur/DinosaurAttackR012.png");
		    } else {
		    	this.image = loadImage("src/material/monster/Dinosaur/DinosaurAttack012.png");
		    	this.x = 1000;
			}
			break;
		case "mush":
			this.type = type;
			this.total_blood= 10;
			this.cur_blood = this.total_blood;
			this.atk_range = 100;
			this.cosion_size = 30;
			this.x = 100;
			this.y = 530;
			this.state = 0;
			this.atk_value = 8;
			this.cooldown_value = 1;
			this.cost = 50;
			this.value = 35;
			if(side == 0) {
				this.image = loadImage("src/material/monster/Mushroom/jump_r.png");
		    } else {
		    	this.image = loadImage("src/material/monster/Mushroom/jump.png");
		    	this.x = 1000;
			}
			break;
		case "snowgila":
			this.type = type;
			this.total_blood= 100;
			this.cur_blood = this.total_blood;
			this.atk_range = 5;
			this.cosion_size = 40;
			this.x = 100;
			this.y = 530;
			this.state = 0;
			this.atk_value = 3;
			this.cooldown_value = 1;
			this.cost = 100;
			this.value = 120;
			if(side == 0) {
				this.image = loadImage("src/material/monster/Snow/SnowAttackR000.png");
		    } else {
		    	this.image = loadImage("src/material/monster/Snow/SnowAttack000.png");
		    	this.x = 1000;
			}
			break;
		}
	}
}
