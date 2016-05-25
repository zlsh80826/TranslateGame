/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            if(playerWaitCounter == 0){
                try {
                    player1 = listenSocket.accept();
                    String address = player1.getInetAddress().toString();               
                    monitor.addText("\nConnection from " + address.substring(1) + " : " + player1.getPort());
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                playerWaitCounter ++;
            }else if(playerWaitCounter == 1){
                try {
                    player2 = listenSocket.accept();
                    String address= player2.getInetAddress().toString();
                    monitor.addText("\nConnection from " + address.substring(1) + " : " + player2.getPort());
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                playerWaitCounter = 0;                
            }
        }        
    }
}
