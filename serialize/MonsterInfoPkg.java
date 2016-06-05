/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author zlsh80826
 */
public class MonsterInfoPkg implements Serializable{

    public ArrayList<MushroomInfo> mushroomInfos;
    public ArrayList<BaronInfo> baronInfos;
    public ArrayList<SnowInfo> snowInfos;
    public ArrayList<DinosaurInfo> dinosaurInfos;

    public MonsterInfoPkg() {
        mushroomInfos = new ArrayList<MushroomInfo>();
        baronInfos = new ArrayList<BaronInfo>();
        snowInfos = new ArrayList<SnowInfo>();
        dinosaurInfos = new ArrayList<DinosaurInfo>();
    }
    
    public MonsterInfoPkg(MonsterInfoPkg pkg){
        this.baronInfos = pkg.baronInfos;
        this.dinosaurInfos = pkg.dinosaurInfos;
        this.mushroomInfos = pkg.mushroomInfos;
        this.snowInfos = pkg.snowInfos;
    }
    
    public void addMushroomInfo(MushroomInfo mushroomInfo){
        mushroomInfos.add(mushroomInfo);
    }
    
    public void addBaronInfo(BaronInfo info){
        baronInfos.add(info);
    }
    
    public void addSnowInfo(SnowInfo info){
        snowInfos.add(info);
    }
    
    public void addDinosaurInfo(DinosaurInfo info){
        dinosaurInfos.add(info);
    }
}
