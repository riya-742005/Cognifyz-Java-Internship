import java.io.*;
import java.util.Scanner;

public class FileEncryptDecrypt {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose operation: 1. Encrypt  2. Decrypt");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter input file name: ");
        String inputFile = scanner.nextLine();

        System.out.print("Enter output file name: ");
        String outputFile = scanner.nextLine();

        System.out.print("Enter shift key (e.g., 3): ");
        int shift = scanner.nextInt();

        try {
            String content = readFile(inputFile);
            String result = (choice == 1) ? encrypt(content, shift) : decrypt(content, shift);
            writeFile(outputFile, result);
            System.out.println((choice == 1 ? "Encryption" : "Decryption") + " complete. Saved to " + outputFile);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }

    public static String readFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }

        reader.close();
        return content.toString();
    }

    public static void writeFile(String fileName, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(content);
        writer.close();
    }

    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                result.append((char) ((ch - base + shift) % 26 + base));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text, int shift) {
        return encrypt(text, 26 - (shift % 26)); // reverse shift
    }
}
