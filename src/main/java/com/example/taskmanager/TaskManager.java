package com.example.taskmanager;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.presentation.TaskPresenter;
import com.example.taskmanager.services.TaskService;
import java.util.List;
import java.util.Optional;

/**
 * Facade class for task management operations.
 * Follows the Single Responsibility Principle (SRP) - coordinates
 * between service and presentation layers.
 * Follows the Dependency Inversion Principle (DIP) - depends on
 * abstractions (TaskService and TaskPresenter) not concrete implementations.
 * Implements the Facade pattern for simplified client interaction.
 */
public class TaskManager {

  private final TaskService taskService;
  private final TaskPresenter presenter;

  /**
   * Creates a TaskManager with the specified service and presenter.
   *
   * @param taskService the service for task operations
   * @param presenter the presenter for output
   */
  public TaskManager(TaskService taskService, TaskPresenter presenter) {
    if (taskService == null) {
      throw new IllegalArgumentException("TaskService cannot be null");
    }
    if (presenter == null) {
      throw new IllegalArgumentException("TaskPresenter cannot be null");
    }
    this.taskService = taskService;
    this.presenter = presenter;
  }

  /**
   * Adds a new task with the given name.
   *
   * @param taskName the name of the task
   */
  public void addTask(String taskName) {
    addTask(taskName, "");
  }

  /**
   * Adds a new task with the given name and description.
   *
   * @param taskName the name of the task
   * @param description the description of the task
   */
  public void addTask(String taskName, String description) {
    try {
      Task task = taskService.createTask(taskName, description);
      presenter.displaySuccess("Task added: " + task.getName());
    } catch (IllegalArgumentException e) {
      presenter.displayError(e.getMessage());
    }
  }

  /**
   * Lists all tasks.
   */
  public void listTasks() {
    List<Task> tasks = taskService.getAllTasks();
    presenter.displayTasks(tasks);
  }

  /**
   * Removes a task by its 1-based index.
   *
   * @param index the 1-based index of the task to remove
   */
  public void removeTask(int index) {
    Optional<Task> task = taskService.getTaskByIndex(index);
    if (task.isPresent()) {
      String taskName = task.get().getName();
      if (taskService.removeTaskByIndex(index)) {
        presenter.displaySuccess("Task removed: " + taskName);
      } else {
        presenter.displayError("Failed to remove task.");
      }
    } else {
      presenter.displayError("Task not found at index " + index);
    }
  }

  /**
   * Marks a task as completed by its 1-based index.
   *
   * @param index the 1-based index of the task to complete
   */
  public void completeTask(int index) {
    Optional<Task> task = taskService.getTaskByIndex(index);
    if (task.isPresent()) {
      if (taskService.completeTask(task.get().getId())) {
        presenter.displaySuccess("Task completed: " + task.get().getName());
      } else {
        presenter.displayError("Failed to complete task.");
      }
    } else {
      presenter.displayError("Task not found at index " + index);
    }
  }

  /**
   * Edits a task by its 1-based index.
   *
   * @param index the 1-based index of the task to edit
   * @param newName the new name for the task
   * @param newDescription the new description for the task
   */
  public void editTask(int index, String newName, String newDescription) {
    Optional<Task> task = taskService.getTaskByIndex(index);
    if (task.isPresent()) {
      if (taskService.updateTask(index, newName, newDescription)) {
        presenter.displaySuccess("Task updated successfully");
      } else {
        presenter.displayError("Failed to update task. Name cannot be empty.");
      }
    } else {
      presenter.displayError("Task not found at index " + index);
    }
  }

  /**
   * Gets the total number of tasks.
   *
   * @return the count of tasks
   */
  public int getTaskCount() {
    return taskService.getTaskCount();
  }
}

