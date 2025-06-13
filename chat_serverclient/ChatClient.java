import java.io.*;
import java.net.*;

public class ChatClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            System.out.println("✅ Connected to server.");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            // Ask for client name
            System.out.print("Enter your name: ");
            String clientName = console.readLine();
            output.println(clientName); // Send name to server

            // Thread to receive messages
            Thread receiveThread = new Thread(() -> {
                String msg;
                try {
                    while ((msg = input.readLine()) != null) {
                        if (msg.equalsIgnoreCase("exit")) {
                            System.out.println("Server ended the chat.");
                            break;
                        }
                        System.out.println("Server: " + msg);
                    }
                } catch (IOException e) {
                    System.out.println("❌ Server disconnected.");
                }
            });

            receiveThread.start();

            // Sending messages to server
            String message;
            while ((message = console.readLine()) != null) {
                output.println(message);
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("You left the chat.");
                    break;
                }
            }

            socket.close();
            System.exit(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

