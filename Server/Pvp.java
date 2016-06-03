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
import java.util.logging.Level;
import java.util.logging.Logger;
import serialize.*;

/**
 *
 * @author zlsh80826
 */
public class Pvp extends Thread {
    Socket pA;
    Socket pB;
    ObjectInputStream pAIn;
    ObjectInputStream pBIn;
    ObjectOutputStream pAOut;
    ObjectOutputStream pBOut;
    Listener monitor;
    boolean loadComplete = false;
    boolean pALoadComplete = false;
    boolean pBLoadComplete = false;
    
    public Pvp(Listener monitor, Socket pA, Socket pB){
        this.pA = pA;
        this.pB = pB;
        this.monitor = monitor;
        try {
            pAIn = new ObjectInputStream(pA.getInputStream());
        } catch (IOException ex) {
            System.out.println("pAInputStream init error");
        }
        
        try {
            pBIn = new ObjectInputStream(pB.getInputStream());
        } catch (IOException ex) {
            System.out.println("pBInputStream init error");
        }
        
        try {
            pAOut = new ObjectOutputStream(pA.getOutputStream());
        } catch (IOException ex) {
            System.out.println("OutputStream init error");
        }

        try {
            pBOut = new ObjectOutputStream(pB.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Pvp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void run(){
        PvpListen pAHandler = new PvpListen(this, pAIn, 0);
        PvpListen pBHandler = new PvpListen(this, pBIn, 1);
        pBHandler.start();
        pAHandler.start();
        while(true){
            if(loadComplete){
                sendStartSelect();
            }
        }
    }
    
    public void sendStartSelect(){
        try {
            pAOut.writeObject(new Situation("startselect"));
            pBOut.writeObject(new Situation("startselect"));
        } catch (IOException ex) {
            System.out.println("send start select sitution error");
        }
    }
    
    public synchronized void setLoadComplete(int id){
        if(id == 0)
            pALoadComplete = true;
        else if(id == 1)
            pBLoadComplete = true;
        if( pALoadComplete && pBLoadComplete )
            loadComplete = true;
    }
}
