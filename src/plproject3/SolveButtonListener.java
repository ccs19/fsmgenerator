package plproject3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: Christopher Schneider
 * Programming Langauges Project 3
 * Finite State Machine Solver
 */
public class SolveButtonListener implements ActionListener{

    //Parsed data
    private ArrayList<String> parsedAlphabet = null;
    private ArrayList<Integer> parsedAcceptStates = null;
    private ArrayList<String> parsedStateTransitions = null;
    private int numStates = -1;
    private int startState = -1;

    //Generated state machine
    Fsm fsm;
    FsmData fsmData;



    //Parent JPanel and load listener with data
    private FsmPanelPart2 parent = null;
    private LoadButtonListener loadButtonListener = null;

    //word to check
    private String word = null;

    //Thread to run checking
    private ExecutorService checkWordThread = Executors.newSingleThreadExecutor();


    /**
     * Word solving class
     * @param fsmSolverPanel Parent to the solve button
     * @param listener Load button listener to retrieve strings
     */
    SolveButtonListener(FsmPanelPart2 fsmSolverPanel){
        parent = fsmSolverPanel;
    }


    public void setFsmData(FsmData fsmData){
        this.fsmData = fsmData;
    }

    public void setWord(String word){
        this.word = word;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkWord();
        printData();
    }

    private void printData(){
        GenerateLisp g = new GenerateLisp(fsm);
        g.generateLisp();
    }



    /**
     * Submits data to checkWordThread
     */
    private void checkWord(){
        fsm = new Fsm(fsmData, word);
        checkWordThread.submit(fsm);
        isValidWord();
    }


    /**
     * Checks if a valid word was found and shows a message dialog
     */
    private void isValidWord(){
        System.out.println("Accept states: ");
        for(int i = 0; i < fsmData.getAcceptStates().size(); i++){
            System.out.println(fsmData.getAcceptStates().get(i));
        }
        if(fsm.getValid()){
            JOptionPane.showMessageDialog(parent, "String is valid for this FSM!", "Valid String!", JOptionPane.OK_OPTION);
        }
        else{
            JOptionPane.showMessageDialog(parent, "String is not valid for this FSM", "Invalid String!", JOptionPane.OK_OPTION);
        }
    }

}
