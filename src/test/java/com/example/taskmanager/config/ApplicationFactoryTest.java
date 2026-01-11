package com.example.taskmanager.config;

import com.example.taskmanager.TaskManager;
import com.example.taskmanager.presentation.ConsoleTaskPresenter;
import com.example.taskmanager.presentation.TaskPresenter;
import com.example.taskmanager.repositories.InMemoryTaskRepository;
import com.example.taskmanager.repositories.TaskRepository;
import com.example.taskmanager.services.TaskService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ApplicationFactory.
 */
class ApplicationFactoryTest {

  @Test
  void testCreateTaskManager() {
    TaskManager taskManager = ApplicationFactory.createTaskManager();

    assertNotNull(taskManager);
  }

  @Test
  void testCreateTaskManagerWithCustomComponents() {
    TaskRepository repository = new InMemoryTaskRepository();
    TaskPresenter presenter = new ConsoleTaskPresenter();

    TaskManager taskManager = ApplicationFactory.createTaskManager(repository, presenter);

    assertNotNull(taskManager);
  }

  @Test
  void testCreateTaskRepository() {
    TaskRepository repository = ApplicationFactory.createTaskRepository();

    assertNotNull(repository);
    assertTrue(repository instanceof InMemoryTaskRepository);
  }

  @Test
  void testCreateTaskService() {
    TaskRepository repository = new InMemoryTaskRepository();

    TaskService service = ApplicationFactory.createTaskService(repository);

    assertNotNull(service);
  }

  @Test
  void testCreateTaskPresenter() {
    TaskPresenter presenter = ApplicationFactory.createTaskPresenter();

    assertNotNull(presenter);
    assertTrue(presenter instanceof ConsoleTaskPresenter);
  }

  @Test
  void testFactoryCreatesWorkingComponents() {
    TaskManager taskManager = ApplicationFactory.createTaskManager();

    // Should be able to add a task without errors
    taskManager.addTask("Test Task", "Test Description");
    
    assertEquals(1, taskManager.getTaskCount());
  }
}
