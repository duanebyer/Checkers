package com.byer.checkers;

import java.io.PrintWriter;

/**
 *
 * @author DuaneByer
 */
public class Main {
    
    public static void main(String[] args) {
        Game game = new Game(new StupidCheckersPlayer(), new StupidCheckersPlayer(), 20);
        while (true) {
            game.getBoard().print(new PrintWriter(System.out));
            Game.Status result = game.takeNextTurn();
            if (result == Game.Status.BlackWin) {
                System.out.println("Black wins!");
                break;
            }
            if (result == Game.Status.WhiteWin) {
                System.out.println("White wins!");
                break;
            }
            if (result == Game.Status.Tie) {
                System.out.println("Tie!");
            }
        }
    }
    
}
