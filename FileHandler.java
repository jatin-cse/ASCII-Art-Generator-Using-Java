import java.io.FileWriter;

public class FileHandler {

    public void saveToFile(String content, String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(content);
            writer.close();
            System.out.println("File saved successfully!");
        } catch (Exception e) {
            System.out.println("Error saving file!");
        }
    }
}