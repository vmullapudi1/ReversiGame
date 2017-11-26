
/**
 * 
 */
public enum Piece {
    BLACK("B"),
    WHITE("W"),
    NONE("-");
    private String letter;

    Piece(String s){
        this.letter=s;
    }
    @Override
    public String toString() {
        return this.letter;
    }
}