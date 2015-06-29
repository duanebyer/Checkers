/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.byer.checkers;

import java.util.Scanner;

/**
 *
 * @author duane_000
 */
public class HumanPlayer extends Player {
    
    @Override
    public Board getMove(Board board, boolean isWhite) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your move: ");
        while(true) {
            Board move = board.getMove(scanner.nextLine(), isWhite);
            if (board.isMovePossible(move, isWhite)) {
                return move;
            }
            else {
                System.out.println("Invalid move. Try again: ");
            }
        }
    }
    
}
