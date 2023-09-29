package fileJob;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class for reading data
 */
public class ReadFromFile {
    /**
     * Static method to read data from file
     * @param fileName relative project path
     * @return String object
     */
    public static String read(String fileName){
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        return sb.toString();
    }
}
