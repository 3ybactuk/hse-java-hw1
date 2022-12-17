package ru.hse.javaprogramming;

import java.util.SortedSet;
import java.util.TreeSet;

public class Gossipers {
    /**
     * The maximum amount of gossips one gossiper can receive until she gets tired.
     */
    public final int maxMoves;

    /**
     * The maximum amount of gossipers.
     */
    public final int maxGossipers;

    /**
     * The sorted set of gossipers, sorted alphabetically.
     */
    public static SortedSet<Gossiper> gossipersSet = new TreeSet<>(
            (Gossiper g1, Gossiper g2) -> (g1.getName()).compareTo(g2.getName()));

    /**
     * Constructor from maxMoves and maxGossipers.
     * @param maxMoves the maximum amount of gossips one gossiper can receive until she gets tired.
     * @param maxGossipers the maximum amount of gossipers.
     */
    public Gossipers(int maxMoves, int maxGossipers) {
        this.maxMoves = maxMoves;
        this.maxGossipers = maxGossipers;
    }

    /**
     * Find the gossiper by name
     * @param name gossiper's name
     * @return Gossiper object if found, else null
     */
    public Gossiper findByName(String name) {
        for (Gossiper gossiper : gossipersSet) {
            if (name.equals(gossiper.getName())) {
                return gossiper;
            }
        }

        return null;
    }

    /**
     * Create a gossiper instance with chosen name.
     * Possible types:
     * 1. null
     * 2. censor
     * 3. spammer
     * 4. simple
     * 5. deduplicator
     * Max amount of gossipers = 100.
     *
     * @param name gossiper name
     * @param type gossiper type
     */
    public void create(String name, String type) {
        if (name.length() < 1) {
            System.out.println("Error: name cannot be empty.");
            return;
        }

        if (findByName(name) != null) {
            System.out.println("Error: name already taken.");
            return;
        }

        if (gossipersSet.size() >= maxGossipers) {
            System.out.println("Error: maximum amount of gossipers reached (" + maxGossipers + ").");
            return;
        }

        switch (type) {
            case "null" -> gossipersSet.add(new NullGossiper(name, maxMoves));
            case "censor" -> gossipersSet.add(new CensorGossiper(name, maxMoves));
            case "spammer" -> gossipersSet.add(new SpammerGossiper(name, maxMoves));
            case "simple" -> gossipersSet.add(new SimpleGossiper(name, maxMoves));
            case "deduplicator" -> gossipersSet.add(new DeduplicatorGossiper(name, maxMoves));
            default -> {
                System.out.println("Error: unknown type.");
                return;
            }
        }

        System.out.println(name + " successfully created.");
    }

    /**
     * Links or unlinks gossiper-listener from gossiper-spreader
     * @param name1 gossiper-spreader
     * @param name2 gossiper-listener
     * @param isLink true = linking; false = unlinking
     */
    public void linkOrUnlink(String name1, String name2, boolean isLink) {
        Gossiper talker = findByName(name1);
        Gossiper listener = findByName(name2);

        if (talker == null) {
            System.out.println("Error: name \"" + name1 + "\" not found.");
            return;
        }

        if (listener == null) {
            System.out.println("Error: name \"" + name2 + "\" not found.");
            return;
        }

        if (talker == listener) {
            System.out.println("Error: can't link/unlink \"" + name1 + "\" to themselves.");
            return;
        }

        if (isLink) {
            talker.addListener(listener);
        } else {
            talker.removeListener(listener);
        }
    }

    /**
     * Send the message to gossiper by the name
     * @param name gossiper's name
     * @param message message text
     */
    public void message(String name, String message) {
        Gossiper talker = findByName(name);

        if (talker == null) {
            System.out.println("Error: name \"" + name + "\" not found.");
            return;
        }

        if (message.length() < 1) {
            System.out.println("Error: message cannot be empty.");
            return;
        }

        talker.getGossipMessage(message);
    }

    /**
     * Alphabetically output the names of gossipers
     */
    public void gossips() {
        for (Gossiper gossiper : gossipersSet) {
            System.out.println(gossiper.getName());
        }
    }

    /**
     * Alphabetically output the listeners of a gossiper
     * @param name gossiper name
     */
    public void listeners(String name) {
        Gossiper talker = findByName(name);

        if (talker == null) {
            System.out.println("Error: name \"" + name + "\" not found.");
            return;
        }

        for (Gossiper gossiper : talker.listeners) {
            System.out.println(gossiper.getName());
        }
    }
}
