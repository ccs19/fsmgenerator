package plproject3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by chris_000 on 4/2/2015.
 */
public class GenerateLispButtonListener implements ActionListener {

    //Generated state machine
    Fsm fsm;
    //Fsm data
    FsmData fsmData;

    //Parent JPanel and load listener with data
    private FsmSolverPanel parent = null;
    private LoadButtonListener loadButtonListener = null;

    //word to check
    private String word = null;




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

}


