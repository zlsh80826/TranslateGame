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
import serialize.AttackRequest;
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
            Object obj = in.readObject();
            if(obj instanceof MonsterInfoPkg)
                parent.setPkg((MonsterInfoPkg)obj);
            else if(obj instanceof AttackRequest){
               // System.out.println("Server get Attack Request");
                parent.sendAttackRequest();
            }else{
                System.out.println("recv unregnize");
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(MonsterListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
