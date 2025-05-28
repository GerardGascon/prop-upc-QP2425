package edu.upc.prop.scrabble.domain.turns;


import static edu.upc.prop.scrabble.domain.turns.TurnResult.Skip;

/**
 * Classe encarregada de gestionar el flux de torns en una partida.
 * S'encarrega de controlar quin jugador finalitza i quin comença el torn,
 * a més de portar el recompte de torns i comprovar si la partida ha acabat.
 *
 * @author Biel Pérez
 */
public class Turn {
    /**
     * Atribut que s'encarrega d'avaluar si la partida ha acabat.
     * @see Endgame
     */
    private final Endgame endgame;
    /**
     * Array de jugadors que gestiona l'ordre dels torns.
     * @see IGamePlayer
     */
    private final IGamePlayer[] players;
    /**
     * Comptador que indica el número actual del torn.
     */
    private int turnNumber;
    /**
     * Comptador dels skips consecutius realitzats durant la partida.
     */
    private int skipCounter;

    /**
     * Constructora de la classe Turn.
     *
     * @param endgame Lògica per determinar si la partida ha finalitzat.
     * @param players Jugadors participants a la partida.
     */
    public Turn(Endgame endgame, IGamePlayer[] players) {
        this.endgame = endgame;
        this.players = players;
        this.turnNumber = 0;
        this.skipCounter = 0;
    }

    /**
     * Gestiona el canvi de torns entre jugadors.
     * <ul>
     * <li> Finalitza el torn del jugador actual.
     * <li> Inicia el torn del següent jugador.
     * <li> Controla el nombre de torns consecutius que s'han passat.
     * <li> Determina si la partida ha de finalitzar segons la lògica de l'Endgame.
     * </ul>
     * @param result Resultat del torn (ex: Skip si el jugador ha passat).
     * @return true si la partida ha acabat, false altrament.
     *
     * @see Endgame
     * @see IGamePlayer
     */
    public boolean run(TurnResult result) {
        int endTurn = turnNumber % players.length;
        int startTurn = (turnNumber+1) % players.length;
        players[endTurn].endTurn();

        if (result == Skip)
            skipCounter++;
        else
            skipCounter = 0;

        boolean ended = endgame.run(skipCounter);
        if (ended)
            return true;

        players[startTurn].startTurn();
        turnNumber = turnNumber + 1;
        return false;
    }
}
