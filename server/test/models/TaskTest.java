package models;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TaskTest extends BaseModelTest {
//    @BeforeClass
//    public static void startClass() {
//        User ben = User.find.where().eq("email", "ben@fakemail.com").findUnique();
//        User alicia = User.find.where().eq("email", "alicia@fakemail.com").findUnique();
//        User jennifer = User.find.where().eq("email", "jennifer@fakemail.com").findUnique();
//
//        Task task1 = new Task();
//        task1.title = "Test Task1";
//        task1.owner = ben;
//        task1.toggleIsDone();
//        task1.save();
//
//        Task task2 = new Task();
//        task2.title = "Test Task2";
//        task2.owner = alicia;
//        task2.save();
//
//        Task task3 = new Task();
//        task3.title = "isDone Test 1";
//        task3.owner = jennifer;
//        task3.save();
//
//        Task task4 = new Task();
//        task4.title = "Test Task4";
//        task4.owner = ben;
//        task4.save();
//
//        Task task5 = new Task();
//        task5.title = "Test Task5";
//        task5.owner = ben;
//        task5.save();
//
//        Task task6 = new Task();
//        task6.title = "isDone Test 2";
//        task6.owner = alicia;
//        task6.save();
//    }

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