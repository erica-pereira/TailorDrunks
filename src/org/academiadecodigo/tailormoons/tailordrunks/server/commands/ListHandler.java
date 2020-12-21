package org.academiadecodigo.tailormoons.tailordrunks.server.commands;

import org.academiadecodigo.tailormoons.tailordrunks.server.ClientHandler;
import org.academiadecodigo.tailormoons.tailordrunks.server.Server;

public class ListHandler implements CommandHandler{


    @Override
    public void execute(ClientHandler user, String text, Server server) {

        user.sendMessage("Player's in game are:");

        for (ClientHandler player: server.getPLAYERS()) {
            user.sendMessage(player.getName());
        }
    }

}
