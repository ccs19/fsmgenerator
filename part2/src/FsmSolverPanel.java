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
    final private String exitConfirmMessage = "Are you sure you want to exit?\nUnsaved automaton will be lost.";
    private static final String stringEntryString = "Word entry";

    //GridBagConstraints padding
    Insets labelInset = new Insets(10,10,0,10);
    Insets textFieldInset = new Insets(0,10,10,10);
    Insets buttonInsets = new Insets(0,0,0,5);


    //User entry
    private JTextField stringEntry;


    //Constants
    private static final int JTF_STRINGENTRYLEN = 25;


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

        /** Load & Exit buttons**/
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = textFieldInset;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        this.add(addLoadExitButtons(), gbc);

        this.setVisible(true);
    }

    private JPanel addLoadExitButtons() {
        //JPanel to return
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc;

        /**Load Button**/
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.insets = buttonInsets;
        JButton loadButton = new JButton(loadString);
        loadButton.addActionListener(new LoadButtonListener(this));
        buttonsPanel.add(loadButton, gbc);
        /**********************************/

        /**Exit Button**/
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.insets = buttonInsets;
        JButton exitButton = new JButton(exitString);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
        });
        buttonsPanel.add(exitButton, gbc);
        /**********************************/

        return buttonsPanel;
    }

}
