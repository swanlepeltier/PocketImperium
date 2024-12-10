package com.pocketimperium.game;

import com.pocketimperium.player.Player;
import java.util.Scanner;

public class Game {
    Scanner scanner = new Scanner(System.in);

    public void start(){
        System.out.println("-------------------");
        System.out.println("| Pocket Imperium |");
        System.out.println("-------------------");

        Player[] playerList = {new Player(), new Player(), new Player()};

        Sector[] map = {
            new Sector("top"), new Sector("top"), new Sector("top"),
            new Sector("side"), new Sector("TriPrime"), new Sector("side"),
            new Sector("bottom"), new Sector("bottom"), new Sector("bottom")
        }; 

        for (Player player : playerList) {
            boolean validInput = false;
        
            // Gestion sécurisée de l'état du joueur (0 : Bot, 1 : Humain)
            while (!validInput) {
                System.out.println("Enter player state (0 : Bot, 1 : Human): ");
                if (scanner.hasNextInt()) {
                    int playerState = scanner.nextInt();
                    if (playerState == 0 || playerState == 1) {
                        player.setPlayerState(playerState == 1); // true si 1 (Human), false si 0 (Bot)
                        validInput = true;
                    } else {
                        System.out.println("Invalid input. Please enter 0 or 1.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number (0 or 1).");
                    scanner.next(); // Consommer l'entrée incorrecte
                }
            }
        
            // Gestion sécurisée du nom du joueur
            System.out.println("Enter the name of the player: ");
            scanner.nextLine(); // Consommer le retour de ligne restant après nextInt()
            String playerName = scanner.nextLine();
            player.setName(playerName);
        }
        

        for(Player player : playerList){
            player.placeFleet();
        }

    }
}
