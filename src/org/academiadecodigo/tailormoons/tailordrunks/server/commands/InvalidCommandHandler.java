package org.academiadecodigo.tailormoons.tailordrunks.server.commands;

import org.academiadecodigo.tailormoons.tailordrunks.server.ClientHandler;
import org.academiadecodigo.tailormoons.tailordrunks.server.Server;

public class InvalidCommandHandler implements CommandHandler{


    @Override
    public void execute(ClientHandler user, String text, Server server) {

        user.sendMessage("Invalid command. Type /help for a list of commands.");

    }

}
