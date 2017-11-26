import javax.swing.*;
import java.awt.Point;
import java.util.*;

/**
 * 
 */
public class HumanPlayer extends Player {

    /**
     * Default constructor
     */
    public HumanPlayer(){
        this(Piece.WHITE,"Default Human");
    }

    HumanPlayer(Piece p, String name) {
        super(p,name);
    }

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