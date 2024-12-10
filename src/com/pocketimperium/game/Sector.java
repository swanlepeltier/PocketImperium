package com.pocketimperium.game;

import java.util.ArrayList;

public class Sector {
    private ArrayList<Hex> hexList = new ArrayList<>();

    public Sector(String type){
        if("top".equals(type)){
            hexList.add(new Hex());
        }
    }
}
