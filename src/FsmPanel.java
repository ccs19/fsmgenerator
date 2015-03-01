import com.sun.corba.se.spi.orbutil.fsm.FSM;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Chris on 3/1/2015.
 */
public class FsmPanel extends JPanel
{

    //Text field lengths
    final private int JTF_NUMSTATES  = 5;
    final private int JTF_ALPHABET = 10;
    final private int JTF_TRANSITIONS = 75;
    final private int JTF_STARTSTATE = 5;
    final private int JTF_ACCEPTSTATES = 10;



    FsmPanel()
    {
        super(new GridLayout(5,2));

        //Text labels
        JLabel statesLabel = new JLabel("Number of States");
        JLabel alphabet = new JLabel("Alphabet");
        JLabel transitions = new JLabel("State Transitions");
        JLabel startState = new JLabel("Start State");
        JLabel acceptStates = new JLabel("Accept States");

        //Text Fields
        JTextField jtf_numStates = new JTextField(JTF_NUMSTATES);
        JTextField jtf_alphabet = new JTextField(JTF_ALPHABET);
        JTextField jtf_transitions = new JTextField(JTF_TRANSITIONS);
        JTextField jtf_startState = new JTextField(JTF_STARTSTATE);
        JTextField jtf_acceptStates = new JTextField(JTF_ACCEPTSTATES);


        //Insert items
        this.add(statesLabel);
        this.add(jtf_numStates);

        this.add(alphabet);
        this.add(jtf_alphabet);

        this.add(transitions);
        this.add(jtf_transitions);

        this.add(startState);
        this.add(jtf_startState);

        this.add(acceptStates);
        this.add(jtf_acceptStates);

        this.setVisible(true);
    }
}
