package Duke.TaskTests;

import Duke.task.Deadline;
import Duke.task.Events;
import Duke.task.TaskList;
import Duke.task.ToDos;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TaskListTest {
    @Test
    public void TaskList(){
        LocalDateTime sample = LocalDateTime.of(2021,12,25,16,0,0);
        ToDos toDos = new ToDos("eat", "eat");
        Deadline deadline = new Deadline("return book", sample);
        Events event = new Events("Finish Homework",sample.toLocalDate(),sample.toLocalTime(),sample.toLocalTime().plusHours(2));
        TaskList tasks = new TaskList();
        tasks.addTask(toDos);
        tasks.addTask(deadline);
        tasks.addTask(event);


        assertEquals(3, tasks.getListSize());
        assertEquals(event,tasks.getLastTask());
        assertEquals(toDos, tasks.getTask(0));
    }
}
