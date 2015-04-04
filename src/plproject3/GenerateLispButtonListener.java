package plproject3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by chris_000 on 4/2/2015.
 */
public class GenerateLispButtonListener implements ActionListener {

    //Parsed data
    private ArrayList<String> parsedAlphabet = null;
    private ArrayList<Integer> parsedAcceptStates = null;
    private ArrayList<String> parsedStateTransitions = null;
    private int numStates = -1;
    private int startState = -1;

    //Generated state machine
    Fsm fsm;



    //Parent JPanel and load listener with data
    private FsmSolverPanel parent = null;
    private LoadButtonListener loadButtonListener = null;

    //word to check
    private String word = null;

    //Fsm data
    FsmData fsmData;


    /**
     * Word solving class
     * @param fsmSolverPanel Parent to the solve button
     * @param listener Load button listener to retrieve strings
     */
    GenerateLispButtonListener(FsmSolverPanel fsmSolverPanel, LoadButtonListener listener, FsmData fsmData){
        parent = fsmSolverPanel;
        this.fsmData = fsmData;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        printData();
    }

    private void printData(){
        GenerateLisp g = new GenerateLisp(fsm);
        g.generateLisp();
    }



    /**
     * Checks if a valid word was found and shows a message dialog
     */
    private void isValidWord(){
        System.out.println("Accept states: ");
        for(int i : parsedAcceptStates)
            System.out.println(" " + i);
        if(fsm.getValid()){
            JOptionPane.showMessageDialog(parent, "String is valid for this FSM!", "Valid String!", JOptionPane.OK_OPTION);
        }
        else{
            JOptionPane.showMessageDialog(parent, "String is not valid for this FSM", "Invalid String!", JOptionPane.OK_OPTION);
        }
    }

}


