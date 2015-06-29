package com.byer.checkers;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Duane Byer
 */
public final class Board {
    
    public Board() {
        this.data = new PieceType[8][8];
        
        this.data[0][0] = this.data[0][2] = this.data[0][4] = this.data[0][6] = PieceType.WhiteChecker;
        this.data[1][1] = this.data[1][3] = this.data[1][5] = this.data[1][7] = PieceType.WhiteChecker;
        this.data[2][0] = this.data[2][2] = this.data[2][4] = this.data[2][6] = PieceType.WhiteChecker;
        
        this.data[7][1] = this.data[7][3] = this.data[7][5] = this.data[7][7] = PieceType.BlackChecker;
        this.data[6][0] = this.data[6][2] = this.data[6][4] = this.data[6][6] = PieceType.BlackChecker;
        this.data[5][1] = this.data[5][3] = this.data[5][5] = this.data[5][7] = PieceType.BlackChecker;
    }
    
    private Board(Board board) {
        this.data = new PieceType[8][8];
        for (int i = 0; i < 8; ++i) {
            System.arraycopy(board.data[i], 0, this.data[i], 0, 8);
        }
    }
    
    private Board(PieceType[][] data) {
        this.data = data;
    }
    
    public boolean isWhiteWinner() {
        this.calculatePossibleMoves(false);
        return this.blackPossibleMoves.isEmpty();
    }
    
    public boolean isBlackWinner() {
        this.calculatePossibleMoves(true);
        return this.whitePossibleMoves.isEmpty();
    }
    
    public PieceType getPieceAt(int row, int column) {
        return this.data[row][column];
    }
    
    public List<Board> getPossibleMoves(boolean isWhiteTurn) {
        this.calculatePossibleMoves(isWhiteTurn);
        return isWhiteTurn ? this.whitePossibleMoves : this.blackPossibleMoves;
    }
    
    private void calculatePossibleMoves(boolean isWhiteTurn) {
        if (isWhiteTurn && this.hasWhitePossibleMoves) {
            return;
        }
        else if (!isWhiteTurn && this.hasBlackPossibleMoves) {
            return;
        }
        List<Board> jumpMoves = new ArrayList<>();
        List<Board> advanceMoves = new ArrayList<>();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (isWhiteTurn) {
                    if (this.data[i][j] == PieceType.WhiteChecker || this.data[i][j] == PieceType.WhiteKing) {
                        jumpMoves.addAll(getJumpMoves(i, j, isWhiteTurn));
                        advanceMoves.addAll(getAdvanceMoves(i, j, isWhiteTurn));
                    }
                }
                else {
                    if (this.data[i][j] == PieceType.BlackChecker || this.data[i][j] == PieceType.BlackKing) {
                        jumpMoves.addAll(getJumpMoves(i, j, isWhiteTurn));
                        advanceMoves.addAll(getAdvanceMoves(i, j, isWhiteTurn));
                    }
                }
            }
        }
        if (!jumpMoves.isEmpty()) {
            if (isWhiteTurn) {
                this.hasWhitePossibleMoves = true;
                this.whitePossibleMoves = jumpMoves;
            }
            else {
                this.hasBlackPossibleMoves = true;
                this.blackPossibleMoves = jumpMoves;
            }
        }
        else {
            if (isWhiteTurn) {
                this.hasWhitePossibleMoves = true;
                this.whitePossibleMoves = advanceMoves;
            }
            else {
                this.hasBlackPossibleMoves = true;
                this.blackPossibleMoves = advanceMoves;
            }
        }
    }
    
    private List<Board> getAdvanceMoves(int row, int column, boolean isWhiteTurn) {
        List<Board> result = new ArrayList<>();
        if (isWhiteTurn) {
            if (row <= 6) {
                if (column <= 6) {
                    if (this.data[row + 1][column + 1] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row + 1][column + 1] = row == 6 ? PieceType.WhiteKing : this.data[row][column];
                        Board next = new Board(newData);
                        result.add(next);
                    }
                }
                if (column >= 1) {
                    if (this.data[row + 1][column - 1] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row + 1][column - 1] = row == 6 ? PieceType.WhiteKing : this.data[row][column];
                        Board next = new Board(newData);
                        result.add(next);
                    }
                }
            }
            if (this.data[row][column] == PieceType.WhiteKing && row >= 1) {
                if (column <= 6) {
                    if (this.data[row - 1][column + 1] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row - 1][column + 1] = PieceType.WhiteKing;
                        Board next = new Board(newData);
                        result.add(next);
                    }
                }
                if (column >= 1) {
                    if (this.data[row - 1][column - 1] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row - 1][column - 1] = PieceType.WhiteKing;
                        Board next = new Board(newData);
                        result.add(next);
                    }
                }
            }
        }
        else {
            if (row >= 1) {
                if (column <= 6) {
                    if (this.data[row - 1][column + 1] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row - 1][column + 1] = row == 1 ? PieceType.BlackKing : this.data[row][column];
                        Board next = new Board(newData);
                        result.add(next);
                    }
                }
                if (column >= 1) {
                    if (this.data[row - 1][column - 1] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row - 1][column - 1] = row == 1 ? PieceType.BlackKing : this.data[row][column];
                        Board next = new Board(newData);
                        result.add(next);
                    }
                }
            }
            if (this.data[row][column] == PieceType.BlackKing && row <= 6) {
                if (column <= 6) {
                    if (this.data[row + 1][column + 1] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row + 1][column + 1] = PieceType.BlackKing;
                        Board next = new Board(newData);
                        result.add(next);
                    }
                }
                if (column >= 1) {
                    if (this.data[row + 1][column - 1] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row + 1][column - 1] = PieceType.BlackKing;
                        Board next = new Board(newData);
                        result.add(next);
                    }
                }
            }
        }
        return result;
    }
    
    private List<Board> getJumpMoves(int row, int column, boolean isWhiteTurn) {
        List<Board> result = new ArrayList<>();
        if (isWhiteTurn) {
            if (row <= 5) {
                if (column <= 5) {
                    if ((this.data[row + 1][column + 1] == PieceType.BlackChecker || this.data[row + 1][column + 1] == PieceType.BlackKing)
                            && this.data[row + 2][column + 2] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row + 1][column + 1] = null;
                        newData[row + 2][column + 2] = row == 5 ? PieceType.WhiteKing : this.data[row][column];
                        Board next = new Board(newData);
                        List<Board> nextJumpMoves = next.getJumpMoves(row + 2, column + 2, isWhiteTurn);
                        if (nextJumpMoves.isEmpty()) {
                            result.add(next);
                        }
                        else {
                            result.addAll(nextJumpMoves);
                        }
                    }
                }
                if (column >= 2) {
                    if ((this.data[row + 1][column - 1] == PieceType.BlackChecker || this.data[row + 1][column - 1] == PieceType.BlackKing)
                            && this.data[row + 2][column - 2] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row + 1][column - 1] = null;
                        newData[row + 2][column - 2] = row == 5 ? PieceType.WhiteKing : this.data[row][column];
                        Board next = new Board(newData);
                        List<Board> nextJumpMoves = next.getJumpMoves(row + 2, column - 2, isWhiteTurn);
                        if (nextJumpMoves.isEmpty()) {
                            result.add(next);
                        }
                        else {
                            result.addAll(nextJumpMoves);
                        }
                    }
                }
            }
            if (this.data[row][column] == PieceType.WhiteKing && row >= 2) {
                if (column <= 5) {
                    if ((this.data[row - 1][column + 1] == PieceType.BlackChecker || this.data[row - 1][column + 1] == PieceType.BlackKing)
                            && this.data[row - 2][column + 2] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row - 1][column + 1] = null;
                        newData[row - 2][column + 2] = PieceType.WhiteKing;
                        Board next = new Board(newData);
                        List<Board> nextJumpMoves = next.getJumpMoves(row - 2, column + 2, isWhiteTurn);
                        if (nextJumpMoves.isEmpty()) {
                            result.add(next);
                        }
                        else {
                            result.addAll(nextJumpMoves);
                        }
                    }
                }
                if (column >= 2) {
                    if ((this.data[row - 1][column - 1] == PieceType.BlackChecker || this.data[row - 1][column - 1] == PieceType.BlackKing)
                            && this.data[row - 2][column - 2] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row - 1][column - 1] = null;
                        newData[row - 2][column - 2] = PieceType.WhiteKing;
                        Board next = new Board(newData);
                        List<Board> nextJumpMoves = next.getJumpMoves(row - 2, column - 2, isWhiteTurn);
                        if (nextJumpMoves.isEmpty()) {
                            result.add(next);
                        }
                        else {
                            result.addAll(nextJumpMoves);
                        }
                    }
                }
            }
        }
        else {
            if (row >= 2) {
                if (column <= 5) {
                    if ((this.data[row - 1][column + 1] == PieceType.WhiteChecker || this.data[row - 1][column + 1] == PieceType.WhiteKing)
                            && this.data[row - 2][column + 2] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row - 1][column + 1] = null;
                        newData[row - 2][column + 2] = row == 2 ? PieceType.BlackKing : this.data[row][column];
                        Board next = new Board(newData);
                        List<Board> nextJumpMoves = next.getJumpMoves(row - 2, column + 2, isWhiteTurn);
                        if (nextJumpMoves.isEmpty()) {
                            result.add(next);
                        }
                        else {
                            result.addAll(nextJumpMoves);
                        }
                    }
                }
                if (column >= 2) {
                    if ((this.data[row - 1][column - 1] == PieceType.WhiteChecker || this.data[row - 1][column - 1] == PieceType.WhiteKing)
                            && this.data[row - 2][column - 2] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row - 1][column - 1] = null;
                        newData[row - 2][column - 2] = row == 2 ? PieceType.BlackKing : this.data[row][column];
                        Board next = new Board(newData);
                        List<Board> nextJumpMoves = next.getJumpMoves(row - 2, column - 2, isWhiteTurn);
                        if (nextJumpMoves.isEmpty()) {
                            result.add(next);
                        }
                        else {
                            result.addAll(nextJumpMoves);
                        }
                    }
                }
            }
            if (this.data[row][column] == PieceType.BlackKing && row <= 5) {
                if (column <= 5) {
                    if ((this.data[row + 1][column + 1] == PieceType.WhiteChecker || this.data[row + 1][column + 1] == PieceType.WhiteKing)
                            && this.data[row + 2][column + 2] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row + 1][column + 1] = null;
                        newData[row + 2][column + 2] = PieceType.BlackKing;
                        Board next = new Board(newData);
                        List<Board> nextJumpMoves = next.getJumpMoves(row + 2, column + 2, isWhiteTurn);
                        if (nextJumpMoves.isEmpty()) {
                            result.add(next);
                        }
                        else {
                            result.addAll(nextJumpMoves);
                        }
                    }
                }
                if (column >= 2) {
                    if ((this.data[row + 1][column - 1] == PieceType.WhiteChecker || this.data[row + 1][column - 1] == PieceType.WhiteKing)
                            && this.data[row + 2][column - 2] == null) {
                        PieceType[][] newData = new PieceType[8][8];
                        for (int i = 0; i < 8; ++i) {
                            System.arraycopy(this.data[i], 0, newData[i], 0, 8);
                        }
                        newData[row][column] = null;
                        newData[row + 1][column - 1] = null;
                        newData[row + 2][column - 2] = PieceType.BlackKing;
                        Board next = new Board(newData);
                        List<Board> nextJumpMoves = next.getJumpMoves(row + 2, column - 2, isWhiteTurn);
                        if (nextJumpMoves.isEmpty()) {
                            result.add(next);
                        }
                        else {
                            result.addAll(nextJumpMoves);
                        }
                    }
                }
            }
        }
        return result;
    }
    
    public boolean isMovePossible(Board board, boolean isWhiteTurn) {
        this.calculatePossibleMoves(isWhiteTurn);
        return isWhiteTurn ? this.whitePossibleMoves.contains(board) : this.blackPossibleMoves.contains(board);
    }
    
    public Board getMove(String moveStr, boolean isWhiteTurn) {
        Board result = new Board(this);
        moveStr = moveStr.replace(" ", "");
        if (moveStr.length() < 5) {
            return null;
        }
        int pos1row = Integer.parseInt(moveStr.charAt(0) + "") - 1;
        int pos1col = Integer.parseInt(moveStr.charAt(1) + "") - 1;
        boolean capturing = moveStr.charAt(2) == 'x';
        if (!capturing && moveStr.charAt(2) != '-') {
            return null;
        }
        int pos2row = Integer.parseInt(moveStr.charAt(3) + "") - 1;
        int pos2col = Integer.parseInt(moveStr.charAt(4) + "") - 1;
        moveStr = moveStr.substring(5);
        while (true) {
            if (pos1row >= 8 || pos1row < 0 || pos1col >= 8 || pos1col < 0 ||
                pos2row >= 8 || pos2row < 0 || pos2col >= 8 || pos2col < 0) {
                    return null;
            }
            if (!capturing && (Math.abs(pos2row - pos1row) != 1 || Math.abs(pos2col - pos1col) != 1)) {
                return null;
            }
            if (capturing && (Math.abs(pos2row - pos1row) != 2 || Math.abs(pos2col - pos1col) != 2)) {
                return null;
            }
            if (!capturing) {
                result.data[pos2row][pos2col] = result.data[pos1row][pos1col];
                result.data[pos1row][pos1col] = null;
            }
            else {
                result.data[(pos1row + pos2row) / 2][(pos1col + pos2col) / 2] = null;
                result.data[pos2row][pos2col] = result.data[pos1row][pos1col];
                result.data[pos1row][pos1col] = null;
            }
            if (isWhiteTurn && pos2row == 7) {
                result.data[pos2row][pos2col] = PieceType.WhiteKing;
            }
            else if (!isWhiteTurn && pos2row == 0) {
                result.data[pos2row][pos2col] = PieceType.BlackKing;
            }
            if (moveStr.length() == 0) {
                break;
            }
            else if (moveStr.length() < 3) {
                return null;
            }
            pos1row = pos2row;
            pos1col = pos2col;
            capturing = moveStr.charAt(0) == 'x';
            if (!capturing && moveStr.charAt(0) != '-') {
                return null;
            }
            pos2row = Integer.parseInt(moveStr.charAt(1) + "") - 1;
            pos2col = Integer.parseInt(moveStr.charAt(2) + "") - 1;
            moveStr = moveStr.substring(3);
        }
        return result;
    }
    
    public void print(PrintWriter writer) {
        // +---+
        // |   |
        // +---+
        writer.write("   +---+---+---+---+---+---+---+---+\n");
        for (int i = 7; i >= 0; --i) {
            writer.write(" " + (i + 1) + " |");
            for (int j = 0; j < 8; ++j) {
                char piece = ' ';
                if (this.data[i][j] == PieceType.WhiteChecker) {
                    piece = 'o';
                }
                if (this.data[i][j] == PieceType.WhiteKing) {
                    piece = 'O';
                }
                if (this.data[i][j] == PieceType.BlackChecker) {
                    piece = 'x';
                }
                if (this.data[i][j] == PieceType.BlackKing) {
                    piece = 'X';
                }
                writer.write(" " + piece + " |");
            }
            writer.write("\n   +---+---+---+---+---+---+---+---+\n");
        }
        writer.write("     1   2   3   4   5   6   7   8  \n");
        writer.flush();
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Board) {
            Board board = (Board) other;
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    if (this.data[i][j] != board.data[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
    }
    
    private boolean hasWhitePossibleMoves = false;
    private boolean hasBlackPossibleMoves = false;
    private List<Board> whitePossibleMoves;
    private List<Board> blackPossibleMoves;
    private final PieceType[][] data;
    
}
