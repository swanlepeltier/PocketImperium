package com.pocketimperium.game;

import com.pocketimperium.player.Fleet;
import com.pocketimperium.player.Player;
import java.io.*;
import java.util.Scanner;

/**
 * Classe représentant le jeu Pocket Imperium.
 * Cette classe gère l'état global du jeu, y compris les joueurs, la carte, et les différentes phases de jeu.
 */
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Liste des joueurs participant au jeu.
     */
    private Player[] playerList = {new Player(this), new Player(this), new Player(this)};

    /**
     * Instance de la carte utilisée dans le jeu.
     */
    private Carte carte = new Carte();

    /**
     * Nombre de manches écoulées depuis le début du jeu.
     */
    private int round = 0;

    /**
     * Scanner utilisé pour capturer les entrées utilisateur. Transient pour éviter la sérialisation.
     */
    private transient Scanner scanner = new Scanner(System.in);

    /**
     * Récupère le numéro de la manche actuelle.
     * @return Numéro de la manche.
     */
    public int getRound(){
        return round;
    }

    /**
     * Récupère la liste des joueurs participant au jeu.
     * @return Tableau des joueurs.
     */
    public Player[] getPlayerList() {
        return playerList;
    }

    /**
     * Démarre le jeu en initialisant les joueurs et la carte.
     * Gère les entrées utilisateur pour configurer les joueurs et leurs positions initiales.
     */
    public void start(){
        System.out.println("\n");
        System.out.println("-------------------");
        System.out.println("| Pocket Imperium |");
        System.out.println("-------------------");
        System.out.println("\n");

        carte.afficherCarte();     

        for (Player player : playerList) {
            Boolean validInput = false;
        
            // Gestion sécurisée de l'état du joueur (0 : Bot, 1 : Humain)
            while (!validInput) {
                System.out.println("Enter player state (0 : Bot, 1 : Human): ");
                if (scanner.hasNextInt()) {
                    int playerState = scanner.nextInt();
                    if (playerState == 0 || playerState == 1) {
                        player.setPlayerState(playerState == 1); // true si 1 (Humain), false si 0 (Bot)
                        validInput = true;
                    } else {
                        System.out.println("Invalid input. Please enter 0 or 1.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number (0 or 1).");
                    scanner.next(); // Consomme l'entrée incorrecte
                }
            }
        
            // Gestion sécurisée du nom du joueur
            System.out.println("Enter the name of the player: ");
            scanner.nextLine(); // Consomme le retour de ligne restant après nextInt()
            String playerName = scanner.nextLine();
            player.setName(playerName);
        }
        
        // Initialisation des positions de départ des joueurs
        int sectorNumber = 0;
        int hexNumber = 0;
        Boolean validInput = false;

        for(Player player : playerList){
            while (!validInput) {
                sectorNumber = player.chooseSector();
                hexNumber = player.chooseHex();
                validInput = player.validHex(sectorNumber, hexNumber) && player.validSector(sectorNumber) && player.validSectorStart(sectorNumber) && player.hasSystem1(sectorNumber, hexNumber); 
            }
            validInput = false;
            player.placeFleetStart(carte.getSectors().get(sectorNumber).getHexs().get(hexNumber));
            System.out.println(player.toString());
        }

        for(int i = 2; i >= 0; i--){
            while (!validInput) {
                sectorNumber = playerList[i].chooseSector();
                hexNumber = playerList[i].chooseHex();
                validInput = playerList[i].validHex(sectorNumber, hexNumber) && playerList[i].validSector(sectorNumber) && playerList[i].validSectorStart(sectorNumber) && playerList[i].hasSystem1(sectorNumber, hexNumber); 
            }
            validInput = false;
            playerList[i].placeFleetStart(carte.getSectors().get(sectorNumber).getHexs().get(hexNumber));
            System.out.println(playerList[i].toString());
        }
    }

    /**
     * Récupère l'instance de la carte du jeu.
     * @return Carte du jeu.
     */
    public Carte getCarte() {
        return carte;
    }

    /**
     * Joue une manche complète en exécutant les actions des joueurs dans l'ordre des cartes choisies.
     */
    public void playRound() {
        int cardOrder[][] = new int[3][3];
        int[] actionCount = new int[3];

        for (Player player : playerList){
            player.chooseCardOrder();
        }

        for (int i = 0; i < playerList.length; i++) {
            Player player = playerList[i];
            cardOrder[i] = player.getCardOrder();
        }
        for (int i = 0; i < playerList.length; i++) {
            System.out.println(playerList[i].getName() + " : " + cardOrder[i][0] + " " + cardOrder[i][1] + " " + cardOrder[i][2]);
        }
                
        // Le minimum est la première carte
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

            for (int j = 0; j < 3; j++){
                int minIndex = cardOrderMinIndex(cardOrder, i); // Trouver l'index de la carte minimum

                if(cardOrder[minIndex][i] == 1){
                    System.out.println(playerList[minIndex].getName() + " : Expand :");
                    System.out.println(playerList[minIndex].toString());
                    playerList[minIndex].expand(actionCount[0]);
                }
                else if(cardOrder[minIndex][i] == 2){
                    System.out.println(playerList[minIndex].getName() + " : Explore :");
                    System.out.println(playerList[minIndex].toString());
                    playerList[minIndex].explore(actionCount[1]);
                }
                else if(cardOrder[minIndex][i] == 3){
                    System.out.println(playerList[minIndex].getName() + " : Exterminate :");
                    System.out.println(playerList[minIndex].toString());
                    playerList[minIndex].exterminate(actionCount[2]);
                }
                cardOrder[minIndex][i] = 4; // Marquer la carte comme jouée

                if(playerList[minIndex].getFleets().size() == 0){
                    end();
                }

            }

            actionCount[0] = 0;
            actionCount[1] = 0;
            actionCount[2] = 0;

        }
        // Rotation des joueurs
        rotatePlayers();
    }

    /**
     * Change l'ordre des joueurs pour simuler une rotation après une manche.
     */
    public void rotatePlayers() {
        Player[] playerlistTemp = new Player[playerList.length];

        playerlistTemp[0] = playerList[1];
        playerlistTemp[1] = playerList[2];
        playerlistTemp[2] = playerList[0];

        playerList = playerlistTemp;
    }

    /**
     * Trouve l'index de la carte minimum pour un tour donné.
     * @param cardOrder Tableau des ordres de cartes.
     * @param cardTurn Numéro du tour.
     * @return Index de la carte minimum.
     */
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

    /**
     * Gère le calcul des scores pour chaque joueur en fin de manche.
     */
    public void score(){
        int sectorIndex;
        for (Player player : playerList){
            for (Fleet fleet : player.getFleets()){
                if(fleet.getHex().getSector() == 6){
                    sectorIndex = player.chooseSector();
                    player.score(sectorIndex);
                }
            }
            sectorIndex = player.chooseSector();
            player.score(sectorIndex);
            System.out.println(player.getName() + " : Score : " + player.getScore());
            }
            round++;
        }

    /**
     * Termine le jeu et affiche les résultats.
     */
    public void end(){
        int winnerIndex = 0;
        System.out.println("\n");
        System.out.println("-------------------");
        System.out.println("| End of the game |");
        System.out.println("-------------------");
        System.out.println("\n");

        for (Player player : playerList){
            for (Fleet fleet : player.getFleets()){
                player.setScore(player.getScore() + fleet.getHex().getSector()*2);
            }
        }
        for (Player player : playerList){
            System.out.println(player.getName() + " : Score : " + player.getScore());
            System.out.println(player.toString());
        }

        for (int i = 1; i < playerList.length; i++) {
            if (playerList[i].getScore() > playerList[winnerIndex].getScore()) {
                winnerIndex = i;
            }
        }
        System.out.println(playerList[winnerIndex].getName() + " wins !");
        File saveFile = new File("save/save.ser");
        if (saveFile.exists()) {
            if (saveFile.delete()) {
                System.out.println("Save file deleted.");
            } else {
                System.out.println("Failed to delete save file.");
            }
        }
        System.exit(0);
    }

    /**
     * Sauvegarde l'état actuel du jeu dans un fichier.
     * @param filePath Chemin du fichier où sauvegarder le jeu.
     * @throws IOException En cas d'erreur lors de la sauvegarde.
     */
    public void saveGame(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Charge un état de jeu à partir d'un fichier.
     * @param filePath Chemin du fichier à partir duquel charger le jeu.
     * @return Instance de la classe Game chargée depuis le fichier.
     * @throws IOException En cas d'erreur de lecture.
     * @throws ClassNotFoundException Si la classe Game n'est pas trouvée.
     */
    public static Game loadGame(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Game) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game: " + e.getMessage());
            throw e;
        }
    }
}