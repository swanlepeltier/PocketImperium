package com.pocketimperium.player;

public class Ship {
    private int id;
    private Player owner;

    public Ship(int id, Player owner) {
        this.id = id;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getOwner() {
        return owner;
    }
}
