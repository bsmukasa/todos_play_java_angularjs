package models;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserTest extends BaseModelTest {
    @Test
    public void createAndRetrieveUser() {
        User user = new User();
        user.email = "jane@fakemail.com";
        user.password = "passw0rd";
        user.firstName = "Jane";
        user.lastName = "Doe";
        user.save();

        User jane = User.find.where().eq("email", "jane@fakemail.com").findUnique();

        assertNotNull(jane);
        assertEquals("Jane", jane.firstName);
    }

    @Test
    public void findByUserEmail() {
        User user = User.findByEmail("ben@fakemail.com");

        assertNotNull(user);
        assertEquals("Ben", user.firstName);
    }

    @Test
    public void tryAuthenticateUser() {
        assertNotNull(User.authenticate("ben@fakemail.com", "secret"));
        assertNull(User.authenticate("bob@gmail.com", "badpassword"));
        assertNull(User.authenticate("tom@gmail.com", "secret"));
    }
}
