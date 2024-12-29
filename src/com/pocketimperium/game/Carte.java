package com.pocketimperium.game;

import java.io.Serializable;
import java.util.ArrayList;

public class Carte implements Serializable{

    private final ArrayList<Sector> sectors = new ArrayList<>();

    public Carte(){
        for (int i = 0; i < 6; i++){
            Sector sector = new Sector(i);
            sectors.add(sector);
        }
        
        sectors.add(new TriPrime(6));

        // Ajouter les voisins inter-secteur

        // sector0
        sectors.get(0).getHexs().get(2).addNeighbor(sectors.get(1).getHexs().get(0));
        sectors.get(0).getHexs().get(2).addNeighbor(sectors.get(1).getHexs().get(5));
        sectors.get(0).getHexs().get(3).addNeighbor(sectors.get(1).getHexs().get(5));

        //  sector1
        sectors.get(1).getHexs().get(3).addNeighbor(sectors.get(2).getHexs().get(1));
        sectors.get(1).getHexs().get(3).addNeighbor(sectors.get(2).getHexs().get(0));
        sectors.get(1).getHexs().get(4).addNeighbor(sectors.get(2).getHexs().get(0));

        //  sector2
        sectors.get(2).getHexs().get(4).addNeighbor(sectors.get(3).getHexs().get(2));
        sectors.get(2).getHexs().get(4).addNeighbor(sectors.get(3).getHexs().get(1));
        sectors.get(2).getHexs().get(5).addNeighbor(sectors.get(3).getHexs().get(1));

        //  sector3
        sectors.get(3).getHexs().get(0).addNeighbor(sectors.get(4).getHexs().get(2));
        sectors.get(3).getHexs().get(5).addNeighbor(sectors.get(4).getHexs().get(3));
        sectors.get(3).getHexs().get(5).addNeighbor(sectors.get(4).getHexs().get(2));

        //  sector4
        sectors.get(4).getHexs().get(0).addNeighbor(sectors.get(5).getHexs().get(4));
        sectors.get(4).getHexs().get(0).addNeighbor(sectors.get(5).getHexs().get(3));
        sectors.get(4).getHexs().get(1).addNeighbor(sectors.get(5).getHexs().get(3));

        //  sector5
        sectors.get(5).getHexs().get(1).addNeighbor(sectors.get(0).getHexs().get(5));
        sectors.get(5).getHexs().get(1).addNeighbor(sectors.get(0).getHexs().get(4));
        sectors.get(5).getHexs().get(2).addNeighbor(sectors.get(0).getHexs().get(4));

        // triprime
        sectors.get(6).getHexs().get(0).addNeighbor(sectors.get(0).getHexs().get(4));
        sectors.get(6).getHexs().get(0).addNeighbor(sectors.get(0).getHexs().get(3));

        sectors.get(6).getHexs().get(0).addNeighbor(sectors.get(1).getHexs().get(5));
        sectors.get(6).getHexs().get(0).addNeighbor(sectors.get(1).getHexs().get(4));

        sectors.get(6).getHexs().get(0).addNeighbor(sectors.get(2).getHexs().get(0));
        sectors.get(6).getHexs().get(0).addNeighbor(sectors.get(2).getHexs().get(5));

        sectors.get(6).getHexs().get(0).addNeighbor(sectors.get(3).getHexs().get(1));
        sectors.get(6).getHexs().get(0).addNeighbor(sectors.get(3).getHexs().get(0));

        sectors.get(6).getHexs().get(0).addNeighbor(sectors.get(4).getHexs().get(2));
        sectors.get(6).getHexs().get(0).addNeighbor(sectors.get(4).getHexs().get(1));

        sectors.get(6).getHexs().get(0).addNeighbor(sectors.get(5).getHexs().get(3));
        sectors.get(6).getHexs().get(0).addNeighbor(sectors.get(5).getHexs().get(2));
    }

    public void afficherCarte(){
        for (Sector sector : sectors){
            System.out.println(sector.toString());
        }
    }

    public ArrayList<Sector> getSectors() {
        return sectors;
    }
}
