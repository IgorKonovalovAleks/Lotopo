package com.igorvenv.lotopo.items;

public class Bow extends Weapon {

    public static final int DEFAULT_ATK = 7;
    public static final double DISTANCE = 2.5;
    public static final int DELTA = 4;

    public Bow() {
        this.type = Item.BOW;
        this.delta = DELTA;
        this.effect = DEFAULT_ATK;
        this.distance = DISTANCE;
        this.lvl = 1;
    }

}
