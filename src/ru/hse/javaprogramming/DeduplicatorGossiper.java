package ru.hse.javaprogramming;

import java.util.HashSet;
import java.util.Set;

/**
 * DeduplicatorGossiper only sends and receives messages that weren't received before.
 */
public class DeduplicatorGossiper extends Gossiper {
    /**
     * A set containing only unique messages.
     */
    protected Set<String> gossipMessages = new HashSet<>();

    public DeduplicatorGossiper(String name, int maxMoves) {
        super(name, maxMoves);
    }

    /**
     * Remembers the gossip and only prints and spreads it if it weren't known before.
     * @param gossipMessage contains the text of a gossip message.
     */
    @Override
    public void getGossipMessage(String gossipMessage) {
        if (gossipMessages.add(gossipMessage)) {
            ++currentMessageN;
            doGossipAction(gossipMessage);
        }
    }

    /**
     * Prints and spreads the gossips that weren't encountered before.
     * @param gossipMessage contains the text of a gossip message.
     */
    @Override
    public void doGossipAction(String gossipMessage) {
        printMessage(gossipMessage);
        sendMessage(gossipMessage);
    }

}
