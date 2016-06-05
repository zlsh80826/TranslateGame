/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import java.io.Serializable;

/**
 *
 * @author zlsh80826
 */
public class Info extends SerialItem implements Serializable{
    public int action;
    public float x;
    public float y;
    public boolean reverse;
    public int curHp;
    public int LV;
    public int exp;
    public int curMp;
    
    public Career career;
    
    Info(int action, float x, float y, boolean reverse, Career career, int curHp, int LV, int exp, int curMp){
        this.action = action;
        this.x = x;
        this.y = y;
        this.reverse = reverse;
        this.career = career;
        this.curHp = curHp;
        this.LV = LV;
        this.exp = exp;
        this.curMp = curMp;
    }
}
