package plproject3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Christopher Schneider
 * Programming Langauges Project 3
 * Finite State Machine Solver
 */
public class FsmPanelPart2 extends JPanel {

    final private String loadString = "Load Automaton";
    final private String exitString = "Exit";
    final private String solveString = "Solve";
    private static final String stringEntryString = "Word entry";

    //GridBagConstraints padding
    Insets labelInset = new Insets(10,10,0,10);
    Insets textFieldInset = new Insets(0,10,10,10);

    //User entry
    private JTextField wordEntry;

    //Buttons
    private JButton solveButton;


    //Load listener
    LoadButtonListener loadButtonListener = null;
    SolveButtonListener solveButtonListener = null;

    //Constants
    private static final int JTF_STRINGENTRYLEN = 30;


    FsmPanelPart2()
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


        wordEntry = new JTextField(JTF_STRINGENTRYLEN);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = textFieldInset;
        this.add(wordEntry, gbc);


        /** Load & Exit buttons**/
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = textFieldInset;
        this.add(addButtons(), gbc);

        /**Solve button**/
        solveButton = new JButton(solveString);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = textFieldInset;
        solveButtonListener = new SolveButtonListener(this);
        solveButton.addActionListener(solveButtonListener);
        solveButton.setEnabled(false);// Disabled to start
        this.add(solveButton, gbc);

        this.setVisible(true);
    }

    /**
     * Adds load and save buttons
     * @return Panel of load/save buttons
     */
    private JPanel addButtons() {
        //JPanel to return
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        /**Load Button**/

        JButton loadButton = new JButton(loadString);
        loadButtonListener = new LoadButtonListener(this);
        loadButton.addActionListener(loadButtonListener);
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

    /**
     * Enable or disable solve button
     * @param option Enable or disable solve button
     */
    public void enableSolveButton(boolean option){
        solveButtonListener.setFsmData(loadButtonListener.getFsmData());
        solveButtonListener.setWord(this.getWordEntryString());
        solveButton.setEnabled(option);
    }

    /**
     * Returns user entry in text field
     * @return User entry
     */
    public String getWordEntryString(){ return wordEntry.getText();}

    public void setWordEntryString(String entry){wordEntry.setText(entry);}
}
