package org.academiadecodigo.tailormoons.tailordrunks.server.commands;

import org.academiadecodigo.tailormoons.tailordrunks.server.ClientHandler;
import org.academiadecodigo.tailormoons.tailordrunks.server.Server;

import java.io.IOException;

public class QuitHandler implements CommandHandler{


    @Override
    public void execute(ClientHandler user, String text, Server server) {

        try {
            user.getClientSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
