import com.sun.corba.se.spi.orbutil.fsm.FSM;

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

    final private String numStatesString = "Number of states";
    final private String startStateString = "Start State";
    final private String acceptStatesString = "Accept States";
    final private String alphabetString = "Alphabet";
    final private String transitionsString = "State Transistions";
    final private String saveString = "Save";
    final private String exitString = "Exit";
    final private String exitConfirmMessage = "Are you sure you want to exit?\nUnsaved changes will be lost.";

    private JTextField numStates,
            startState,
            acceptStates,
            alphabet,
            transitions;

    FsmPanel()
    {
        super(new GridBagLayout());

        Insets labelInset = new Insets(10,10,0,10);
        Insets textFieldInset = new Insets(0,10,10,10);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;


        /**Number of states**/
        JLabel numStatesLabel = new JLabel(numStatesString);
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.insets = labelInset;
        this.add(numStatesLabel, gbc);

        numStates = new JTextField(JTF_NUMSTATES);
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.insets = textFieldInset;
        this.add(numStates, gbc);
        /***********************************/


        /**Start state**/
        JLabel startStateLabel = new JLabel(startStateString);
        gbc.gridx=1;
        gbc.gridy=0;
        gbc.insets = labelInset;
        this.add(startStateLabel, gbc);

        startState = new JTextField(JTF_STARTSTATE);
        gbc.gridx=1;
        gbc.gridy=1;
        gbc.insets = textFieldInset;
        this.add(startState, gbc);
        /**********************************/

        /**Accept states**/
        JLabel acceptStatesLabel = new JLabel(acceptStatesString);
        gbc.gridx=2;
        gbc.gridy=0;
        gbc.insets = labelInset;
        this.add(acceptStatesLabel, gbc);

        acceptStates = new JTextField(JTF_ACCEPTSTATES);
        gbc.gridx=2;
        gbc.gridy=1;
        gbc.insets = textFieldInset;
        this.add(acceptStates, gbc);
        /**********************************/


        /**Alphabet**/
        JLabel alphabetLabel = new JLabel(alphabetString);
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.insets = labelInset;
        this.add(alphabetLabel, gbc);

        alphabet = new JTextField(JTF_ALPHABET);
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.insets = textFieldInset;
        gbc.gridwidth=3;
        this.add(alphabet, gbc);
        /**********************************/



        /**State Transisitions**/
        JLabel transitionsLabel = new JLabel(transitionsString);
        gbc.gridx=0;
        gbc.gridy=4;
        gbc.insets = labelInset;
        gbc.gridwidth=1;
        this.add(transitionsLabel, gbc);

        transitions = new JTextField(JTF_TRANSITIONS);
        gbc.gridx=0;
        gbc.gridy=5;
        gbc.insets = textFieldInset;
        gbc.gridwidth=3;
        this.add(transitions, gbc);
        /**********************************/


        /**Save Button**/
        JButton saveButton = new JButton(saveString);
        saveButton.addActionListener(new SaveButtonListener(this));
        gbc.gridx=1;
        gbc.gridy=6;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(20,10,10,10);
        this.add(saveButton, gbc);
        /**********************************/


        /**Exit Button**/
        JButton exitButton = new JButton(exitString);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, exitConfirmMessage, exitString, JOptionPane.YES_NO_OPTION);
                if(reply == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }
            }
        });
        gbc.gridx=2;
        gbc.gridy=6;
        this.add(exitButton, gbc);
        /**********************************/

        this.setVisible(true);
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
