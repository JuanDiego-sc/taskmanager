package com.example.taskmanager.repositories;

import com.example.taskmanager.models.Task;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Task persistence operations.
 * Follows the Interface Segregation Principle (ISP) and
 * Dependency Inversion Principle (DIP) by providing an abstraction
 * for data access operations.
 */
public interface TaskRepository {

  /**
   * Saves a task to the repository.
   *
   * @param task the task to save
   * @return the saved task
   */
  Task save(Task task);

  /**
   * Finds a task by its unique identifier.
   *
   * @param id the task identifier
   * @return an Optional containing the task if found
   */
  Optional<Task> findById(String id);

  /**
   * Finds a task by its index position.
   *
   * @param index the zero-based index
   * @return an Optional containing the task if found
   */
  Optional<Task> findByIndex(int index);

  /**
   * Retrieves all tasks from the repository.
   *
   * @return a list of all tasks
   */
  List<Task> findAll();

  /**
   * Deletes a task by its identifier.
   *
   * @param id the task identifier
   * @return true if the task was deleted, false otherwise
   */
  boolean deleteById(String id);

  /**
   * Deletes a task by its index position.
   *
   * @param index the zero-based index
   * @return true if the task was deleted, false otherwise
   */
  boolean deleteByIndex(int index);

  /**
   * Returns the total number of tasks.
   *
   * @return the count of tasks
   */
  int count();

  /**
   * Checks if a task exists by its identifier.
   *
   * @param id the task identifier
   * @return true if the task exists
   */
  boolean existsById(String id);
}
