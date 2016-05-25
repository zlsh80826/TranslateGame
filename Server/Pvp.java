/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zlsh80826
 */
public class Pvp {
    Socket pA;
    Socket pB;
    InputStreamReader pAIn;
    InputStreamReader pBIn;
    OutputStreamWriter pAOut;
    OutputStreamWriter pBOut;
    Listener monitor;
    
    
    public Pvp(Listener monitor, Socket pA, Socket pB){
        this.pA = pA;
        this.pB = pB;
        this.monitor = monitor;
        try {
            pAIn = new InputStreamReader(pA.getInputStream());
            pBIn = new InputStreamReader(pB.getInputStream());
        } catch (IOException ex) {
            System.out.println("InputStream init error");
        }
        
        try {
            pAOut = new OutputStreamWriter(pA.getOutputStream());
            pBOut = new OutputStreamWriter(pB.getOutputStream());
        } catch (IOException ex) {
            System.out.println("OutputStream init error");
        }
        System.out.println("Connection establish");
    }
    
    
}
