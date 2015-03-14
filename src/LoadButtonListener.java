import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by chris_000 on 3/10/2015.
 */
public class LoadButtonListener implements ActionListener {

    FsmSolverPanel listenPanel;
    String numStatesString = "";
    int numStates = 0;
    int startState = 0;
    int alphabetLength = 0;
    String alphabet = "";
    String parsedAlphabet[];
    String stateTransitions = "";
    String startStateString = "";

    String acceptStates = "";

    BufferedReader fileReader;


    //Safe verification parameters
    ArrayList<String> unsafeLoadReasons;

    LoadButtonListener(FsmSolverPanel jPanel){
        listenPanel = jPanel;
        unsafeLoadReasons = new ArrayList<String>();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        loadAutomaton();
        checkAutomaton();
    }

    private void loadAutomaton(){
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(".")); //Select project directory
        int returnVal = jFileChooser.showOpenDialog(listenPanel);

        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = jFileChooser.getSelectedFile();
            try {
                fileReader = new BufferedReader(new FileReader(file));
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(listenPanel, "Failed to load file. Cannot load from disk.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void checkAutomaton() {
        try {
            numStatesString = readNextLine();
            alphabet = readNextLine();
            stateTransitions = readNextLine();
            startStateString = readNextLine();
            acceptStates = readNextLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(listenPanel, "Error Reading File", "File Read Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidFsmFormatException e) {
            JOptionPane.showMessageDialog(listenPanel, "Invalid file format", "Invalid File Format", JOptionPane.ERROR_MESSAGE);
        }finally {
            try{
                fileReader.close();
            } catch (IOException e){
                JOptionPane.showMessageDialog(listenPanel, "Failed to close file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String readNextLine() throws IOException, InvalidFsmFormatException{
        String s = fileReader.readLine();
        if(s == null) {
            throw new InvalidFsmFormatException();
        }
        return s;
    }


    private class InvalidFsmFormatException extends Exception
    {

        public InvalidFsmFormatException(){
            super("Invalid FSM File Format");
        }
    }
}
