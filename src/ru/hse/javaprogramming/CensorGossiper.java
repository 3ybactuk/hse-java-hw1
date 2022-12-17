package ru.hse.javaprogramming;

/**
 * CensorGossiper writes all the received messages, but passes only messages containing word "java".
 */
public class CensorGossiper extends Gossiper {

    public CensorGossiper(String name, int maxMoves) {
        super(name, maxMoves);
    }

    /**
     * Prints name, number of a gossip, gossip message.
     * Only spreads gossips that contain "Java" in any case
     */
    @Override
    public void doGossipAction(String gossipMessage) {
        printMessage(gossipMessage);
        if (gossipMessage.toLowerCase().contains("java")) {
            sendMessage(gossipMessage);
        }
    }

}
