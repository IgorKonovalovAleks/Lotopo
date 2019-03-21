package com.igorvenv.lotopo.units;

import com.igorvenv.lotopo.GameField;

public class Dragon extends Unit {

    public static final int DEFENCE = 5;
    public static final int HEALTH = 100;

    public Dragon(){
        this.def = DEFENCE;
        this.health = HEALTH;
        this.type = GameField.DRAGON;

    }

}
