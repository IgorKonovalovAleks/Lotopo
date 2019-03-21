package com.igorvenv.lotopo.items;

public class Armor extends Item {

    public static final int DEFAULT_DEF = 1;
    public static final int DELTA = 1;

    public Armor(){
        this.type = Item.ARMOR;

        this.effect = DEFAULT_DEF;
        this.lvl = 1;
        this.delta = DELTA;


    }

}
