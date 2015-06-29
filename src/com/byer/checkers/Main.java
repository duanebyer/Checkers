package com.byer.checkers;

import java.io.PrintWriter;

/**
 *
 * @author DuaneByer
 */
public class Main {
    
    public static void main(String[] args) {
        Game game = new Game(new StupidCheckersPlayer(), new StupidCheckersPlayer());
        while (true) {
            game.getBoard().print(new PrintWriter(System.out));
            int result = game.takeNextTurn();
            if (result == -1) {
                System.out.println("Black wins!");
                break;
            }
            if (result == 1) {
                System.out.println("White wins!");
                break;
            }
        }
    }
    
}
