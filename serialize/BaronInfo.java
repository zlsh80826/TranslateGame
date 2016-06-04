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
public class BaronInfo extends MonsterInfo implements Serializable  {
    
    public BaronInfo(float x, float y, boolean reverse, int action, int curHp) {
        super(x, y, reverse, action, curHp);
    }
    
}
