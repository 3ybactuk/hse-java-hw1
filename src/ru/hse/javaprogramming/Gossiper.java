package ru.hse.javaprogramming;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Abstract class of Gossiper, defines the basic functionality of a Gossiper.
 */
public abstract class Gossiper {
    /**
     * Gossiper's name
     */
    private final String name;

    /**
     * Gossiper's listeners
     */
    public SortedSet<Gossiper> listeners = new TreeSet<>(
            (Gossiper g1, Gossiper g2) -> (g1.name).compareTo(g2.name));

    /**
     * The maximum amount of gossips one gossiper can receive until gossiper gets tired.
     */
    protected final int maxMoves;

    /**
     * Current amount of received messages.
     */
    protected int currentMessageN = 0;

    /**
     * Constructor from name and maxMoves (messages until tired)
     * @param name gossiper name
     * @param maxMoves messages gossiper may receive until tired
     */
    public Gossiper(String name, int maxMoves) {
        Objects.requireNonNull(name, "name == null");

        this.name = name;
        this.maxMoves = maxMoves;
    }

    /**
     * Name getter
     * @return gossiper's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Check if gossiper is somewhere in chain of listeners of this gossiper
     * @param gossiper gossiper to find in a chain
     * @return true if found else false
     */
    private boolean isACycle(Gossiper gossiper) {
        if (this instanceof NullGossiper || gossiper instanceof NullGossiper) {
            return false;
        }

        for (Gossiper listener : listeners) {
            if (listener.equals(gossiper) || listener.isACycle(gossiper)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds a gossiper to listeners
     * @param gossiper the Gossiper object
     */
    public void addListener(Gossiper gossiper) {
        if (listeners.contains(gossiper)) {
            System.out.println("Error: \"" + gossiper.name + "\" is already listening to \"" + this.name + "\"");
            return;
        }

        if (gossiper.isACycle(this)) {
            System.out.println("Error: making \"" + gossiper.name + "\" a listener of \"" + this.name + "\" will cause an infinite cycle");
            return;
        }

        listeners.add(gossiper);
        System.out.println("\"" + gossiper.name + "\" listens to \"" + this.name + "\"");
    }

    /**
     * Removes a gossiper from listeners
     * @param gossiper the Gossiper object
     */
    public void removeListener(Gossiper gossiper) {
        if (!listeners.contains(gossiper)) {
            System.out.println("Error: \"" + gossiper.name + "\" isn't a listener of \"" + this.name + "\"");
            return;
        }

        listeners.remove(gossiper);
        System.out.println("\"" + gossiper.name + "\" isn't listening to \"" + this.name + "\" anymore");
    }

    /**
     * Prints a tired message containing a gossiper's name.
     */
    public void printTiredMessage() {
        System.out.println(name + " is tired");
    }

    /**
     * Sends a gossip message to listeners
     * @param gossipMessage contains the text of a gossip message
     */
    public void sendMessage(String gossipMessage) {
        for (Gossiper listener : listeners) {
            listener.getGossipMessage(gossipMessage);
        }
    }

    /**
     * Remembers a gossip
     * @param gossipMessage contains the text of a gossip message
     */
    public void getGossipMessage(String gossipMessage) {
        if (currentMessageN < maxMoves) {
            ++currentMessageN;
            doGossipAction(gossipMessage);
        }

        if (currentMessageN >= maxMoves) {
            printTiredMessage();
        }
    }

    /**
     * Prints message with a number of a message
     * @param gossipMessage message
     */
    public void printMessage(String gossipMessage) {
        System.out.println(this.name + ", message number = " + currentMessageN + ", message: \"" + gossipMessage + "\"");
    }


    /**
     * Prints name, number of a message, gossip message.
     * Spreads the gossip to listeners according to gossiper's rules
     */
    public abstract void doGossipAction(String gossipMessage);
}
