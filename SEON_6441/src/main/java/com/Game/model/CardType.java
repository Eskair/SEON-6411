package com.Game.model;

/**
 * Represents the different types of cards available in the game.
 * Each card type has a unique effect when played.
 */
public enum CardType {
    /** A card that allows a player to destroy an opponent's army. */
    BOMB,

    /** A card that enables a player to fortify a territory, making it immune to attacks for one turn. */
    BLOCKADE,

    /** A card that allows the movement of troops between non-adjacent territories. */
    AIRLIFT,

    /** A card that prevents battles between two players for a turn. */
    NEGOTIATE
}
