package org.academiadecodigo.tailormoons.tailordrunks.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class UserInput implements Runnable {
    private PrintStream out;
    private volatile boolean halt = false;
    private Socket socket;


    public UserInput(PrintStream out, Socket socket) {
        this.out = out;
        this.socket = socket;
    }

    @Override
    public void run() {

        Scanner userIn = new Scanner(System.in);
        while (!socket.isClosed()) {

            String temp = userIn.nextLine();
            if (temp.equals("/quit")) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                out.println(temp);
                out.flush();
            }
        }
    }
}


