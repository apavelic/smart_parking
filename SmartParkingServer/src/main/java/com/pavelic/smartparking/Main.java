package com.pavelic.smartparking;

import com.pavelic.smartparking.threads.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static final int PORT = 1234;

    public static void main(String[] args) {

        ServerSocket socket = null;

        try {
            socket = new ServerSocket(PORT);
            System.out.println("SERVER STARTED ON PORT: " + PORT);
            socket.accept();
            System.out.println("Smart parking is ONLINE");

            while(true) {
                try {
                    Socket client = socket.accept();
                    ExecutorService es = Executors.newFixedThreadPool(4);
                    es.execute(new ClientThread(client));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}