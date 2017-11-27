import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashSet;

/**
 * A class represeting a human player. prompts the human to choose from the legal moves.
 */
public class HumanPlayer extends Player {

// --Commented out by Inspection START (11/27/2017 15:17):
//    /**
//     * Default constructor
//     */
//    public HumanPlayer(){
//        this(Piece.WHITE,"Default Human");
//    }
// --Commented out by Inspection STOP (11/27/2017 15:17)

    HumanPlayer(Piece p, String name) {
        super(p,name);
    }

    /**
     * A method that takes a board, gets its legal moves, and asks the user to choose one
     *
     * @param b
     * @return a point representing the chosen move
     */
    @Override
    public Point getMove(Board b){
        LinkedHashSet<Point> legalMoves=super.legalMoves(b);
        Point[] moves=legalMoves.toArray(new Point[legalMoves.size()]);
        if(moves.length==0) {
            return null;
        }
        return (Point)JOptionPane.showInputDialog(null,b.toString()+"\nChoose one of the following legal moves:",
                ("Player Move Select: "+super.getPlayerName()),JOptionPane.QUESTION_MESSAGE,null,moves,moves[0]);
    }

}