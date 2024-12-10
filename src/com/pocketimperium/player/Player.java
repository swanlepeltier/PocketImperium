package com.pocketimperium.player;

public class Player {
    private String color;
    private Fleet[] fleetList;
    private Boolean isHuman;
    private String name;

    public void placeFleet(){
        
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
}
