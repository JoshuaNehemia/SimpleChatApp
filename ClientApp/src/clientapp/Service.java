/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joshu
 */
public class Service implements Runnable {

    //FIELD 
    private Socket socket;
    private BufferedReader received;
    private DataOutputStream send;
    private String name;
    private Thread t;

    //CONSTRUCTOR
    public Service(String _serverAddress, int _serverPort) {
        try {
            this.setSocket(new Socket(_serverAddress, _serverPort));
        } catch (Exception ex) {
            System.out.println("WARNING: \n" + ex);
        }
    }

    //GETTER AND SETTER
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null || name != "") {
            this.name = name;
        } else {
            return;
        }
    }

    public BufferedReader getReceived() {
        return received;
    }

    public void setReceived(BufferedReader received) {
        this.received = received;
    }

    public DataOutputStream getSend() {
        return send;
    }

    public void setSend(DataOutputStream send) {
        this.send = send;
    }

    //MULTITHREADING
    public void start() {
        if (this.t == null) {
            t = new Thread(this, "CLIENT");
            t.start();
        }
    }

    @Override
    public void run() {
        System.out.println("CONNECTED TO SERVER");
        this.ReceivedFromServer(); 
            try {
                while (true) {
                    String message = received.readLine();
                    System.out.println(message);
                }
            } catch (Exception ex) {
                System.out.println("WARNING: \n" + ex);
            }
        
    }

    //METHOD
    public void SendToServer(String _message) {
        try {
            setSend(new DataOutputStream(this.getSocket().getOutputStream()));
            send.writeBytes(_message + "\n");
        } catch (Exception ex) {
            System.out.println("WARNING: \n" + ex);
        }
    }

    public void ReceivedFromServer() {
        try {
            setReceived(new BufferedReader(new InputStreamReader(this.getSocket().getInputStream())));
        } catch (Exception ex) {
            System.out.println("WARNING: \n" + ex);
        }

    }
}
