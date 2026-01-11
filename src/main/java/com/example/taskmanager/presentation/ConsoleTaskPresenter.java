package com.example.taskmanager.presentation;

import com.example.taskmanager.models.Task;
import java.io.PrintStream;
import java.util.List;

/**
 * Console implementation of TaskPresenter.
 * Follows the Single Responsibility Principle (SRP) - only handles
 * console output formatting.
 * Follows the Liskov Substitution Principle (LSP) - can replace
 * any TaskPresenter implementation.
 * Follows the Open/Closed Principle (OCP) - can be extended for
 * different console styles.
 */
public class ConsoleTaskPresenter implements TaskPresenter {

  private static final String TASK_FORMAT = "[%d] %s%s - %s";
  private static final String COMPLETED_MARKER = " ✓";
  private static final String PENDING_MARKER = "";
  private static final String SEPARATOR = "─".repeat(40);

  private final PrintStream output;

  /**
   * Creates a ConsoleTaskPresenter with the specified output stream.
   *
   * @param output the output stream for display
   */
  public ConsoleTaskPresenter(PrintStream output) {
    this.output = output != null ? output : System.out;
  }

  /**
   * Creates a ConsoleTaskPresenter using System.out.
   */
  public ConsoleTaskPresenter() {
    this(System.out);
  }

  @Override
  public void displayTask(Task task, int index) {
    if (task == null) {
      return;
    }
    String completedStatus = task.isCompleted() ? COMPLETED_MARKER : PENDING_MARKER;
    String description = task.getDescription().isEmpty() 
        ? "No description" 
        : task.getDescription();
    output.println(String.format(TASK_FORMAT, index, task.getName(), completedStatus, description));
  }

  @Override
  public void displayTasks(List<Task> tasks) {
    if (tasks == null || tasks.isEmpty()) {
      displayInfo("No tasks found.");
      return;
    }

    output.println(SEPARATOR);
    output.println("📋 Task List (" + tasks.size() + " tasks)");
    output.println(SEPARATOR);

    for (int i = 0; i < tasks.size(); i++) {
      displayTask(tasks.get(i), i + 1);
    }

    output.println(SEPARATOR);
  }

  @Override
  public void displaySuccess(String message) {
    output.println("✅ " + message);
  }

  @Override
  public void displayError(String message) {
    output.println("❌ Error: " + message);
  }

  @Override
  public void displayInfo(String message) {
    output.println("ℹ️  " + message);
  }
}
