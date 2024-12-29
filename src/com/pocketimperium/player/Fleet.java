package com.pocketimperium.player;

import java.io.Serializable;

import com.pocketimperium.game.Hex;

public class Fleet implements Serializable{
    private int amount;
    private Hex hex;
    private Player player;

    public Fleet(Hex hex, int amount, Player player) {
        this.hex = hex;
        this.amount = amount;
        this.player = player;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        if (this.amount == 0) {
            this.hex.setFleet(null);
            this.player.removeFleet(this);
        }
    }

    public int getAmount() {
        return amount;
    }

    public Hex getHex() {
        return hex;
    }

    public void setHex(Hex hex) {
        this.hex = hex;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString(){
        return this.amount + " Ship in " + this.hex.toString();
    }

}
