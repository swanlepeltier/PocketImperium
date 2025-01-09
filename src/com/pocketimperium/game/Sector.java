package com.pocketimperium.game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Représente un secteur du plateau de jeu dans Pocket Imperium.
 * Un secteur contient un ensemble d'hexagones connectés entre eux.
 * Chaque secteur possède un identifiant unique et des hexagones qui composent ses éléments de jeu.
 */

public class Sector implements Serializable {

    /**
     * Identifiant unique du secteur.
     */
    private final int id;

    /**
     * Liste des hexagones appartenant à ce secteur.
     */
    protected final ArrayList<Hex> hexagones;

    /**
     * Constructeur principal de la classe Sector.
     * Initialise le secteur avec un identifiant unique et remplit le secteur
     * avec des hexagones connectés entre eux de manière prédéfinie.
     *
     * @param id L'identifiant unique du secteur.
     */
    public Sector(int id) {
        this.id = id;
        this.hexagones = new ArrayList<>();
        ArrayList<Integer> availableSystem = new ArrayList<>();

        // Ajout des systèmes disponibles dans le secteur.
        availableSystem.add(2); // Un système de niveau 2
        availableSystem.add(1); // Deux systèmes de niveau 1
        availableSystem.add(1);

        // Remplissage avec des systèmes de niveau 0
        for (int i = 0; i < 4; i++) {
            availableSystem.add(0);
        }

        // Création et attribution aléatoire des systèmes aux hexagones
        for (int i = 0; i < 7; i++) {
            int randomSystem = (int) (Math.random() * availableSystem.size());
            hexagones.add(new Hex(i, id, availableSystem.get(randomSystem)));
            availableSystem.remove(randomSystem);
        }

        // Connexions des hexagones à leur centre et entre eux

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

    /**
     * Constructeur minimaliste permettant de créer un secteur sans
     * initialisation d'hexagones ni de connexions.
     *
     * @param id L'identifiant unique du secteur.
     * @param skipInitialization Indique si l'initialisation des hexagones doit être sautée.
     */
    protected Sector(int id, boolean skipInitialization) {
        this.id = id;
        this.hexagones = new ArrayList<>();
        // Pas d'initialisation supplémentaire
    }

    /**
     * Retourne une représentation textuelle du secteur.
     * Affiche les informations de chaque hexagone du secteur.
     *
     * @return Une chaîne représentant le secteur et ses hexagones.
     */
    @Override
    public String toString() {
        String result = "";
        for (Hex hex : hexagones) {
            result += hex.toString() + "\n";
        }
        return result;
    }

    /**
     * Obtient l'identifiant du secteur.
     *
     * @return L'identifiant unique de ce secteur.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtient la liste des hexagones contenus dans ce secteur.
     *
     * @return Une liste d'objets Hex appartenant à ce secteur.
     */
    public ArrayList<Hex> getHexs() {
        return hexagones;
    }
}
