import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Listener for save button
 */
public class SaveButtonListener implements ActionListener {

    FsmPanel listenPanel;
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

            for (String unsafeSaveReason : unsafeSaveReasons) {
                dialogMessage += unsafeSaveReason + "\n";
            }
            //Show dialog with input errors found
            JOptionPane.showMessageDialog(null, dialogMessage, "Cannot save", JOptionPane.OK_OPTION);
        }
        else {
            saveAutomaton();
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
        numStates = listenPanel.getNumStates();
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
        alphabet = listenPanel.getAlphabet();
        if(alphabet.length() == 0)
            unsafeSaveReasons.add("Invalid entry in Alphabet");
    }

    private void checkAccepStates()
    {
        acceptStates = listenPanel.getAcceptStates();
        if(acceptStates.length() == 0)
            unsafeSaveReasons.add("Invalid entry in Accept States");
    }

    private void checkStateTransitions()
    {
        stateTransitions = listenPanel.getTransitions();
        if(stateTransitions.length() == 0)
            unsafeSaveReasons.add("Invalid entry in State Transitions");
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

    private void saveAutomaton()
    {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(".")); //Select project directory
        int returnVal = jFileChooser.showSaveDialog(listenPanel);

        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = jFileChooser.getSelectedFile();

            try {
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
                fileWriter.write(numStates + "\n" +
                                 alphabet + "\n" +
                                 stateTransitions + "\n" +
                                 startState + "\n" +
                                 acceptStates);
                fileWriter.close();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "Failed to save. Cannot write to disk.", "Fatal Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
