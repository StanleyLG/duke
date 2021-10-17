package Duke.parser;

import Duke.exception.TimeManagementException;
import Duke.exception.UnknownSyntaxException;
import Duke.storage.Storage;
import Duke.task.Deadline;
import Duke.task.Task;
import Duke.task.TaskList;
import Duke.ui.UI;


public class CMD_Deadline extends CMD{

    public CMD_Deadline(String command){
        super(command);
    }

    @Override
    public boolean execute(TaskList taskList, UI ui, Storage storage) {
        boolean success = true;
        try {
            if (!CMD_Enum.DEADLINE.getName().equals(super.keyword)) throw new UnknownSyntaxException(super.keyword);
            String[] dl = super.detail.split("/by", 2);
            Task task = new Deadline(dl[0], dl[1]);
            taskList.addTask(task);
        } catch (IndexOutOfBoundsException e) {
            success = false;
            throw new TimeManagementException();
        }

        if (success) {
            reply(taskList);
        }

        return false;
    }
}
