package com.hoan.jdk21study.study.advance.concurrent;

public class BankExample {
    static class Account {
        private int balance = 1000;

        public void withdraw(int amount) {
            if (balance >= amount) {
                try {
                    Thread.sleep(100); // 잔액 확인 후 출금까지 시간차 발생
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + " 출금 완료. 남은 잔액: " + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + " 출금 실패. 잔액 부족.");
            }
        }
    }

    public static void main(String[] args) {
        Account account = new Account();

        Runnable task = () -> account.withdraw(700);

        Thread t1 = new Thread(task, "은행원A");
        Thread t2 = new Thread(task, "은행원B");

        t1.start();
        t2.start();
    }
}

