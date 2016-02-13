package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.libs.Json;
import play.mvc.BodyParser;
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
        } else {
            JsonNode jsonUser = Json.toJson(user);
            ObjectNode response = Json.newObject();
            response.set("user", jsonUser);
            return ok(response);
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result create() {
        JsonNode jsonUser = request().body().asJson();
        User user = new User();
        user.email = jsonUser.findPath("email").textValue();
        user.password = jsonUser.findPath("password").textValue();
        user.firstName = jsonUser.findPath("firstName").textValue();
        user.lastName = jsonUser.findPath("lastName").textValue();
        user.save();

        ObjectNode response = Json.newObject();
        response.put("message", "User created.");

        return ok(response);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result authenticate() {
        JsonNode jsonUser = request().body().asJson();
        String email = jsonUser.findPath("email").textValue();
        String password = jsonUser.findPath("password").textValue();
        User user = User.authenticate(email, password);

        ObjectNode response = Json.newObject();
        if (user == null) {
            response.put("message", "Email and Password combination does not exist.");
            return unauthorized(response);
        } else {
            response.put("message", "User authenticated.");
            return ok(response);
        }

    }

//    @BodyParser.Of(BodyParser.Json.class)
//    public Result getByEmail() {
//        JsonNode jsonUser = request().body().asJson();
//        String email = jsonUser.findPath("email").textValue();
//        User user = User.findByEmail(email);
//        if (user == null) {
//            return badRequest("Email is not registered.");
//        } else {
//            return ok(); // TODO Must return the JSON serialized user.
//        }
//    }
}
