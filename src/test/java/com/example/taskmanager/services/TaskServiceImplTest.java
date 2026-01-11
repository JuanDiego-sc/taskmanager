package com.example.taskmanager.services;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.repositories.InMemoryTaskRepository;
import com.example.taskmanager.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TaskServiceImpl.
 */
class TaskServiceImplTest {

  private TaskService taskService;
  private TaskRepository taskRepository;

  @BeforeEach
  void setUp() {
    taskRepository = new InMemoryTaskRepository();
    taskService = new TaskServiceImpl(taskRepository);
  }

  @Test
  void testConstructorWithNullRepository() {
    assertThrows(IllegalArgumentException.class, () -> {
      new TaskServiceImpl(null);
    });
  }

  @Test
  void testCreateTaskWithNameAndDescription() {
    Task task = taskService.createTask("Test Task", "Test Description");

    assertNotNull(task);
    assertEquals("Test Task", task.getName());
    assertEquals("Test Description", task.getDescription());
    assertFalse(task.isCompleted());
  }

  @Test
  void testCreateTaskWithNameOnly() {
    Task task = taskService.createTask("Test Task");

    assertNotNull(task);
    assertEquals("Test Task", task.getName());
    assertEquals("", task.getDescription());
  }

  @Test
  void testCreateTaskWithInvalidName() {
    assertThrows(IllegalArgumentException.class, () -> {
      taskService.createTask("", "Description");
    });
  }

  @Test
  void testGetAllTasks() {
    taskService.createTask("Task 1", "Description 1");
    taskService.createTask("Task 2", "Description 2");

    List<Task> tasks = taskService.getAllTasks();

    assertEquals(2, tasks.size());
  }

  @Test
  void testGetAllTasksEmpty() {
    List<Task> tasks = taskService.getAllTasks();

    assertTrue(tasks.isEmpty());
  }

  @Test
  void testGetTaskById() {
    Task created = taskService.createTask("Test Task", "Description");

    Optional<Task> found = taskService.getTaskById(created.getId());

    assertTrue(found.isPresent());
    assertEquals(created.getId(), found.get().getId());
  }

  @Test
  void testGetTaskByIdNotFound() {
    Optional<Task> found = taskService.getTaskById("non-existent-id");

    assertFalse(found.isPresent());
  }

  @Test
  void testGetTaskByIndex() {
    taskService.createTask("Task 1", "Description 1");
    taskService.createTask("Task 2", "Description 2");

    Optional<Task> found = taskService.getTaskByIndex(1);

    assertTrue(found.isPresent());
    assertEquals("Task 1", found.get().getName());
  }

  @Test
  void testGetTaskByIndexOutOfBounds() {
    Optional<Task> found = taskService.getTaskByIndex(10);

    assertFalse(found.isPresent());
  }

  @Test
  void testGetTaskByIndexZero() {
    Optional<Task> found = taskService.getTaskByIndex(0);

    assertFalse(found.isPresent());
  }

  @Test
  void testRemoveTask() {
    Task task = taskService.createTask("Test Task", "Description");

    boolean removed = taskService.removeTask(task.getId());

    assertTrue(removed);
    assertEquals(0, taskService.getTaskCount());
  }

  @Test
  void testRemoveTaskNotFound() {
    boolean removed = taskService.removeTask("non-existent-id");

    assertFalse(removed);
  }

  @Test
  void testRemoveTaskByIndex() {
    taskService.createTask("Task 1", "Description 1");
    taskService.createTask("Task 2", "Description 2");

    boolean removed = taskService.removeTaskByIndex(1);

    assertTrue(removed);
    assertEquals(1, taskService.getTaskCount());
  }

  @Test
  void testRemoveTaskByIndexOutOfBounds() {
    boolean removed = taskService.removeTaskByIndex(10);

    assertFalse(removed);
  }

  @Test
  void testCompleteTask() {
    Task task = taskService.createTask("Test Task", "Description");

    boolean completed = taskService.completeTask(task.getId());

    assertTrue(completed);
    Optional<Task> found = taskService.getTaskById(task.getId());
    assertTrue(found.isPresent());
    assertTrue(found.get().isCompleted());
  }

  @Test
  void testCompleteTaskNotFound() {
    boolean completed = taskService.completeTask("non-existent-id");

    assertFalse(completed);
  }

  @Test
  void testUpdateTask() {
    taskService.createTask("Original Task", "Original Description");

    boolean updated = taskService.updateTask(1, "Updated Task", "Updated Description");

    assertTrue(updated);
    Optional<Task> found = taskService.getTaskByIndex(1);
    assertTrue(found.isPresent());
    assertEquals("Updated Task", found.get().getName());
    assertEquals("Updated Description", found.get().getDescription());
  }

  @Test
  void testUpdateTaskNotFound() {
    boolean updated = taskService.updateTask(10, "New Name", "New Description");

    assertFalse(updated);
  }

  @Test
  void testUpdateTaskWithEmptyName() {
    taskService.createTask("Original Task", "Description");

    boolean updated = taskService.updateTask(1, "", "Description");

    assertFalse(updated);
  }

  @Test
  void testUpdateTaskWithNullName() {
    taskService.createTask("Original Task", "Description");

    boolean updated = taskService.updateTask(1, null, "Description");

    assertFalse(updated);
  }

  @Test
  void testGetTaskCount() {
    assertEquals(0, taskService.getTaskCount());

    taskService.createTask("Task 1", "Description");
    assertEquals(1, taskService.getTaskCount());

    taskService.createTask("Task 2", "Description");
    assertEquals(2, taskService.getTaskCount());
  }
}
