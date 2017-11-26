import javax.swing.*;
import java.awt.Point;
import java.util.regex.Pattern;

/**
 * 
 */
public class HumanPlayer extends Player {

    /**
     * Default constructor
     */
    public HumanPlayer(){
        this(Piece.WHITE);
    }

    public HumanPlayer(Piece p) {
        super(p);
    }

    public Point getMove(Board b){
        boolean valid=false;
        String s;
        int x;
        int y;
        while(!valid) {
            s=JOptionPane.showInputDialog(null, "Enter the point of your next move (ordered pair)", "Next Move?",
                    JOptionPane.QUESTION_MESSAGE);

                String[]nums=s.replaceAll("[()]","").split(",");
                try{
                    x=Integer.parseInt(nums[0]);
                    y=Integer.parseInt(nums[1]);
                    Point p=new Point(x,y);
                    if(checkMoveLegality(p,b))
                        return p;
                }
                catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"The input cannot be read. Please Try again.",
                        "Couldn't parse ordered pair",JOptionPane.INFORMATION_MESSAGE);
                }
            JOptionPane.showMessageDialog(null,"The input is not valid. Please Try again.",
                    "Invalid input",JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

}