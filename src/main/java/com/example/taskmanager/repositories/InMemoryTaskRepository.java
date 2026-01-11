package com.example.taskmanager.repositories;

import com.example.taskmanager.models.Task;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * In-memory implementation of TaskRepository.
 * Follows the Open/Closed Principle (OCP) - open for extension
 * through inheritance, closed for modification.
 * Implements Liskov Substitution Principle (LSP) by properly
 * implementing the TaskRepository interface.
 */
public class InMemoryTaskRepository implements TaskRepository {

  private final List<Task> tasks;

  /**
   * Creates a new InMemoryTaskRepository with an empty task list.
   */
  public InMemoryTaskRepository() {
    this.tasks = new ArrayList<>();
  }

  @Override
  public Task save(Task task) {
    if (task == null) {
      throw new IllegalArgumentException("Task cannot be null");
    }
    
    // Check if task already exists (update scenario)
    Optional<Task> existing = findById(task.getId());
    if (existing.isPresent()) {
      int index = tasks.indexOf(existing.get());
      tasks.set(index, task);
    } else {
      tasks.add(task);
    }
    return task;
  }

  @Override
  public Optional<Task> findById(String id) {
    if (id == null) {
      return Optional.empty();
    }
    return tasks.stream()
        .filter(task -> task.getId().equals(id))
        .findFirst();
  }

  @Override
  public Optional<Task> findByIndex(int index) {
    if (index < 0 || index >= tasks.size()) {
      return Optional.empty();
    }
    return Optional.of(tasks.get(index));
  }

  @Override
  public List<Task> findAll() {
    return Collections.unmodifiableList(new ArrayList<>(tasks));
  }

  @Override
  public boolean deleteById(String id) {
    return findById(id)
        .map(tasks::remove)
        .orElse(false);
  }

  @Override
  public boolean deleteByIndex(int index) {
    if (index < 0 || index >= tasks.size()) {
      return false;
    }
    tasks.remove(index);
    return true;
  }

  @Override
  public int count() {
    return tasks.size();
  }

  @Override
  public boolean existsById(String id) {
    return findById(id).isPresent();
  }
}
