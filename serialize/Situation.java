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
public class Situation implements Serializable{
    String status;
    public Situation(String str){
        this.status = str;
    }
    
    public String getStatus(){
        return status;
    }
}
