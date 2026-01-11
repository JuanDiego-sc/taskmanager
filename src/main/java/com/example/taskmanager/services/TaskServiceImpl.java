package com.example.taskmanager.services;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.repositories.TaskRepository;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of TaskService.
 * Follows the Single Responsibility Principle (SRP) - handles only
 * task business logic.
 * Follows the Dependency Inversion Principle (DIP) - depends on
 * TaskRepository abstraction, not concrete implementation.
 * Follows the Open/Closed Principle (OCP) - can be extended
 * without modifying existing code.
 */
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;

  /**
   * Creates a TaskServiceImpl with the specified repository.
   * Constructor injection follows DIP.
   *
   * @param taskRepository the repository for task persistence
   */
  public TaskServiceImpl(TaskRepository taskRepository) {
    if (taskRepository == null) {
      throw new IllegalArgumentException("TaskRepository cannot be null");
    }
    this.taskRepository = taskRepository;
  }

  @Override
  public Task createTask(String name, String description) {
    Task task = new Task(name, description);
    return taskRepository.save(task);
  }

  @Override
  public Task createTask(String name) {
    return createTask(name, "");
  }

  @Override
  public List<Task> getAllTasks() {
    return taskRepository.findAll();
  }

  @Override
  public Optional<Task> getTaskById(String id) {
    return taskRepository.findById(id);
  }

  @Override
  public Optional<Task> getTaskByIndex(int index) {
    // Convert from 1-based to 0-based index
    return taskRepository.findByIndex(index - 1);
  }

  @Override
  public boolean removeTask(String id) {
    return taskRepository.deleteById(id);
  }

  @Override
  public boolean removeTaskByIndex(int index) {
    // Convert from 1-based to 0-based index
    return taskRepository.deleteByIndex(index - 1);
  }

  @Override
  public boolean completeTask(String id) {
    Optional<Task> taskOptional = taskRepository.findById(id);
    if (taskOptional.isPresent()) {
      Task task = taskOptional.get();
      task.markAsCompleted();
      taskRepository.save(task);
      return true;
    }
    return false;
  }

  @Override
  public boolean updateTask(int index, String newName, String newDescription) {
    Optional<Task> taskOptional = getTaskByIndex(index);
    if (taskOptional.isPresent()) {
      Task task = taskOptional.get();
      try {
        task.setName(newName);
        task.setDescription(newDescription);
        taskRepository.save(task);
        return true;
      } catch (IllegalArgumentException e) {
        return false;
      }
    }
    return false;
  }

  @Override
  public int getTaskCount() {
    return taskRepository.count();
  }
}
