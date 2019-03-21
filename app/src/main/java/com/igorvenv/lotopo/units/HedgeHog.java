package com.igorvenv.lotopo.units;

import com.igorvenv.lotopo.GameField;

public class HedgeHog extends Unit {

    public static final int ATK = 3;
    public static final int DEF = 0;
    public static final int HEALTH = 5;

    public HedgeHog() {
        this.atk = ATK;
        this.def = DEF;
        this.health = HEALTH;
        this.type = GameField.HEDGEHOG;

    }

}
