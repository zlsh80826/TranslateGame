/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package single;

import java.util.ArrayList;
import java.util.TimerTask;
import translategame.unit;

/**
 *
 * @author zlsh80826
 */
public class RemoveTask extends TimerTask {
    ArrayList<unit> a;
    ArrayList<Monster> b;
    int i;
    public RemoveTask(ArrayList<unit> a, ArrayList<Monster> b, int i){
        this.a = a;
        this.b = b;
        this.i = i;
    }
    @Override
    public void run() {
        if(a.size() > i)
            a.remove(i);
        if(b.size() > i)
            b.remove(i);
    }
    
}
