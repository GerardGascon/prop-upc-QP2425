package edu.upc.prop.scrabble.domain.turns;

/**
 * Interfície que representa l'objecte jugador des de la capa de presentació.
 * Aquesta interfície defineix els mètodes per gestionar el torn d'un jugador dins del joc.
 *
 * @author Gerard Gascón
 */
public interface IGamePlayer {
    /**
     * Inicia el torn del jugador.
     * Aquest mètode es crida quan és el torn del jugador per realitzar una acció (per exemple,
     * col·locar una paraula al tauler). A partir d'aquí, el jugador pot dur a terme les seves accions.
     */
    void startTurn();

    /**
     * Finalitza el torn del jugador.
     * Aquest mètode es crida quan el jugador ha acabat les seves accions durant el torn
     * (per exemple, ha col·locat una paraula). Retorna el resultat del torn, que pot incloure
     * detalls com els punts obtinguts o la validesa del moviment.
     *
     * @see TurnResult
     */
    void endTurn();

    /**
     * Comprova si el jugador està actualment jugant el seu torn.
     * Aquest mètode s'utilitza per saber si el jugador està actiu en el torn o ja l'ha finalitzat.
     *
     * @return <b>true</b> si el jugador està actiu jugant el seu torn, <b>false</b> si ha acabat o encara no ha començat.
     */
    boolean isActive();
}
