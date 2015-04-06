package plproject3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author: Christopher Schneider
 * Programming Langauges Project 3
 * Finite State Machine Solver
 */
public class LoadStringButtonListener implements ActionListener{

    //Generated state machine
    Fsm fsm;
    FsmData fsmData;

    //Timeout for executor thread in seconds
    int timeOut = 30;


    //Parent JPanel and load listener with data
    private FsmSolverPanel parent = null;

    //word to check
    private String word = null;

    //Thread to run checking
    private ExecutorService checkWordThread;


    /**
     * Word solving class
     * @param fsmSolverPanel Parent to the solve button
     */
    LoadStringButtonListener(FsmSolverPanel fsmSolverPanel){
        parent = fsmSolverPanel;
    }


    /**
     * Prompts the user if they would like to load a string from disk. If so, the file is read, and the first
     * line in the file is placed in the text field.
     */
    private boolean loadString(){

        BufferedReader b = FileManager.openFile(parent);
        if(b == null) return false;
        else {
            try {
                String s = FileManager.readNextLine(b);
                parent.setWordEntryString(s);
                this.setWord(s);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Error Reading File", "File Read Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        }
    }


    /**
     *
     * @param fsmData Initialized FSM data
     */
    public void setFsmData(FsmData fsmData){
        this.fsmData = fsmData;
    }

    /**
     *
     * @param word Word to be analyzed
     */
    public void setWord(String word){
        this.word = word;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean result = loadString();
        if(result)
            checkWord();
    }



    /**
     * Submits data to checkWordThread
     */
    private void checkWord(){
        checkWordThread = Executors.newSingleThreadExecutor();
        fsm = new Fsm(fsmData, word);
        fsm.setOption(Fsm.checkOption.solveEntry);
        checkWordThread.submit(fsm);

        checkWordThread.shutdown();
        try {
            checkWordThread.awaitTermination(timeOut, TimeUnit.SECONDS);
            isValidWord();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(parent, "Timeout when solving FSM", "Timeout", JOptionPane.ERROR_MESSAGE);
        }
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
            JOptionPane.showMessageDialog(parent, "String is valid for this FSM!", "Valid String!", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(parent, "String is not valid for this FSM", "Invalid String!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
