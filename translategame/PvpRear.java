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
        System.out.println("Rear run");
        //front.init();
        System.out.println("Init complete");
        while (isRunning) {
            Request request = recv();
            
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

    public Request recv() {
        try {
            Request request = (Request) in.readObject();
            return request;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(WaitRoomRear.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void sendLoadComplete(){
        System.out.println("loadcomplete");
        try {
            out.writeObject(new Situation("loadcomplete"));
        } catch (IOException ex) {
            System.out.println("send complete status error");
        }
    }
}
