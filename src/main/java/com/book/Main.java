package com.book;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountBook book = new AccountBook();
        // book.load("account.dat");

        while (true) {
            System.out.println("===== 个人记账本 =====");
            System.out.println("1. 记一笔账");
            System.out.println("2. 查看账单（按日期排序）");
            System.out.println("3. 按月统计");
            System.out.println("4. 删除账单");
            System.out.println("5. 退出");
            System.out.print("请选择：");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("请输入账单编号：");
                    String id = sc.next();
                    System.out.print("请输入日期（格式：yyyy-MM-dd）：");
                    String date = sc.next();
                    System.out.print("请输入金额：");
                    double amount = sc.nextDouble();
                    System.out.print("请输入类别：");
                    String category = sc.next();
                    System.out.print("请输入备注：");
                    String remark = sc.next();
                    book.addTransaction(new Transaction(id, amount, category, LocalDate.parse(date), remark));
                    System.out.println("添加成功！账单编号：" + id);
                    break;
                case 2:
                    System.out.println(book.getTransactions());
                    break;
                case 3:
                    System.out.print("请输入年份：");
                    int year = sc.nextInt();
                    System.out.print("请输入月份：");
                    int month = sc.nextInt();
                    System.out.println(book.getMonthlyReport(year, month));
                    break;
                case 4:
                    System.out.print("请输入账单编号：");
                    String idToRemove = sc.next();
                    boolean removed = book.removeTransaction(idToRemove);
                    if (removed) {
                        System.out.println("删除成功！账单编号：" + idToRemove);
                    } else {
                        System.out.println("删除失败！账单编号：" + idToRemove + " 不存在");
                    }
                    break;
                case 5:
                    System.out.println("退出程序");
                    sc.close();

                    System.exit(0);
                default:
                    System.out.println("无效的选择，请重新输入");
            }
        }
    }
}
