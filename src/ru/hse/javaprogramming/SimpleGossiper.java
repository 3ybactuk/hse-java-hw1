package ru.hse.javaprogramming;

/**
 * SimpleGossiper writes received messages and passes them without any changes.
 */
public class SimpleGossiper extends Gossiper {

    public SimpleGossiper(String name, int maxMoves) {
        super(name, maxMoves);
    }

    /**
     * Writes received messages and passes them without any changes.
     * @param gossipMessage received message.
     */
    @Override
    public void doGossipAction(String gossipMessage) {
        printMessage(gossipMessage);
        sendMessage(gossipMessage);
    }
}
