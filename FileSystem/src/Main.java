import Controller.FileController;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Create a new file system instance
        FileController fs = FileController.getInstance();
        // Create a scanner to handle user input
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true; // Flag to control the program loop
        // Display instructions for the user
        System.out.println("File System Manager - Commands:");
        System.out.println("1. create <path> - Create a new path");
        System.out.println("2. write <path> <content> - Write content to a file");
        System.out.println("3. read <path> - Read content from a file");
        System.out.println("4. update <path> <new path> - Update a Folder name or File Name");
        System.out.println("5. delete <path> - Delete a path");
        System.out.println("6. restore <path> - restore a folder or file from trash");
        System.out.println("7. display - Show the entire file system structure");
        System.out.println("8. exit - Exit the program");
        // Main program loop to process commands
        while (isRunning) {
            System.out.print("\nEnter command: ");
            String input = scanner.nextLine().trim(); // Read and trim user input
            String[] parts = input.split("\\s+", 3); // Split input into command, path, and possibly content
            if (parts.length == 0)
                continue; // Skip empty input
            String command = parts[0].toLowerCase(); // Get the command
            try {
                switch (command) {
                    case "create":
                        // Create a new path
                        if (parts.length >= 2) {
                            String path = parts[1]; // Extract path
                            boolean isCreated = fs.CreateFileOrFolder(path); // Attempt to create the path
                            System.out.println(isCreated ? "Path created successfully" : "Failed to create path");
                        } else {
                            System.out.println("Usage: create <path>");
                        }
                        break;
                    case "write":
                        // Write content to a file
                        if (parts.length >= 3) {
                            String path = parts[1]; // Extract path
                            String content = parts[2]; // Extract content
                            boolean isWritten = fs.SetFileContent(path, content); // Attempt to write content
                            System.out.println(
                                    isWritten ? "Content written successfully" : "Failed to write content");
                        } else {
                            System.out.println("Usage: write <path> <content>");
                        }
                        break;
                    case "read":
                        // Read content from a file
                        if (parts.length >= 2) {
                            String path = parts[1]; // Extract path
                            String content = fs.GetFileContent(path); // Attempt to read content
                            if (content != null) {
                                System.out.println("Content: " + content);
                            } else {
                                System.out.println("Failed to read content");
                            }
                        } else {
                            System.out.println("Usage: read <path>");
                        }
                        break;
                    case "update":
                        if (parts.length >= 3) {
                            String oldPath = parts[1]; // Extract old path
                            String newPath = parts[2]; //Extract new path
                            boolean isCreated = fs.updateFileOrFolder(oldPath,newPath); // Attempt to create the path
                            System.out.println(isCreated ? "Path updated successfully" : "Failed to update path");
                        } else {
                            System.out.println("Usage: update <old path> <new path>");
                        }
                        break;
                    case "delete":
                        // Delete a specific path from the file system
                        if (parts.length >= 2) {
                            String path = parts[1]; // Extract path
                            boolean isDeleted = fs.DeleteFileOrFolder(path); // Attempt to delete the path
                            System.out.println(isDeleted ? "Path deleted successfully" : "Failed to delete path");
                        } else {
                            System.out.println("Usage: delete <path>");
                        }
                        break;
                    case "restore":
                        // Delete a specific path from the file system
                        if (parts.length >= 2) {
                            String path = parts[1]; // Extract path
                            boolean isDeleted = fs.restoreDeletedFileOrFolder(path); // Attempt to delete the path
                            System.out.println(isDeleted ? "Path restored successfully" : "Failed to restore path");
                        } else {
                            System.out.println("Usage: restore <path>");
                        }
                        break;
                    case "display":
                        // Display the entire file system structure
                        System.out.println("nFile System Structure:");
                        fs.display();
                        break;
                    case "exit":
                        // Exit the program
                        isRunning = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        // Handle unknown commands
                        System.out.println(
                                "Unknown command. Available commands: create, write, read, delete, display, exit");
                }
            } catch (Exception e) {
                // Handle general exceptions
                System.out.println("Error: " + e.getMessage());
            }
        }
        // Close the scanner to release system resources
        scanner.close();
    }
}