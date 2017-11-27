
/**
 * An enum that enumerates the types of pieces that are present in the game board
 */
public enum Piece {
    BLACK("B"),
    WHITE("W"),
    NONE("0");
    private String letter;

    Piece(String s){
        this.letter=s;
    }
    @Override
    public String toString() {
        return this.letter;
    }
}