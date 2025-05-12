

import Controllers.ExpenseService;
import Models.User;

import java.io.*;
import java.util.*;



public class ExpenseManagerApp {
    static Map<String, User> users = new HashMap<>();
    static User currentUser = null;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        loadUsers();

        while (true) {
            System.out.print("Enter your username to login or register (or type 'exit' to quit): ");
            String username = scanner.nextLine().trim();
            if (username.equalsIgnoreCase("exit")) break;

            currentUser = users.computeIfAbsent(username, User::new);
            System.out.println("Logged in as: " + currentUser.username);

            ExpenseService expenseService = new ExpenseService(users);

            while (true) {
                System.out.println("\n1. Add Simple Expenses\n2. Add Tagged Expenses\n3. Add Group Expense\n4. View Summary\n5. List My Expenses\n6. Switch User\n7. Exit");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        System.out.println("Enter expenses (e.g., taxi-500,movie-200):");
                        String input = scanner.nextLine();
                        expenseService.addSimpleExpenses(input, currentUser.username);
                        break;
                    case "2":
                        System.out.println("Enter tagged expense (e.g., taxi - [@john-500,@jane-200]):");
                        String taggedInput = scanner.nextLine();
                        expenseService.addTaggedExpenses(taggedInput, currentUser.username);
                        break;
                    case "3":
                        System.out.println("Enter group expense (e.g., Taxi - 01/Jan 25 - [@john-500,@jane-600,@ram-100]):");
                        String groupInput = scanner.nextLine();
                        expenseService.addGroupExpense(groupInput, currentUser.username);
                        break;
                    case "4":
                        currentUser.printSummary();
                        break;
                    case "5":
                        currentUser.listExpenses();
                        break;
                    case "6":
                        saveUsers();
                        expenseService.saveExpensesToFile();
                        currentUser = null;
                        System.out.println("Switching user...");
                        break;
                    case "7":
                        saveUsers();
                        expenseService.saveExpensesToFile();
                        System.out.println("Data saved. Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }

                if (currentUser == null) break;
            }
        }
    }

    static void loadUsers() {
        File file = new File("C:/Users/RismanJayashanker/Downloads/demo/LLD/ExpenseManager/data/users.txt" );
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                users.put(line.trim(), new User(line.trim()));
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    static void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:/Users/RismanJayashanker/Downloads/demo/LLD/ExpenseManager/data/users.txt"))) {
            for (String username : users.keySet()) {
                bw.append(username);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
}