/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import de.looksgood.ani.Ani;
import java.util.TimerTask;

/**
 *
 * @author zlsh80826
 */
public class SettingTimer extends TimerTask {
    
    Character ch;
    boolean value;
    Action action;
    
    SettingTimer(Character ch, Action action, boolean value) {
        this.ch = ch;
        this.value = value;
        this.action = action;
    }
    
    @Override
    public void run() {
        if (Action.HIT.equals(action)) {
            ch.setAction(Action.STAND);
            ch.setHit(value);
        } else if (Action.ATTACK.equals(action)) {
            if(ch.isMoving())
                ch.setAction(Action.MOVE);
            else
                ch.setAction(Action.STAND);
        } else if (action == Action.JUMP) {
            Ani.to(ch, 0.2f, "y", ch.y + 50, Ani.SINE_IN);
        }
    }
    
}
