package org.academiadecodigo.tailormoons.tailordrunks.server.deck;

import org.academiadecodigo.tailormoons.tailordrunks.server.ClientHandler;

public class Card {

    private Ranks rank;
    private Suits suits;


    public Card(Ranks rank, Suits suits) {
        this.rank = rank;
        this.suits = suits;
    }

    @Override
    public String toString() {
        return "picked: " + rank + " OF " + suits + "\n" + rank.getMessage();
    }

    public Ranks getRank() {
        return rank;
    }
}

