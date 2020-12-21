package org.academiadecodigo.tailormoons.tailordrunks.server.commands;

import org.academiadecodigo.tailormoons.tailordrunks.server.ClientHandler;
import org.academiadecodigo.tailormoons.tailordrunks.server.Server;

public class HelpHandler implements CommandHandler{


    @Override
    public void execute(ClientHandler user, String text, Server server) {

        user.sendMessage("Commands available are:");
        user.sendMessage(user.getCommands().keySet().toString());

    }

}
