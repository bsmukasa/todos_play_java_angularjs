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
        String email = session().get("userEmail");

        ObjectNode response = Json.newObject();
        if (email == null) {
            response.put("message", "No user is logged in.");
            return unauthorized();
        } else {
            User user = User.findByEmail(email);
            JsonNode jsonUser = Json.toJson(user);
            response.set("user", jsonUser);
            return ok(response);
        }
    }

    public Result getByID(Long id) {
        User user = User.find.byId(id);

        ObjectNode response = Json.newObject();
        if (user == null) {
            response.put("message", "User id does not exist.");
            return notFound(response);
        } else {
            JsonNode jsonUser = Json.toJson(user);
            response.set("user", jsonUser);
            return ok(response);
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result create() {
        JsonNode jsonRequest = request().body().asJson();
        User user = new User();
        user.email = jsonRequest.findPath("email").textValue();
        user.password = jsonRequest.findPath("password").textValue();
        user.firstName = jsonRequest.findPath("firstName").textValue();
        user.lastName = jsonRequest.findPath("lastName").textValue();
        user.save();

        session().clear();
        session("userEmail", user.email);

        ObjectNode response = Json.newObject();
        response.put("message", "User created.");

        return ok(response);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result login() {
        JsonNode jsonRequest = request().body().asJson();
        String email = jsonRequest.findPath("email").textValue();
        String password = jsonRequest.findPath("password").textValue();
        User user = User.authenticate(email, password);

        ObjectNode response = Json.newObject();
        if (user == null) {
            response.put("message", "Incorrect email or password.");
            return badRequest(response);
        } else {
            session().clear();
            session("UserEmail", user.email);
            response.put("message", "User authenticated.");
            return ok(response);
        }

    }

    public Result logout() {
        session().clear();

        ObjectNode response = Json.newObject();
        response.put("message", "Logged Out.");
        return ok(response);
    }

    public Result isAuthenticated() {
        if (session().get("userEmail") == null) {
            return unauthorized();
        } else {
            ObjectNode response = Json.newObject();
            response.put("message", "User already logged in.");
            response.put("user", session().get("userEmail"));
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
