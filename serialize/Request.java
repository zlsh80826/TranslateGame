/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import java.io.Serializable;

/**
 *
 * @author zlsh80826
 */
public class Request implements Serializable{
    String command;
    public Request(String command){
        this.command = command;
    }
    
    @Override
    public String toString(){
        return command;
    }
}
