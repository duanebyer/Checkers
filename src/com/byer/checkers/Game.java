package com.byer.checkers;

/**
 *
 * @author Duane Byer
 */
public class Game {

    public Game(Player whitePlayer, Player blackPlayer, int turnLimit) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = new Board();
        this.isWhiteTurn = true;
        this.turnLimit = turnLimit;
    }
    
    public Status takeNextTurn() {
        turnsTaken ++;
        int numWhitePieces = this.board.getNumWhiteCheckers();
        int numBlackPieces = this.board.getNumBlackCheckers();
        if (this.isWhiteTurn) {
            if (this.board.isBlackWinner()) {
                return Status.BlackWin;
            }
            this.board = this.whitePlayer.getMove(this.board, true);
        }
        else {
            if (this.board.isWhiteWinner()) {
                return Status.WhiteWin;
            }
            this.board = this.blackPlayer.getMove(this.board, false);
        }
        this.isWhiteTurn = !this.isWhiteTurn;
        if (this.board.getNumWhiteCheckers() != numWhitePieces || this.board.getNumBlackCheckers() != numBlackPieces) {
            this.turnsSinceLastCapture = 0;
        }
        else {
            this.turnsSinceLastCapture += 1;
        }
        if (this.turnsSinceLastCapture > this.turnLimit) {
            if (numWhitePieces > numBlackPieces) {
                return Status.WhiteWin;
            }
            else if (numWhitePieces < numBlackPieces) {
                return Status.BlackWin;
            }
            else {
                return Status.Tie;
            }
        }
        return Status.Ongoing;
    }
    
    public Board getBoard() {
        return this.board;
    }
    
    public int getTurnsTaken() {
        return turnsTaken;
    }
    
    public enum Status {
        WhiteWin,
        BlackWin,
        Tie,
        Ongoing
    }
    
    private Board board;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private final int turnLimit;
    private int turnsSinceLastCapture = 0;
    private boolean isWhiteTurn;
    private int turnsTaken = 0;
    
}
