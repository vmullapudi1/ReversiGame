import java.awt.Point;
import java.util.LinkedHashSet;

abstract class Player {

    private Piece p;

    String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    private String playerName;
    /**
     * Default constructor
     */
    Player(Piece p, String name) {
        this.p = p;
        this.playerName=name;
    }


    /**
     * @return a point representing the location of the player's next move
     */
    public abstract java.awt.Point getMove(Board b);

    Piece getPiece() {
        return p;
    }

    public void setPiece(Piece p) {
        this.p = p;
    }


    LinkedHashSet<Point> legalMoves(Board b) {
        //Start with all the empty spaces-places where it is possible to put a piece
        LinkedHashSet<Point> candidates = b.getCurrentEmptySpaces();
        LinkedHashSet<Point> moves=new LinkedHashSet<>();
        Piece[][] state = b.getBoardState();
        int dimension=b.getBoardDimension();
        int tempX;
        int tempY;
        Piece current;
        for (Point p : candidates) {
            //check up, down,left/right, and all the diagonal directions for being terminated in a piece of the same color and having the other color in between
            searchLoop:
            //the case where x,y=0,0 is ignored since that is an empty spot
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    tempX=p.x+x;
                    tempY=p.y+y;
                    //check to make sure still in bounds of the array
                    if(tempX<0||tempY<0||tempX>=dimension||tempY>=dimension)
                        continue;
                    current  = state[tempY][tempX];
                    //if an empty space is reached or the same color search in this direction is over-Need a piece of the other color in the middle
                    if(current==this.p||current==Piece.NONE)
                        continue;
                    while (true) {
                        //Move in the direction
                        tempX += x;
                        tempY += y;
                        //stop searching in this direction if the bounds are broken
                        if(tempX<0||tempY<0||tempX>=dimension||tempY>=dimension)
                            break;
                        current = state[tempY][tempX];

                        // If the algorithm finds another piece of the same color along a direction
                        // end the loop, since the move is valid
                        if (current == this.p) {
                            moves.add(p);
                            break searchLoop;
                        }
                    }
                }
            }

        }
        return moves;
    }
}
