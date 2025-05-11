package com.makitara.bio;


import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {
    public static void main(String[] args) throws Exception {
        ServerSocket bioServer = new ServerSocket(8080);
        while (true) {
            Socket socket = bioServer.accept();
            InputStream clientInput = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = clientInput.read(bytes)) != -1) {
                System.out.println(new String(bytes,  0, len));
                System.out.println("===");
            }
            socket.close();
            System.out.println("Client disconnected");
        }
    }
}
