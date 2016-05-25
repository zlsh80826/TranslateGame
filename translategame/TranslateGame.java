/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translategame;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            System.out.println("?");
            InputStream tmpStream = socket.getInputStream();
            System.out.println("???");
            ObjectInputStream tmp = new ObjectInputStream(tmpStream);
            System.out.println(count);
            System.out.flush();
            if(count == 1){
                Request request = (Request)tmp.readObject();
                System.out.println(request.toString());
            }
            count ++;
        } catch (IOException ex) {
            System.out.println("Connect error");
        } catch (ClassNotFoundException ex) {
            System.out.println("Csst error");
        }
    }
    
    static public void main(String[] args){
        TranslateGame game = new TranslateGame();
        game.start();
    }
}
