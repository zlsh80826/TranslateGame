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
            Object obj = in.readObject();
            if (obj instanceof Situation) {
                this.parseSituation((Situation) obj);
            } else if (obj instanceof Info) {
                if (null != ((Info) obj).cmd) {
                    switch (((Info) obj).cmd) {
                        case "info":
                            this.front.enemy.setInfo((Info) obj);
                            break;
                        case "exp":
                            this.front.hero.pulseExp(((Info) obj).exp);
                            break;
                        case "dmg":
                            this.front.hero.setCurHp(((Info) (obj)).dmg);
                            break;
                        case "time":
                            this.front.time = Integer.toString(((Info) obj).dmg);
                        default:
                            break;
                    }
                }
            } else {
                System.out.println("Client recv unrecognize packet");
                in.reset();
            }
        } catch (IOException | ClassNotFoundException ex) {
            try {
                in.close();
                in = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex1) {
                Logger.getLogger(PvpRear.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
        }
    }

    void parseSituation(Situation status) {
        System.out.println("Status: " + status.getStatus());
        if ("startgame".equals(status.getStatus())) {
            monsterHandler.start();
            front.startGame(status.getInfo());
        } else if ("startpvp".equals(status.getStatus())) {
            front.pvp();
        }
    }

    public void sendLoadComplete() {
        try {
            out.writeObject(new Situation("loadcomplete"));
            out.flush();
        } catch (IOException ex) {
            System.out.println("send complete status error");
        }
    }

    public void sendSelectComplete(Info info) {
        try {
            //out.writeObject(new Situation("selectcomplete", career));
            out.writeObject(new Situation("selectcomplete", info));
            out.flush();
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
            out.writeObject(new Info(0, 0, 0, false, Career.SwordMan, 0, 0, value, 0, false, 0, 0, false, 0, "exp"));
            out.reset();
        } catch (IOException ex) {
            Logger.getLogger(PvpRear.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendDmgRequest(int value) {
        try {
            out.writeObject(new Info(0, 0, 0, false, Career.SwordMan, 0, 0, 0, 0, false, 0, 0, false, value, "dmg"));
            out.reset();
        } catch (IOException ex) {
            Logger.getLogger(PvpRear.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
