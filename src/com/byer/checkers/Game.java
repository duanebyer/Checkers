package com.byer.checkers;

/**
 *
 * @author Duane Byer
 */
public class Game {
    
    public Game(Player whitePlayer, Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = new Board();
        this.isWhiteTurn = true;
    }
    
    public int takeNextTurn() {
        if (this.isWhiteTurn) {
            if (this.board.isBlackWinner()) {
                return -1;
            }
            this.board = this.whitePlayer.getMove(this.board, true);
        }
        else {
            if (this.board.isWhiteWinner()) {
                return 1;
            }
            this.board = this.blackPlayer.getMove(this.board, false);
        }
        
        this.isWhiteTurn = !this.isWhiteTurn;
        return 0;
    }
    
    public Board getBoard() {
        return this.board;
    }
    
    private Board board;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private boolean isWhiteTurn;
    
}
