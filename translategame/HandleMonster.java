/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import serialize.AttackRequest;
import serialize.MonsterInfoPkg;

/**
 *
 * @author zlsh80826
 */
public class HandleMonster extends Thread {

    PvpFront boss;
    Socket monsterSocket;
    ObjectInputStream in;
    ObjectOutputStream out;

    HandleMonster(PvpFront front, Socket monsterSocket) {
        this.boss = front;
        this.monsterSocket = monsterSocket;

        try {
            this.in = new ObjectInputStream(monsterSocket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(HandleMonster.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            this.out = new ObjectOutputStream(monsterSocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(HandleMonster.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        this.boss.setHM(this);
        while (true) {
            recv();
        }
    }

    public void recv() {
        try {
            Object obj = in.readObject();
            if (obj instanceof MonsterInfoPkg) {
                this.boss.setMonsterInfo((MonsterInfoPkg) obj);
            } else if (obj instanceof AttackRequest) {
                this.boss.enemy.attack();
                System.out.println("get request");
            } else{
                System.out.println("unregnize");
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(HandleMonster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void sendInfo(MonsterInfoPkg pkg) {
        try {
            out.writeObject(new MonsterInfoPkg(pkg));
            out.reset();
        } catch (IOException ex) {
            Logger.getLogger(HandleMonster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void sendAttackRequest() {
        try {
            out.writeObject(new AttackRequest());
            out.reset();
            System.out.println("!Control send request");
        } catch (IOException ex) {
            Logger.getLogger(HandleMonster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
