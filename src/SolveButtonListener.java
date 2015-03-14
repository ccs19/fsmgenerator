import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Chris on 3/14/2015.
 */
public class SolveButtonListener implements ActionListener{

    //Parsed data
    private String parsedAlphabet[] = null;
    private int parsedAcceptStates[] = null;
    private String parsedStateTransitions[] = null;
    private int numStates = -1;
    private int startState = -1;

    //Parent JPanel
    FsmSolverPanel parent = null;


    SolveButtonListener(FsmSolverPanel fsmSolverPanel, LoadButtonListener listener){
        parsedAlphabet = listener.getParsedAlphabet();
        parsedAcceptStates = listener.getParsedAcceptStates();
        parsedStateTransitions = listener.getParsedStateTransitions();
        numStates = listener.getNumStates();
        startState = listener.getStartState();
        parent = fsmSolverPanel;
    }




    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
