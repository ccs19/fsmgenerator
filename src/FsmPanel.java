import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Chris on 3/1/2015.
 */
public class FsmPanel extends JPanel
{

    //Text field lengths
    final private int JTF_NUMSTATES  = 5;
    final private int JTF_ALPHABET = 10;
    final private int JTF_TRANSITIONS = 20;
    final private int JTF_STARTSTATE = 5;
    final private int JTF_ACCEPTSTATES = 10;


    //Text field label strings
    final private String numStatesString = "Number of states\n (0 - n-1)";
    final private String startStateString = "Start State";
    final private String acceptStatesString = "Accept States (Seperated by commas)";
    final private String alphabetString = "Alphabet (Seperated by commas)";
    final private String[] transitionsString = {"State Transistions (Seperate by commas)" , "Format: (current:dest:input)"};
    final private String saveString = "Save";
    final private String exitString = "Exit";
    final private String exitConfirmMessage = "Are you sure you want to exit?\nUnsaved automaton will be lost.";


    //GridBagConstraints padding
    Insets labelInset = new Insets(10,10,0,10);
    Insets textFieldInset = new Insets(0,10,10,10);


    //User input fields
    private JTextField numStates,
            startState,
            acceptStates,
            alphabet,
            transitions;
    ////////////////////

    FsmPanel()
    {
        super(new GridBagLayout());
        GridBagConstraints gbc;

        /**Top row**/
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        this.add(topRow(), gbc);

        /**Alphabet**/
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel alphabetLabel = new JLabel(alphabetString);
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.insets = labelInset;
        gbc.gridwidth = 1;
        this.add(alphabetLabel, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        alphabet = new JTextField(JTF_ALPHABET);
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.insets = textFieldInset;
        gbc.gridwidth = 3;
        this.add(alphabet, gbc);
        /**********************************/



        /**State Transisitions**/
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel transitionsLabel = new JLabel(transitionsString[0]);
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.insets = labelInset;
        gbc.gridwidth = 1;
        this.add(transitionsLabel, gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel transitionsLabel2 = new JLabel(transitionsString[1]);
        gbc.gridx=0;
        gbc.gridy=4;
        gbc.insets = new Insets(0,10,0,10);
        gbc.gridwidth = 1;
        this.add(transitionsLabel2,gbc);

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        transitions = new JTextField(JTF_TRANSITIONS);
        gbc.gridx=0;
        gbc.gridy=5;
        gbc.insets = textFieldInset;
        gbc.gridwidth = 3;
        this.add(transitions, gbc);
        /**********************************/


        /**Save & Exit Buttons**/
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx=1;
        gbc.gridy=6;
        gbc.gridwidth = 2;
        gbc.insets = textFieldInset;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        this.add(addSaveExitButtons(),gbc );

        this.setVisible(true);
    }






    private JPanel topRow()
    {
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel topRow = new JPanel(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;

        /**Number of states**/
        JLabel numStatesLabel = new JLabel(numStatesString);
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.insets = labelInset;
        topRow.add(numStatesLabel, gbc);

        numStates = new JTextField(JTF_NUMSTATES);
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.insets = textFieldInset;
        topRow.add(numStates, gbc);
        /***********************************/


        /**Start state**/
        JLabel startStateLabel = new JLabel(startStateString);
        gbc.gridx=1;
        gbc.gridy=0;
        gbc.insets = labelInset;
        topRow.add(startStateLabel, gbc);

        startState = new JTextField(JTF_STARTSTATE);
        gbc.gridx=1;
        gbc.gridy=1;
        gbc.insets = textFieldInset;
        topRow.add(startState, gbc);
        /**********************************/

        /**Accept states**/
        JLabel acceptStatesLabel = new JLabel(acceptStatesString);
        gbc.gridx=2;
        gbc.gridy=0;
        gbc.insets = labelInset;
        topRow.add(acceptStatesLabel, gbc);

        acceptStates = new JTextField(JTF_ACCEPTSTATES);
        gbc.gridx=2;
        gbc.gridy=1;
        gbc.insets = textFieldInset;
        topRow.add(acceptStates, gbc);
        /**********************************/
        return topRow;
    }




    private JPanel addSaveExitButtons()
    {
        //JPanel to return
        JPanel buttonsPanel = new JPanel(new GridLayout(1,3,10,0));

        /**Save Button**/
        JButton saveButton = new JButton(saveString);
        saveButton.addActionListener(new SaveButtonListener(this));
        buttonsPanel.add(saveButton);
        /**********************************/

        /**Exit Button**/
        final Component thisPanel = this;
        JButton exitButton = new JButton(exitString);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**int reply = JOptionPane.showConfirmDialog(thisPanel, exitConfirmMessage, exitString, JOptionPane.YES_NO_OPTION);
                if(reply == JOptionPane.YES_OPTION)
                {**/
                    System.exit(0);
             //   }
            }
        });
        buttonsPanel.add(exitButton);
        /**********************************/

        return buttonsPanel;
    }


    public String getNumStates() {
        return numStates.getText();
    }

    public String getStartState() {
        return startState.getText();
    }

    public String getAcceptStates() {
        return acceptStates.getText();
    }

    public String getAlphabet() {
        return alphabet.getText();
    }

    public String getTransitions() {
        return transitions.getText();
    }
}
