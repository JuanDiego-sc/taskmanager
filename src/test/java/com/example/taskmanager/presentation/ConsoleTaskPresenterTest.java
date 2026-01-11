package com.example.taskmanager.presentation;

import com.example.taskmanager.models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ConsoleTaskPresenter.
 */
class ConsoleTaskPresenterTest {

  private ByteArrayOutputStream outputStream;
  private ConsoleTaskPresenter presenter;

  @BeforeEach
  void setUp() {
    outputStream = new ByteArrayOutputStream();
    presenter = new ConsoleTaskPresenter(new PrintStream(outputStream));
  }

  @Test
  void testConstructorWithPrintStream() {
    PrintStream customStream = new PrintStream(new ByteArrayOutputStream());
    ConsoleTaskPresenter customPresenter = new ConsoleTaskPresenter(customStream);

    assertNotNull(customPresenter);
  }

  @Test
  void testConstructorWithNullPrintStream() {
    ConsoleTaskPresenter defaultPresenter = new ConsoleTaskPresenter(null);

    assertNotNull(defaultPresenter);
  }

  @Test
  void testDefaultConstructor() {
    ConsoleTaskPresenter defaultPresenter = new ConsoleTaskPresenter();

    assertNotNull(defaultPresenter);
  }

  @Test
  void testDisplayTask() {
    Task task = new Task("Test Task", "Test Description");

    presenter.displayTask(task, 1);

    String output = outputStream.toString();
    assertTrue(output.contains("1"));
    assertTrue(output.contains("Test Task"));
    assertTrue(output.contains("Test Description"));
  }

  @Test
  void testDisplayTaskWithNoDescription() {
    Task task = new Task("Test Task", "");

    presenter.displayTask(task, 1);

    String output = outputStream.toString();
    assertTrue(output.contains("No description"));
  }

  @Test
  void testDisplayCompletedTask() {
    Task task = new Task("Test Task", "Description");
    task.markAsCompleted();

    presenter.displayTask(task, 1);

    String output = outputStream.toString();
    assertTrue(output.contains("✓"));
  }

  @Test
  void testDisplayNullTask() {
    presenter.displayTask(null, 1);

    String output = outputStream.toString();
    assertTrue(output.isEmpty());
  }

  @Test
  void testDisplayTasks() {
    List<Task> tasks = new ArrayList<>();
    tasks.add(new Task("Task 1", "Description 1"));
    tasks.add(new Task("Task 2", "Description 2"));

    presenter.displayTasks(tasks);

    String output = outputStream.toString();
    assertTrue(output.contains("Task List"));
    assertTrue(output.contains("2 tasks"));
    assertTrue(output.contains("Task 1"));
    assertTrue(output.contains("Task 2"));
  }

  @Test
  void testDisplayEmptyTaskList() {
    List<Task> tasks = new ArrayList<>();

    presenter.displayTasks(tasks);

    String output = outputStream.toString();
    assertTrue(output.contains("No tasks found"));
  }

  @Test
  void testDisplayNullTaskList() {
    presenter.displayTasks(null);

    String output = outputStream.toString();
    assertTrue(output.contains("No tasks found"));
  }

  @Test
  void testDisplaySuccess() {
    presenter.displaySuccess("Operation successful");

    String output = outputStream.toString();
    assertTrue(output.contains("✅"));
    assertTrue(output.contains("Operation successful"));
  }

  @Test
  void testDisplayError() {
    presenter.displayError("An error occurred");

    String output = outputStream.toString();
    assertTrue(output.contains("❌"));
    assertTrue(output.contains("Error"));
    assertTrue(output.contains("An error occurred"));
  }

  @Test
  void testDisplayInfo() {
    presenter.displayInfo("Information message");

    String output = outputStream.toString();
    assertTrue(output.contains("ℹ️"));
    assertTrue(output.contains("Information message"));
  }

  @Test
  void testDisplayTasksWithMixedCompletionStatus() {
    List<Task> tasks = new ArrayList<>();
    Task task1 = new Task("Completed Task", "Description");
    task1.markAsCompleted();
    tasks.add(task1);
    tasks.add(new Task("Pending Task", "Description"));

    presenter.displayTasks(tasks);

    String output = outputStream.toString();
    assertTrue(output.contains("Completed Task"));
    assertTrue(output.contains("Pending Task"));
    assertTrue(output.contains("✓"));
  }
}
