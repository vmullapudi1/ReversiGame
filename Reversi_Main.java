import javax.swing.*;
import java.awt.Point;
/**
 * Initializes the board and players, runs the game
 */
class Reversi_Main {
    private static Board b;
    private static Player p1;
    private static Player p2;
    /**
     *
     */
    public static void main(String...args) {
        //create the board
        final int N=queryBoardSize();
        b=new Board(N);
        //DEBUG
        //System.out.println(b);
        //System.out.println(b);
        //create the players
        switch (queryPlayer1()){

            case HUMAN:
                p1=new HumanPlayer(Piece.WHITE, "Player 1");
                break;
            case CPU:
                p1=new CPUPlayer(Piece.WHITE, "Player 1-CPU");
                break;
        }

        switch (queryPlayer2()){

            case HUMAN:
                p2=new HumanPlayer(Piece.BLACK, "Player 2");
                break;
            case CPU:
                p2=new CPUPlayer(Piece.BLACK, "Player 2-CPU");
                break;
        }

        startGame();

    }

    private static void startGame(){
        //Used to store the moves returned by the players to pass to the board
        Point p;
        //Run the game until it is determined that the game has ended
        while(true){
            p=p1.getMove(b);
            if(p==null) {
                JOptionPane.showMessageDialog(null,"The game has ended due, as no move was selected (game terminated or game over)");
                break;
            }
            b.performMove(p,p1.getPiece());
            //DEBUG Print boardstate to console
            //System.out.println(b);
            p=p2.getMove(b);
            if(p==null) {
                JOptionPane.showMessageDialog(null,"The game has ended due, as no move was selected (game terminated or game over)");
                break;
            }
            b.performMove(p,p2.getPiece());
            //DEBUG print boardstate to console
            //System.out.println(b);

        }
        scoreGame();

    }

    private static void scoreGame() {
        int[]scores=new int[2];
        for(Piece[]i:b.getBoardState()){
            for(Piece j:i){
                if(j==Piece.WHITE)
                    scores[0]++;
                else if(j==Piece.BLACK)
                    scores[1]++;
            }
        }
        //Print scores and winner
        StringBuilder message=new StringBuilder("P1");
        if(p1 instanceof HumanPlayer){
            message.append(", a human player, scored "+scores[0]+" points.\n");
        }
        else
            message.append(", a computer player, scored "+scores[0]+" points.\n");

        if(p2 instanceof HumanPlayer){
            message.append("P2, a human player, scored "+scores[1]+" points.\n");
        }
        else
            message.append("P2, a computer player, scored "+scores[1]+" points.\n");
        if(scores[0]>scores[1])
            message.append("P1 wins!");
        else if(scores[0]==scores[1])
            message.append("P1 tied P2!");
        else
            message.append("P2 wins!");
        JOptionPane.showMessageDialog(null,message.toString(),"Game Over!",JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * @return an even integer n>=2 representing the board side length. Asks the user.     *
     */
    private static int queryBoardSize() {

        boolean valid=false;
        int n=-1;
        //Repeat until user says no or valid input is returned
        while(!valid) {
            String s = JOptionPane.showInputDialog("Enter the desired board size (even numbers greater than or equal to 2 only)", "4");
            try {
                n=Integer.parseInt(s);
            }
            catch (NumberFormatException e){
                int choice=JOptionPane.showConfirmDialog(null, "The input Number could not be parsed. would you like to try again?","Input error",
                        JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(choice==JOptionPane.NO_OPTION){
                    JOptionPane.showMessageDialog(null,"User elected not to enter a valid input. \n Exiting.","Exiting",JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
            valid=n%2==0&&n>1;
            if(!valid){
                int choice=JOptionPane.showConfirmDialog(null, "The input number was not even or not >=2. Would you like to try again?","Input error",
                        JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                if(choice==JOptionPane.NO_OPTION){
                    JOptionPane.showMessageDialog(null,"User elected not to enter a valid input. \n Exiting.","Exiting",JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
        }
        return n;
    }

    /**
     * @return The type of player that is represented by P1
     */
    private static PlayerType queryPlayer1() {
        PlayerType[] possibleValues = { PlayerType.HUMAN, PlayerType.CPU };

        return (PlayerType)JOptionPane.showInputDialog(null,
                "Choose one", "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0]);
    }

    /**
     * @return the type of player that is represented by p2;
     */
    private static PlayerType queryPlayer2() {
        PlayerType[] possibleValues = { PlayerType.HUMAN, PlayerType.CPU };
        return (PlayerType)JOptionPane.showInputDialog(null,
                "Choose one", "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0]);
    }

}