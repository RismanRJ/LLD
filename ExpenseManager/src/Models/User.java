package Models;

import  java.util.*;

public class User {
    public String username;
    public Map<String, Double> balanceSheet = new HashMap<>();
    public List<String> personalExpenses = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }

    public void updateBalance(String friend, double amount) {
        balanceSheet.put(friend, balanceSheet.getOrDefault(friend, 0.0) + amount);
    }

    public void addExpenseLog(String log) {
        personalExpenses.add(log);
    }

    public void printSummary() {
        System.out.println("Summary for " + username + ":");
        for (Map.Entry<String, Double> entry : balanceSheet.entrySet()) {
            String relation = entry.getValue() >= 0 ? "Owed by" : "Owes to";
            System.out.println(relation + " " + entry.getKey() + " -> " + Math.abs(entry.getValue()));
        }
        double net = balanceSheet.values().stream().mapToDouble(Double::doubleValue).sum();
        System.out.println("Total assets/liabilities = " + net);
    }

    public void listExpenses() {
        System.out.println("Expenses for " + username + ":");
        for (String log : personalExpenses) {
            System.out.println(log);
        }
    }
}
