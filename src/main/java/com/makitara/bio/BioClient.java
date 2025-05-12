package com.makitara.bio;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BioClient {
    public static void main(String[] args) throws Exception {
        Thread addThread = new Thread(() -> {
            try {
                add();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "addThread");
        Thread subtractThread = new Thread(() -> {
            try {
                subtract();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "subtractThread");
        addThread.start();
        subtractThread.start();
        addThread.join();
        subtractThread.join();
        System.out.println("done");
    }
    private static void subtract() throws Exception{
        Socket bioClient = new Socket("localhost", 8080);
        System.out.println("subtract connected");
        OutputStream clientOutput = bioClient.getOutputStream();
        for(int i = 0; i < 10; i++) {
            clientOutput.write(("-" + i + "-").getBytes());
            Thread.sleep(1000);
            clientOutput.flush();
        }
        bioClient.close();
        System.out.println("subtract disconnected");
    }
    private static void add() throws Exception{
        Socket bioClient = new Socket("localhost", 8080);
        System.out.println("add connected");
        OutputStream clientOutput = bioClient.getOutputStream();
        for(int i = 0; i < 10; i++) {
            clientOutput.write(("+" + i + "+").getBytes());
            clientOutput.flush();
        }
        bioClient.close();
        System.out.println("add disconnected");
    }
}
