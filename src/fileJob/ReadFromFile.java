package fileJob;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFromFile {
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
            System.err.println("Произошла ошибка при чтении файла: " + e.getMessage());
        }
        return sb.toString();
    }
}
