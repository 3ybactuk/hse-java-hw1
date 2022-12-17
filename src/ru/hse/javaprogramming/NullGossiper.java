package ru.hse.javaprogramming;

/**
 * NullGossiper only receives messages but doesn't spread them.
 */
public class NullGossiper extends Gossiper {

    public NullGossiper(String name, int maxMoves) {
        super(name, maxMoves);
    }

    /**
     * Only prints messages that have been received without spreading them.
     */
    @Override
    public void doGossipAction(String gossipMessage) {
        printMessage(gossipMessage);
    }
}
