package org.academiadecodigo.tailormoons.tailordrunks.server.commands;

import org.academiadecodigo.tailormoons.tailordrunks.server.ClientHandler;
import org.academiadecodigo.tailormoons.tailordrunks.server.Server;

public class WhisperHandler implements CommandHandler {


    @Override
    public void execute(ClientHandler user, String text, Server server) {


        String[] whisper = text.split(" ");
        String name = whisper[0];
        String whisperMessage = text.substring(name.length() + 1);

        for (ClientHandler player : server.getPLAYERS()) {
            if (player.getName().equals(name)) {
                player.sendMessage("<" + user.getName() + ">" + ": " + whisperMessage);
                return;
            }
        }
        user.sendMessage("Incorrect name, try again.");
    }
}
