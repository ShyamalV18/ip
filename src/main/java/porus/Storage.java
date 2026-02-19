package porus;

import porus.task.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Handles loading and saving tasks to disk.
 * <p>
 * Uses a relative file path to ensure OS independence.
 * Automatically creates the data folder and file if missing.
 */
public class Storage {

    private final String filePath;

    /**
     * Creates a Storage object with a given file path.
     *
     * @param filePath relative path to data file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from file.
     *
     * @return list of tasks loaded from disk
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        try {
            createFileIfMissing(file);

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    Task task = parseLine(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    // Stretch goal: corrupted line handling
                    // Skip corrupted lines instead of crashing
                }
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error loading data file.");
        }

        return tasks;
    }

    /**
     * Saves tasks to file.
     *
     * @param tasks list of tasks to save
     */
    public void save(ArrayList<Task> tasks) {
        File file = new File(filePath);

        try {
            createFileIfMissing(file);

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (Task task : tasks) {
                writer.write(formatTask(task));
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving data file.");
        }
    }

    /**
     * Creates file and parent directories if they do not exist.
     */
    private void createFileIfMissing(File file) throws IOException {
        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }
    }

    /**
     * Converts a Task into storage format.
     */
    private String formatTask(Task task) {
        String status = task.getDone() ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + status + " | " + task.getDescription();
        }

        if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return "D | " + status + " | "
                    + d.getDescription() + " | " + d.getBy();
        }

        if (task instanceof Event) {
            Event e = (Event) task;
            return "E | " + status + " | "
                    + e.getDescription() + " | "
                    + e.getFrom() + " | " + e.getTo();
        }

        return "";
    }

    /**
     * Parses a line from file into a Task.
     */
    private Task parseLine(String line) {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;

        switch (type) {
        case "T":
            task = new Todo(description);
            break;

        case "D":
            if (parts.length < 4) {
                return null;
            }
            task = new Deadline(description, parts[3]);
            break;

        case "E":
            if (parts.length < 5) {
                return null;
            }
            task = new Event(description, parts[3], parts[4]);
            break;

        default:
            return null;
        }

        task.setDone(isDone);
        return task;
    }
}
