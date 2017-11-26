
import java.awt.*;

/**
 * 
 */
public class CPUPlayer extends Player {

    /**
     * Default constructor
     */
    public CPUPlayer() {
        this(Piece.BLACK,"Default CPU");
    }

    CPUPlayer(Piece p,String name){
        super(p,name);
    }

    @Override
    public Point getMove(Board b) {
        return null;
    }

}