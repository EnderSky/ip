import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {

    private String filePathString;

    public Storage(String filePath) {
        this.filePathString = filePath;
    }

    public void saveTasks(ArrayList<Task> tasks) {
        // Create file and parent directories if they do not exist
        File file = new File(this.filePathString);
        Path filePath = Paths.get(this.filePathString);

        try {
            if (!Files.exists(filePath)) {
                Path parentDir = filePath.getParent();
                if (parentDir != null) {
                    Files.createDirectories(parentDir);
                }

                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
        
        // Save tasks to file at filePath
        // Per row: <type> | <isDone> | <description> | <additionalInfo>
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.filePathString))) {
            for (Task task : tasks) {
                StringBuilder sb = new StringBuilder();
                sb.append(task.getType()).append(" | ");
                sb.append(task.isDone() ? "1" : "0").append(" | ");
                sb.append(task.getDescription());
                if (task.getAdditionalInfo() != null) {
                    sb.append(" | ").append(task.getAdditionalInfo());
                }

                // Write sb.toString() to file
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while writing to file: " + e.getMessage());
        }
    }
}
