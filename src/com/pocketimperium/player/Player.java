package com.pocketimperium.player;

import com.pocketimperium.game.Game;
import com.pocketimperium.game.Hex;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe représentant un joueur dans le jeu Pocket Imperium.
 */

public class Player implements Serializable{
    private String color;
    private ArrayList<Fleet> fleetList;
    private Boolean isHuman;
    private String name;
    private int remainingShip = 15;
    private int[] cardOrder = {1, 2, 3};
    private Game game;
    private int score = 0;

    private transient Scanner scanner = new Scanner(System.in);

    /**
     * Constructeur de la classe Player.
     * Initialise une liste de flottes et associe le joueur à une partie.
     * 
     * @param game L'instance de la partie en cours.
     */

    public Player(Game game){
        fleetList = new ArrayList<>();
        this.game = game;
    }

    /**
     * Définit le score du joueur.
     * 
     * @param score Le score à attribuer au joueur.
     */
    public void setScore(int score){
        this.score = score;
    }

    /**
     * Retourne le score actuel du joueur.
     * 
     * @return Le score du joueur.
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Retourne la couleur associée au joueur.
     * 
     * @return La couleur du joueur.
     */
    public String getColor(){
        return this.color;
    }

    /**
     * Met à jour le score du joueur en fonction des flottes présentes dans un secteur.
     * 
     * @param sectorIndex L'indice du secteur à évaluer.
     */
    public void score(int sectorIndex){
        int tempScore = 0;
        for (Fleet fleet : fleetList){
            if(fleet.getHex().getSector() == sectorIndex){
                tempScore += fleet.getHex().getSysteme();
            }
        }
        this.score += tempScore;
    }

    /**
     * Retourne la taille de la liste des flottes du joueur.
     * 
     * @return La taille de la liste des flottes.
     */
    public int getFleetListSize(){
        return fleetList.size();
    }

    /**
     * Place une flotte initiale dans un hex donné (seulement pour le début de la partie).
     * 
     * @param hex L'hex dans lequel placer la flotte.
     */
    public void placeFleetStart(Hex hex){
        Fleet tempFleet = new Fleet(hex, 2, this);
        fleetList.add(tempFleet);
        this.remainingShip -= 2;
        hex.setFleet(tempFleet);
    }

    /**
     * Permet au joueur de choisir un secteur.
     * 
     * @return L'indice du secteur choisi.
     */
    public int chooseSector(){
        if(this.isHuman){
            System.out.print(this.name + " : choose a Sector : ");
            return scanner.nextInt();
        }
        else{
            return (int)(Math.random() * 6);
        }
    }

    /**
     * Permet au joueur de choisir un hex.
     * 
     * @return L'indice de l'hex choisi.
     */
    public int chooseHex(){
        if(this.isHuman){
            System.out.print(this.name + " : choose a Hex : ");
            return scanner.nextInt();
        }
        else{
            return (int)(Math.random() * 6);
        }
    }

    /**
     * Permet au joueur de définir l'ordre des cartes.
     * 
     * @return Un tableau contenant l'ordre des cartes.
     */
    public int[] chooseCardOrder(){
        if(this.isHuman){
            System.out.print(this.name + " : choose the order of the cards : ");
            for (int i = 0; i < 3; i++){
                cardOrder[i] = scanner.nextInt();
            }
        }
        else{
            ArrayList<Integer> cardList = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                cardList.add(i);
            }
            java.util.Collections.shuffle(cardList);
            for (int i = 0; i < 3; i++) {
                cardOrder[i] = cardList.get(i);
            }
        }
        return cardOrder;
    }

    /**
     * Retourne l'ordre actuel des cartes du joueur.
     * 
     * @return Un tableau contenant l'ordre des cartes.
     */
    public int[] getCardOrder(){
        return cardOrder;
    }

    /**
     * Définit si le joueur est humain ou contrôlé par l'IA.
     * 
     * @param choice Vrai si le joueur est humain, faux sinon.
     */
    public void setPlayerState(Boolean choice){
        this.isHuman = choice;
    }

    /**
     * Définit le nom du joueur.
     * 
     * @param name Le nom du joueur.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Retourne le nom du joueur.
     * 
     * @return Le nom du joueur.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Définit la couleur du joueur.
     * 
     * @param color La couleur à attribuer au joueur.
     */
    public void setColor(String color){
        this.color = color;
    }

    /**
     * Retourne la liste des flottes du joueur.
     * 
     * @return Une liste contenant les flottes du joueur.
     */
    public ArrayList<Fleet> getFleets(){
        return fleetList;
    }

    /**
     * Ajoute une nouvelle flotte au joueur.
     * 
     * @param hex L'hex associé à la flotte.
     * @param amount Le nombre de vaisseaux dans la flotte.
     */
    public void addFleet(Hex hex, int amount){
        this.remainingShip -= amount;
        this.fleetList.add(new Fleet(hex, amount, this));
    }

    /**
     * Supprime une flotte du joueur.
     * 
     * @param fleet La flotte à supprimer.
     */
    public void removeFleet(Fleet fleet){
        this.remainingShip += fleet.getAmount();
        this.fleetList.remove(fleet);
    }

    /**
     * Vérifie si une flotte a une quantité valide pour une action.
     * 
     * @param fleetIndex L'indice de la flotte.
     * @param amount La quantité à vérifier.
     * @return Vrai si la quantité est valide, faux sinon.
     */
    public Boolean validFleetAmount(int fleetIndex, int amount){
        Boolean result = fleetList.get(fleetIndex).getAmount() >= amount;
        if(fleetIndex >= fleetList.size() || amount <= 0){
            return false;
        }
        if(!result){
            if(this.isHuman){
                System.out.println("Error: Not enough ships in the selected fleet.");
            }
        }

        return result;
    }

    /**
     * Vérifie si un hex est valide dans un secteur donné.
     * 
     * @param sectorIndex L'indice du secteur.
     * @param hexIndex L'indice de l'hex.
     * @return Vrai si l'hex est valide, faux sinon.
     */
    public Boolean validHex(int sectorIndex, int hexIndex) {
        Hex targetHex = game.getCarte().getSectors().get(sectorIndex).getHexs().get(hexIndex);
        Fleet fleet = targetHex.getFleet();
        Boolean result = ((fleet == null) || (fleet.getPlayer() == this)) && hexIndex >= 0 && hexIndex < game.getCarte().getSectors().get(sectorIndex).getHexs().size();
        if (!result) {
            if(this.isHuman){
                System.out.println("Error: The selected hex already has an enemy fleet.");
            }
        }
        return result;
    }

    /**
     * Vérifie si un secteur est valide.
     * 
     * @param sectorIndex L'indice du secteur.
     * @return Vrai si le secteur est valide, faux sinon.
     */
    public Boolean validSector(int sectorIndex) {
        Boolean result = sectorIndex >= 0 && sectorIndex < game.getCarte().getSectors().size();
        if (!result) {
            if(this.isHuman){
                System.out.println("Error: The selected sector index is out of bounds.");
            }
        }
        return result;
    }

    /**
     * Vérifie si un secteur est valide pour un placement initial.
     * 
     * @param sectorIndex L'indice du secteur.
     * @return Vrai si le secteur est valide, faux sinon.
     */
    public Boolean validSectorStart(int sectorIndex) {
        Boolean result = true;
        for(Hex hex : game.getCarte().getSectors().get(sectorIndex).getHexs()){
            result = hex.getFleet() == null;
            if(!result){
                break;
            }
        }
        if (!result) {
            if(this.isHuman){
                System.out.println("Error: The selected sector already has an enemy fleet.");
            }
            return result;
        }
        return result;
    }

    /**
     * Vérifie si un hex est un voisin direct ou indirect d'une flotte.
     * 
     * @param sectorIndex L'indice du secteur.
     * @param hexIndex L'indice de l'hex.
     * @param fleetIndex L'indice de la flotte.
     * @return Vrai si l'hex est un voisin valide, faux sinon.
     */
    public Boolean validNeighbour(int sectorIndex, int hexIndex, int fleetIndex) {
        Hex targetHex = game.getCarte().getSectors().get(sectorIndex).getHexs().get(hexIndex);
        Hex fleetHex = fleetList.get(fleetIndex).getHex();
        Boolean result = targetHex.getNeighbor().contains(fleetHex) || 
                targetHex.getNeighbor().stream().anyMatch(neighbour -> 
                    neighbour.getNeighbor().contains(fleetHex) && 
                    neighbour.getSector() != 6);
        if (!result) {
            if (this.isHuman) {
                System.out.println("Error: The selected hex is not a direct neighbour or neighbour of a neighbour of the fleet's current hex.");
            }
        }
        return result;
    }

    /**
     * Vérifie si un hex est un voisin direct d'une flotte.
     * 
     * @param sectorIndex L'indice du secteur.
     * @param hexIndex L'indice de l'hex.
     * @param fleetIndex L'indice de la flotte.
     * @return Vrai si l'hex est un voisin direct valide, faux sinon.
     */
    public Boolean validNeighbourSolo(int sectorIndex, int hexIndex, int fleetIndex) {
        Hex targetHex = game.getCarte().getSectors().get(sectorIndex).getHexs().get(hexIndex);
        Hex fleetHex = fleetList.get(fleetIndex).getHex();
        Boolean result = targetHex.getNeighbor().contains(fleetHex);
        Boolean resultTemp = true;

        if(targetHex.getFleet() != null){
            resultTemp = targetHex.getFleet().getPlayer() != this;
        }

        result = result && resultTemp;

        if (!result) {
            if(this.isHuman){
                System.out.println("Error: The selected hex is not a direct neighbour of the fleet's current hex.");
            }
        }
        return result;
    }

    /**
     * Vérifie si un hex contient un système de type 1.
     * 
     * @param sectorIndex L'indice du secteur.
     * @param hexIndex L'indice de l'hex.
     * @return Vrai si l'hex contient un système de type 1, faux sinon.
     */
    public Boolean hasSystem1(int sectorIndex, int hexIndex){
        Hex targetHex = game.getCarte().getSectors().get(sectorIndex).getHexs().get(hexIndex);
        Boolean result = targetHex.getSysteme() == 1;
        if (!result) {
            if(this.isHuman){
                System.out.println("Error: The selected hex does not have a system.");
            }
        }
        return result;
    }

    /**
     * Vérifie si un hex contient un système.
     * 
     * @param sectorIndex L'indice du secteur.
     * @param hexIndex L'indice de l'hex.
     * @return Vrai si l'hex contient un système, faux sinon.
     */
    public Boolean hasSystem(int sectorIndex, int hexIndex){
        Hex targetHex = game.getCarte().getSectors().get(sectorIndex).getHexs().get(hexIndex);
        Boolean result = targetHex.getSysteme() > 0;
        if (!result) {
            if(this.isHuman){
                System.out.println("Error: The selected hex does not have a system.");
            }
        }
        return result;
    }

    
    /**
     * Permet au joueur d'effectuer la commande "Expand" pour augmenter ses forces.
     * Cette commande consiste à déployer des vaisseaux supplémentaires dans les hexagones
     * contenant déjà des systèmes contrôlés par le joueur.
     * 
     * Règles spécifiques :
     * - Les vaisseaux supplémentaires sont pris dans la réserve du joueur.
     * - Un hex sans système ou un hex non contrôlé ne peut pas accueillir de nouveaux vaisseaux.
     * - Le nombre de vaisseaux déployés dépend de la puissance utilisée et de la disponibilité dans la réserve.
     * 
     * Implémentation :
     * - Si le joueur n'a pas assez de vaisseaux disponibles, le déploiement est limité.
     * - Le joueur humain choisit une flotte existante pour étendre ses forces.
     * - Le pouvoir utilisé influence la quantité de renforts ajoutés à la flotte sélectionnée.
     * 
     * @param power La puissance utilisée pour l'expansion, influençant le nombre de vaisseaux ajoutés.
     */

     public void expand(int power){
        int fleetIndex = -1;
        if(this.remainingShip < power){
            power = this.remainingShip;
        }

        if(this.isHuman){
            while (fleetIndex >= fleetList.size() || fleetIndex < 0){
                System.out.print(this.name + " : choose a Fleet : ");
                fleetIndex = scanner.nextInt();  
            }
        }
        else{
            // Randomly choose a fleet
            fleetIndex = (int)(Math.random() * (fleetList.size() - 1));
        }
        
        if (power == 1){
            fleetList.get(fleetIndex).setAmount(3 + fleetList.get(fleetIndex).getAmount());
        }
        else if (power == 2){
            fleetList.get(fleetIndex).setAmount(2 + fleetList.get(fleetIndex).getAmount());
        }
        else if (power == 3){
            fleetList.get(fleetIndex).setAmount(1 + fleetList.get(fleetIndex).getAmount());
        }
        this.remainingShip -= power;
        System.out.println(this.toString());
    }

    /**
     * Permet au joueur d'effectuer la commande "Explore" pour déplacer ses flottes.
     * Cette commande consiste à explorer de nouveaux hexagones, permettant au joueur de gagner
     * le contrôle de systèmes inoccupés ou de renforcer des flottes dans d'autres hexagones.
     * 
     * Règles spécifiques :
     * - Chaque flotte peut être déplacée d'un maximum de 2 hexagones.
     * - Les flottes ne peuvent pas traverser ou entrer dans des hexagones occupés par des flottes ennemies.
     * - Les flottes ne peuvent pas traverser l'hex du Tri-Prime, mais peuvent s'y arrêter pour en prendre le contrôle.
     * - Le déplacement peut inclure la division ou la fusion de flottes en cours de route.
     * 
     * Implémentation :
     * - Le joueur choisit une flotte, la quantité de vaisseaux à déplacer, et les hexagones cibles.
     * - Les mouvements doivent respecter les règles de voisinage et de validité des hexagones.
     * - Le pouvoir utilisé influence le nombre de déplacements autorisés pour les flottes.
     * 
     * @param power La puissance utilisée pour l'exploration, influençant le nombre de déplacements possibles.
     */

    public void explore(int power){
        int repeat = 3;
        int fleetIndex = 0;
        int amount = 0;
        int sectorIndex = 0;
        int hexIndex = 0;
        Boolean validInput = false;

        if (power == 2){
            repeat = 2;
        }
        else if (power == 3){
            repeat = 1;
        }

        for(int i = 0; i < repeat; i++){
            while (!validInput) {
                if(this.isHuman){
                    System.out.print(this.name + " : choose a Fleet : ");
                    fleetIndex = scanner.nextInt();
                    System.out.print(this.name + " : choose amount : ");
                    amount = scanner.nextInt();
                    System.out.print(this.name + " : choose a Sector : ");
                    sectorIndex = scanner.nextInt();
                    System.out.print(this.name + " : choose a Hex : ");
                    hexIndex = scanner.nextInt();
                }
                else{
                    // Randomly choose a fleet
                    fleetIndex = (int)(Math.random() * fleetList.size());
                    amount = (int)(Math.random() * fleetList.get(fleetIndex).getAmount() + 1);
                    sectorIndex = (int)(Math.random() * 7);
                    hexIndex = (int)(Math.random() * 7);
                }

                if(sectorIndex == 6){
                    hexIndex = 0;
                }

                boolean validFleetAmount = this.validFleetAmount(fleetIndex, amount);

                boolean validHex = this.validHex(sectorIndex, hexIndex);

                boolean validNeighbour = this.validNeighbour(sectorIndex, hexIndex, fleetIndex);

                validInput = validFleetAmount && validHex && validNeighbour;

                if(fleetList.get(fleetIndex).getHex() == game.getCarte().getSectors().get(sectorIndex).getHexs().get(hexIndex)){
                    validInput = false;
                }

                if(fleetIndex >= fleetList.size()){
                    fleetIndex = fleetList.size() - 1;
                }

                if(fleetIndex < 0){
                    fleetIndex = 0;
                }

            }
            
            validInput = false;
            fleetList.get(fleetIndex).setAmount(fleetList.get(fleetIndex).getAmount() - amount);
            if(game.getCarte().getSectors().get(sectorIndex).getHexs().get(hexIndex).getFleet() == null){
                game.getCarte().getSectors().get(sectorIndex).getHexs().get(hexIndex).setFleet(new Fleet(game.getCarte().getSectors().get(sectorIndex).getHexs().get(hexIndex), amount, this));
                fleetList.add(game.getCarte().getSectors().get(sectorIndex).getHexs().get(hexIndex).getFleet());
            }
            else{
                game.getCarte().getSectors().get(sectorIndex).getHexs().get(hexIndex).getFleet().setAmount(game.getCarte().getSectors().get(sectorIndex).getHexs().get(hexIndex).getFleet().getAmount() + amount);
            }

            System.out.println(this.toString());

            if(this.getFleets().size() == 0){
                game.end();
            }
        }
    }

    /**
     * Permet au joueur d'effectuer la commande "Exterminate" pour attaquer des systèmes ennemis.
     * Cette commande consiste à envoyer des flottes pour envahir les hexagones occupés par d'autres joueurs,
     * dans le but de prendre le contrôle des systèmes adverses.
     * 
     * Règles spécifiques :
     * - Une flotte attaquante doit être dans un hex voisin du système cible.
     * - Les combats se résolvent en comparant les tailles des flottes. Chaque joueur perd un nombre de vaisseaux
     *   égal à la taille de la plus petite flotte impliquée.
     * - Une invasion peut soit capturer le système, soit le rendre inoccupé si les forces s'annulent.
     * - Les hex déjà contrôlés par le joueur ne peuvent pas être ciblés.
     * 
     * Implémentation :
     * - Le joueur choisit un hex cible et une flotte à proximité pour l'invasion.
     * - Le pouvoir utilisé influence le nombre d'attaques possibles dans un tour.
     * - Les flottes restantes après le combat déterminent le nouveau contrôle du système.
     * 
     * @param power La puissance utilisée pour l'extermination, influençant le nombre d'attaques possibles.
     */

    public void exterminate(int power){
        int repeat = 3;
        Boolean validInput = false;
        if (power == 2){
            repeat = 2;
        }
        else if (power == 3){
            repeat = 1;
        }

        for(int i = 0; i < repeat; i++){
            int sectorIndex = 0;
            int hexIndex = 0;
            int fleetIndex = 0;
            while (!validInput) {
                if(this.isHuman){
                    System.out.println(this.name + " : choose a Sector to exterminate : ");
                    sectorIndex = scanner.nextInt();
                    System.out.println(this.name + " : choose a Hex to exterminate : ");
                    hexIndex = scanner.nextInt();
                    System.out.println(this.name + " : choose a Fleet to use : ");
                    fleetIndex = scanner.nextInt();
                }
                else{
                    // Randomly choose a sector
                    sectorIndex = (int)(Math.random() * 7);
                    // Randomly choose a hex
                    hexIndex = (int)(Math.random() * 7);
                    // Randomly choose a fleet
                    fleetIndex = (int)(Math.random() * fleetList.size());
                }

                if(fleetIndex >= fleetList.size()){
                    fleetIndex = fleetList.size() - 1;
                }

                if(fleetIndex < 0){
                    fleetIndex = 0;
                }

                if(sectorIndex == 6){
                    hexIndex = 0;
                }

                validInput = validNeighbourSolo(sectorIndex, hexIndex, fleetIndex);
            }
            validInput = false;
            Hex tempHex = game.getCarte().getSectors().get(sectorIndex).getHexs().get(hexIndex);
            int amountLeft;
            if(hasSystem(sectorIndex, hexIndex)){
                if(tempHex.getFleet() == null){
                    amountLeft = - fleetList.get(fleetIndex).getAmount();
                }
                else{
                    amountLeft = tempHex.getFleet().getAmount() - fleetList.get(fleetIndex).getAmount();
                }

                if(amountLeft < 0){
                    fleetList.get(fleetIndex).setAmount(0);
                    if(tempHex.getFleet() == null){
                        tempHex.setFleet(new Fleet(tempHex, Math.abs(amountLeft), this));
                        fleetList.add(tempHex.getFleet());
                    }
                    else{
                        tempHex.getFleet().setAmount(0);
                        tempHex.setFleet(new Fleet(tempHex, Math.abs(amountLeft), this));
                        fleetList.add(tempHex.getFleet());
                    }
                    
                }
                else if(amountLeft == 0){
                    tempHex.getFleet().setAmount(0);
                    fleetList.get(fleetIndex).setAmount(0);
                }
                else{
                    tempHex.getFleet().setAmount(amountLeft);
                    fleetList.get(fleetIndex).setAmount(0);
                }
            }
            else{
                System.out.println("No system to exterminate");
            }
            System.out.println(this.toString());

            if(this.getFleets().size() == 0){
                game.end();
            }
            }
        }

    /**
     * Retourne une représentation sous forme de chaîne de caractères des informations du joueur.
     * 
     * @return Une chaîne de caractères représentant le joueur.
     */
    @Override
    public String toString(){
        String result = this.name + " :\n";
        for (Fleet fleet : fleetList){
            result += fleet.toString() + "\n";
        }
        return  result;
    }

}