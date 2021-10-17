package Duke.storage;

import Duke.exception.*;
import Duke.task.*;
import Duke.ui.*;

import java.io.*;
import java.util.Scanner;

public class Storage {
    protected boolean isExist;
    protected String fileName;
    protected File file;
    private static ReturnMessages returnMessage = new ReturnMessages();
    Scanner scanner;

    public Storage(String fileName) {
        this.fileName = fileName;
        this.file = new File(System.getProperty("user.dir") + "/src/main/java/Duke.Storage/"+fileName); // create a File for the given file path
    }

    public TaskList load() throws DukeException {
        TaskList taskList = new TaskList();
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // process the line
                    System.out.println("Loading...");
                    taskList.addTask(decodeStr2Task(line));
                }
            } catch (IOException e) {
                // 文件解析失败
                throw new DukeException("file parse fail");
            }
        } else {
            try {
                createFile();
            } catch (IOException e) {
                // 文件创建失败
                throw new DukeException("create file fail");
            }
        }
        return taskList;
    }

    public void createFile() throws IOException {
        try {
            file.createNewFile();
        } catch (IOException ioe) {
            this.isExist = true;
            System.out.println("File exists");
        }
        System.out.println("File created: " + file.getName());
    }

    public void save(TaskList taskList)  {

        FileWriter myWriter;
        try {
            myWriter = new FileWriter(file.getAbsoluteFile(), true);
            FileWriter finalMyWriter = myWriter;
            taskList.getTaskList().forEach((task) -> {
                try {
                    finalMyWriter.write(task.encodeTask() + "\n");
                } catch (IOException e) {
                    //
                    throw new DukeException(e.getMessage());
                }
            });
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new DukeException(e.getMessage());
        }


        System.out.println("Successfully wrote to the file.");
    }

    public Task decodeStr2Task(String str) {
        String[] line = str.split(" \\| ");
        Task task = null;
        switch (line[0]) {
            case "T":
                task = new ToDos(line[2], line[2]);

                break;
            case "D":
                // String[] dl = detail.split("/by", 2);
                task = new Deadline(line[2], line[3]);
                break;
            case "E":
                task = new Events(line[2], line[3]);
                break;
        }
        if (Task.DONE.equals(line[1])) {
            if (task != null) {
                task.setDone();
            }
        }
        return task;
    }
}
