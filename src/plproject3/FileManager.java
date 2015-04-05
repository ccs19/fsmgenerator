package plproject3;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by chris_000 on 4/5/2015.
 */
public class FileManager{

    private static String previousDir = ".";





    public static BufferedReader openFile(JPanel panel){
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(previousDir));
        BufferedReader bufferedReader = null;
        int returnVal = jFileChooser.showOpenDialog(panel);

        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = jFileChooser.getSelectedFile();
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(panel, "Failed to load file. Cannot load from disk.", "Error", JOptionPane.ERROR_MESSAGE);
            }finally{
                try {
                    previousDir = file.getCanonicalPath(); //Save directory
                    return bufferedReader;
                }catch(Exception e){
                    previousDir = "."; //If this fails for some reason, default to application dir
                    return bufferedReader;
                }
            }
        }
        else
            return bufferedReader;
    }



    public static String getPreviousDir(){
        return previousDir;
    }

    public static String readNextLine(BufferedReader bufferedReader) throws IOException, InvalidFsmFormatException{
        String s = bufferedReader.readLine();
        if(s == null)
            throw new InvalidFsmFormatException();
        return s;
    }

}
