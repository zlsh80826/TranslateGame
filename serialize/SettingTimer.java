/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import java.util.TimerTask;

/**
 *
 * @author zlsh80826
 */
public class SettingTimer extends TimerTask {
    Character ch;
    boolean value;
    String action;
    
    SettingTimer(Character ch, String action, boolean value){
        this.ch = ch;
        this.value = value;
        this.action = action;
    }
    @Override
    public void run() {
        if("setHit".equals(action)){
            ch.setHit(value);
        }
    }
    
}
