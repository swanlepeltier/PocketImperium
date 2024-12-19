package com.pocketimperium.game;

import com.pocketimperium.player.Fleet;
import com.pocketimperium.player.Player;
import java.util.Scanner;

public class Game {
    Scanner scanner = new Scanner(System.in);
    Player[] playerList = {new Player(), new Player(), new Player()};

    public void start(){
        System.out.println("-------------------");
        System.out.println("| Pocket Imperium |");
        System.out.println("-------------------");

        Carte carte = new Carte();
        carte.afficherCarte();

        for (Player player : playerList) {
            player.setPlayerState(true);
        }

        playerList[0].setName("Swan");
        playerList[1].setName("Emma");
        playerList[2].setName("Mathieu");

        /*
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
        */

        int sectorNumber;
        int hexNumber;


        for(Player player : playerList){
            sectorNumber = player.chooseSector();
            hexNumber = player.chooseHex();
            player.placeFleetStart(carte.getSectors().get(sectorNumber).getHexs().get(hexNumber));
            System.out.println(player.toString());
        }

        for(int i = 2; i >= 0; i--){
            sectorNumber = playerList[i].chooseSector();
            hexNumber = playerList[i].chooseHex();
            playerList[i].placeFleetStart(carte.getSectors().get(sectorNumber).getHexs().get(hexNumber));
            System.out.println(playerList[i].toString());
        }

    }

    public int chooseSector(Player currentPlayer){
        int sectorNumber = 0;
        Boolean validInput = false;
        while (!validInput) {
            sectorNumber = currentPlayer.chooseSector();

            for (Player player : playerList){

                if(player != currentPlayer){
                    if(player.getFleetListSize() > 0){

                        for (Fleet fleet : player.getFleets()){
                            System.out.println(fleet.getHex().getSector());

                            if (sectorNumber != fleet.getHex().getSector()){
                                validInput = true;
                            }
                            else{
                                validInput = false;
                            }

                        }
                    }
                }
            }
        }
        return sectorNumber;
    }

}
