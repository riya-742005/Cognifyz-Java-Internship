import java.util.Scanner;

public class PasswordStrengthChecker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a password to check: ");
        String password = scanner.nextLine();

        int strengthScore = getStrengthScore(password);

        if (strengthScore < 3) {
            System.out.println("Password Strength: Weak");
        } else if (strengthScore == 3) {
            System.out.println("Password Strength: Medium");
        } else {
            System.out.println("Password Strength: Strong");
        }

        scanner.close();
    }

    public static int getStrengthScore(String password) {
        int score = 0;

        if (password.length() >= 8) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[a-z].*")) score++;
        if (password.matches(".*[0-9].*")) score++;
        if (password.matches(".*[!@#$%^&*()].*")) score++;

        return score;
    }
}
