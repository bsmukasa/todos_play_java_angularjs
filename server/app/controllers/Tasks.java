package controllers;

import models.Task;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class Tasks extends Controller {

    public Result list() {
        List<Task> taskList = Task.find.all();
        return ok(); // TODO return taskList serialized in JSON
    }

    public Result getByID(Long id) {
        Task task = Task.find.byId(id);
        if (task == null) {
            return notFound();
        }
        return ok(); // TODO return task serialized in JSON
    }

    public Result update(Long id) {
        Task task = Task.find.byId(id);
        if (task == null) {
            return notFound();
        }

        // TODO Should validate request data and then update the task.

        return ok(); // TODO return task serialized in JSON
    }

    public Result delete(Long id) {
        Task task = Task.find.byId(id);
        if (task == null) {
            return notFound();
        }

        task.delete();

        return ok("Todo deleted."); // TODO return task serialized in JSON
    }

    public Result add() {
        return play.mvc.Results.TODO;
    }
}
