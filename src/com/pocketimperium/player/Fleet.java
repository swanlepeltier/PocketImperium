package com.pocketimperium.player;

import com.pocketimperium.game.Hex;
import java.io.Serializable;

/**
 * La classe Fleet représente une flotte de vaisseaux spatiaux appartenant à un joueur,
 * située dans un hexagone spécifique sur la carte.
 */
public class Fleet implements Serializable {
    private int amount; // Nombre de vaisseaux dans cette flotte
    private Hex hex; // L'hexagone où se trouve la flotte
    private Player player; // Le joueur propriétaire de cette flotte

    /**
     * Constructeur de la classe Fleet.
     *
     * @param hex    L'hexagone où la flotte est initialement placée.
     * @param amount Le nombre initial de vaisseaux dans la flotte.
     * @param player Le joueur propriétaire de la flotte.
     */
    public Fleet(Hex hex, int amount, Player player) {
        this.hex = hex;
        this.amount = amount;
        this.player = player;
    }

    /**
     * Modifie le nombre de vaisseaux dans la flotte.
     * Si le nombre de vaisseaux devient 0, la flotte est supprimée de l'hexagone
     * et du joueur auquel elle appartient.
     *
     * @param amount Le nouveau nombre de vaisseaux dans la flotte.
     */
    public void setAmount(int amount) {
        this.amount = amount;
        if (this.amount == 0) {
            // Si la flotte est vide, la retirer de l'hexagone et du joueur
            this.hex.setFleet(null); // Supprime la flotte de l'hexagone
            this.player.removeFleet(this); // Retire la flotte du joueur
        }
    }

    /**
     * Retourne le nombre de vaisseaux dans la flotte.
     *
     * @return Le nombre de vaisseaux.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Retourne l'hexagone où se trouve la flotte.
     *
     * @return L'hexagone de la flotte.
     */
    public Hex getHex() {
        return hex;
    }

    /**
     * Modifie l'hexagone où se trouve la flotte.
     *
     * @param hex Le nouvel hexagone où la flotte doit être déplacée.
     */
    public void setHex(Hex hex) {
        this.hex = hex;
    }

    /**
     * Retourne le joueur propriétaire de la flotte.
     *
     * @return Le joueur propriétaire.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères de la flotte.
     *
     * @return Une chaîne décrivant le nombre de vaisseaux et leur position.
     */
    @Override
    public String toString() {
        return this.amount + " Ship in " + this.hex.toString();
    }
}
