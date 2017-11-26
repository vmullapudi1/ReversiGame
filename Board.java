import java.awt.*;
import java.util.*;
/**
 * Stores the and alters the state of the board as needed.
 */
class Board {
    private Piece[][] boardState;
    private int boardDimension;

    LinkedHashSet<Point> getCurrentEmptySpaces() {
        return currentEmptySpaces;
    }

    private LinkedHashSet<Point> currentEmptySpaces;
    /**
     * Default constructor
     */
    public Board() {
        this(4);
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

    private static LinkedHashSet<Point> findEmptySpaces(Piece[][] c){
        LinkedHashSet<Point> spaces=new LinkedHashSet<>();
        for(int i=0;i<c.length;i++){
            for(int j=0;j<c[i].length;j++){
                if(c[i][j]==Piece.NONE)
                    spaces.add(new Point(i,j));
            }
        }
        return spaces;
    }
    /**
     * @param move the location to place the piece
     * @param p The piece type to place an the board location indicated by move
     */
     void performMove(java.awt.Point move, Piece p) {
        boardState[move.y][move.x]=p;
        currentEmptySpaces.remove(move);
        int tempX=move.x;
        int tempY=move.y;
        Piece current;
        boolean valid=false;
        //check up, down,left/right, and all the diagonal directions for being terminated in a piece of the same color and having the other color in between
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                tempX+=x;
                tempY+=y;
                //check to make sure still in bounds of the array
                if(tempX<0||tempY<0||tempX>=boardDimension||tempY>=boardDimension)
                    continue;
                current  = boardState[tempY][tempX];
                //if an empty space is reached or the same color search in this direction is over
                if(current==p||current==Piece.NONE)
                    continue;
                //Search in a given direction
                while (true) {
                    tempX += x;
                    tempY += y;
                    //stop searching in this direction if the bounds are broken
                    if(tempX<0||tempY<0||tempX>=boardDimension||tempY>=boardDimension)
                        break;
                    current = boardState[tempY][tempX];

                    // If the algorithm finds another piece of the same color along a direction
                    // end the search in that direction, since the move is valid
                    if (current == p) {
                        valid = true;
                        break;
                    }
                }
                //if valid direction, backtrack and flip all the tokens in the middle
                if(valid){
                    boardState[tempY][tempX]=p;
                    while(true){
                        if(boardState[tempY][tempX]==p)
                            break;
                        tempX -= x;
                        tempY -= y;
                        boardState[tempY][tempX]=p;
                    }
                }
            }
        }


    }


    @Override
    public String toString() {
        String s= Arrays.deepToString(boardState).replaceAll("], ","]\n");
        return s.substring(1,s.length()-1)+"\n";
    }

}