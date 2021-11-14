package dev._2lstudios.jelly.errors;

public class PlayerOfflineException extends Exception {
    private final String player;

    public PlayerOfflineException(final String player) {
        super("Player " + player + " isn't online.");
        this.player = player;
    }

    public String getPlayer () {
        return this.player;
    }
}
