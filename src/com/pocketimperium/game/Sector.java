package com.pocketimperium.game;

import java.util.ArrayList;

public class Sector {
    private final String nom;
    private final ArrayList<Hex> hexagones;

    public Sector(String nom) {
        this.nom = nom;
        this.hexagones = new ArrayList<>();
        ArrayList<Integer> availableSystem = new ArrayList<>();

        availableSystem.add(2);
        availableSystem.add(1);
        availableSystem.add(1);

        for (int i = 0; i < 4; i++){
            availableSystem.add(0);
        }
        
        for (int i = 0; i < 7; i++){
            int randomSystem = (int) (Math.random()*availableSystem.size());
            hexagones.add(new Hex(i, nom, availableSystem.get(randomSystem)));
            availableSystem.remove(randomSystem);
        }

        // Index des hexagones :
        //        1   2
        //      0   C   3
        //        5   4
        // C est le centre (hexagone 6)

        Hex center = hexagones.get(6);
        hexagones.get(0).addNeighbor(center); // 0 connecté à C
        hexagones.get(1).addNeighbor(center); // 1 connecté à C
        hexagones.get(2).addNeighbor(center); // etc.
        hexagones.get(3).addNeighbor(center);
        hexagones.get(4).addNeighbor(center);
        hexagones.get(5).addNeighbor(center);

        // Ajouter les connexions périphériques
        hexagones.get(0).addNeighbor(hexagones.get(1));
        hexagones.get(1).addNeighbor(hexagones.get(2));
        hexagones.get(2).addNeighbor(hexagones.get(3));
        hexagones.get(3).addNeighbor(hexagones.get(4));
        hexagones.get(4).addNeighbor(hexagones.get(5));
        hexagones.get(5).addNeighbor(hexagones.get(0));

    }

    @Override
    public String toString(){
        String result = "";
        for(Hex hex : hexagones){
            result += hex.toString() + "\n";
        }
        return result;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Hex> getHexs() {
        return hexagones;
    }

}
