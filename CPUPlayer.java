import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Random;

/**
 * A class that represents the computerized player. Has eo difficulties: a level0 that returns a random valid move when asked for a move, and a level1 that returns the move that
 * results in the best score for it after the move.
 */
public class CPUPlayer extends Player {
    private int difficulty;
// --Commented out by Inspection START (11/27/2017 15:17):
//    /**
//     * Default constructor
//     */
//    public CPUPlayer() {
//        this(Piece.BLACK,"Default CPU");
//    }
// --Commented out by Inspection STOP (11/27/2017 15:17)

    /**
     * Constructor for creating cpu players
     * @param p the piece type assigned to this player
     * @param name a string describing this cpu players
     */
    CPUPlayer(Piece p,String name){
        super(p,name);
        Integer[] diff = {0, 1};
        difficulty = (int) JOptionPane.showInputDialog(null, "Select CPU Difficulty Level",
                ("Difficulty Select: " + super.getPlayerName()), JOptionPane.QUESTION_MESSAGE, null, diff, diff[0]);
    }

    /**
     * @param b the board that is used to generate the move played by the CPU
     * @return a point representing the location that the player would like to place a piece
     */
    @Override
    public Point getMove(Board b) {
        LinkedHashSet<Point> moveCandidates = super.legalMoves(b);
        Point[] moves = new Point[moveCandidates.size()];
        //Used to store the move
        Point m;
        moves = moveCandidates.toArray(moves);
        //If difficulty 0, just have the computer play a random move
        if (difficulty == 0) {
            if (moves.length == 1)
                m = moves[0];
            else
                m = moves[new Random().nextInt(moves.length - 1)];
            JOptionPane.showMessageDialog(null, "CPU Player " + super.getPlayerName() + " plays move " + m, getPlayerName() + " move", JOptionPane.INFORMATION_MESSAGE);
            return m;
        }
        //For the more advanced difficulty, have the cpu play the move with the highest resulting score in its favor
        //A proper decision tree could be used here for better AI
        m = getMove(b, moves, new int[moves.length]);
        JOptionPane.showMessageDialog(null, "CPU Player " + super.getPlayerName() + " plays move " + m, getPlayerName() + " move", JOptionPane.INFORMATION_MESSAGE);
        return m;
    }

    /**
     * @param b              the board upon which the move is to be played
     * @param moveCandidates an array of the possible valid moves
     * @param netScore       an int[] of scores resulting from each move (to be used to generate more moves into the future (not in use currently)
     * @return a Point representing the chosen move of the player.
     */
    private Point getMove(Board b, Point[] moveCandidates, int[] netScore) {
        //if there are no available moves, return null
        if (moveCandidates.length == 0)
            return null;
        //check each move candidate and generate scores for them
        for (int i = 0; i < moveCandidates.length; i++) {
            //used to avoid playing null moves prematurely
            if (moveCandidates[i] == null) {
                netScore[i] = Integer.MIN_VALUE;
                continue;
            }
            //Create a new board representing the board state after the move i
            Board newBoard = Board.simulateMove(moveCandidates[i], super.getPiece(), b);
            //score this board
            int[] newScores = Board.scoreGame(newBoard);
            if (super.getPiece() == Piece.WHITE)
                netScore[i] = newScores[0] - newScores[1];
            else
                netScore[i] = newScores[1] - newScores[0];
        }
        //find the best scored move
        int highestScoreIndex = 0;
        int max = netScore[0];
        for (int i = 1; i < netScore.length; i++) {
            if (max < netScore[i]) {
                max = netScore[i];
                highestScoreIndex = i;
            }
        }
        //play the best scored move
        return moveCandidates[highestScoreIndex];
    }

}