package glados.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import glados.task.Deadline;
import glados.task.Event;
import glados.task.Task;
import glados.task.Todo;

/***
 * Handles loading and saving of tasks to and from the file system.
 */
public class Storage {

    private String filePathString;

    /***
     * Constructor for Storage class.
     *
     * @param filePath The file path where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePathString = filePath;
    }

    /***
     * Loads tasks from the file at the specified file path.
     *
     * @return An ArrayList of Task objects loaded from the file.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Path filePath = Paths.get(this.filePathString);

        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
            // Check if file exists
            if (Files.exists(filePath)) {
                // Read file line by line and create Task objects
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] parts = line.split(" \\| ");
                    char type = parts[0].charAt(0);
                    boolean isDone = parts[1].equals("1");
                    String description = parts[2];
                    Task task = null;

                    switch (type) {
                        case 'T':
                            assert parts.length == 3 : "Todo task should have exactly 3 parts";
                            task = new Todo(description);
                            break;
                        case 'D':
                            assert parts.length == 4 : "Deadline task should have exactly 4 parts";
                            String by = parts[3];

                            // Parse by to LocalDateTime with format: DD MMM YYYY HH:mm am/pm (12-hour
                            // format)
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a");
                            LocalDateTime byDateTime = LocalDateTime.parse(by, formatter);

                            task = new Deadline(description, byDateTime);
                            break;
                        case 'E':
                            assert parts.length == 5 : "Event task should have exactly 5 parts";
                            String from = parts[3];
                            String to = parts[4];
                            task = new Event(description, from, to);
                            break;
                        default:
                            System.out.println("Unknown task type: " + type);
                    }

                    if (task != null) {
                        if (isDone) {
                            task.markAsDone();
                        }
                        tasks.add(task);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        return tasks;
    }

    /***
     * Saves the given list of tasks to the file at the specified file path.
     *
     * @param tasks The ArrayList of Task objects to be saved.
     */
    public void saveTasks(ArrayList<Task> tasks) {
        // Create file and parent directories if they do not exist
        Path filePath = Paths.get(this.filePathString);

        try {
            // Check if file & parent folders exist
            if (!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }

            // Create file if it doesn't exist
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }

        // Save tasks to file at filePath
        // Per row: <type> | <isDone> | <description> | <additionalInfo>
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath)) {
            for (Task task : tasks) {
                StringBuilder sb = new StringBuilder();
                sb.append(task.getType()).append(" | ");
                sb.append(task.isDone() ? "1" : "0").append(" | ");
                sb.append(task.getDescription());
                if (task.getAdditionalInfo() != null) {
                    sb.append(" | ").append(task.getAdditionalInfo());
                }

                // Write sb.toString() to file
                bufferedWriter.write(sb.toString());
                bufferedWriter.newLine();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while writing to file: " + e.getMessage());
        }
    }
}
