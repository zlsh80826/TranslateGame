/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author zlsh80826
 */
public class Server {

    static public void main(String[] args) {
        Monitor monitor = new Monitor();
        Listener listener = new Listener(monitor);
        Thread listenDeamon = new Thread(listener);
        listenDeamon.start();
    }
}
