package com.example.taskmanager.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Task class.
 */
class TaskTest {

  @Test
  void testTaskCreationWithNameAndDescription() {
    Task task = new Task("Test Task", "Test Description");

    assertNotNull(task.getId());
    assertEquals("Test Task", task.getName());
    assertEquals("Test Description", task.getDescription());
    assertFalse(task.isCompleted());
  }

  @Test
  void testTaskCreationWithIdNameAndDescription() {
    String id = "custom-id-123";
    Task task = new Task(id, "Test Task", "Test Description");

    assertEquals(id, task.getId());
    assertEquals("Test Task", task.getName());
    assertEquals("Test Description", task.getDescription());
    assertFalse(task.isCompleted());
  }

  @Test
  void testTaskCreationWithNullDescription() {
    Task task = new Task("Test Task", null);

    assertEquals("", task.getDescription());
  }

  @Test
  void testTaskCreationWithEmptyName() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Task("", "Description");
    });
  }

  @Test
  void testTaskCreationWithNullName() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Task(null, "Description");
    });
  }

  @Test
  void testTaskCreationWithWhitespaceName() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Task("   ", "Description");
    });
  }

  @Test
  void testSetName() {
    Task task = new Task("Original Name", "Description");
    task.setName("Updated Name");

    assertEquals("Updated Name", task.getName());
  }

  @Test
  void testSetNameWithWhitespace() {
    Task task = new Task("Original Name", "Description");
    task.setName("  Updated Name  ");

    assertEquals("Updated Name", task.getName());
  }

  @Test
  void testSetNameWithEmptyString() {
    Task task = new Task("Original Name", "Description");

    assertThrows(IllegalArgumentException.class, () -> {
      task.setName("");
    });
  }

  @Test
  void testSetDescription() {
    Task task = new Task("Test Task", "Original Description");
    task.setDescription("Updated Description");

    assertEquals("Updated Description", task.getDescription());
  }

  @Test
  void testSetDescriptionWithNull() {
    Task task = new Task("Test Task", "Original Description");
    task.setDescription(null);

    assertEquals("", task.getDescription());
  }

  @Test
  void testMarkAsCompleted() {
    Task task = new Task("Test Task", "Description");
    assertFalse(task.isCompleted());

    task.markAsCompleted();

    assertTrue(task.isCompleted());
  }

  @Test
  void testSetCompleted() {
    Task task = new Task("Test Task", "Description");

    task.setCompleted(true);
    assertTrue(task.isCompleted());

    task.setCompleted(false);
    assertFalse(task.isCompleted());
  }

  @Test
  void testEqualsWithSameId() {
    Task task1 = new Task("id1", "Task 1", "Description 1");
    Task task2 = new Task("id1", "Task 2", "Description 2");

    assertEquals(task1, task2);
  }

  @Test
  void testEqualsWithDifferentId() {
    Task task1 = new Task("id1", "Task 1", "Description");
    Task task2 = new Task("id2", "Task 1", "Description");

    assertNotEquals(task1, task2);
  }

  @Test
  void testEqualsWithSameObject() {
    Task task = new Task("Test Task", "Description");

    assertEquals(task, task);
  }

  @Test
  void testEqualsWithNull() {
    Task task = new Task("Test Task", "Description");

    assertNotEquals(null, task);
  }

  @Test
  void testEqualsWithDifferentClass() {
    Task task = new Task("Test Task", "Description");
    String notATask = "Not a Task";

    assertNotEquals(task, notATask);
  }

  @Test
  void testHashCode() {
    Task task1 = new Task("id1", "Task 1", "Description");
    Task task2 = new Task("id1", "Task 2", "Different Description");

    assertEquals(task1.hashCode(), task2.hashCode());
  }

  @Test
  void testToString() {
    Task task = new Task("test-id", "Test Task", "Description");

    String result = task.toString();

    assertTrue(result.contains("test-id"));
    assertTrue(result.contains("Test Task"));
    assertTrue(result.contains("false"));
  }

  @Test
  void testToStringWhenCompleted() {
    Task task = new Task("test-id", "Test Task", "Description");
    task.markAsCompleted();

    String result = task.toString();

    assertTrue(result.contains("true"));
  }
}
