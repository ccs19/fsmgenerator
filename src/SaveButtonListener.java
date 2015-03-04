import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.Integer.*;

/**
 * Created by Chris on 3/3/2015.
 */
public class SaveButtonListener implements ActionListener {

    FsmPanel listenPanel;
    String saveString = "";
    String numStates = "";
    String alphabet = "";
    String stateTransitions = "";
    String startState = "";
    String acceptStates = "";

    //Safe verification parameters
    ArrayList<String> unsafeSaveReasons;

    SaveButtonListener(FsmPanel jPanel)
    {
        listenPanel = jPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        unsafeSaveReasons = new ArrayList<String>();
        checkStrings();
        if(unsafeSaveReasons.size() > 0) {  //If any invalid input in text fields...

            String dialogMessage = "Cannot save\n";

            for(int i = 0; i < unsafeSaveReasons.size(); i++)
            {
                dialogMessage += unsafeSaveReasons.get(i) + "\n";
            }
            JOptionPane.showMessageDialog(null, dialogMessage, "Cannot save", JOptionPane.OK_OPTION);
        }
        else {
            JFileChooser jFileChooser = new JFileChooser();
        }
    }

    private void checkStrings() {
        checkNumStates();
        checkAlphabet();
        checkAccepStates();
        checkStateTransitions();
        checkStartState();
    }

    private void checkNumStates()
    {
        String numStates = listenPanel.getNumStates();
        try
        {
            Integer.parseInt(numStates);
        }
        catch(NumberFormatException e )
        {
            unsafeSaveReasons.add("Invalid entry in Number of States");
        }
    }

    private void checkAlphabet()
    {

    }

    private void checkAccepStates()
    {

    }

    private void checkStateTransitions()
    {

    }

    private void checkStartState()
    {
        startState = listenPanel.getStartState();
        try
        {
            Integer.parseInt(startState);
        }
        catch (NumberFormatException e)
        {
            unsafeSaveReasons.add("Invalid entry in Start State");
        }
    }
}
