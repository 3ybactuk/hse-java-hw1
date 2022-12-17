package ru.hse.javaprogramming;

import java.util.Random;

/**
 * SpammerGossiper writes received message and sends it from 2 to 5 times
 */
public class SpammerGossiper extends Gossiper {
    private final Random rand = new Random();

    public SpammerGossiper(String name, int maxMoves) {
        super(name, maxMoves);
    }

    /**
     * Generates random integer in range [min; max]
     * @param min left border
     * @param max right border
     * @return random integer in range [min; max]
     */
    private int randInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * Prints received message and spreads it 2 to 5 times.
     * @param gossipMessage received message
     */
    @Override
    public void doGossipAction(String gossipMessage) {
        printMessage(gossipMessage);

        for (int i = 0; i < randInt(2, 5); i++) {
            sendMessage(gossipMessage);
        }
    }
}
