/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package clientapp;

import java.util.Scanner;

/**
 *
 * @author joshu
 */
public class ClientApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Service service = new Service("localhost", 6000);
        service.start();

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter Your Name: ");
        String name = scan.nextLine();

        service.setName(name);
        
        while (true) {
            String message = scan.nextLine();

            service.SendToServer(service.getName() + ": " + message);
        }

    }

}
