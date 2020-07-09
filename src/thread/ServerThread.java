/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import controller.Controller;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 *
 * @author marko
 */
public class ServerThread extends Thread {

    private final ServerSocket serverSocket;

    public ServerThread() throws IOException {
        try (FileInputStream input = new FileInputStream("socket.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            Integer port = Integer.parseInt(properties.getProperty("port"));
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            System.out.println("Waiting client");
            try {
                Socket socket = serverSocket.accept();
                ClientThread thread = new ClientThread(socket);
                Controller.getInstance().getClients().add(thread);
                thread.start();

                System.out.println("Client connected");
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
        }

        stopAllThreads();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    private void stopAllThreads() {
        for (ClientThread client : Controller.getInstance().getClients()) {
            try {
                client.getSocket().close();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
        }
    }
}
