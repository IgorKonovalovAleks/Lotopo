package com.igorvenv.lotopo.units;

import com.igorvenv.lotopo.GameField;

import java.util.ArrayList;
import java.util.Random;

public class Player extends Unit {

    public static final int PLAYER_DEFAULT_ATK = 5;
    public static final int PLAYER_DEFAULT_DEF = 0;
    public static final int PLAYER_DEFAULT_HEALTH = 10;


    public Player() {
        this.atk = PLAYER_DEFAULT_ATK;
        this.def = PLAYER_DEFAULT_DEF;
        this.health = PLAYER_DEFAULT_HEALTH;
        this.type = GameField.PLAYER;
    }
}
