package models;

import com.avaje.ebean.Ebean;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import play.libs.Yaml;
import play.test.FakeApplication;
import play.test.Helpers;

import java.io.IOException;
import java.util.List;

public class BaseModelTest {
    public static FakeApplication app;

    @BeforeClass
    public static void startApp() throws IOException {
        app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
        Helpers.start(app);

        Ebean.save((List<?>) Yaml.load("test-data.yml"));

//        User user1 = new User();
//        user1.email = "ben@fakemail.com";
//        user1.password = "secret";
//        user1.firstName = "Ben";
//        user1.lastName = "Mukasa";
//        user1.save();
//
//        User user2 = new User();
//        user2.email = "alicia@fakemail.com";
//        user2.password = "secret";
//        user2.firstName = "Alicia";
//        user2.lastName = "Mukasa";
//        user2.save();
//
//        User user3 = new User();
//        user3.email = "jennifer@fakemail.com";
//        user3.password = "secret";
//        user3.firstName = "Jennifer";
//        user3.lastName = "Mukasa";
//        user3.save();
//
//        User user4 = new User();
//        user4.email = "charles@fakemail.com";
//        user4.password = "secret";
//        user4.firstName = "Charles";
//        user4.lastName = "Mukasa";
//        user4.save();
    }

    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }
}
