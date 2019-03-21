package com.igorvenv.lotopo.units;

import android.support.annotation.NonNull;

import com.igorvenv.lotopo.items.Item;

public abstract class Unit {

    public static final int ROTATION_RIGHT = 0;
    public static final int ROTATION_LEFT = 1;
    public static final int ROTATION_UP = 2;
    public static final int ROTATION_DOWN = 3;
    public static final int ROTATION_RIGHT_UP = 4;
    public static final int ROTATION_LEFT_UP = 5;
    public static final int ROTATION_LEFT_DOWN = 6;
    public static final int ROTATION_RIGHT_DOWN = 7;

    protected int x;
    protected int y;
    protected int atk;
    protected int def;
    protected int health;
    protected int type;
    protected Item item;

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setParams(int a, int d){
        atk = a;
        def = d;
    }

    public int getAtk(){
        return atk;
    }

    public void hurt(int delta) {this.health += delta - delta * def / 10;}

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getType(){return this.type;}

    public Item getItem(){return this.item;}

    public int getHealth(){return this.health;}

}
