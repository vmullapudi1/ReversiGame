
import java.awt.*;
import java.util.*;

/**
 * 
 */
public class CPUPlayer extends Player {

    /**
     * Default constructor
     */
    public CPUPlayer() {
        this(Piece.BLACK);
    }

    public CPUPlayer(Piece p){
        super(p);
    }

    @Override
    public Point getMove(Board b) {
        return null;
    }

}