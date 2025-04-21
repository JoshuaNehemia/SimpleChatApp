/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverapp;

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
public class SocketHandler extends Thread {

    //FIELD
    private Service parent;
    private Socket socket;
    private BufferedReader received;
    private DataOutputStream send;

    //CONSTRUCTOR
    public SocketHandler(Service _parent, Socket _clientSocket) {
        this.setParent(_parent);
        this.setSocket(_clientSocket);
    }

    //GETTER_AND_SETTER
    public Service getParent() {
        return parent;
    }

    public final void setParent(Service parent) {
        if (parent != null) {
            this.parent = parent;
        } else {
            System.out.println("WARNING:\nParent is null");
            return;
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public final void setSocket(Socket socket) {
        if (socket != null) {
            this.socket = socket;
        } else {
            System.out.println("WARNING:\nSocket is null");
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

    //METHOD
    @Override
    public void run() {
        System.out.println("READY TO RECEIVED FROM CLIENT");
        this.ReceivedFromClient();
        try {
            while (true) {
                String message = this.getReceived().readLine();
                System.out.println("RECEIVED:\n" + message + "\nFROM:\n" + this.getSocket());
                this.parent.Broadcast(message, this);
            }
        } catch (Exception ex) {
            System.out.println("WARNING: \n" + ex);
        }
    }

    public void SendToClient(String _message) {
        try {
            this.setSend(new DataOutputStream(this.getSocket().getOutputStream()));
            this.getSend().writeBytes(_message + "\n");
            System.out.println("SENT:\n" + _message + "\nTO:\n" + this.getSocket());
        } catch (Exception ex) {
            System.out.println("WARNING: \n" + ex);
        }
    }

    public void ReceivedFromClient() {
        try {
            this.setReceived(new BufferedReader(new InputStreamReader(this.getSocket().getInputStream())));
        } catch (Exception ex) {
            System.out.println("WARNING: \n" + ex);
        }
    }
}
