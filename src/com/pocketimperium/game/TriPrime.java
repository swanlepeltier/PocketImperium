package com.pocketimperium.game;

/**
 * Représente un secteur spécifique appelé "TriPrime" dans le jeu Pocket Imperium.
 * <p>
 * Cette classe hérite de {@link Sector} et contient un seul hexagone central avec un système de niveau 3.
 * Elle est conçue pour initialiser un secteur spécial, différent des secteurs standard.
 * </p>
 */
public class TriPrime extends Sector {

    /**
     * Constructeur de la classe TriPrime.
     * <p>
     * Initialise un secteur TriPrime avec un seul hexagone contenant un système de niveau 3.
     * </p>
     *
     * @param id l'identifiant unique du secteur TriPrime
     */
    public TriPrime(int id) {
        super(id, true); // Appelle le constructeur de {@link Sector} en mode minimaliste (sans initialisation standard).
        hexagones.add(new Hex(0, id, 3)); // Ajoute un hexagone unique avec un système de niveau 3.
    }
}
