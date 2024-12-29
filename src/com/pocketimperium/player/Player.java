package com.pocketimperium.player;

import com.pocketimperium.game.Hex;
import java.util.ArrayList;
import java.util.Scanner;
import com.pocketimperium.game.Game;

public class Player {
    private String color;
    private ArrayList<Fleet> fleetList;
    private Boolean isHuman;
    private String name;
    private int remainingShip = 15;
    private int[] cardOrder = {1, 2, 3};
    private Game game;
    private int score = 0;

    private Scanner scanner = new Scanner(System.in);

    public Player(Game game){
        fleetList = new ArrayList<>();
        this.game = game;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
        return this.score;
    }

    public String getColor(){
        return this.color;
    }

    public void score(int sectorIndex){
        int tempScore = 0;
        for (Fleet fleet : fleetList){
            if(fleet.getHex().getSector() == sectorIndex){
                tempScore += fleet.getHex().getSysteme();
            }
        }
        this.score += tempScore;
    }

    public int getFleetListSize(){
        return fleetList.size();
    }

    public void placeFleetStart(Hex hex){
        Fleet tempFleet = new Fleet(hex, 2, this);
        fleetList.add(tempFleet);
        this.remainingShip -= 2;
        hex.setFleet(tempFleet);
    }

    public int chooseSector(){
        if(this.isHuman){
            System.out.print(this.name + " : choose a Sector : ");
            return scanner.nextInt();
        }
        else{
            return (int)(Math.random() * 6);
        }
    }

    public int chooseHex(){
        if(this.isHuman){
            System.out.print(this.name + " : choose a Hex : ");
            return scanner.nextInt();
        }
        else{
            return (int)(Math.random() * 6);
        }
    }

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

    public int[] getCardOrder(){
        return cardOrder;
    }

    public void setPlayerState(Boolean choice){
        this.isHuman = choice;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setColor(String color){
        this.color = color;
    }

    public ArrayList<Fleet> getFleets(){
        return fleetList;
    }

    public void addFleet(Hex hex, int amount){
        this.remainingShip -= amount;
        this.fleetList.add(new Fleet(hex, amount, this));
    }

    public void removeFleet(Fleet fleet){
        this.remainingShip += fleet.getAmount();
        this.fleetList.remove(fleet);
    }

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

    public Boolean validSector(int sectorIndex) {
        Boolean result = sectorIndex >= 0 && sectorIndex < game.getCarte().getSectors().size();
        if (!result) {
            if(this.isHuman){
                System.out.println("Error: The selected sector index is out of bounds.");
            }
        }
        return result;
    }

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

    public Boolean validNeighbour(int sectorIndex, int hexIndex, int fleetIndex) {
        Hex targetHex = game.getCarte().getSectors().get(sectorIndex).getHexs().get(hexIndex);
        Hex fleetHex = fleetList.get(fleetIndex).getHex();
        Boolean result = targetHex.getNeighbor().contains(fleetHex) || 
                targetHex.getNeighbor().stream().anyMatch(neighbour -> 
                    neighbour.getNeighbor().contains(fleetHex) && 
                    neighbour.getSector() != 6);
        if (!result) {
            if (this.isHuman) {
                System.out.println("Error:" + this.name + " : " + targetHex.toString() + " : " + fleetList.get(fleetIndex).toString());
            }
        }
        return result;
    }

    public Boolean validNeighbourSolo(int sectorIndex, int hexIndex, int fleetIndex) {
        Hex targetHex = game.getCarte().getSectors().get(sectorIndex).getHexs().get(hexIndex);
        Hex fleetHex = fleetList.get(fleetIndex).getHex();
        Boolean result = targetHex.getNeighbor().contains(fleetHex);
        if (!result) {
            if(this.isHuman){
                System.out.println("Error: The selected hex is not a direct neighbour of the fleet's current hex.");
            }
        }
        return result;
    }

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
        }
    }

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
            }
        }

    @Override
    public String toString(){
        String result = this.name + " :\n";
        for (Fleet fleet : fleetList){
            result += fleet.toString() + "\n";
        }
        return  result;
    }

}
