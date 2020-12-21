package org.academiadecodigo.tailormoons.tailordrunks.server.deck;

public enum Suits {
    SPADES("S"),
    HEARTS("H"),
    CLUBS("C"),
    DIAMONDS("D");

    private String symbol;

    Suits(String symbol) {
        this.symbol = symbol;
    }
}
