package com.example.taskmanager.services;

import com.example.taskmanager.models.Task;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for task business operations.
 * Follows the Interface Segregation Principle (ISP) by defining
 * only task-related operations.
 * Supports Dependency Inversion Principle (DIP) by providing
 * an abstraction that high-level modules can depend on.
 */
public interface TaskService {

  /**
   * Creates a new task with the given name and description.
   *
   * @param name the task name
   * @param description the task description
   * @return the created task
   */
  Task createTask(String name, String description);

  /**
   * Creates a new task with only a name.
   *
   * @param name the task name
   * @return the created task
   */
  Task createTask(String name);

  /**
   * Retrieves all tasks.
   *
   * @return list of all tasks
   */
  List<Task> getAllTasks();

  /**
   * Finds a task by its identifier.
   *
   * @param id the task identifier
   * @return an Optional containing the task if found
   */
  Optional<Task> getTaskById(String id);

  /**
   * Finds a task by its index (1-based for user convenience).
   *
   * @param index the 1-based index
   * @return an Optional containing the task if found
   */
  Optional<Task> getTaskByIndex(int index);

  /**
   * Removes a task by its identifier.
   *
   * @param id the task identifier
   * @return true if the task was removed
   */
  boolean removeTask(String id);

  /**
   * Removes a task by its index (1-based for user convenience).
   *
   * @param index the 1-based index
   * @return true if the task was removed
   */
  boolean removeTaskByIndex(int index);

  /**
   * Marks a task as completed.
   *
   * @param id the task identifier
   * @return true if the task was marked as completed
   */
  boolean completeTask(String id);

  /**
   * Updates a task's name and description by its index.
   *
   * @param index the 1-based index
   * @param newName the new task name
   * @param newDescription the new task description
   * @return true if the task was updated
   */
  boolean updateTask(int index, String newName, String newDescription);

  /**
   * Returns the total number of tasks.
   *
   * @return the count of tasks
   */
  int getTaskCount();
}
