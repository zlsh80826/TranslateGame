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
public class SnowInfo extends MonsterInfo implements Serializable {
    
    public SnowInfo(float x, float y, int reverse, int action, int curHp, int maxHp) {
        super(x, y, reverse, action, curHp, maxHp);
    }
    
}
