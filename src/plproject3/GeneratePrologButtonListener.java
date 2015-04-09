package plproject3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Listener for generate Prolog button
 */
public class GeneratePrologButtonListener implements ActionListener {

    //Generated state machine
    private Fsm fsm;


    //Parent JPanel and load listener with data
    private FsmSolverPanel parent = null;

    //Quicksave option
    private boolean quickSave = false;

    //Executor thread info
    private int timeOut = 5;
    private ExecutorService fsmThread;

    //Quick save filename
    private static final String quickSaveName = "fsa.pl";

    //Manual save info
    private static final String prologExt = "pl",
            prologDescription = "Prolog (*.pl)";



    /**
     * Prolog listener class
     * @param fsmSolverPanel Parent to the solve button
     */
    GeneratePrologButtonListener(FsmSolverPanel fsmSolverPanel){
        parent = fsmSolverPanel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        printData();
    }

    /**
     * Makes a call to generate Prolog and saves the data
     */
    private void printData(){
        GenerateProlog g = new GenerateProlog(fsm);
        String prologData = g.generateProlog();
        if(!quickSave)
            FileManager.saveFile(parent,prologData, prologExt, prologDescription);
        else {
            FileManager.saveFile(parent, prologData, quickSaveName);
        }
    }

    /**
     * Sets the fsmData and generates the state machine based on that data
     * @param fsmData Data of the FSM
     */
    public void setFsmData(FsmData fsmData){
        fsm = new Fsm(fsmData);
        fsm.setOption(Fsm.checkOption.generateStateTable);
        fsmThread = Executors.newSingleThreadExecutor();
        fsmThread.submit(fsm);
        fsmThread.shutdown();
        try{
            fsmThread.awaitTermination(timeOut, TimeUnit.SECONDS);
        }catch(Exception e){
            JOptionPane.showMessageDialog(parent, "Timeout when generating State Tables", "Timeout", JOptionPane.ERROR_MESSAGE);
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
