package com.igorvenv.lotopo;

import android.util.Log;

import com.igorvenv.lotopo.items.Armor;
import com.igorvenv.lotopo.items.Bow;
import com.igorvenv.lotopo.items.Hand;
import com.igorvenv.lotopo.items.Item;
import com.igorvenv.lotopo.items.Sword;
import com.igorvenv.lotopo.units.Container;
import com.igorvenv.lotopo.units.Dragon;
import com.igorvenv.lotopo.units.HedgeHog;
import com.igorvenv.lotopo.units.Player;
import com.igorvenv.lotopo.units.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameLogicController {

    public ArrayList<Unit> units; //every unit in the game
    public int[][] swamp;         //field for rendering

    private int dragonRate;
    private Item playerWeapon;
    private Item playerArmor;




    public GameLogicController(){
        dragonRate = 0;
        playerWeapon = new Hand();
        playerArmor = new Hand();
    }


    private void dragonTurn(){

        if (dragonRate < 2) {
            dragonRate++;
            return;
        } else dragonRate = 0;

        int[] p;
        p = getPosition(units.size() - 1);

        if (getDistance(getPosition(0), getPosition(units.size() - 1)) > 4.5){


            switch (new Random().nextInt(8)){

                case Unit.ROTATION_DOWN:
                    p[1]++;
                    if (isAble(p[0], p[1])) {
                        units.get(units.size() - 1).move(p[0], p[1]);
                    }
                    break;

                case Unit.ROTATION_UP:
                    p[1]--;
                    if (isAble(p[0], p[1])) {
                        units.get(units.size() - 1).move(p[0], p[1]);
                    }
                    break;

                case Unit.ROTATION_LEFT:
                    p[0]--;
                    if (isAble(p[0], p[1])) {
                        units.get(units.size() - 1).move(p[0], p[1]);
                    }
                    break;

                case Unit.ROTATION_RIGHT:
                    p[0]++;
                    if (isAble(p[0], p[1])) {
                        units.get(units.size() - 1).move(p[0], p[1]);
                    }
                    break;

                case Unit.ROTATION_LEFT_DOWN:
                    p[1]++;
                    p[0]--;
                    if (isAble(p[0], p[1])) {
                        units.get(units.size() - 1).move(p[0], p[1]);
                    }
                    break;

                case Unit.ROTATION_LEFT_UP:
                    p[0]--;
                    p[1]--;
                    if (isAble(p[0], p[1])) {
                        units.get(units.size() - 1).move(p[0], p[1]);
                    }
                    break;

                case Unit.ROTATION_RIGHT_DOWN:
                    p[1]++;
                    p[0]++;
                    if (isAble(p[0], p[1])) {
                        units.get(units.size() - 1).move(p[0], p[1]);
                    }
                    break;

                case Unit.ROTATION_RIGHT_UP:
                    p[0]++;
                    p[1]--;
                    if (isAble(p[0], p[1])) {
                        units.get(units.size() - 1).move(p[0], p[1]);
                    }
                    break;

            }

        } else if (getDistance(getPosition(0), getPosition(units.size() - 1)) < 2){

            units.get(0).hurt(units.get(units.size() - 1).getAtk());
            Log.d("GAME", "player has " + units.get(0).getHealth());

        } else {

            if (units.get(0).getX() > units.get(units.size() - 1).getX()) {
                p[0]++;
            } else {
                p[0]--;
            }

            if (units.get(0).getY() > units.get(units.size() - 1).getY()) {
                p[1]++;
            } else {
                p[1]--;
            }

            if (isAble(p[0], p[1])) {
                units.get(units.size() - 1).move(p[0], p[1]);
            }

        }

        updateSwamp();
    }


    private Unit find(int x, int y) {
        for (Unit unit: units) {
            if (unit.getY() == y && unit.getX() == x) {
                return unit;
            }
        }
        return null;
    }

    private int findNum(int x, int y) {
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).getY() == y && units.get(i).getX() == x) {
                return i;
            }
        }
        return -1;
    }


    private int whatIsThis(int x, int y){
        if (x > 9 || x < 0 || y > 7 || y < 0) return -1;
        for(int i = 0; i < 8; i++) {
            if (Arrays.equals(getPosition(i), new int[] {x, y})) {
                return units.get(i).getType();
            }
        }
        return -1;
    }

    private boolean isAble(int x, int y){
        if (x > 9 || x < 0 || y > 7 || y < 0) return false;
        for(int i = 0; i < 8; i++) {
            if (Arrays.equals(getPosition(i), new int[] {x, y})) {
                return false;
            }
        }
        return true;
    }

    private double getDistance(int[] unit1, int[] unit2){
        return Math.sqrt(Math.pow(unit1[0] - unit2[0], 2) + Math.pow(unit1[1] - unit2[1], 2));
    }

    private int[] getPosition(int index){
        return new int[] {units.get(index).getX(), units.get(index).getY()};
    }


    public void updateSwamp(){
        //set game params for player (atk, def)
        units.get(0).setParams(playerWeapon.getEffect(), playerArmor.getEffect());

        for (int i = 0; i < swamp.length; i++){
            for (int j = 0; j < swamp[0].length; j++)
            swamp[i][j] = 0;
        }
        for (Unit unit: units){
            Log.d("UPDATING SWAMP", unit.getClass().toString());

            if (unit.getType() == GameField.PLAYER) {

                swamp[unit.getX()][unit.getY()] = GameField.PLAYER;

            } else if (unit.getType() == GameField.HEDGEHOG) {

                if (getDistance(getPosition(0), new int[] {unit.getX(), unit.getY()}) <= 2.5) {
                    swamp[unit.getX()][unit.getY()] = GameField.HEDGEHOG;
                }

            } else if (unit.getType() == GameField.DRAGON) {

                if (getDistance(getPosition(0), getPosition(units.size() - 1)) <= 2.5) {
                    swamp[unit.getX()][unit.getY()] = GameField.DRAGON;
                }

            } else if (unit.getType() == GameField.ITEM){
                if (getDistance(getPosition(0), new int[] {unit.getX(), unit.getY()}) <= 2.5) {
                    swamp[unit.getX()][unit.getY()] = GameField.ITEM;
                }
            }
        }

        int x, y;

        x = getPosition(0)[0] - 2;
        y = getPosition(0)[1] + 2;
        for (int i = 0; i < 3; i++) {
            x++;
            if (isAble(x, y)) swamp[x][y] = GameField.VISIBLE_PLACE;
        }
        x++;

        for (int i = 0; i < 3; i++) {
            y--;
            if (isAble(x, y)) swamp[x][y] = GameField.VISIBLE_PLACE;
        }
        y--;

        for (int i = 0; i < 3; i++) {
            x--;
            if (isAble(x, y)) swamp[x][y] = GameField.VISIBLE_PLACE;
        }
        x--;

        for (int i = 0; i < 3; i++) {
            y++;
            if (isAble(x, y)) swamp[x][y] = GameField.VISIBLE_PLACE;
        }

        x = getPosition(0)[0] - 1;
        y = getPosition(0)[1] + 1;
        for (int i = 0; i < 2; i++) {
            if (isAble(x, y)) swamp[x][y] = GameField.ABLE_PLACE;
            x++;
        }

        for (int i = 0; i < 2; i++) {
            if (isAble(x, y)) swamp[x][y] = GameField.ABLE_PLACE;
            y--;
        }

        for (int i = 0; i < 2; i++) {
            if (isAble(x, y)) swamp[x][y] = GameField.ABLE_PLACE;
            x--;
        }

        for (int i = 0; i < 2; i++) {
            if (isAble(x, y)) swamp[x][y] = GameField.ABLE_PLACE;
            y++;
        }

    }

    public void handleTouch(int x, int y){
        Log.d("HANDLE_TOUCH", "start");
        if ((int)getDistance(getPosition(0), new int[] {x, y}) == 1) {
            Log.d("HANDLE_TOUCH", "passed");

            if (isAble(x, y)) units.get(0).move(x, y);
            else {
                switch (whatIsThis(x, y)) {

                    case GameField.DRAGON:

                        units.get(units.size() - 1).hurt(units.get(0).getAtk());
                        Log.d("GAME", "dragon has " + units.get(units.size() - 1).getHealth());

                        break;

                    case GameField.ITEM:

                        if (find(x, y).getItem().getType() != Item.ARMOR) {
                            this.playerWeapon = find(x, y).getItem();
                            Log.d("GAME", "player got weapon: " + playerWeapon.getClass().toString());

                        } else {
                            this.playerArmor = find(x, y).getItem();
                            Log.d("GAME", "player got armor " + units.get(0).getHealth());

                        }
                        units.remove(find(x, y));
                        break;

                    case GameField.PLAYER:

                        //TODO: open menu
                        break;

                    case GameField.HEDGEHOG:

                        find(x, y).hurt(units.get(0).getAtk());
                        Log.d("GAME", "hh has " + find(x, y).getHealth());

                        break;

                }
            }

            Log.d("HANDLE_TOUCH", "updating...");

            dragonTurn();

            updateSwamp();

        }
        else Log.d("HANDLE_TOUCH", Double.toString(getDistance(getPosition(0), new int[] {x, y})));
    }

    public int[][] generate(){
        swamp = new int[10][8];
        units = new ArrayList<>();
        units.addAll(Arrays.asList(new Player(), new Container(new Sword()),
                new Container(new Bow()),
                new Container(new Armor()),
                new HedgeHog(), new HedgeHog(), new HedgeHog(), new Dragon()));

        //generating player position
        int playerx = new Random().nextInt(8), playery = new Random().nextInt(8),
        dragonx, dragony;
        units.get(0).setX(playerx);
        units.get(0).setY(playery);

        //generating dragon position (far from player)
        if (playerx < 5) {
            dragonx = 8;
        } else {
            dragonx = 1;
        }
        units.get(7).setX(dragonx);


        if (playery < 4) {
            dragony = 6;
        } else {
            dragony = 1;
        }
        units.get(7).setY(dragony);

        //generating hedgehoges positions
        for (int i = 1; i < 7; i++){
            int[] x = new int[7], y = new int[7];

            while (true) {

                //generating random coords
                x[i] = new Random().nextInt(10);
                y[i] = new Random().nextInt(8);

                //check dragon and player coords
                if ((x[i] == playerx && y[i] == playery) ||
                        (x[i] == dragonx && y[i] == dragony)){
                    continue;
                }

                //check another hedgehogs coords
                boolean f = false;
                for (int n = 1; n < i; n++){
                    if (x[i] == units.get(n).getX() && y[i] == units.get(n).getY()) f = true;
                }
                if (f){
                    continue;
                }

                break;
            }
            units.get(i).setX(x[i]);
            units.get(i).setY(y[i]);
        }

        for(Unit u: units){
            Log.d("CHECK_INIT", Integer.toString(u.getX()));
            Log.d("CHECK_INIT", Integer.toString(u.getY()));
        }

        updateSwamp();

        return swamp;
    }

}
