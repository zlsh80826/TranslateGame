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
import serialize.*;

/**
 *
 * @author zlsh80826
 */
public class PvpRear extends Thread {

    TranslateGame parent;
    Socket socket;
    PvpFront front;
    ObjectOutputStream out;
    ObjectInputStream in;
    ObjectOutputStream monsterOut;
    ObjectInputStream monsterIn;
    boolean isRunning;
    HandleMonster monsterHandler;

    Socket monsterSocket;

    PvpRear(TranslateGame parent, Socket socket, Socket monsterSocket) {
        this.parent = parent;
        this.socket = socket;
        this.monsterSocket = monsterSocket;
        this.isRunning = true;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("output Stream error");
        }
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("input Stream error");
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            recv();

            //need implements
        }
    }

    public void setFront(PvpFront front) {
        this.front = front;
        this.monsterHandler = new HandleMonster(front, monsterSocket);
    }

    public void end() {
        this.isRunning = false;
        this.front.stop();
    }

    public void recv() {
        try {
            SerialItem obj = (SerialItem) in.readObject();
            //System.out.println("Recv obj...");
            if (obj instanceof Situation) {
                this.parseSituation((Situation) obj);
            } else if (obj instanceof Info) {
                this.front.enemy.setInfo((Info) obj);
            } else if (obj instanceof ExpRequest) {
                this.front.hero.pulseExp(((ExpRequest) (obj)).exp);
            } else if (obj instanceof DmgRequest) {
                this.front.hero.setCurHp(((DmgRequest) (obj)).dmg);
            } else {
                System.out.println("Client recv unrecognize packet");
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(WaitRoomRear.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("QQ");
        }
    }

    void parseSituation(Situation status) {
        System.out.println("Status: " + status.getStatus());
        if ("startgame".equals(status.getStatus())) {
            //System.out.println(status.getCareer());
            //front.startGame(status.getCareer());
            monsterHandler.start();
            front.startGame(status.getInfo());
        }
    }

    public void sendLoadComplete() {
        try {
            out.writeObject(new Situation("loadcomplete"));
        } catch (IOException ex) {
            System.out.println("send complete status error");
        }
    }

    public void sendSelectComplete(Info info) {
        try {
            //out.writeObject(new Situation("selectcomplete", career));
            out.writeObject(new Situation("selectcomplete", info));
        } catch (IOException ex) {
            System.out.println("send complete status error");
        }
    }

    public void sendInfo(Info info) {
        try {
            out.writeObject(info);
            out.reset();
        } catch (IOException ex) {
            Logger.getLogger(PvpRear.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendExpRequest(int value) {
        try {
            out.writeObject(new ExpRequest(value));
            out.reset();
        } catch (IOException ex) {
            Logger.getLogger(PvpRear.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendDmgRequest(int value) {
        try {
            out.writeObject(new DmgRequest(value));
            out.reset();
        } catch (IOException ex) {
            Logger.getLogger(PvpRear.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
