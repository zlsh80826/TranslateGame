/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import serialize.*;

/**
 *
 * @author zlsh80826
 */
public class PvpListen extends Thread {

    ObjectInputStream in;
    Pvp parent;
    int identify;

    PvpListen(Pvp pvp, ObjectInputStream in, int identify) {
        this.in = in;
        this.parent = pvp;
        this.identify = identify;
    }

    @Override
    public void run() {
        while (true) {
            recv();
        }
    }

    public void recv() {
        try {
            SerialItem pkg = (SerialItem) in.readObject();
            //System.out.println("Recv Obj...");
            if (pkg instanceof Situation) {
                parseSituation((Situation) pkg);
            } else if(pkg instanceof Info){
                parent.sendInfoToPeer(identify, (Info)pkg);
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("recv packet error");
            Logger.getLogger(PvpListen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void parseSituation(Situation pkg) {
        System.out.println("Status:" + pkg.getStatus());
        if ("loadcomplete".equals(pkg.getStatus())) {
            parent.setLoadComplete(identify);
        } else if ("selectcomplete".equals(pkg.getStatus())) {
            //System.out.println( identify + " " + pkg.getCareer() );
            //parent.setSelectComplete(identify, pkg.getCareer());
            parent.setSelectComplete(identify, pkg.getInfo());
        }
    }
}
