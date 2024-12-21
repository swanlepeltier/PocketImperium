package com.pocketimperium;

import com.pocketimperium.game.Game;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
        for (int i = 0; i<9; i++){
            game.playRound();
        }
    }
}
