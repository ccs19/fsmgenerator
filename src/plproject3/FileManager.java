package plproject3;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

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
                    previousDir = file.getParent() + "\\"; //Save directory
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

    public static void saveFile(JPanel panel, String data, String extension, String description){
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(description, extension);
        jFileChooser.setFileFilter(filter);
        jFileChooser.setCurrentDirectory(new File(previousDir)); //Set project or previous directory
        int returnVal = jFileChooser.showSaveDialog(panel);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = jFileChooser.getSelectedFile();

            String fileName[] = file.getName().split("/."); //Check for extension

            if(fileName.length == 1){
                file = new File(previousDir, fileName[0]+extension);
            }

            writeFile(panel, data, file);
            try {
                previousDir = file.getParent() + "\\";
            }catch(Exception e){
                previousDir = ".";
            }
        }
    }

    public static void saveFile(JPanel panel, String data, String fileName){
        File file = new File(fileName);
        writeFile(panel, data, file);
    }

    private static void writeFile(JPanel panel, String data, File file){
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write(data);
            fileWriter.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(panel, "Failed to save. Cannot write to disk.", "Fatal Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
