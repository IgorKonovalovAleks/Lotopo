package com.igorvenv.lotopo.items;

public class Hand extends Weapon {

    public static final int DEFAULT_ATK = 3;
    public static final double DISTANCE = 1.5;
    public static final int DELTA = 0;

    public Hand() {
        this.type = Item.HAND;
        this.delta = DELTA;
        this.effect = DEFAULT_ATK;
        this.distance = DISTANCE;
        this.lvl = 1;
    }

}
