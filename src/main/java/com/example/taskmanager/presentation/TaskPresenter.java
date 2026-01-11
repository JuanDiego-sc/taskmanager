package com.example.taskmanager.presentation;

import com.example.taskmanager.models.Task;
import java.util.List;

/**
 * Interface for presenting task information to users.
 * Follows the Interface Segregation Principle (ISP) by defining
 * only presentation-related operations.
 * Supports Single Responsibility Principle (SRP) - handles only
 * output formatting and display.
 */
public interface TaskPresenter {

  /**
   * Displays a single task.
   *
   * @param task the task to display
   * @param index the display index (1-based)
   */
  void displayTask(Task task, int index);

  /**
   * Displays a list of tasks.
   *
   * @param tasks the list of tasks to display
   */
  void displayTasks(List<Task> tasks);

  /**
   * Displays a success message.
   *
   * @param message the success message
   */
  void displaySuccess(String message);

  /**
   * Displays an error message.
   *
   * @param message the error message
   */
  void displayError(String message);

  /**
   * Displays an informational message.
   *
   * @param message the info message
   */
  void displayInfo(String message);
}
