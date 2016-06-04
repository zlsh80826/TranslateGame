/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialize;

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
    public float x;
    public float y;
}
