import java.io.*;
import java.net.*;

public class ChatServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("🟢 Server started. Waiting for client...");

            Socket socket = serverSocket.accept();
            System.out.println("✅ Client connected.");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            // Ask for server name
            System.out.print("Enter your name: ");
            String serverName = console.readLine();

            // Ask for client name from input stream
            String clientName = input.readLine();
            System.out.println("Client's name is: " + clientName);

            // Thread to read messages from client
            Thread receiveThread = new Thread(() -> {
                String msg;
                try {
                    while ((msg = input.readLine()) != null) {
                        if (msg.equalsIgnoreCase("exit")) {
                            System.out.println(clientName + " left the chat.");
                            break;
                        }
                        System.out.println(clientName + ": " + msg);
                    }
                } catch (IOException e) {
                    System.out.println("❌ Connection lost.");
                }
            });

            receiveThread.start();

            // Sending messages to client
            String message;
            while ((message = console.readLine()) != null) {
                output.println(message);
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("You left the chat.");
                    break;
                }
            }

            socket.close();
            serverSocket.close();
            System.exit(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
