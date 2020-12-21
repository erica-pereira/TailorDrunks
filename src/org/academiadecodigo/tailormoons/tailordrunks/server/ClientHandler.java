package org.academiadecodigo.tailormoons.tailordrunks.server;

import org.academiadecodigo.tailormoons.tailordrunks.server.commands.*;
import org.academiadecodigo.tailormoons.tailordrunks.server.deck.Card;
import org.academiadecodigo.tailormoons.tailordrunks.server.deck.Ranks;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private String name;
    private Scanner input;
    private PrintWriter output;
    private Server server;
    private boolean halt = false;
    private Map<String, CommandHandler> commands = new TreeMap<>();

    public ClientHandler(Socket clientSocket, String name, Server server) throws IOException {
        this.clientSocket = clientSocket;
        this.name = name;
        this.server = server;

        InputStream inputStream = clientSocket.getInputStream();
        input = new Scanner(inputStream);
        output = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void init() {
        commands.put("/quit", new QuitHandler());
        commands.put("/list", new ListHandler());
        commands.put("/whisper", new WhisperHandler());
        commands.put("/help", new HelpHandler());
    }


    @Override
    public void run() {

        init();

        sendMessage("Welcome to the Tailor Drunks game! \n" +
                    "For a list of commands type '/help'. \n" +
                    "To pick a card wait your turn and just press ENTER. \n \n" +
                    "If you're feeling sick make sure to reach a toilet... \n" +
                    "Proceed at your own risk!!! \n");

        server.echo(this, "=> What's your drinking name?");
        setName(input.nextLine());
        sendMessage("Hey " + name + ", let's get drunk!");
        server.broadcast(name + " has joined the party!", this);

        if (server.isFirst(this)) {
            halt = true;
        }
        server.whoIsCurrentPlayer(this);

        while (true) {

            if (!input.hasNextLine() || clientSocket.isClosed()) {
                server.broadcast(name + " has left the game! Pussy...");
                server.whoIsNext();
                server.remove(this);
                return;
            }

            String message = input.nextLine();

            if (message.equals("")) {
                if (halt) {
                    Card pickedCard = server.pickCard();
                    server.broadcast(name + " " + pickedCard.toString());
                    checkHasInput(pickedCard);
                    finishTurn();
                }
            }

            String[] words = message.split(" ");
            String command = words[0];
            String text = "";

            if (message.startsWith("/")) {

                if (words.length > 1) {
                    text = message.substring(words[0].length() + 1);
                }

                commands.getOrDefault(command, new InvalidCommandHandler()).execute(this, text, server);
            }
        }
    }


    private void checkHasInput(Card pickedCard) {

        if (pickedCard.getRank().isHasInput()) {

            if(pickedCard.getRank() == Ranks.JACK) {
                sendMessage("=> Choose a tailorMate that needs a break." );

            }else {
                sendMessage("=> Pick who drinks: ");
            }

            boolean isPlayer = false;
            try {
                String playerName = "";
                while (!isPlayer) {
                    playerName = input.nextLine();
                    if (server.checkPlayer(playerName)) {
                        isPlayer = true;
                    } else {
                        sendMessage("=> Are you already drunk? That player does not exist. Try again!");
                    }
                }

                server.broadcast("=> " + playerName + " is the chosen one!");

            } catch (NoSuchElementException ex) {
                server.broadcast("Player disconnect", this);
                server.remove(this);
            }
        }
    }

    private void finishTurn() {
        sendMessage("=> Press ENTER when you're done");
        try {
            String finish = input.nextLine();
            if (finish != null) {
                halt = false;
            }
        } catch (NoSuchElementException ex) {
            server.broadcast("PLAYER DISCONNECTED", this);
            server.remove(this);
        } finally {
            server.whoIsNext();
        }
    }

    public void sendMessage(String message) {
        output.println(message);
    }

    public String getName() {
        return name;
    }

    public Server getServer() {
        return server;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public Map<String, CommandHandler> getCommands() {
        return commands;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void setHalt() {
        halt = true;
    }

    public boolean isHalt() {
        return halt;
    }

}
