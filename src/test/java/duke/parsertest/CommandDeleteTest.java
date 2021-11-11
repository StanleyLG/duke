package duke.parsertest;

import duke.exception.EmptyTaskListException;
import duke.exception.TimeParseException;
import duke.parser.Command;
import duke.parser.CommandParser;
import duke.storage.StorageTaskList;
import duke.task.Deadline;
import duke.task.TaskList;
import duke.task.ToDos;
import duke.ui.UI;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommandDeleteTest {

    private static Command c;
    private static StorageTaskList storageTaskList;
    private static UI ui;
    private static TaskList taskList;
    private static String sampleCmd = "delete 1";
    @BeforeAll
    static void init(){
        ui = new UI();
        storageTaskList = new StorageTaskList("/deleteTest.txt");
        taskList = new TaskList();
        ToDos sampleData = new ToDos("sleep");
        taskList.addTask(sampleData);
    }

    @Test
    void deleteCommand_deleteTask_successAndFalse(){
        c = CommandParser.parse(sampleCmd);
        EmptyTaskListException exception = assertThrows(EmptyTaskListException.class, () -> {
            c.execute(taskList, ui, storageTaskList);
            assertEquals(0, taskList.getListSize());
        });
        String errorMsg = exception.getMessage();
        assertEquals("empty task", errorMsg);
        assertFalse(c.isExit());
    }

    @AfterAll
    static void wrapUp(){
        File cleanUP = new File(System.getProperty("user.dir") + "/deleteTest.txt");
        if(cleanUP.exists()){
            assertTrue(cleanUP.delete());
        }
    }
}
