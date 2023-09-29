package fileJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for writing data
 */
public class WriteToFile {
    /**
     * Static method to write data to file, Type is "append"
     * @param text - any text
     * @param filePath - relative project path
     */
    public static void write(String text, String filePath) {
        try {
            File file = new File(filePath);
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text);
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("Log updated");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error when writing text to a file: " + e.getMessage());
        }
    }
}
