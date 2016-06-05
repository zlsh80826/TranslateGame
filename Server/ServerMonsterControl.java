/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import serialize.Action;
import serialize.BaronInfo;
import serialize.DinosaurInfo;
import serialize.MonsterInfoPkg;
import serialize.MonsterTimer;
import serialize.MushroomInfo;
import serialize.SnowInfo;
import java.util.Timer;

/**
 *
 * @author zlsh80826
 */
public class ServerMonsterControl extends Thread {

    Socket pA;
    Socket pB;
    ObjectOutputStream pAOut;
    ObjectOutputStream pBOut;
    ObjectInputStream pAIn;
    ObjectInputStream pBIn;
    MonsterInfoPkg monsterInfoPkg;
    Random random = new Random();
    Timer timer = new Timer();
    MonsterListener MLA;
    MonsterListener MLB;

    ServerMonsterControl(Socket pA, Socket pB) {
        this.pA = pA;
        this.pB = pB;
        try {
            pAOut = new ObjectOutputStream(pA.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerMonsterControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pBOut = new ObjectOutputStream(pB.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerMonsterControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pAIn = new ObjectInputStream(pA.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerMonsterControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pBIn = new ObjectInputStream(pB.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerMonsterControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        monsterInfoPkg = new MonsterInfoPkg();
        MLA = new MonsterListener(this, pAIn);
        MLB = new MonsterListener(this, pBIn);
    }

    @Override
    public void run() {

        MLA.start();
        //MLB.start();
        while (true) {
            try {
                sendMonsterInfo();
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerMonsterControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void sendMonsterInfo() {
        try {
            //pAOut.writeObject(monsterInfoPkg);
            //pAOut.flush();
            pBOut.writeObject(monsterInfoPkg);
            pBOut.flush();
        } catch (IOException ex) {
            Logger.getLogger(Pvp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void setPkg(MonsterInfoPkg pkg) {        
        this.monsterInfoPkg = pkg;
        System.out.println(pkg.mushroomInfos.get(0).x);
    }

}
