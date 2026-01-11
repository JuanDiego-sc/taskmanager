package com.example.taskmanager;

import com.example.taskmanager.presentation.TaskPresenter;
import com.example.taskmanager.services.TaskService;
import com.example.taskmanager.models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TaskManager.
 */
class TaskManagerTest {

  private TaskManager taskManager;
  private MockTaskService mockService;
  private MockTaskPresenter mockPresenter;

  @BeforeEach
  void setUp() {
    mockService = new MockTaskService();
    mockPresenter = new MockTaskPresenter();
    taskManager = new TaskManager(mockService, mockPresenter);
  }

  @Test
  void testConstructorWithNullService() {
    assertThrows(IllegalArgumentException.class, () -> {
      new TaskManager(null, mockPresenter);
    });
  }

  @Test
  void testConstructorWithNullPresenter() {
    assertThrows(IllegalArgumentException.class, () -> {
      new TaskManager(mockService, null);
    });
  }

  @Test
  void testAddTaskWithNameOnly() {
    taskManager.addTask("Test Task");

    assertEquals(1, mockService.tasks.size());
    assertEquals("Test Task", mockService.tasks.get(0).getName());
    assertTrue(mockPresenter.lastMessage.contains("Task added"));
  }

  @Test
  void testAddTaskWithNameAndDescription() {
    taskManager.addTask("Test Task", "Test Description");

    assertEquals(1, mockService.tasks.size());
    assertEquals("Test Task", mockService.tasks.get(0).getName());
    assertEquals("Test Description", mockService.tasks.get(0).getDescription());
    assertTrue(mockPresenter.lastMessage.contains("Task added"));
  }

  @Test
  void testAddTaskWithInvalidName() {
    mockService.throwExceptionOnCreate = true;

    taskManager.addTask("", "Description");

    assertTrue(mockPresenter.lastError.contains("Error") || mockPresenter.lastError.length() > 0);
  }

  @Test
  void testListTasks() {
    mockService.tasks.add(new Task("Task 1", "Description 1"));
    mockService.tasks.add(new Task("Task 2", "Description 2"));

    taskManager.listTasks();

    assertEquals(2, mockPresenter.displayedTasks.size());
  }

  @Test
  void testRemoveTask() {
    Task task = new Task("Test Task", "Description");
    mockService.tasks.add(task);

    taskManager.removeTask(1);

    assertEquals(0, mockService.tasks.size());
    assertTrue(mockPresenter.lastMessage.contains("Task removed"));
  }

  @Test
  void testRemoveTaskNotFound() {
    taskManager.removeTask(99);

    assertTrue(mockPresenter.lastError.contains("not found"));
  }

  @Test
  void testCompleteTask() {
    Task task = new Task("Test Task", "Description");
    mockService.tasks.add(task);

    taskManager.completeTask(1);

    assertTrue(task.isCompleted());
    assertTrue(mockPresenter.lastMessage.contains("completed"));
  }

  @Test
  void testCompleteTaskNotFound() {
    taskManager.completeTask(99);

    assertTrue(mockPresenter.lastError.contains("not found"));
  }

  @Test
  void testEditTask() {
    Task task = new Task("Original Task", "Original Description");
    mockService.tasks.add(task);

    taskManager.editTask(1, "Updated Task", "Updated Description");

    assertTrue(mockPresenter.lastMessage.contains("updated"));
  }

  @Test
  void testEditTaskNotFound() {
    taskManager.editTask(99, "New Name", "New Description");

    assertTrue(mockPresenter.lastError.contains("not found"));
  }

  @Test
  void testEditTaskWithInvalidName() {
    Task task = new Task("Original Task", "Description");
    mockService.tasks.add(task);
    mockService.failOnUpdate = true;

    taskManager.editTask(1, "", "Description");

    assertTrue(mockPresenter.lastError.contains("Failed") || mockPresenter.lastError.contains("empty"));
  }

  @Test
  void testGetTaskCount() {
    mockService.tasks.add(new Task("Task 1", "Description"));
    mockService.tasks.add(new Task("Task 2", "Description"));

    int count = taskManager.getTaskCount();

    assertEquals(2, count);
  }

  // Mock implementations for testing
  private static class MockTaskService implements TaskService {
    List<Task> tasks = new ArrayList<>();
    boolean throwExceptionOnCreate = false;
    boolean failOnUpdate = false;

    @Override
    public Task createTask(String name, String description) {
      if (throwExceptionOnCreate || name == null || name.trim().isEmpty()) {
        throw new IllegalArgumentException("Invalid task name");
      }
      Task task = new Task(name, description);
      tasks.add(task);
      return task;
    }

    @Override
    public Task createTask(String name) {
      return createTask(name, "");
    }

    @Override
    public List<Task> getAllTasks() {
      return new ArrayList<>(tasks);
    }

    @Override
    public Optional<Task> getTaskById(String id) {
      return tasks.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<Task> getTaskByIndex(int index) {
      if (index <= 0 || index > tasks.size()) {
        return Optional.empty();
      }
      return Optional.of(tasks.get(index - 1));
    }

    @Override
    public boolean removeTask(String id) {
      return tasks.removeIf(t -> t.getId().equals(id));
    }

    @Override
    public boolean removeTaskByIndex(int index) {
      if (index <= 0 || index > tasks.size()) {
        return false;
      }
      tasks.remove(index - 1);
      return true;
    }

    @Override
    public boolean completeTask(String id) {
      Optional<Task> task = getTaskById(id);
      if (task.isPresent()) {
        task.get().markAsCompleted();
        return true;
      }
      return false;
    }

    @Override
    public boolean updateTask(int index, String newName, String newDescription) {
      if (failOnUpdate) {
        return false;
      }
      Optional<Task> task = getTaskByIndex(index);
      if (task.isPresent()) {
        try {
          task.get().setName(newName);
          task.get().setDescription(newDescription);
          return true;
        } catch (IllegalArgumentException e) {
          return false;
        }
      }
      return false;
    }

    @Override
    public int getTaskCount() {
      return tasks.size();
    }
  }

  private static class MockTaskPresenter implements TaskPresenter {
    String lastMessage = "";
    String lastError = "";
    String lastInfo = "";
    List<Task> displayedTasks = new ArrayList<>();

    @Override
    public void displayTask(Task task, int index) {
      // Not used in these tests
    }

    @Override
    public void displayTasks(List<Task> tasks) {
      displayedTasks = new ArrayList<>(tasks);
    }

    @Override
    public void displaySuccess(String message) {
      lastMessage = message;
    }

    @Override
    public void displayError(String message) {
      lastError = message;
    }

    @Override
    public void displayInfo(String message) {
      lastInfo = message;
    }
  }
}
