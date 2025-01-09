package com.pocketimperium;

import com.pocketimperium.game.Game;
import com.pocketimperium.player.Player;
import java.io.File;
import java.io.IOException;

/**
 * Représente le jeu Pocket Imperium, une implémentation du jeu de stratégie 
 * où les joueurs se déplacent dans une carte, explorent, se développent 
 * et s'affrontent pour remporter la victoire.
 * Classe principale de l'application Pocket Imperium.
 * Cette classe contient le point d'entrée principal pour lancer le jeu.
 */
public class Main {

    /**
     * Chemin du fichier de sauvegarde pour le jeu.
     */
    private static final String SAVE_FILE_PATH = "save/save.ser";

    /**
     * Méthode principale qui initialise et exécute le jeu.
     *
     * @param args les arguments de ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        Game game;
        File saveFile = new File(SAVE_FILE_PATH);

        // Vérifie l'existence du répertoire de sauvegarde et le crée si nécessaire.
        File saveDir = saveFile.getParentFile();
        if (saveDir != null && !saveDir.exists()) {
            if (saveDir.mkdirs()) {
                System.out.println("Save directory created.");
            } else {
                System.err.println("Failed to create save directory.");
                return;
            }
        }

        // Tente de charger une partie sauvegardée ou démarre une nouvelle partie.
        if (saveFile.exists()) {
            try {
                game = Game.loadGame(SAVE_FILE_PATH);
                System.out.println("Game loaded from save file.");
                for (Player player : game.getPlayerList()) {
                    System.out.println(player.getName() + " : Score : " + player.getScore());
                    System.out.println(player.toString());
                }
                System.out.println("Round left : " + (9 - game.getRound()));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Failed to load game. Starting a new game.");
                game = new Game();
                game.start();
            }
        } else {
            try {
                if (saveFile.createNewFile()) {
                    System.out.println("Save file created.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to create new save file.");
                return;
            }
            game = new Game();
            game.start();
        }

        // Boucle principale pour jouer les tours restants du jeu.
        for (int i = game.getRound(); i < 9; i++) {
            game.playRound();
            game.score();
            try {
                game.saveGame(SAVE_FILE_PATH);
                System.out.println("Game saved.");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to save game.");
            }
        }

        // Termine la partie.
        game.end();
    }
}
