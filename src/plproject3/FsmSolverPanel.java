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
public class FsmSolverPanel extends JPanel {

    private static final String loadString = "Load FSA";
    private static final String exitString = "Exit";
    private static final String solveString = "Load String";
    private static final String stringEntryString = "Word entry";


    //GridBagConstraints padding
    private static final Insets labelInset = new Insets(10,10,0,10);
    private static final Insets textFieldInset = new Insets(0,10,10,10);

    //Buttons
    private JButton solveStringButton;
    private JButton saveLispButton;

    //Text field
    private JTextField wordEntry;

    //Load listener
    private LoadButtonListener loadButtonListener = null;
    private LoadStringButtonListener loadStringButtonListener = null;
    private GenerateLispButtonListener lispButtonListener = null;

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

        /**Load string**/
        wordEntry = new JTextField(JTF_STRINGENTRYLEN);
        wordEntry.setEditable(false);
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
        solveStringButton = new JButton(solveString);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = textFieldInset;
        loadStringButtonListener = new LoadStringButtonListener(this);
        solveStringButton.addActionListener(loadStringButtonListener);
        solveStringButton.setEnabled(false);// Disabled to start
        this.add(solveStringButton, gbc);

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
    public void enableButtons(boolean option){

        /**Solve button**/
        loadStringButtonListener.setFsmData(loadButtonListener.getFsmData());
        loadStringButtonListener.setWord(this.getWordEntryString());
        solveStringButton.setEnabled(option);


        /**Lisp button**/

    }

    /**
     * Returns user entry in text field
     * @return User entry
     */
    public String getWordEntryString(){ return wordEntry.getText();}

    public void setWordEntryString(String entry){wordEntry.setText(entry);}
}
