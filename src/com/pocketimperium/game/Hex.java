package com.pocketimperium.game;

import java.util.ArrayList;

public class Hex {
    private final int id;
    private final String secteur;
    private final int systeme; // "Niveau 1", "Niveau 2", ou null
    private final ArrayList<Hex> neighbors;

    public Hex(int id, String secteur, int systeme) {
        this.id = id;
        this.secteur = secteur;
        this.systeme = systeme;
        this.neighbors = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getSecteur() {
        return secteur;
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

    @Override
    public String toString() {
        String result = "Hex{" + "id='" + id + '\'' + ", secteur='" + secteur + '\'' + ", systeme='" + systeme + '\'' + '}' + " :\n\n";
        for (Hex hex : neighbors){
            result += " - Hex{" + "id='" + hex.id + '\'' + ", secteur='" + hex.secteur + '\'' + ", systeme='" + hex.systeme + '\'' + '}' + "\n";
        }
        return result;
    }
}
