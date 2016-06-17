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
import javax.swing.JFrame;
import serialize.*;

/**
 *
 * @author zlsh80826
 */
public class TranslateGame {

    JFrame frame;
    LoginPaint loginFrame;
    String serverAddress;
    int serverPort;
    //Socket socket;

    //test variable
    int count = 0;

    public TranslateGame() {
        frame = new JFrame("Translate Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1134, 704);
        frame.setVisible(true);
        /* Need to implement
        frame.setShape(shape);
        frame.setIconImage(image);        
         */
        frame.setLocation(550, 100);//need to tune      
        serverAddress = "192.168.1.177";
        serverPort = 8888;
    }

    public void start() {
        loginFrame = new LoginPaint(this);
        frame.setContentPane(loginFrame);
        loginFrame.init();
        loginFrame.start();
    }

    public void connect(String account, String password) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(serverAddress, serverPort);
        Socket monsterSocket = new Socket(serverAddress, serverPort);
        ObjectInputStream out = new ObjectInputStream(socket.getInputStream());

        RoomType type = (RoomType) out.readObject();
        if (type instanceof WaitRoomType) {
            createWaitRoom(socket, monsterSocket);
            createPvpRoom(socket, monsterSocket, true);
        } else if (type instanceof PvpRoomType) {
            createPvpRoom(socket, monsterSocket, false);
        }
    }

    public void createWaitRoom(Socket socket, Socket monsterSocket) {
        WaitRoomRear waitRear = new WaitRoomRear(this, socket);
        WaitRoomFront waitFront = new WaitRoomFront(this, waitRear);
        waitRear.setFront(waitFront);
        frame.setContentPane(waitFront);
        waitFront.init();
        waitRear.start();
        waitFront.start();
        try {
            waitRear.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(TranslateGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createPvpRoom(Socket socket, Socket monsterSocket, boolean control) {
        PvpRear pvpRear = new PvpRear(this, socket, monsterSocket);
        PvpFront pvpFront = new PvpFront(this, pvpRear, control);
        pvpRear.setFront(pvpFront);
        frame.setContentPane(pvpFront);
        pvpFront.init();
        pvpRear.start();
        pvpFront.start();
        pvpRear.front.initial();
    }

    static public void main(String[] args) {
        TranslateGame game = new TranslateGame();
        game.start();
    }

    public void createSingle() {
        Single single = new Single(this);
        frame.setContentPane(single);
        single.init();
        single.start();
    }
}
