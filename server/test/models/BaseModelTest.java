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
    }

    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }
}
