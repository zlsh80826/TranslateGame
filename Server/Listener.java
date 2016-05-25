/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import serialize.*;
/**
 *
 * @author zlsh80826
 */
public class Listener implements Runnable{
    Monitor monitor;
    ServerSocket listenSocket;
    int playerWaitCounter;
    int port;
    Socket player1;
    Socket player2;
    WaitRoom waitroom;
    
    Listener(Monitor monitor){
        this.monitor = monitor;
        this.playerWaitCounter = 0;
        this.port = 8888;
        try {
            this.listenSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Server Socket Create failed\n");
        }
        monitor.addText("\nServer is listening on port " + port + "\n");
    }

    @Override
    public void run() {
        while(true){
            System.out.println(playerWaitCounter);
            if(playerWaitCounter == 0){
                try {
                    player1 = listenSocket.accept();
                    String address = player1.getInetAddress().toString();               
                    monitor.addText("\nConnection from " + address.substring(1) + " : " + player1.getPort());
                    ObjectOutputStream out = new ObjectOutputStream(player1.getOutputStream());
                    out.writeObject(new WaitRoomType());
                    waitroom = new WaitRoom(this, player1);
                    waitroom.start();
                } catch (IOException ex) {
                    System.out.println("waitroom exists error");
                }
                playerWaitCounter ++;
            }else if(playerWaitCounter == 1){
                try {
                    player2 = listenSocket.accept();
                    String address= player2.getInetAddress().toString();
                    monitor.addText("\nConnection from " + address.substring(1) + " : " + player2.getPort());
                    waitroom.end();
                    ObjectOutputStream out = new ObjectOutputStream(player2.getOutputStream());
                    out.writeObject(new PvpRoomType());
                    waitroom.join();
                    Pvp pvp = new Pvp(this, player1, player2);
                    pvp.start();
                } catch (IOException ex) {
                    System.out.println("PVP exists error");
                } catch (InterruptedException ex) {
                    Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
                }
                playerWaitCounter = 0;                
            }
        }        
    }
}
