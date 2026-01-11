package com.example.taskmanager;

import com.example.taskmanager.config.ApplicationFactory;
import java.util.Scanner;

/**
 * Main entry point for the Task Manager application.
 * Provides an interactive menu for task management operations
 * following SOLID principles.
 */
public class Main {

  private static final Scanner scanner = new Scanner(System.in);
  private static final TaskManager taskManager = ApplicationFactory.createTaskManager();

  /**
   * Application entry point.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    displayWelcome();
    boolean running = true;

    while (running) {
      displayMenu();
      int choice = getIntInput("Enter your choice: ");

      switch (choice) {
        case 1:
          handleAddTask();
          break;
        case 2:
          handleListTasks();
          break;
        case 3:
          handleEditTask();
          break;
        case 4:
          handleCompleteTask();
          break;
        case 5:
          handleRemoveTask();
          break;
        case 0:
          running = false;
          displayGoodbye();
          break;
        default:
          System.out.println("❌ Invalid option. Please try again.\n");
      }
    }

    scanner.close();
  }

  private static void displayWelcome() {
    System.out.println("\n" + "═".repeat(50));
    System.out.println("     📋 Welcome to Task Manager Application");
    System.out.println("═".repeat(50) + "\n");
  }

  private static void displayMenu() {
    System.out.println("\n┌" + "─".repeat(48) + "┐");
    System.out.println("│" + " ".repeat(18) + "MAIN MENU" + " ".repeat(21) + "│");
    System.out.println("├" + "─".repeat(48) + "┤");
    System.out.println("│  1. ➕ Add Task" + " ".repeat(32) + "│");
    System.out.println("│  2. 📋 List All Tasks" + " ".repeat(26) + "│");
    System.out.println("│  3. ✏️  Edit Task" + " ".repeat(31) + "│");
    System.out.println("│  4. ✅ Mark Task as Completed" + " ".repeat(18) + "│");
    System.out.println("│  5. 🗑️  Remove Task" + " ".repeat(29) + "│");
    System.out.println("│  0. 🚪 Exit" + " ".repeat(36) + "│");
    System.out.println("└" + "─".repeat(48) + "┘");
  }

  private static void handleAddTask() {
    System.out.println("\n➕ ADD NEW TASK");
    System.out.println("─".repeat(40));
    
    String name = getStringInput("Task name: ");
    if (name.isEmpty()) {
      System.out.println("❌ Task name cannot be empty.");
      return;
    }
    
    String description = getStringInput("Description (optional): ");
    taskManager.addTask(name, description);
  }

  private static void handleListTasks() {
    System.out.println();
    taskManager.listTasks();
  }

  private static void handleEditTask() {
    System.out.println("\n✏️  EDIT TASK");
    System.out.println("─".repeat(40));
    
    if (taskManager.getTaskCount() == 0) {
      System.out.println("ℹ️  No tasks available to edit.");
      return;
    }
    
    taskManager.listTasks();
    int index = getIntInput("\nEnter task number to edit: ");
    
    if (index <= 0) {
      System.out.println("❌ Invalid task number.");
      return;
    }
    
    String newName = getStringInput("New task name: ");
    if (newName.isEmpty()) {
      System.out.println("❌ Task name cannot be empty.");
      return;
    }
    
    String newDescription = getStringInput("New description (optional): ");
    taskManager.editTask(index, newName, newDescription);
  }

  private static void handleCompleteTask() {
    System.out.println("\n✅ MARK TASK AS COMPLETED");
    System.out.println("─".repeat(40));
    
    if (taskManager.getTaskCount() == 0) {
      System.out.println("ℹ️  No tasks available to complete.");
      return;
    }
    
    taskManager.listTasks();
    int index = getIntInput("\nEnter task number to complete: ");
    
    if (index > 0) {
      taskManager.completeTask(index);
    } else {
      System.out.println("❌ Invalid task number.");
    }
  }

  private static void handleRemoveTask() {
    System.out.println("\n🗑️  REMOVE TASK");
    System.out.println("─".repeat(40));
    
    if (taskManager.getTaskCount() == 0) {
      System.out.println("ℹ️  No tasks available to remove.");
      return;
    }
    
    taskManager.listTasks();
    int index = getIntInput("\nEnter task number to remove: ");
    
    if (index > 0) {
      taskManager.removeTask(index);
    } else {
      System.out.println("❌ Invalid task number.");
    }
  }

  private static void displayGoodbye() {
    System.out.println("\n" + "═".repeat(50));
    System.out.println("   👋 Thank you for using Task Manager!");
    System.out.println("═".repeat(50) + "\n");
  }

  private static String getStringInput(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine().trim();
  }

  private static int getIntInput(String prompt) {
    System.out.print(prompt);
    try {
      String input = scanner.nextLine().trim();
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      return -1;
    }
  }
}