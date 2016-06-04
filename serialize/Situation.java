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
public class Situation implements Serializable {

    String status;
    Career career;
    serialize.Character character;
    Info info;

    public Situation(String str) {
        this.status = str;
    }

    public Situation(String str, Career career) {
        this.status = str;
        this.career = career;
    }
    
    public Situation(String str, Character character){
        this.status = str;
        this.character = character;
    }
    
    public Situation(String str, Info info){
        this.status = str;
        this.info = info;
    }
    
    public Info getInfo(){
        return this.info;
    }

    public String getStatus() {
        return status;
    }

    public Career getCareer() {
        return career;
    }
    
    public Character getChar(){
        return character;
    }
}
