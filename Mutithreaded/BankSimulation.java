class BankAccount {
    private int balance = 1000;

    public synchronized void deposit(int amount) {
        balance += amount;
        System.out.println(Thread.currentThread().getName() + " deposited ₹" + amount + ". Balance: ₹" + balance);
    }

    public synchronized void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " withdrew ₹" + amount + ". Balance: ₹" + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " tried to withdraw ₹" + amount + " but insufficient balance!");
        }
    }
}

class DepositThread extends Thread {
    BankAccount account;

    DepositThread(BankAccount account) {
        this.account = account;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            account.deposit(100);
            try { Thread.sleep(100); } catch (InterruptedException e) {}
        }
    }
}

class WithdrawThread extends Thread {
    BankAccount account;

    WithdrawThread(BankAccount account) {
        this.account = account;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            account.withdraw(150);
            try { Thread.sleep(100); } catch (InterruptedException e) {}
        }
    }
}

public class BankSimulation {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        DepositThread depositor = new DepositThread(account);
        WithdrawThread withdrawer = new WithdrawThread(account);

        depositor.setName("Depositor");
        withdrawer.setName("Withdrawer");

        depositor.start();
        withdrawer.start();
    }
}
