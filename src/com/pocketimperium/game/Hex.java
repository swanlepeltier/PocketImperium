package com.pocketimperium.game;

import com.pocketimperium.player.Fleet;

import java.io.Serializable;
import java.util.ArrayList;

public class Hex implements Serializable{
    private final int id;
    private final int sector;
    private final int systeme; // "Niveau 1", "Niveau 2", ou null
    private final ArrayList<Hex> neighbors;
    private Fleet fleet;

    public Hex(int id, int sector, int systeme) {
        this.id = id;
        this.sector = sector;
        this.systeme = systeme;
        this.neighbors = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getSector() {
        return sector;
    }

    public int getSysteme() {
        return systeme;
    }

    public void addNeighbor(Hex neighbor) {
        if (!neighbors.contains(neighbor)) {
            neighbors.add(neighbor);
            neighbor.addNeighbor(this); // Ajout bidirectionnel
        }
    }

    public ArrayList<Hex> getNeighbor() {
        return neighbors;
    }

    public Fleet getFleet() {
        return fleet;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    public boolean isEmpty() {
        return fleet == null;
    }

    @Override
    public String toString() {
        String result = "Hex{" + "id='" + id + '\'' + ", secteur='" + sector + '\'' + ", systeme='" + systeme + '\'' + '}' + " :\n\n";
        for (Hex hex : neighbors){
            result += " - Hex{" + "id='" + hex.id + '\'' + ", secteur='" + hex.sector + '\'' + ", systeme='" + hex.systeme + '\'' + '}' + "\n";
        }
        return result;
    }
}
