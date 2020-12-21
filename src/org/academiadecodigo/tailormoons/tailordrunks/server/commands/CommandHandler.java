package org.academiadecodigo.tailormoons.tailordrunks.server.commands;

import org.academiadecodigo.tailormoons.tailordrunks.server.ClientHandler;
import org.academiadecodigo.tailormoons.tailordrunks.server.Server;

public interface CommandHandler {

    public void execute(ClientHandler user, String text, Server server);
}
