package plproject3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Listener for generate lisp button
 */
public class GenerateLispButtonListener implements ActionListener {

    //Generated state machine
    private Fsm fsm;


    //Parent JPanel and load listener with data
    private FsmSolverPanel parent = null;

    //Quicksave option
    private boolean quickSave = false;

    //Executor thread info
    private static final int timeOut = 5;

    //Quick save filename
    private static final String quickSaveName = "fsm.lsp";

    //Manual save info
    private static final String lispExt = "lsp",
            lispDescription = "Lisp (*.lsp)";



    /**
     * Word solving class
     * @param fsmSolverPanel Parent to the solve button
     */
    GenerateLispButtonListener(FsmSolverPanel fsmSolverPanel){
        parent = fsmSolverPanel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        fsm.setQueryStrings(parent.getQueryStrings());
        printData();
    }

    /**
     * Makes a call to generate lisp and saves the data
     */
    private void printData(){
        GenerateLisp g = new GenerateLisp(fsm);
        String lispData = g.generateLisp();
        if(!quickSave)
            FileManager.saveFile(parent,lispData, lispExt, lispDescription);
        else {
            FileManager.saveFile(parent, lispData, quickSaveName);
        }
    }

    /**
     * Sets the fsmData and generates the state machine based on that data
     * @param fsmData Data of the FSM
     */
    public void setFsmData(FsmData fsmData){
        fsm = new Fsm(fsmData);
        fsm.setOption(Fsm.checkOption.generateStateTable);
        ExecutorService fsmThread = Executors.newSingleThreadExecutor();
        fsmThread.submit(fsm);
        fsmThread.shutdown();
        try{
            fsmThread.awaitTermination(timeOut, TimeUnit.SECONDS);
        }catch(Exception e){
            JOptionPane.showMessageDialog(parent, "Timeout when generating Lisp", "Timeout", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Sets the quicksave option
     * @param option Whether or not this listener should quicksave
     */
    public void setQuickSave(boolean option){
        this.quickSave = option;
    }
}


