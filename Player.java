import java.awt.*;
import java.util.LinkedHashSet;

/**
 * an abstract class defining basic functionalities needed ot play the game, and containing a utility method getLegalMoves() that finds the legal moves of a position for the player
 */
abstract class Player {
    //Stores player piece (Black or white)
    private Piece p;
    //Stores player name (Cpu or human)
    private String playerName;

    /**
     * Default constructor
     */
    Player(Piece p, String name) {
        this.p = p;
        this.playerName = name;
    }

    String getPlayerName() {
        return playerName;
    }

    /**
     * @return a point representing the location of the player's next move
     * An abstract method so hat both types of players can be asked for their next move
     */
    public abstract java.awt.Point getMove(Board b);

    /**
     * @return The Piece representing the piece color of the player
     */
    Piece getPiece() {
        return p;
    }

    /**
     * A method that is used to generate the availible moves for human and cpu players from a boardstate
     *
     * @param b the boardstate that is checked for legal moves
     * @return a LinkedHashSet<Point> containing all of the legal moves for that player given the boardstate
     */
    LinkedHashSet<Point> legalMoves(Board b) {
        //Start with all the empty spaces-places where it is possible to put a piece
        LinkedHashSet<Point> candidates = b.getCurrentEmptySpaces();
        LinkedHashSet<Point> moves = new LinkedHashSet<>();
        Piece[][] state = b.getBoardState();
        int dimension = b.getBoardDimension();
        //Coordinates used to store the location of the piece being checked/traversed
        int tempX;
        int tempY;
        Piece current;
        for (Point p : candidates) {
            //check up, down,left/right, and all the diagonal directions for being terminated in a piece of the same color and having the other color in between
            searchLoop:
            //the case where x,y=0,0 is ignored since that isnt a search direction
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x == 0 && y == 0)
                        continue;
                    tempX = p.x + x;
                    tempY = p.y + y;
                    //check to make sure still in bounds of the array
                    if (tempX < 0 || tempY < 0 || tempX >= dimension || tempY >= dimension)
                        continue;
                    current = state[tempY][tempX];
                    //if an empty space is reached or the same color search in this direction is over-Need a piece of the other color in the middle
                    if (current == this.p || current == Piece.NONE)
                        continue;
                    //If at least one opposing piece is found in the middle start checking in that direction
                    while (true) {
                        //Move in the direction
                        tempX += x;
                        tempY += y;
                        //stop searching in this direction if the bounds are broken or if a blank spot is found
                        if (tempX < 0 || tempY < 0 || tempX >= dimension || tempY >= dimension)
                            break;
                        current = state[tempY][tempX];
                        if (current == Piece.NONE)
                            break;
                        // If the algorithm finds another piece of the same color along a direction
                        // end the loop, since the move is valid
                        if (current == this.p) {
                            moves.add(p);
                            break searchLoop;
                        }
                        //Otherwise if a piece of the opposite color is found keep traversing
                    }
                }
            }

        }
        return moves;
    }
}