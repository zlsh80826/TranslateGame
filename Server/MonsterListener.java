/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import serialize.MonsterInfoPkg;

/**
 *
 * @author zlsh80826
 */
public class MonsterListener extends Thread {
    ObjectInputStream in;
    ServerMonsterControl parent;
    public MonsterListener(ServerMonsterControl parent, ObjectInputStream in){
        this.in = in;
        this.parent = parent;
    }
    
    @Override
    public void run(){
        while(true){
            recv();
        }
    }
    
    public synchronized void recv(){
        try {
            MonsterInfoPkg pkg = (MonsterInfoPkg)in.readObject();
            System.out.println(pkg.mushroomInfos.get(0).x);
            parent.setPkg(pkg);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(MonsterListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
