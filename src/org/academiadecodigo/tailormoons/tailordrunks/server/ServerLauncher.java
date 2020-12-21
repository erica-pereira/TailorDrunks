package org.academiadecodigo.tailormoons.tailordrunks.server;

import java.io.IOException;

public class ServerLauncher {

    private static final int PORT = 8080;

    public static void main(String[] args) {

        try {
            int port = args.length == 0 ? PORT : Integer.parseInt(args[0]);

            Server webServer = new Server(port);
            webServer.start();

        } catch (NumberFormatException | IOException e) {
            System.out.println("Invalid port number: " + args[0]);
        }
    }
}
