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
    public Career career;
    
    Info(int action, float x, float y, boolean reverse, Career career){
        this.action = action;
        this.x = x;
        this.y = y;
        this.reverse = reverse;
        this.career = career;
    }
}
