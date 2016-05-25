/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import java.io.IOException;
import java.net.Socket;
import javax.swing.JFrame;
import serialize.Request;

/**
 *
 * @author zlsh80826
 */
public class TranslateGame {
    JFrame frame;
    LoginPaint loginFrame;
    //Socket socket;
    
    //test variable
    int count = 0;
    
    public TranslateGame(){
        frame = new JFrame("Translate Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1134, 704);
        frame.setVisible(true);
        /* Need to implement
        frame.setShape(shape);
        frame.setIconImage(image);        
        */
        frame.setLocation(550, 100);//need to tune      
    }
    
    public void start(){
        loginFrame = new LoginPaint(this);
        frame.setContentPane(loginFrame);
        loginFrame.init();
        loginFrame.start();  
    }
    
    public void connect(String account, String password){
        try {
            Socket socket = new Socket("127.0.0.1", 8888);
            WaitRoomRear waitRear = new WaitRoomRear(this, socket);
            WaitRoomFront waitFront = new WaitRoomFront(this, waitRear);
            waitRear.setFront(waitFront);
            frame.setContentPane(waitFront);
            waitFront.init();
            waitRear.start();
            waitFront.start();
        } catch (IOException ex) {
            System.out.println("Connect error");
        }
    }
    
    static public void main(String[] args){
        TranslateGame game = new TranslateGame();
        game.start();
    }
}
