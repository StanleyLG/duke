package Duke.parser;

import Duke.exception.EmptyTaskListException;
import Duke.storage.Storage;
import Duke.task.TaskList;
import Duke.ui.UI;

public class CMD_List extends CMD {
    public CMD_List(String command) {
        super(command);
    }

    @Override
    public boolean execute(TaskList taskList, UI ui, Storage storage) {
        taskList.printTaskList();
        return false;
    }
}