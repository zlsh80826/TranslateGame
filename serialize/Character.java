/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

import de.looksgood.ani.AniSequence;
import java.util.ArrayList;

/**
 *
 * @author zlsh80826
 */
public abstract class Character {

    public abstract void display();

    public abstract void setStand();

    public abstract void setMove();

    public abstract void setHit();

    public abstract void setAttack();

    public abstract void setClimb();

    public abstract void setReverse();

    public abstract Info getInfo();

    public abstract void setInfo(Info info);

    public abstract void introduction();

    public abstract void hideIntroduction();

    public abstract void setPos(float x, float y);

    public abstract void setLeft(StoryMap map);

    public abstract void setRight(StoryMap map);

    public abstract void jump();
    public float x;
    public float y;
    public boolean jumping;
    public ArrayList<Float> gravity;
    AniSequence aniseq;
    public boolean isDroping;

    public void setGravity() {
        gravity = new ArrayList<Float>();
        gravity.add(0f);
        gravity.add(1f);
        gravity.add(4f);
        gravity.add(9f);
        gravity.add(16f);
        gravity.add(25f);
        gravity.add(36f);
        gravity.add(49f);
    }
}
