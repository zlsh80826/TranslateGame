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
import serialize.*;


/**
 *
 * @author zlsh80826
 */
public class WaitRoom extends Thread {

    Socket player;
    ObjectOutputStream out;
    ObjectInputStream in;
    boolean isRunning;
    int time;

    WaitRoom(Listener parent, Socket socket) {
        this.player = socket;
        this.isRunning = true;
        this.time = 0;
        
        try {
            this.in = new ObjectInputStream(player.getInputStream());
        } catch (IOException ex) {
            System.out.println("Create InputStream with problem");
        }

        try {
            this.out = new ObjectOutputStream(player.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Create OutputStream with problem");
        } 
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                sendMessage(Integer.toString(time++));
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Thread Interrupt.");
            }
        }
        System.out.println("exit loop");
    }
    
    public void sendMessage(String message){
        try {
            out.writeObject(new Message(message));
        } catch (IOException ex) {
            System.out.println("send message error");
        }
    }
    
    public void end() throws IOException{
        out.writeObject(new PvpRoomType());
        isRunning = false;
    }
}
