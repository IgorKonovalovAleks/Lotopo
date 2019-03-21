package com.igorvenv.lotopo.items;

public class Sword extends Weapon {

    public static final int DEFAULT_ATK = 10;
    public static final double DISTANCE = 1.5;
    public static final int DELTA = 5;

    public Sword() {
        this.type = Item.SWORD;

        this.delta = DELTA;
        this.effect = DEFAULT_ATK;
        this.distance = DISTANCE;
        this.lvl = 1;
    }

}
