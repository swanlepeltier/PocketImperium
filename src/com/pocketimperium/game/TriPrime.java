package com.pocketimperium.game;

public class TriPrime extends Sector {
    public TriPrime(int id) {
        super(id, true);
        hexagones.add(new Hex(0, id, 3));
    }
}
