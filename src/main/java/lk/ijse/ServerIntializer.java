package lk.ijse;

import java.io.*;
import java.net.*;
import java.time.LocalDate;

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
                            dataOutputStream.writeUTF(LocalDate.now().toString());
                            break;
                        case "DATE":
                            dataOutputStream.writeUTF("Server Date: " + LocalDate.now());
                            break;
                        case "UPTIME":
                            dataOutputStream.writeUTF("KeepAlive: " + socket.getKeepAlive());
                            break;
                        case "BYE":
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
