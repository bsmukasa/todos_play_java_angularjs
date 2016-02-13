package models;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TaskTest extends BaseModelTest {
    @Test
    public void createAndRetrieveTask() {
        User user = new User();
        user.email = "steve@fakemail.com";
        user.password = "passw0rd";
        user.firstName = "Steve";
        user.lastName = "Rogers";
        user.save();

        User steve = User.find.where().eq("email", "steve@fakemail.com").findUnique();

        Task task = new Task();
        task.title = "Steve Task1";
        task.owner = steve;
        task.save();

        Task resultTask = Task.find.where().eq("title", "Steve Task1").findUnique();

        assertNotNull(resultTask);
        assertEquals("Steve Task1", resultTask.title);
    }

    @Test
    public void isDoneToggledOnAndOff() {
        Task task1 = Task.find.where().eq("title", "isDone Test 1").findUnique();
        task1.toggleIsDone();
        task1.save();

        Task task2 = Task.find.where().eq("title", "isDone Test 2").findUnique();
        task2.toggleIsDone();
        task2.toggleIsDone();
        task2.save();

        assertTrue(task1.isDone);
        assertFalse(task2.isDone);
    }

    @Test
    public void findAllTasksByUserEmail() {
        List<Task> results = Task.findAllByUserEmail("ben@fakemail.com");

        assertEquals(3, results.size());
        assertEquals("Fix the documentation", results.get(0).title);
    }

    @Test
    public void findIncompleteTasksByUserEmail() {
        List<Task> results = Task.findIncompleteByUserEmail("ben@fakemail.com");

        assertEquals(2, results.size());
        assertEquals("Start angularjs tutorial", results.get(0).title);
    }
}