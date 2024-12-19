package com.pocketimperium.player;

import com.pocketimperium.game.Hex;

public class Fleet {
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

    @Override
    public String toString(){
        return this.amount + " Ship in " + this.hex.toString();
    }

}
