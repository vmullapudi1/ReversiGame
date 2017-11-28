import javax.swing.*;
import java.awt.*;
/**
 * Initializes the board and players, runs the game
 */
public class Reversi_Main {
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
        System.out.println(b);
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
        //Begin the game loop
        startGame();

    }

    /**
     * Runs the game until it is over
     */
    private static void startGame(){
        StringBuilder separator = new StringBuilder(b.getBoardDimension() * 3);
        for (int i = 0; i < b.getBoardDimension() * 3; i++)
            separator.append('-');
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
            JOptionPane.showMessageDialog(null, b.toString());
            //DEBUG Print boardstate to console
            System.out.println(separator);
            System.out.println(b);
            System.out.println("p1 Move Success: " + p);
            p=p2.getMove(b);
            if(p==null) {
                JOptionPane.showMessageDialog(null,"The game has ended, as no move was selected (game terminated or game over)");
                break;
            }
            b.performMove(p,p2.getPiece());
            JOptionPane.showMessageDialog(null, b.toString());
            //DEBUG print boardstate to console
            System.out.println(separator);
            System.out.println(b);
            System.out.println("p2 Move Success: " + p);

        }
        //show the scores/who won
        displayScore(b);
    }

    /**
     * Prints the scores and victor given a board state
     *
     * @param b the board to print the scores for
     */
    private static void displayScore(Board b) {
        int[] scores = Board.scoreGame(b);
        //Print scores and winner
        StringBuilder message = new StringBuilder();
        if (p1 instanceof HumanPlayer) {
            message.append("P1, a human player, scored ").append(scores[0]).append(" points.\n");
        } else
            message.append("P1, a computer player, scored ").append(+scores[0]).append(" points.\n");

        if (p2 instanceof HumanPlayer) {
            message.append("P2, a human player, scored ").append(scores[1]).append(" points.\n");
        } else
            message.append("P2, a computer player, scored ").append(scores[1]).append(" points.\n");
        if(scores[0]>scores[1])
            message.append("P1 wins!");
        else if(scores[0]==scores[1])
            message.append("P1 tied P2!");
        else
            message.append("P2 wins!");
        JOptionPane.showMessageDialog(null,message.toString(),"Game Over!",JOptionPane.INFORMATION_MESSAGE);
        //DEBUG
        System.out.println(message);
    }

    /**
     * ask the user how big to make the board
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
            } catch (NumberFormatException e) {
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
     * ask the user what type of player to make the first player
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
     * ask the user what type of player to make p2
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