public abstract class Player {

    private  Piece p;
    /**
     * Default constructor
     */
    public Player(Piece p) {
        this.p=p;
    }


    /**
     * @return a point representing the location of the player's next move
     */
    public abstract java.awt.Point getMove(Board b);

    public Piece getPiece() {
        return p;
    }

    public void setPiece(Piece p) {
        this.p = p;
    }

    public boolean checkMoveLegality(java.awt.Point move, Board b) {
        //check to make sure the coordinate is in bounds
        if(move.x>b.getBoardDimension()||move.x<0||move.y>b.getBoardDimension()||move.y<0)
            return false;
        if(b.getBoardState()[move.x][move.y]!=Piece.NONE)
            return false;
        //TODO Check Lines
        return true;
    }
}