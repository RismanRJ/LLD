package Controllers;

import Models.User;

import java.io.*;
import java.util.*;

public class ExpenseService {
    private List<String> expenses;
    private Map<String, User> users;

    public ExpenseService(Map<String, User> users) {
        this.users = users;
        this.expenses= loadExpenses();
    }

    public void addSimpleExpenses(String input, String currentUser) {
        String[] items = input.split(",");
        int total = 0;
        for (String item : items) {
            String[] parts = item.split("-");
            String task = parts[0].trim();
            int amount = Integer.parseInt(parts[1].trim());
            System.out.println("Spent " + amount + " for " + task);
            total += amount;
            String log = "Spent " + amount + " for " + task;
            users.get(currentUser).addExpenseLog(log);
            expenses.add(task + "-" + amount);
        }
        System.out.println("Total liabilities = -" + total);
    }

    public void addTaggedExpenses(String input, String currentUser) {
        String[] parts = input.split("-", 2);
        String task = parts[0].trim();
        String[] tagged = parts[1].replace("[", "").replace("]", "").split(",");
        int total = 0;

        for (String tag : tagged) {
            String[] detail = tag.trim().substring(1).split("-");
            String name = detail[0];
            int amount = Integer.parseInt(detail[1]);
            total += amount;

            users.putIfAbsent(name, new User(name));

            if (!name.equals(currentUser)) {
                users.get(currentUser).updateBalance(name, -amount);
                users.get(name).updateBalance(currentUser, amount);
                String log = task + " - Borrowed (-) " + amount + " from " + name;
                users.get(currentUser).addExpenseLog(log);
                System.out.println(log);
            } else {
                String log = task + " - You Spent (+) " + amount;
                users.get(currentUser).addExpenseLog(log);
                System.out.println(log);
            }
        }

        expenses.add(input);
        double net = users.get(currentUser).balanceSheet.values().stream().mapToDouble(Double::doubleValue).sum();
        System.out.println("Total assets/liabilities = " + net);
    }

    public void addGroupExpense(String input, String currentUser) {
        try {
            String[] split1 = input.split("-", 3);
            String task = split1[0].trim();
            String date = split1[1].trim();
            String[] tagged = split1[2].replace("[", "").replace("]", "").split(",");

            Map<String, Integer> contributions = new HashMap<>();
            int total = 0;
            for (String tag : tagged) {
                String[] detail = tag.trim().substring(1).split("-");
                String name = detail[0];
                int amount = Integer.parseInt(detail[1]);
                total += amount;
                contributions.put(name, amount);
                users.putIfAbsent(name, new User(name));
            }

            int equalShare = total / contributions.size();
            for (Map.Entry<String, Integer> entry : contributions.entrySet()) {
                String name = entry.getKey();
                int paid = entry.getValue();
                int difference = paid - equalShare;
                for (Map.Entry<String, Integer> other : contributions.entrySet()) {
                    String otherName = other.getKey();
                    if (name.equals(otherName)) continue;
                    int otherPaid = other.getValue();
                    int otherDiff = otherPaid - equalShare;

                    if (difference < 0 && otherDiff > 0) {
                        int settleAmount = Math.min(-difference, otherDiff);
                        users.get(name).updateBalance(otherName, -settleAmount);
                        users.get(otherName).updateBalance(name, settleAmount);
                        String log = "Group: " + task + " - " + date + " - " + name + " owes " + settleAmount + " to " + otherName;
                        if (name.equals(currentUser))
                            users.get(currentUser).addExpenseLog("Owes " + settleAmount + " to " + otherName + " for " + task);
                        else if (otherName.equals(currentUser))
                            users.get(currentUser).addExpenseLog("Lent " + settleAmount + " to " + name + " for " + task);
                        System.out.println(log);
                        difference += settleAmount;
                    }
                }
            }

            expenses.add(input);
            double net = users.get(currentUser).balanceSheet.values().stream().mapToDouble(Double::doubleValue).sum();
            System.out.println("Total assets/liabilities = " + net);
        } catch (Exception e) {
            System.out.println("Invalid format. Use: Task - Date - [@name-amount,...]");
        }
    }

    public void saveExpensesToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:/Users/RismanJayashanker/Downloads/demo/LLD/ExpenseManager/data/expenses.txt"))) {
            if(expenses.isEmpty()){
                System.out.println("returning heere");
                return;
            };
            for (String exp : expenses) {
                bw.append(exp);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }


    List<String> loadExpenses() {
        File file = new File("C:/Users/RismanJayashanker/Downloads/demo/LLD/ExpenseManager/data/expenses.txt" );
        if (!file.exists()) return new ArrayList<>();
        List<String> expense= new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                expense.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }

        return expense;
    }
}
