/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.byer.checkers;

import java.util.List;

/**
 *
 * @author duane_000
 */
public class StupidCheckersPlayer extends Player {

    @Override
    public Board getMove(Board board, boolean isWhite) {
        /*System.out.println("I'm thinking hard.");
        try {
            Thread.sleep(1);
        }
        catch (Exception exception) {
            
        }*/
        List<Board> getMoves = board.getPossibleMoves(isWhite);
        return getMoves.get((int) (Math.random() * getMoves.size()));
    }
    
}
