package com.example.taskmanager.models;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a task entity in the task management system.
 * This class follows the Single Responsibility Principle (SRP)
 * by only handling task data representation.
 */
public class Task {

  private final String id;
  private String name;
  private String description;
  private boolean completed;

  /**
   * Creates a new Task with auto-generated ID.
   *
   * @param name the name of the task
   * @param description the description of the task
   */
  public Task(String name, String description) {
    this.id = UUID.randomUUID().toString();
    this.name = validateName(name);
    this.description = description != null ? description : "";
    this.completed = false;
  }

  /**
   * Creates a new Task with specified ID.
   *
   * @param id the unique identifier for the task
   * @param name the name of the task
   * @param description the description of the task
   */
  public Task(String id, String name, String description) {
    this.id = id;
    this.name = validateName(name);
    this.description = description != null ? description : "";
    this.completed = false;
  }

  private String validateName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Task name cannot be null or empty");
    }
    return name.trim();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = validateName(name);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description != null ? description : "";
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  /**
   * Marks this task as completed.
   */
  public void markAsCompleted() {
    this.completed = true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Task task = (Task) o;
    return Objects.equals(id, task.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return String.format("Task{id='%s', name='%s', completed=%s}", id, name, completed);
  }
}
