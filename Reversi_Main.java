import javax.swing.*;
import java.awt.Point;
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
                p1=new HumanPlayer(Piece.WHITE);
                break;
            case CPU:
                p1=new CPUPlayer(Piece.WHITE);
                break;
        }

        switch (queryPlayer2()){

            case HUMAN:
                p2=new HumanPlayer(Piece.BLACK);
                break;
            case CPU:
                p2=new CPUPlayer(Piece.BLACK);
                break;
        }

        startGame();

    }

    private static void startGame(){
        boolean gameOver=false;
        Point p;
        while(!gameOver){
            p=p1.getMove(b);
            b.performMove(p,p1.getPiece());
            System.out.println(b);
            //checkOver();
            p=p2.getMove(b);
            b.performMove(p,p2.getPiece());
            System.out.println(b);
            //checkOver;
        }
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