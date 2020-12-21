package org.academiadecodigo.tailormoons.tailordrunks.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client {


    public static void main(String[] args) throws IOException {

        if(args.length <2) {
            System.out.println("Usage: java -jar tailordrunks.jar <ip address> <port>");
            return;
        }
        String ipAddress = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (final Socket socket = new Socket(ipAddress, portNumber)) {
            BufferedReader socketIn = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            PrintStream socketOut = new PrintStream(socket.getOutputStream());

            UserInput userInput = new UserInput(socketOut, socket);
            Thread userThread = new Thread(userInput);
            userThread.start();

            while (!socket.isClosed()) {

                System.out.println(socketIn.readLine());

            }
        }
    }
}
