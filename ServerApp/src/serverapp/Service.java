/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverapp;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author joshu
 */
public class Service implements Runnable {

    //FIELD
    private ServerSocket serverSocket;
    private Socket incomingClientSocket;
    private Thread t;
    private ArrayList<SocketHandler> clients;

    //CONSTRUCTOR
    public Service(int _port) {
        this.CreateServerSocket(_port);
        this.clients = new ArrayList<>();
    }

    //TCP
    public void CreateServerSocket(int _port) {
        try {
            serverSocket = new ServerSocket(6000);
            System.out.println("SERVER SOCKET OPENED");
        } catch (Exception ex) {
            System.out.println("WARNING: \n" + ex);
        }

    }

    public void Broadcast(String _message, SocketHandler sender) {
        System.out.println("SERVER SENDING MESSAGE\n" + _message);
        for (SocketHandler c : clients) {
            if (c != sender) {
                c.SendToClient(_message);
            }
        }
    }

    //MULTITHREADING   
    public void start() {
        System.out.println("SERVER MULTITHREAD RUNNING");
        if (this.t == null) {
            t = new Thread(this, "SERVER");
            t.start();
        }
    }

    @Override
    public void run() {
        System.out.println("SERVER RUNNING");
        while (true) {
            while (true) {
                try {
                    incomingClientSocket = serverSocket.accept();
                    System.out.println("NEW CLIENT CONNECTED\n" + incomingClientSocket);

                    SocketHandler incomingClient = new SocketHandler(this, incomingClientSocket);
                    incomingClient.start();
                    clients.add(incomingClient);
                } catch (Exception ex) {
                    System.out.println("WARNING: \n" + ex);
                }

            }
        }
    }

}
