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

    private Scanner scanner = new Scanner(System.in);

    public Player(Game game){
        fleetList = new ArrayList<>();
        this.game = game;
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
        System.out.print(this.name + " : choose a Sector : ");
        return scanner.nextInt();
    }

    public int chooseHex(){
        System.out.print(this.name + " : choose a Hex : ");
        return scanner.nextInt();
    }

    public int[] chooseCardOrder(){
        System.out.print(this.name + " : choose the order of the cards : ");
        for (int i = 0; i < 3; i++){
            cardOrder[i] = scanner.nextInt();
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

    public void expand(int power){
        int fleetIndex;
        if(this.isHuman){
            System.out.print(this.name + " : choose a Fleet : ");
            fleetIndex = scanner.nextInt();
        }
        else{
            // Randomly choose a fleet
            fleetIndex = (int)(Math.random() * fleetList.size());
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
    }

    public void explore(int power){
        int repeat = 3;
        int fleetIndex;
        int amount;
        int sectorIndex;
        int hexIndex;

        if (power == 2){
            repeat = 2;
        }
        else if (power == 3){
            repeat = 1;
        }

        for(int i = 0; i < repeat; i++){
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
                amount = (int)(Math.random() * fleetList.get(fleetIndex).getAmount());
                sectorIndex = (int)(Math.random() * fleetList.get(fleetIndex).getHex().getSector());
                hexIndex = (int)(Math.random() * fleetList.get(fleetIndex).getHex().getId());
            }
            fleetList.get(fleetIndex).setAmount(fleetList.get(fleetIndex).getAmount() - amount);
            Fleet tempFleet = new Fleet(game.carte.getSectors().get(sectorIndex).getHexs().get(hexIndex), amount, this);
            game.carte.getSectors().get(sectorIndex).getHexs().get(hexIndex).setFleet(tempFleet);
            fleetList.add(tempFleet);
        }
    }

    public void exterminate(int power){
        System.out.println(this.name + " : choose a Sector to exterminate : ");
        int sectorIndex = scanner.nextInt();
        System.out.println(this.name + " : choose a Hex to exterminate : ");
        int hexIndex = scanner.nextInt();
        System.out.println(this.name + " : choose a Fleet to use : ");
        int fleetIndex = scanner.nextInt();

        Hex tempHex = game.carte.getSectors().get(sectorIndex).getHexs().get(hexIndex);

        int amountLeft = tempHex.getFleet().getAmount() - fleetList.get(fleetIndex).getAmount();
        if(amountLeft < 0){
            tempHex.getFleet().setAmount(0);
            tempHex.setFleet(new Fleet(tempHex, Math.abs(amountLeft), this));
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

    @Override
    public String toString(){
        String result = this.name + " :\n";
        for (Fleet fleet : fleetList){
            result += fleet.toString() + "\n";
        }
        return  result;
    }

}
