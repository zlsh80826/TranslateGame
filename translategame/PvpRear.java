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
    boolean isRunning;

    PvpRear(TranslateGame parent, Socket socket) {
        this.parent = parent;
        this.socket = socket;
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
    }

    public void end() {
        this.isRunning = false;
        this.front.stop();
    }

    public void recv() {
        try {
            Object obj = in.readObject();
            System.out.println("Recv obj...");
            if (obj instanceof Situation) {
                this.parseSituation((Situation) obj);
            } else if(obj instanceof Info){
                this.front.enemy.setInfo((Info)obj);
            }else {
                System.out.println("Client recv unrecognize packet");
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(WaitRoomRear.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void parseSituation(Situation status) {
        System.out.println("Status: " + status.getStatus());
        if ("startgame".equals(status.getStatus())) {
            //System.out.println(status.getCareer());
            //front.startGame(status.getCareer());
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
    
    public void sendInfo(Info info){
        try {
            out.writeObject(info);
        } catch (IOException ex) {
            Logger.getLogger(PvpRear.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
