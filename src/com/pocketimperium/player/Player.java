package com.pocketimperium.player;

import com.pocketimperium.game.Hex;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private String color;
    private ArrayList<Fleet> fleetList;
    private Boolean isHuman;
    private String name;
    private int remainingShip = 15;

    private Scanner scanner = new Scanner(System.in);

    public Player(){
        fleetList = new ArrayList<>();
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

    public void setPlayerState(Boolean choice){
        this.isHuman = choice;
    }

    public void setName(String name){
        this.name = name;
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

    publi

    @Override
    public String toString(){
        String result = this.name + " :\n";
        for (Fleet fleet : fleetList){
            result += fleet.toString() + "\n";
        }
        return  result;
    }

}
