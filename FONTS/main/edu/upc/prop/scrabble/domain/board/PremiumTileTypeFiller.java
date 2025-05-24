package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.PremiumTileType;

/**
 * Classe que s'encarrega de "omplir" la vista del tauler amb la informació
 * sobre les caselles premiades (premium) del tauler.
 * <p>
 * Aquesta classe recorre el tauler i actualitza la vista perquè mostri
 * correctament els tipus de caselles prèmium, com doble paraula, triple lletra, etc.
 * <p>
 * També estableix la casella central com a DoubleWord (doble paraula).
 *
 * @author Gerard Gascón
 */
public class PremiumTileTypeFiller {
    /** El tauler del joc */
    private final Board board;
    /** La interfície que representa la vista del tauler */
    private final IBoard view;

    /**
     * Constructor que inicialitza la classe amb el tauler i la vista.
     *
     * @param board El tauler on es volen obtenir les caselles premium
     * @param view La vista que cal actualitzar amb la informació del tauler
     */
    public PremiumTileTypeFiller(Board board, IBoard view) {
        this.board = board;
        this.view = view;
    }

    /**
     * Recorre totes les posicions del tauler i actualitza la vista
     * posant el tipus de casella premium corresponent a cada posició.
     * <p>
     * També estableix la casella central com a casella de doble paraula.
     */
    public void run() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.isPremiumTile(i, j)) {
                    view.setPremiumTile(board.getPremiumTileType(i, j), i, j);
                }
            }
        }

        view.setPremiumTile(PremiumTileType.DoubleWord, board.getSize() / 2, board.getSize() / 2);
    }
}
