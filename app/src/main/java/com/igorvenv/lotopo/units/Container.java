package com.igorvenv.lotopo.units;

import com.igorvenv.lotopo.GameField;
import com.igorvenv.lotopo.items.Item;

public class Container extends Unit{

    public static final int TYPE_SWORD = 0;
    public static final int TYPE_ARMOR = 1;
    public static final int TYPE_BOW = 2;


    public Container(Item i){
        this.item = i;
        this.type = GameField.ITEM;

    }

}
