
import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Random;

/**
 * 
 */
public class CPUPlayer extends Player {
    private int difficulty;
    /**
     * Default constructor
     */
    public CPUPlayer() {
        this(Piece.BLACK,"Default CPU");
    }

    CPUPlayer(Piece p,String name){
        super(p,name);
        Integer[] diff = {0, 1};
        difficulty = (int) JOptionPane.showInputDialog(null, "Select CPU Difficulty Level",
                ("Difficulty Select: " + super.getPlayerName()), JOptionPane.QUESTION_MESSAGE, null, diff, diff[0]);
    }

    @Override
    public Point getMove(Board b) {
        LinkedHashSet<Point> moveCandidates = super.legalMoves(b);
        Point[] moves = new Point[moveCandidates.size()];
        //If difficulty 0, just have the computer play a random move
        //if(difficulty==0){
        moves = moveCandidates.toArray(moves);
        Point m;


        if (moves.length == 1)
            m = moves[0];
        else
            m = moves[new Random().nextInt(moves.length - 1)];
        JOptionPane.showMessageDialog(null, "CPU Player " + super.getPlayerName() + " plays move " + m, getPlayerName() + " move", JOptionPane.INFORMATION_MESSAGE);
        return m;
        //}

        //Point m= getMove(b,0,moves,new int[moves.length]);
        //JOptionPane.showMessageDialog(null,"CPU Player "+super.getPlayerName()+" plays move "+m,getPlayerName()+" move",JOptionPane.INFORMATION_MESSAGE);
        //return m;
    }

    private Point getMove(Board b, int depth, Point[] moveCandidates, int[] netScore) {

        for (int i = 0; i < moveCandidates.length; i++) {
            Board newBoard = Board.simulateMove(moveCandidates[i], super.getPiece(), b);
            int[] newScores = Board.scoreGame(newBoard);
            if (super.getPiece() == Piece.WHITE)
                netScore[i] = newScores[0] - newScores[1];
            else
                netScore[i] = newScores[1] - newScores[0];
        }
        int highestScoreIndex = 0;
        int max = netScore[0];
        for (int i = 1; i < netScore.length; i++) {
            if (max < netScore[i]) {
                max = netScore[i];
                highestScoreIndex = i;
            }
        }
        return moveCandidates[highestScoreIndex];
    }

}