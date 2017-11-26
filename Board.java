import java.util.Arrays;
/**
 * Stores the and alters the state of the board as needed.
 */
public class Board {
    private Piece[][] boardState;
    private int boardDimension;
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
   public Board(int dimension) {
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
    }

    /**
     * @return an int representing the sidelength of the square playing board.
     */
    public int getBoardDimension() {
        return this.boardDimension;
    }

    public Piece[][] getBoardState() {
        return boardState;
    }


    /**
     * @param move the location to place the piece
     * @param p The piece type to place an the board location indicated by move
     */
    public void performMove(java.awt.Point move, Piece p) {
        boardState[move.x][move.y]=p;
    }

    @Override
    public String toString() {
        String s= Arrays.deepToString(boardState).replaceAll("], ","]\n");
        return s.substring(1,s.length()-1);
    }

}