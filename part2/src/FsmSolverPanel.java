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

    //GridBagConstraints padding
    Insets labelInset = new Insets(10,10,0,10);
    Insets textFieldInset = new Insets(0,10,10,10);


    FsmSolverPanel()
    {
        super(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(addLoadExitButtons(), gbc);


    }

    private JPanel addLoadExitButtons() {
        //JPanel to return
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3, 10, 0));

        /**Save Button**/
        JButton loadButton = new JButton(loadString);
        //loadButton.addActionListener(new SaveButtonListener(this));
        buttonsPanel.add(loadButton);
        /**********************************/

        /**Exit Button**/
        final Component thisPanel = this;
        JButton exitButton = new JButton(exitString);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(thisPanel, exitConfirmMessage, exitString, JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        buttonsPanel.add(exitButton);
        /**********************************/

        return buttonsPanel;
    }

}
