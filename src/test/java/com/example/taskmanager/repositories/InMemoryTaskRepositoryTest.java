package com.example.taskmanager.repositories;

import com.example.taskmanager.models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for InMemoryTaskRepository.
 */
class InMemoryTaskRepositoryTest {

  private InMemoryTaskRepository repository;

  @BeforeEach
  void setUp() {
    repository = new InMemoryTaskRepository();
  }

  @Test
  void testSaveNewTask() {
    Task task = new Task("Test Task", "Description");

    Task saved = repository.save(task);

    assertNotNull(saved);
    assertEquals(task.getId(), saved.getId());
    assertEquals(1, repository.count());
  }

  @Test
  void testSaveNullTask() {
    assertThrows(IllegalArgumentException.class, () -> {
      repository.save(null);
    });
  }

  @Test
  void testSaveExistingTask() {
    Task task = new Task("Test Task", "Description");
    repository.save(task);

    task.setName("Updated Name");
    Task updated = repository.save(task);

    assertEquals("Updated Name", updated.getName());
    assertEquals(1, repository.count());
  }

  @Test
  void testFindById() {
    Task task = new Task("Test Task", "Description");
    repository.save(task);

    Optional<Task> found = repository.findById(task.getId());

    assertTrue(found.isPresent());
    assertEquals(task.getId(), found.get().getId());
  }

  @Test
  void testFindByIdNotFound() {
    Optional<Task> found = repository.findById("non-existent-id");

    assertFalse(found.isPresent());
  }

  @Test
  void testFindByIdWithNull() {
    Optional<Task> found = repository.findById(null);

    assertFalse(found.isPresent());
  }

  @Test
  void testFindByIndex() {
    Task task1 = new Task("Task 1", "Description 1");
    Task task2 = new Task("Task 2", "Description 2");
    repository.save(task1);
    repository.save(task2);

    Optional<Task> found = repository.findByIndex(0);

    assertTrue(found.isPresent());
    assertEquals(task1.getId(), found.get().getId());
  }

  @Test
  void testFindByIndexOutOfBounds() {
    Optional<Task> found = repository.findByIndex(10);

    assertFalse(found.isPresent());
  }

  @Test
  void testFindByIndexNegative() {
    Optional<Task> found = repository.findByIndex(-1);

    assertFalse(found.isPresent());
  }

  @Test
  void testFindAll() {
    Task task1 = new Task("Task 1", "Description 1");
    Task task2 = new Task("Task 2", "Description 2");
    repository.save(task1);
    repository.save(task2);

    List<Task> tasks = repository.findAll();

    assertEquals(2, tasks.size());
  }

  @Test
  void testFindAllEmpty() {
    List<Task> tasks = repository.findAll();

    assertTrue(tasks.isEmpty());
  }

  @Test
  void testFindAllReturnsUnmodifiableList() {
    Task task = new Task("Task", "Description");
    repository.save(task);

    List<Task> tasks = repository.findAll();

    assertThrows(UnsupportedOperationException.class, () -> {
      tasks.add(new Task("New Task", "Description"));
    });
  }

  @Test
  void testDeleteById() {
    Task task = new Task("Test Task", "Description");
    repository.save(task);

    boolean deleted = repository.deleteById(task.getId());

    assertTrue(deleted);
    assertEquals(0, repository.count());
  }

  @Test
  void testDeleteByIdNotFound() {
    boolean deleted = repository.deleteById("non-existent-id");

    assertFalse(deleted);
  }

  @Test
  void testDeleteByIndex() {
    Task task1 = new Task("Task 1", "Description 1");
    Task task2 = new Task("Task 2", "Description 2");
    repository.save(task1);
    repository.save(task2);

    boolean deleted = repository.deleteByIndex(0);

    assertTrue(deleted);
    assertEquals(1, repository.count());
  }

  @Test
  void testDeleteByIndexOutOfBounds() {
    boolean deleted = repository.deleteByIndex(10);

    assertFalse(deleted);
  }

  @Test
  void testDeleteByIndexNegative() {
    boolean deleted = repository.deleteByIndex(-1);

    assertFalse(deleted);
  }

  @Test
  void testCount() {
    assertEquals(0, repository.count());

    repository.save(new Task("Task 1", "Description"));
    assertEquals(1, repository.count());

    repository.save(new Task("Task 2", "Description"));
    assertEquals(2, repository.count());
  }

  @Test
  void testExistsById() {
    Task task = new Task("Test Task", "Description");
    repository.save(task);

    assertTrue(repository.existsById(task.getId()));
    assertFalse(repository.existsById("non-existent-id"));
  }
}
