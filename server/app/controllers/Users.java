package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;

public class Users extends Controller {
    public Result getCurrent() {
        return play.mvc.Results.TODO;
    }

    public Result getByID(Long id) {
        User user = User.find.byId(id);
        if (user == null) {
            return notFound();
        }
        return ok(); // TODO Must return the JSON serialized user.
    }

    public Result create() {
        return play.mvc.Results.TODO;
    }

    public Result authenticate() {
        User user = User.authenticate("email", "password"); // TODO Change so email and password are retrieved from request
        if (user == null) {
            return unauthorized();
        }
        return ok(); // TODO Verify what should be the appropriate response
    }

    public Result getByEmail(String email) {
        User user = User.findByEmail(email);
        if (user == null) {
            return badRequest("Email is not registered.");
        } else {
            return ok(); // TODO Must return the JSON serialized user.
        }
    }
}
