/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import serialize.BaronInfo;
import serialize.DinosaurInfo;
import serialize.MonsterInfoPkg;
import serialize.MushroomInfo;
import serialize.SnowInfo;

/**
 *
 * @author zlsh80826
 */
public class ServerMonsterControl extends Thread {

    Socket pA;
    Socket pB;
    ObjectOutputStream pAOut;
    ObjectOutputStream pBOut;
    MonsterInfoPkg monsterInfoPkg;

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
        monsterInfoPkg = new MonsterInfoPkg();
        initPkg();
    }

    @Override
    public void run() {
        while (true) {
            try {
                sendMonsterInfo();
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerMonsterControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    final void initPkg() {
        Random random = new Random();
        for (int i = 0; i < 10; ++i) {
            monsterInfoPkg.addMushroomInfo(new MushroomInfo(random.nextInt(1350) + 20, 590f, false, 0, 50));
        }

        monsterInfoPkg.addBaronInfo(new BaronInfo(0f, 800f, false, 0, 8000));

        for (int i = 0; i < 3; ++i) {
            monsterInfoPkg.addSnowInfo(new SnowInfo(random.nextInt(630) + 480, 320f, false, 0, 400));
        }

        for (int i = 0; i < 2; ++i) {
            monsterInfoPkg.addDinosaurInfo(new DinosaurInfo(random.nextInt(200) + 100, 170f, false, 0, 1000));
        }

        for (int i = 0; i < 2; ++i) {
            monsterInfoPkg.addDinosaurInfo(new DinosaurInfo(random.nextInt(200) + 1150, 170f, false, 0, 1000));
        }
    }

    public void sendMonsterInfo() {
        try {
            pAOut.writeObject(monsterInfoPkg);
            pAOut.flush();
            pBOut.writeObject(monsterInfoPkg);
            pBOut.flush();
        } catch (IOException ex) {
            Logger.getLogger(Pvp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
