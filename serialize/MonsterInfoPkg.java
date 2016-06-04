/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import java.util.ArrayList;

/**
 *
 * @author zlsh80826
 */
public class MonsterInfoPkg {

    public ArrayList<MushroomInfo> mushroomInfos;
    public ArrayList<BaronInfo> baronInfos;
    public ArrayList<SnowInfo> snowInfos;
    public ArrayList<DinosaurInfo> dinosaurInfos;

    MonsterInfoPkg() {
        mushroomInfos = new ArrayList<MushroomInfo>();
        baronInfos = new ArrayList<BaronInfo>();
        snowInfos = new ArrayList<SnowInfo>();
        dinosaurInfos = new ArrayList<DinosaurInfo>();
    }
    
    public void addMushroomInfos(MushroomInfo mushroomInfo){
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
