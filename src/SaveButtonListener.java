import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Chris on 3/3/2015.
 */
public class SaveButtonListener implements ActionListener{

    JPanel listenPanel;
    String saveString = "null";

    SaveButtonListener(JPanel jPanel)
    {
        listenPanel = jPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

    }


}
