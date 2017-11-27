import java.awt.*;
import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * Stores the and alters the state of the board as needed.
 */
class Board {
    private Piece[][] boardState;
    private int boardDimension;
    private LinkedHashSet<Point> currentEmptySpaces;


// --Commented out by Inspection START (11/27/2017 15:17):
//    /**
//     * Default constructor
//     */
//    public Board() {
//        this(4);
//    }
// --Commented out by Inspection STOP (11/27/2017 15:17)

    private Board(Piece[][] boardState) {
        this.boardState = boardState;
        this.boardDimension = boardState.length;
        this.currentEmptySpaces = findEmptySpaces(boardState);
    }

    /**
     * Creates a board of the indicated dimension and populates it for the beginning of play.
      * @param dimension The board size N for the NxN array that represents the board state
     */
    Board(int dimension) {
       //set the dimension
        this.boardDimension=dimension;
        //create a boardstate that is empty
        boardState=new Piece[dimension][dimension];
        for(int i=0;i<boardState.length;i++){
            for(int j=0;j<boardState[i].length;j++){
                boardState[i][j]=Piece.NONE;
            }
        }
        //set the middle 4 squares with the starting configuration
       boardState[dimension/2-1][dimension/2-1]=Piece.WHITE;
       boardState[dimension/2-1][dimension/2]=Piece.BLACK;
       boardState[dimension/2][dimension/2-1]=Piece.BLACK;
       boardState[dimension/2][dimension/2]=Piece.WHITE;
       currentEmptySpaces=findEmptySpaces(boardState);
    }

    /**
     * @return an int representing the sidelength of the square playing board.
     */
    int getBoardDimension() {
        return this.boardDimension;
    }

    Piece[][] getBoardState() {
        return boardState;
    }

    /**
     * a utility method to get each players score given a board state
     *
     * @param b the board to score
     * @return an int[] with index 0 being p1's score and index 1 being p2's score
     */
    static int[] scoreGame(Board b) {
        int[] scores = new int[2];
        //count the number of pieces of each color on the board
        for (Piece[] i : b.getBoardState()) {
            for (Piece j : i) {
                if (j == Piece.WHITE)
                    scores[0]++;
                else if (j == Piece.BLACK)
                    scores[1]++;
            }
        }
        return scores;
    }

    /**
     * Finds all the empty spaces in a boardstate
     *
     * @param c a piece array representing the boardstate
     * @return a list containing the empty spaces
     */
    private static LinkedHashSet<Point> findEmptySpaces(Piece[][] c){
        LinkedHashSet<Point> spaces=new LinkedHashSet<>();
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                if(c[i][j]==Piece.NONE)
                    spaces.add(new Point(j, i));
            }
        }
        return spaces;
    }

    /**
     * @param move the location to place the piece
     * @param p The piece type to place an the board location indicated by move
     * @param b the board upon which to enact the move
     * @return the new boardstate after the move is done
     */
    static Board simulateMove(java.awt.Point move, Piece p, Board b) {
        int boardDimension = b.getBoardDimension();
        //create a copy of the board so that simulating the move doesn't change the original board
        Piece[][] boardState = new Piece[boardDimension][boardDimension];
        for (int i = 0; i < boardDimension; i++)
            System.arraycopy(b.getBoardState()[i], 0, boardState[i], 0, boardDimension);
        boardState[move.y][move.x]=p;
        int tempX;
        int tempY;
        Piece current;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                tempX = move.x + x;
                tempY = move.y + y;
                //check to make sure still in bounds of the array
                if (tempX < 0 || tempY < 0 || tempX >= boardDimension || tempY >= boardDimension)
                    continue;
                current = boardState[tempY][tempX];
                //if an empty space is reached or the same color search in this direction is over-Need a piece of the other color in the middle
                if (current == p || current == Piece.NONE)
                    continue;
                //
                while (true) {
                    //Move in the direction
                    tempX += x;
                    tempY += y;
                    //stop searching in this direction if the bounds are broken
                    if (tempX < 0 || tempY < 0 || tempX >= boardDimension || tempY >= boardDimension)
                        break;

                    current = boardState[tempY][tempX];
                    //If the piece that is run into is empty, the move isn't valid (can not have open spaces).
                    if (current == Piece.NONE)
                        break;
                    // If the algorithm finds another piece of the same color along a direction
                    // start going backwards and flipping over pieces, and then stop going in this direction
                    if (current == p) {
                        while (true) {
                            tempX -= x;
                            tempY -= y;
                            boardState[tempY][tempX] = p;
                            //stop flipping over once we've gotten back to the original spot
                            if (tempX == move.x && tempY == move.y)
                                break;
                        }
                        break;
                    }
                }
            }

        }
        return new Board(boardState);
    }

    LinkedHashSet<Point> getCurrentEmptySpaces() {
        currentEmptySpaces = findEmptySpaces(boardState);
        return currentEmptySpaces;
    }

    /**
     * Enacts a move on the board by utilizing the simulation method to generate the new board
     * @param move the location to place the piece
     * @param p    The piece type to place an the board location indicated by move
     */
    void performMove(java.awt.Point move, Piece p) {
        boardState = simulateMove(move, p, this).getBoardState();
        currentEmptySpaces = findEmptySpaces(boardState);
    }

    @Override
    public String toString() {
        String s= Arrays.deepToString(boardState).replaceAll("], ","]\n");
        return s.substring(1,s.length()-1)+"\n";
    }

}