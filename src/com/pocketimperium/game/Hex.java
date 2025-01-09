package com.pocketimperium.game;

import com.pocketimperium.player.Fleet;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe représentant une case hexagonale (Hex) dans le jeu.
 * Chaque Hex est caractérisé par un identifiant unique, un secteur auquel il appartient,
 * un niveau de système et une liste de voisins adjacents.
 */

public class Hex implements Serializable {

    /**
     * Identifiant unique de la case hexagonale.
     */
    private final int id;

    /**
     * Numéro du secteur auquel appartient la case hexagonale.
     */
    private final int sector;

    /**
     * Niveau de système associé à la case hexagonale (1, 2 ou null).
     */
    private final int systeme;

    /**
     * Liste des voisins adjacents de la case hexagonale.
     */
    private final ArrayList<Hex> neighbors;

    /**
     * Flotte présente sur la case hexagonale, si elle existe.
     */
    private Fleet fleet;

    /**
     * Constructeur de la classe Hex.
     * 
     * @param id Identifiant unique de la case hexagonale.
     * @param sector Numéro du secteur auquel appartient la case hexagonale.
     * @param systeme Niveau de système associé à la case hexagonale (1, 2 ou null).
     */
    public Hex(int id, int sector, int systeme) {
        this.id = id;
        this.sector = sector;
        this.systeme = systeme;
        this.neighbors = new ArrayList<>();
    }

    /**
     * Retourne l'identifiant unique de la case hexagonale.
     * 
     * @return Identifiant unique de la case hexagonale.
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne le numéro du secteur auquel appartient la case hexagonale.
     * 
     * @return Numéro du secteur.
     */
    public int getSector() {
        return sector;
    }

    /**
     * Retourne le niveau de système associé à la case hexagonale.
     * 
     * @return Niveau de système (1, 2 ou null).
     */
    public int getSysteme() {
        return systeme;
    }

    /**
     * Ajoute un voisin à la liste des voisins de la case hexagonale.
     * L'ajout est bidirectionnel : la case voisine devient également un voisin de cette case.
     * 
     * @param neighbor Case hexagonale voisine à ajouter.
     */
    public void addNeighbor(Hex neighbor) {
        if (!neighbors.contains(neighbor)) {
            neighbors.add(neighbor);
            neighbor.addNeighbor(this); // Ajout bidirectionnel
        }
    }

    /**
     * Retourne la liste des voisins adjacents de la case hexagonale.
     * 
     * @return Liste des voisins adjacents.
     */
    public ArrayList<Hex> getNeighbor() {
        return neighbors;
    }

    /**
     * Retourne la flotte présente sur la case hexagonale.
     * 
     * @return Flotte présente ou null si la case est vide.
     */
    public Fleet getFleet() {
        return fleet;
    }

    /**
     * Associe une flotte à la case hexagonale.
     * 
     * @param fleet Flotte à associer à la case hexagonale.
     */
    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    /**
     * Vérifie si la case hexagonale est vide (aucune flotte présente).
     * 
     * @return true si la case est vide, false sinon.
     */
    public boolean isEmpty() {
        return fleet == null;
    }

    /**
     * Retourne une représentation textuelle de la case hexagonale, incluant ses voisins.
     * 
     * @return Représentation textuelle de la case hexagonale et de ses voisins.
     */
    @Override
    public String toString() {
        String result = "Hex{" + "id='" + id + '\'' + ", secteur='" + sector + '\'' + ", systeme='" + systeme + '\'' + '}' + " :\n\n";
        for (Hex hex : neighbors) {
            result += " - Hex{" + "id='" + hex.id + '\'' + ", secteur='" + hex.sector + '\'' + ", systeme='" + hex.systeme + '\'' + '}' + "\n";
        }
        return result;
    }
}
