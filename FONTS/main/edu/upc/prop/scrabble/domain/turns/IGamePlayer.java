package edu.upc.prop.scrabble.domain.turns;

/**
 * Interface to represent the player object from the presentation layer.
 * This interface defines the methods for managing a player's turn in the game.
 *
 * @author Gerard Gascón
 */
public interface IGamePlayer {
    /**
     * Start the player's turn.
     * This method is called when it is the player's turn to take an action (e.g., play a word on the board).
     * The player can now perform actions during their turn.
     */
    void startTurn();

    /**
     * End the player's turn.
     * This method is called when the player finishes their actions for the turn (e.g., places a word).
     * It returns the result of the turn, which could include details like points scored or the validity of the move.
     *
     * @return The action made by the player, encapsulated in a TurnResult object.
     * @see TurnResult
     */
    void endTurn();

    /**
     * Check if the player is currently in a turn or not.
     * This method is used to determine if the player is actively playing their turn.
     *
     * @return <b>true</b> if the player is in a turn, <b>false</b> if the player has completed their turn or is not yet playing.
     */
    boolean isActive();
}
