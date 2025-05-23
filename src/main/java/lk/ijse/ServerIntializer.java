package lk.ijse;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ServerIntializer {
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    private static ServerSocket serverSocket;
    private static Socket socket;

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(6000);
                socket = serverSocket.accept();
                System.out.println("Client connected!");

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while (!socket.isClosed()) {
                    String type = dataInputStream.readUTF();
                    System.out.println("Client: " + type);

                    switch (type.toUpperCase()) {
                        case "TIME":
                            dataOutputStream.writeUTF(LocalTime.now().toString());
                            break;
                        case "DATE":
                            dataOutputStream.writeUTF("Server: " + LocalDate.now());
                            break;
                        case "UPTIME":
                            dataOutputStream.writeUTF("server: " + socket.getKeepAlive());
                            break;
                        case "EXIT":
                            dataOutputStream.writeUTF("Server: BYE!!");
                            socket.close();
                            break;
                        default:
                            dataOutputStream.writeUTF("Unknown command");
                    }

                    dataOutputStream.flush();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
