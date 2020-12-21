package org.academiadecodigo.tailormoons.tailordrunks.server;

import org.academiadecodigo.tailormoons.tailordrunks.server.deck.Card;
import org.academiadecodigo.tailormoons.tailordrunks.server.deck.Ranks;
import org.academiadecodigo.tailormoons.tailordrunks.server.deck.Suits;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final List<ClientHandler> PLAYERS = new CopyOnWriteArrayList<>();
    private static final LinkedList<Card> deck = new LinkedList<>();

    private ServerSocket serverSocket;
    private int count = 1;
    private int nextPlayer = 1;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() {
        System.out.println("Waiting players on port 8080");
        ExecutorService service = Executors.newFixedThreadPool(10);
        initDeck();

        while (!serverSocket.isClosed() || !(deck.size() == 0)) {

            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, "Player " + count, this);
                count++;

                PLAYERS.add(clientHandler);
                service.execute(clientHandler);
                System.out.println(clientHandler.getName() + " connected!");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void whoIsCurrentPlayer(ClientHandler user) {
        for (ClientHandler player : PLAYERS) {
            if (player.isHalt()) {
                String currentPlayer = player.getName();
                user.sendMessage("=> " + currentPlayer + "'s turn to play! Pick a card.");
            }
        }
    }

    private void initDeck() {
        for (Ranks rank : Ranks.values()) {
            for (Suits suit : Suits.values()) {
                Card card = new Card(rank, suit);
                deck.add(card);
            }
        }
        Collections.shuffle(deck);
    }

    public void broadcast(String message) {
        for (ClientHandler user : PLAYERS) {
            user.sendMessage(message);
        }
    }

    public void broadcast(String message, ClientHandler noUser) {
        for (ClientHandler user : PLAYERS) {
            if (user.equals(noUser)) {
                continue;
            }
            user.sendMessage(message);
        }
    }

    public void echo(ClientHandler me, String message) {
        for (ClientHandler user : PLAYERS) {
            if (me.equals(user)) {
                user.sendMessage(message);
            }
        }
    }

    public Card pickCard() {
        Card pickedCard = deck.getFirst();
        deck.removeFirst();
        System.out.println(deck.getFirst());
        return pickedCard;
    }

    public void whoIsNext() {

        if (nextPlayer == PLAYERS.size()) {
            nextPlayer = 0;
        }

        ClientHandler nextUser = PLAYERS.get(nextPlayer);

        broadcast("=> " + nextUser.getName() + " pick a card!");

        nextUser.setHalt();
        nextPlayer++;
    }

    public boolean isFirst(ClientHandler user) {
        return user == PLAYERS.get(0);
    }

    public void remove(ClientHandler clientHandler) {
        PLAYERS.remove(clientHandler);
    }

    public boolean checkPlayer(String playerName) {
        for (ClientHandler player : PLAYERS) {
            if (playerName.equals(player.getName())) {
                return true;
            }
        }
        return false;
    }

    public List<ClientHandler> getPLAYERS() {
        return PLAYERS;
    }

}
