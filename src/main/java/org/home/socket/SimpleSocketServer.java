package org.home.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleSocketServer {
    public static final int PORT = 19000;

    public static void start() {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server Address " + serverSocket.getLocalSocketAddress());
            System.out.println("Started, waiting for connection...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected from " + socket.getRemoteSocketAddress());

            try (InputStream in = socket.getInputStream();
                 OutputStream out = socket.getOutputStream()) {

                byte[] buf = new byte[32 * 1024];
                int readBytes = in.read(buf);
                String line = new String(buf, 0, readBytes);
                System.out.print("Client> ");
                System.out.println(line);

                line = "Answer from Server!";
                out.write(line.getBytes());
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (Exception e) {
                    // tsss!
                }
            }
        }

    }

}
