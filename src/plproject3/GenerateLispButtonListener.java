package plproject3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by chris_000 on 4/2/2015.
 */
public class GenerateLispButtonListener implements ActionListener {

    //Generated state machine
    private Fsm fsm;
    //Fsm data
    private FsmData fsmData;

    //Parent JPanel and load listener with data
    private FsmSolverPanel parent = null;
    private LoadButtonListener loadButtonListener = null;

    //word to check
    private String word = null;

    //Quicksave option
    private boolean quickSave;

    //Executor thread info
    private int timeOut = 5;
    private ExecutorService fsmThread;

    //Quick save filename
    private static final String quickSaveName = "fsm.lsp";

    //Manual save info
    private static final String lispExt = ".lsp",
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
        printData();
    }

    private void printData(){
        GenerateLisp g = new GenerateLisp(fsm);
        String lispData = g.generateLisp();
        if(!quickSave)
            FileManager.saveFile(parent,lispData, lispExt, lispDescription);
        else if (quickSave){
            FileManager.saveFile(parent, lispData, quickSaveName);
        }
    }

    public void setFsmData(FsmData fsmData){
        this.fsmData = fsmData;
        fsm = new Fsm(fsmData);
        fsm.setOption(Fsm.checkOption.generateStateTable);
        fsmThread = Executors.newSingleThreadExecutor();
        fsmThread.submit(fsm);
        fsmThread.shutdown();
        try{
            fsmThread.awaitTermination(timeOut, TimeUnit.SECONDS);
        }catch(Exception e){
            JOptionPane.showMessageDialog(parent, "Timeout when generating Lisp", "Timeout", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void setQuickSave(boolean option){
        this.quickSave = option;
    }
}


