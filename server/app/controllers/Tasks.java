package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Task;
import models.User;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

public class Tasks extends Controller {

    @Security.Authenticated(Secured.class)
    public Result list() {
        List<Task> taskList = Task.findIncompleteByUserEmail(session().get("userEmail"));

        ObjectNode response = Json.newObject();
        if (taskList == null) {
            response.put("message", "User does not have any todos.");
            return notFound(response);
        } else {
            JsonNode jsonTaskList = Json.toJson(taskList);
            response = Json.newObject();
            response.set("todosList", jsonTaskList);
            return ok(response);
        }
    }

    @Security.Authenticated(Secured.class)
    public Result completeList() {
        List<Task> taskList = Task.findAllByUserEmail(session().get("userEmail"));

        ObjectNode response = Json.newObject();
        if (taskList != null) {
            JsonNode jsonTaskList = Json.toJson(taskList);
            response = Json.newObject();
            response.set("todosList", jsonTaskList);
            return ok(response);
        } else {
            response.put("message", "User does not have any todos.");
            return notFound(response);
        }
    }

    @Security.Authenticated(Secured.class)
    public Result getByID(Long id) {
        Task task = Task.find.byId(id);

        ObjectNode response = Json.newObject();
        if (task == null) {
            response.put("message", "Todo does not exist.");
            return notFound(response);
        } else {
            JsonNode jsonTaskList = Json.toJson(task);
            response = Json.newObject();
            response.set("todo", jsonTaskList);
            return ok(response);
        }
    }

    // TODO Think about validation for add
    @Security.Authenticated(Secured.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result add() {
        JsonNode jsonRequest = request().body().asJson();

        // TODO Make sure user comes from the session.
        User user = new User();
        user.email = "sean@testemail.com";
        user.password = "secret";
        user.firstName = "Sean";
        user.lastName = "Carter";
        user.save();

        Task task = new Task();
        task.title = jsonRequest.findPath("title").textValue();
        task.owner = user; // TODO Make sure this changes to session user.
        task.save();

        ObjectNode response = Json.newObject();
        response.put("message", "Todo created.");

        return ok(response);
    }

    // TODO Think about validation for update
    @Security.Authenticated(Secured.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result update(Long id) {
        Task task = Task.find.byId(id);
        JsonNode jsonRequest = request().body().asJson();

        ObjectNode response = Json.newObject();
        if (task == null) {
            response.put("message", "Todo does not exist.");
            return notFound(response);
        } else {
            task.title = jsonRequest.findPath("title").textValue();
            task.save();

            response.put("message", "Todo updated.");
            return ok(response);
        }
    }

    @Security.Authenticated(Secured.class)
    public Result delete(Long id) {
        Task task = Task.find.byId(id);

        ObjectNode response = Json.newObject();
        if (task == null) {
            response.put("message", "Todo does not exist.");
            return notFound(response);
        } else {
            task.delete();

            response.put("message", "Todo deleted.");
            return ok(response);
        }
    }

    @Security.Authenticated(Secured.class)
    public Result toggleDone(Long id) {
        Task task = Task.find.byId(id);

        ObjectNode response = Json.newObject();
        if (task == null) {
            response.put("message", "Todo does not exist.");
            return notFound(response);
        } else {
            task.toggleIsDone();
            task.save();

            response.put("message", "Todo isDone attribute toggled.");
            return ok(response);
        }
    }
}
