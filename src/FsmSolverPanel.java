import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by chris_000 on 3/10/2015.
 */
public class FsmSolverPanel extends JPanel {

    final private String loadString = "Load";
    final private String exitString = "Exit";
    final private String solveString = "Solve";
    private static final String stringEntryString = "Word entry";

    //GridBagConstraints padding
    Insets labelInset = new Insets(10,10,0,10);
    Insets textFieldInset = new Insets(0,10,10,10);
    Insets loadExitBuffer = new Insets(0,0,0,5);

    Insets solveBuffer = new Insets(10,0,0,10);


    //User entry
    private JTextField stringEntry;


    //Constants
    private static final int JTF_STRINGENTRYLEN = 30;


    FsmSolverPanel()
    {
        super(new GridBagLayout());
        GridBagConstraints gbc;


        /**Text Entry**/
        JLabel stringEntryLabel = new JLabel(stringEntryString);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = labelInset;
        this.add(stringEntryLabel, gbc);


        stringEntry = new JTextField(JTF_STRINGENTRYLEN);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = textFieldInset;
        this.add(stringEntry, gbc);

        /**Solve button**/
        JButton solveButton = new JButton(solveString);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = textFieldInset;
        solveButton.addActionListener(new LoadButtonListener(this));
        this.add(solveButton, gbc);

        /** Load & Exit buttons**/
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = textFieldInset;
        this.add(addButtons(), gbc);

        this.setVisible(true);
    }

    private JPanel addButtons() {
        //JPanel to return
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        /**Load Button**/

        JButton loadButton = new JButton(loadString);
        loadButton.addActionListener(new LoadButtonListener(this));
        buttonsPanel.add(loadButton);
        /**********************************/

        /**Exit Button**/
       // gbc.insets = buttonInsets;
        JButton exitButton = new JButton(exitString);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
        });
        buttonsPanel.add(exitButton);
        /**********************************/

        return buttonsPanel;
    }

}
