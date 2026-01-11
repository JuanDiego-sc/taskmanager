package com.example.taskmanager.config;

import com.example.taskmanager.TaskManager;
import com.example.taskmanager.presentation.ConsoleTaskPresenter;
import com.example.taskmanager.presentation.TaskPresenter;
import com.example.taskmanager.repositories.InMemoryTaskRepository;
import com.example.taskmanager.repositories.TaskRepository;
import com.example.taskmanager.services.TaskService;
import com.example.taskmanager.services.TaskServiceImpl;

/**
 * Factory class for creating application components.
 * Follows the Dependency Inversion Principle (DIP) by centralizing
 * the creation and wiring of dependencies.
 * Implements the Factory pattern for object creation.
 * This can be replaced with a DI framework (Spring, Guice) in larger projects.
 */
public final class ApplicationFactory {

  private ApplicationFactory() {
    // Prevent instantiation
  }

  /**
   * Creates a TaskManager with default configurations.
   * Uses in-memory storage and console presentation.
   *
   * @return a fully configured TaskManager
   */
  public static TaskManager createTaskManager() {
    TaskRepository repository = createTaskRepository();
    TaskService service = createTaskService(repository);
    TaskPresenter presenter = createTaskPresenter();
    return new TaskManager(service, presenter);
  }

  /**
   * Creates a TaskManager with custom components.
   *
   * @param repository the task repository to use
   * @param presenter the presenter to use
   * @return a configured TaskManager
   */
  public static TaskManager createTaskManager(
      TaskRepository repository, 
      TaskPresenter presenter) {
    TaskService service = createTaskService(repository);
    return new TaskManager(service, presenter);
  }

  /**
   * Creates the default TaskRepository implementation.
   *
   * @return an InMemoryTaskRepository
   */
  public static TaskRepository createTaskRepository() {
    return new InMemoryTaskRepository();
  }

  /**
   * Creates a TaskService with the specified repository.
   *
   * @param repository the repository to use
   * @return a TaskServiceImpl
   */
  public static TaskService createTaskService(TaskRepository repository) {
    return new TaskServiceImpl(repository);
  }

  /**
   * Creates the default TaskPresenter implementation.
   *
   * @return a ConsoleTaskPresenter
   */
  public static TaskPresenter createTaskPresenter() {
    return new ConsoleTaskPresenter();
  }
}
