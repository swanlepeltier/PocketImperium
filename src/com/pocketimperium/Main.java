package com.pocketimperium;

import com.pocketimperium.game.Game;

import java.io.File;
import java.io.IOException;

public class Main {
    private static final String SAVE_FILE_PATH = "save/save.ser";

    public static void main(String[] args) {
        Game game;
        File saveFile = new File(SAVE_FILE_PATH);

        File saveDir = saveFile.getParentFile();
        if (saveDir != null && !saveDir.exists()) {
            if (saveDir.mkdirs()) {
                System.out.println("Save directory created.");
            } else {
                System.err.println("Failed to create save directory.");
                return;
            }
        }

        if (saveFile.exists()) {
            try {
                game = Game.loadGame(SAVE_FILE_PATH);
                System.out.println("Game loaded from save file.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Failed to load game. Starting a new game.");
                game = new Game();
                game.start();
            }
        } else {
            try {
                if (saveFile.createNewFile()) {
                    System.out.println("Save file created.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to create new save file.");
                return;
            }
            game = new Game();
            game.start();
        }

        for (int i = game.getRound(); i < 9; i++) {
            game.playRound();
            game.score(i);
            try {
                game.saveGame(SAVE_FILE_PATH);
                System.out.println("Game saved.");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to save game.");
            }
        }

        game.end();
    }
}
