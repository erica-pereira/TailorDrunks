package org.academiadecodigo.tailormoons.tailordrunks.server.deck;

public enum Ranks {
    ACE("Now it's time to Dance! Get up, and give us your best moves until the music stops!", false),
    TWO("You have to drink 2 sips!", false),
    THREE("Choose a TailorMate to drink 3 sips!", true),
    FOUR("You have to drink 4 sips!", false),
    FIVE("Choose a TailorMate to drink 5 sips!", true),
    SIX("You have to drink 6 sips!", false),
    SEVEN("Choose a TailorMate to drink 7 sips!", true),
    EIGHT("Bizz Buzz time!", true),
    NINE("Question game time!", true),
    TEN("Choose an animal, everyone else must imitate it. The worst imitator drinks!", true),
    JACK("We all drink but one, choose who gets a break!", true),
    QUEEN("Who never ever...  drinks a sip!", true),
    KING("We all drink! Cheers!", false);

    private final String message;
    private final boolean hasInput;

    Ranks(String message, boolean hasInput) {
        this.message = message;
        this.hasInput = hasInput;
    }

    public boolean isHasInput() {
        return hasInput;
    }

    public String getMessage() {
        return message;
    }
}
