package main.java.com.pavelic.smartparking.threads;



import main.java.com.pavelic.smartparking.server.Server;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;

/**
 * Created by Andrej on 27.2.2017..
 */
public class ClientThread implements Runnable {
    private Socket clientSocket;
    private Server server;

    public ClientThread(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String parkingId = in.readLine();
            String state = in.readLine();

            server.update(Integer.parseInt(parkingId), state);
            server.insertState(Integer.parseInt(parkingId), state);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
