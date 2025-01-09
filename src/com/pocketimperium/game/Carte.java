package com.pocketimperium.game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe représentant la carte du jeu.
 * Gère les différents secteurs et leurs connexions inter-secteurs.
 * Cette classe est sérialisable pour permettre la sauvegarde et le chargement de la carte.
 */

public class Carte implements Serializable {

    /**
     * Liste des secteurs présents sur la carte.
     */
    private final ArrayList<Sector> sectors = new ArrayList<>();

    /**
     * Constructeur de la classe Carte.
     * Initialise les 6 secteurs standards et un secteur spécial (TriPrime).
     * Configure les connexions entre les différents secteurs.
     */
    public Carte() {
        // Initialisation des 6 secteurs standards
        for (int i = 0; i < 6; i++) {
            Sector sector = new Sector(i);
            sectors.add(sector);
        }

        // Ajout du secteur TriPrime
        sectors.add(new TriPrime(6));

        // Ajouter les voisins inter-secteur

        // Connexions pour le secteur 0
        sectors.get(0).getHexs().get(2).addNeighbor(sectors.get(1).getHexs().get(0));
        sectors.get(0).getHexs().get(2).addNeighbor(sectors.get(1).getHexs().get(5));
        sectors.get(0).getHexs().get(3).addNeighbor(sectors.get(1).getHexs().get(5));

        // Connexions pour le secteur 1
        sectors.get(1).getHexs().get(3).addNeighbor(sectors.get(2).getHexs().get(1));
        sectors.get(1).getHexs().get(3).addNeighbor(sectors.get(2).getHexs().get(0));
        sectors.get(1).getHexs().get(4).addNeighbor(sectors.get(2).getHexs().get(0));

        // Connexions pour le secteur 2
        sectors.get(2).getHexs().get(4).addNeighbor(sectors.get(3).getHexs().get(2));
        sectors.get(2).getHexs().get(4).addNeighbor(sectors.get(3).getHexs().get(1));
        sectors.get(2).getHexs().get(5).addNeighbor(sectors.get(3).getHexs().get(1));

        // Connexions pour le secteur 3
        sectors.get(3).getHexs().get(0).addNeighbor(sectors.get(4).getHexs().get(2));
        sectors.get(3).getHexs().get(5).addNeighbor(sectors.get(4).getHexs().get(3));
        sectors.get(3).getHexs().get(5).addNeighbor(sectors.get(4).getHexs().get(2));

        // Connexions pour le secteur 4
        sectors.get(4).getHexs().get(0).addNeighbor(sectors.get(5).getHexs().get(4));
        sectors.get(4).getHexs().get(0).addNeighbor(sectors.get(5).getHexs().get(3));
        sectors.get(4).getHexs().get(1).addNeighbor(sectors.get(5).getHexs().get(3));

        // Connexions pour le secteur 5
        sectors.get(5).getHexs().get(1).addNeighbor(sectors.get(0).getHexs().get(5));
        sectors.get(5).getHexs().get(1).addNeighbor(sectors.get(0).getHexs().get(4));
        sectors.get(5).getHexs().get(2).addNeighbor(sectors.get(0).getHexs().get(4));

        // Connexions pour le secteur TriPrime (secteur 6)
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

    /**
     * Affiche les informations de tous les secteurs présents sur la carte.
     */
    public void afficherCarte() {
        for (Sector sector : sectors) {
            System.out.println(sector.toString());
        }
    }

    /**
     * Récupère la liste des secteurs présents sur la carte.
     * 
     * @return Liste des secteurs.
     */
    public ArrayList<Sector> getSectors() {
        return sectors;
    }
}
