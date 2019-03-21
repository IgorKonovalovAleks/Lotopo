package com.igorvenv.lotopo.items;

public abstract class Item {

    public static final int SWORD = 1;
    public static final int BOW = 2;
    public static final int ARMOR = 3;
    public static final int HAND = 0;


    protected int effect;
    protected int lvl;
    protected int delta;
    protected int type;

    public int getEffect(){
        return effect;
    }

    public int getLvl(){
        return lvl;
    }

    public void increaseLvl(){
        lvl++;
        effect += delta;
    }

    public int getType(){
        return type;
    }

}
