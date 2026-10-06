package com.book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.util.stream.Collectors;
import java.sql.DriverManager;

public class AccountBook {
    // private List<Transaction> transactions = new ArrayList();

    private Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/accountbook?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "123456";
        return DriverManager.getConnection(url, user, password);
    }

    public AccountBook() {
    }

    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (id, amount, category, date, note) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = this.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, transaction.getId());
            pstmt.setDouble(2, transaction.getAmount());
            pstmt.setString(3, transaction.getCategory());
            pstmt.setDate(4, java.sql.Date.valueOf(transaction.getDate()));
            pstmt.setString(5, transaction.getNote());
            pstmt.executeUpdate();
            System.out.println("Added successfully!");
        } catch (Exception e) {
            System.out.println("Failed to add transaction!");
            e.printStackTrace();
        }
        // this.transactions.add(transaction);
    }

    public boolean removeTransaction(String id) {
        // return this.transactions.removeIf((t) -> t.getId().equals(id));
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaction> getTransactions() {
        // return
        // this.transactions.stream().sorted(Comparator.comparing(Transaction::getDate).reversed()).toList();
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY date DESC";
        try (Connection conn = this.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("id");
                double amount = rs.getDouble("amount");
                String category = rs.getString("category");
                LocalDate date = rs.getDate("date").toLocalDate();
                String note = rs.getString("note");
                transactions.add(new Transaction(id, amount, category, date, note));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;

    }

    public Map<String, Double> getMonthlyReport(int year, int month) {
        List<Transaction> transactions = getTransactions();
        return transactions.stream()
                .filter((t) -> t.getDate().getYear() == year && t.getDate().getMonthValue() == month)
                .collect(Collectors.groupingBy(Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)));

    }

    /*
     * public void save(String filePath) {
     * try {
     * FileOutputStream fos = new FileOutputStream(filePath);
     * ObjectOutputStream oos = new ObjectOutputStream(fos);
     * oos.writeObject(this.transactions);
     * System.out.println("Saved successfully!");
     * } catch (Exception e) {
     * e.printStackTrace();
     * }
     * 
     * }
     * 
     * public void load(String filePath) {
     * try {
     * FileInputStream fis = new FileInputStream(filePath);
     * ObjectInputStream ois = new ObjectInputStream(fis);
     * this.transactions = (List) ois.readObject();
     * ois.close();
     * fis.close();
     * } catch (Exception e) {
     * e.printStackTrace();
     * }
     * 
     * }
     */
}