package lk.ijse;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientInitializer {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 6000);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                Scanner scanner = new Scanner(System.in);

                while (!socket.isClosed()) {
                    System.out.print("Enter command: ");
                    String command = scanner.nextLine();

                    dos.writeUTF(command);
                    dos.flush();

                    String response = dis.readUTF();
                    System.out.println("Server: " + response);

                    if (command.equalsIgnoreCase("BYE")) {
                        socket.close();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
