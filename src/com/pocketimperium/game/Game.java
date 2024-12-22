package com.pocketimperium.game;

import com.pocketimperium.player.Fleet;
import com.pocketimperium.player.Player;
import java.util.Scanner;

public class Game {
    private Scanner scanner = new Scanner(System.in);
    private Player[] playerList = {new Player(this), new Player(this), new Player(this)};
    public Carte carte = new Carte();

    public void start(){
        System.out.println("-------------------");
        System.out.println("| Pocket Imperium |");
        System.out.println("-------------------");

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

    public void playRound(){
        int cardOrder[][] = new int[3][3];
        int[] actionCount = new int[3];

        for (int i = 0; i < playerList.length; i++) {
            Player player = playerList[i];
            for (int card : player.chooseCardOrder()) {
            System.out.println(player.getName() + " : " + card);
            }
            cardOrder[i] = player.getCardOrder();
        }
        for (int i = 0; i < playerList.length; i++) {
            System.out.println(playerList[i].getName() + " : " + cardOrder[i][0] + " " + cardOrder[i][1] + " " + cardOrder[i][2]);
        }
                
        // I want the minimum card to be the first one
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {
                if (cardOrder[j][i] == 1) {
                    actionCount[0]++;
                }
                if (cardOrder[j][i] == 2) {
                    actionCount[1]++;
                }
                if (cardOrder[j][i] == 3) {
                    actionCount[2]++;
                }
            }

            System.out.println("Action count : " + actionCount[0] + " " + actionCount[1] + " " + actionCount[2]);

            for (int j = 0; j < 3; j++){
                int minIndex = cardOrderMinIndex(cardOrder, i); // Find the index of the minimum card

                if(cardOrder[minIndex][i] == 1){
                    playerList[minIndex].expand(actionCount[0]);
                }
                else if(cardOrder[minIndex][i] == 2){
                    playerList[minIndex].explore(actionCount[1]);
                }
                else if(cardOrder[minIndex][i] == 3){
                    playerList[minIndex].exterminate(actionCount[2]);
                }
                cardOrder[minIndex][i] = 4; // Set the card to 4 so it won't be played again
            }

            actionCount[0] = 0;
            actionCount[1] = 0;
            actionCount[2] = 0;

        }
    }

    public int cardOrderMinIndex(int[][] cardOrder, int cardTurn) {
        int min = cardOrder[0][cardTurn];
        int minIndex = 0;
        for (int i = 0; i < cardOrder.length; i++) {
            if (cardOrder[i][cardTurn] < min) {
                min = cardOrder[i][cardTurn];
                minIndex = i;
            }
        }
        return minIndex;
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
