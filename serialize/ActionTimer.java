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
public class ActionTimer extends TimerTask {

    Character ch;
    Action action;

    ActionTimer(Character ch, Action action) {
        this.ch = ch;
        this.action = action;
    }

    @Override
    public void run() {
        if (action == Action.HIT) {
            this.ch.setInvincible(false);
        } else if (action == Action.ATTACK) {
            this.ch.setAttacking(false);
        } else if (action == Action.JUMP) {
            this.ch.setJumping(false);
        } else if (action == Action.COOLDOWN) {
            this.ch.cooldown = false;
        } else if (action == Action.DIE) {
            this.ch.setInvincible(false);
            this.ch.freeze = false;
        }
    }
}
