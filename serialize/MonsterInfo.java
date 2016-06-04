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
public class MonsterInfo implements Serializable{
    public int curHp;
    public int maxHp;
    public int action;
    public int reverse;
    public float x;
    public float y;
    
    public MonsterInfo(float x, float y, int reverse, int action, int curHp, int maxHp){
        this.x = x;
        this.y = y;
        this.reverse = reverse;
        this.action = action;
        this.curHp = curHp;
        this.maxHp = maxHp;        
    }
}
