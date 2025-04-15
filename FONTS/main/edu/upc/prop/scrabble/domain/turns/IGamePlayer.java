package edu.upc.prop.scrabble.domain.turns;

/**
 * Interface to represent the player object from the presentation layer
 * @author Gerard Gascón
 */
public interface IGamePlayer {
    /**
     * Start the player's turn
     */
    void startTurn();

    /**
     * End the player's turn
     * @return The action made by the player
     * @see TurnResult
     */
    TurnResult endTurn();

    /**
     * Check if the player is in a turn or not
     * @return <b>true</b> if the player is in a turn<br>
     * <b>false</b> if the player is not in a turn
     */
    boolean isActive();
}
