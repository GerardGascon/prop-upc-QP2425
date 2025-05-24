package edu.upc.prop.scrabble.domain.ai;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.dawg.Node;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;

/**
 * Classe per actualitzar els CrossChecks
 * @author Albert Usero
 * @author Felipe Martínez
 * @see CrossChecks
 */
public class CrossCheckUpdater {
    /**
     * CrossChecks a actualitzar
     * @see CrossChecks
     */
    private final CrossChecks crossChecks;
    /**
     * Board sobre la qual estan definits els crosschecks
     * @see Board
     */
    private final Board board;
    /**
     * Validador de paraules que servirà per actualizar els CrossChecks
     * @see WordValidator
     */
    private final WordValidator wordValidator;
    /**
     * DAWG necessari de cara al funcionament del validador de paraules
     * @see DAWG
     */
    private final DAWG dawg;
    /**
     * Convertidor de peces
     * @see PiecesConverter
     */
    private final PiecesConverter piecesConverter;

    /**
     * Crea un nou actualitzador de CrossChecks
     * @param pc Convertidor de peces
     * @param crossChecks Crosschecks a actualitzar
     * @param board Tauler sobre el qual s'han creat els crosschecks
     * @param dawg DAWG necessari per crear el word validator
     * @see Board
     * @see DAWG
     * @see CrossChecks
     * @see PiecesConverter
     */
    public CrossCheckUpdater(PiecesConverter pc, CrossChecks crossChecks, Board board, DAWG dawg) {
        this.crossChecks = crossChecks;
        this.board = board;
        this.dawg = dawg;
        this.wordValidator = new WordValidator(dawg);
        this.piecesConverter = pc;
    }

    /**
     * Executa l'actualitzador de CrossChecks i s'actualitzen
     * en funció del moviment realitzat.
     * @param move Moviment realitzat sobre el qual s'han d'actualitzar els CrossChecks
     * @see Movement
     */
    public void run(Movement move) {
        if (move.direction() == Direction.Horizontal)
            calculateHorizontalCrossChecks(move);
        else
            calculateVerticalCrossChecks(move);
    }

    /**
     * Calcula els CrossChecks que s'han creat horitzontalment en conseqüència
     * d'un moviment en horitzontal.
     * @param move Moviment realitzat amb direcció horitzontal
     * @see Movement
     * @see CrossChecks
     */
    private void calculateHorizontalCrossChecks(Movement move) {
        int beginningOfAddedWord = move.x();
        Piece[] size = piecesConverter.run(move.word());
        int endOfAddedWord = move.x() + size.length - 1;

        calculateHorizontalBeginCrossCheck(move, beginningOfAddedWord);
        calculateHorizontalEndCrossCheck(move, endOfAddedWord);
    }

    /**
     * Serveix per determinar quan s'ha de processar un crosscheck d'una casella
     * per saber si és vàlida o si ja té una peça.
     * @param x Posició x de la casella
     * @param y Posició y de la casella
     * @return True si la casella és vàlida i està buida, False altrament
     */
    private boolean canProcessCell(int x, int y) {
        return board.isCellValid(x, y) && board.isCellEmpty(x, y);
    }

    /**
     * Actualitza la casella (en cas que sigui vàlida) immediatament a la dreta
     * del moviment realitzat.
     * @param move Moviment realitzat en horitzontal
     * @param endOfAddedWord Coordenada x final del moviment realitzat
     * @see Movement
     */
    private void calculateHorizontalEndCrossCheck(Movement move, int endOfAddedWord) {
        if (!canProcessCell(endOfAddedWord + 1, move.y()))
            return;

        Node finalNode = getFinalNode(move.word().toUpperCase());
        for (int j = 0; j < crossChecks.getLetters().length; ++j) {
            if (!isNextNodeTerminal(finalNode, crossChecks.getLetters()[j])) {
                crossChecks.setCrossCheck(endOfAddedWord + 1, move.y(), j);
            }
        }
    }

    /**
     * Actualitza la casella (en cas que sigui vàlida) immediatament a l'esquerra
     * del moviment realitzat.
     * @param move Moviment realitzat en horitzontal
     * @param beginningOfAddedWord Coordenada x inicial del moviment realitzat
     * @see Movement
     */
    private void calculateHorizontalBeginCrossCheck(Movement move, int beginningOfAddedWord) {
        if (!canProcessCell(beginningOfAddedWord - 1, move.y()))
            return;

        String[] wordInBoard = new String[2];
        wordInBoard[1] = move.word();

        for (int i = 0; i < crossChecks.getLetters().length; ++i) {
            wordInBoard[0] = crossChecks.getLetters()[i];
            String word = "";
            for (String s : wordInBoard)
                word = word.concat(s);
            if (!wordValidator.run(word)) {
                crossChecks.setCrossCheck(beginningOfAddedWord - 1, move.y(), i);
            }
        }
    }

    /**
     * Calcula els CrossChecks que s'han creat verticalment en conseqüència
     * d'un moviment en vertical.
     * @param move Moviment realitzat amb direcció vertical
     * @see Movement
     */
    private void calculateVerticalCrossChecks(Movement move) {
        int beginningOfAddedWord = move.y();
        Piece[] size = piecesConverter.run(move.word());
        int endOfAddedWord = move.y() + size.length - 1;
        calculateVerticalBeginCrossCheck(move, beginningOfAddedWord);
        calculateVerticalEndCrossCheck(move, endOfAddedWord);
    }

    /**
     * Actualitza la casella (en cas que sigui vàlida) immediatament a baix
     * del moviment realitzat.
     * @param move Moviment realitzat en vertical
     * @param endOfAddedWord Coordenada y final del moviment realitzat
     * @see Movement
     */
    private void calculateVerticalEndCrossCheck(Movement move, int endOfAddedWord) {
        if (!canProcessCell(move.x(), endOfAddedWord + 1))
            return;

        Node finalNode = getFinalNode(move.word().toUpperCase());
        for (int j = 0; j < crossChecks.getLetters().length; ++j) {
            if (!isNextNodeTerminal(finalNode, crossChecks.getLetters()[j])) {
                crossChecks.setCrossCheck(move.x(), endOfAddedWord + 1, j);
            }
        }
    }

    /**
     * Actualitza la casella (en cas que sigui vàlida) immediatament a sobre
     * del moviment realitzat.
     * @param move Moviment realitzat en vertical
     * @param beginningOfAddedWord Coordenada y inicial del moviment realitzat
     * @see Movement
     */
    private void calculateVerticalBeginCrossCheck(Movement move, int beginningOfAddedWord) {
        if (!canProcessCell(move.x(), beginningOfAddedWord - 1))
            return;

        //+1 pq hay que hacer hueco al principio para la letra a probar si va
        String[] wordInBoard = new String[2];
        wordInBoard[1] = move.word();

        for (int i = 0; i < crossChecks.getLetters().length; ++i) {
            wordInBoard[0] = crossChecks.getLetters()[i];
            String word = "";
            for (String s : wordInBoard)
                word = word.concat(s);

            if (!wordValidator.run(word)) {
                crossChecks.setCrossCheck(move.x(), beginningOfAddedWord - 1, i);
            }
        }
    }

    /**
     * @param word Paraula sobre la qual volem obtenir el node final
     * @return Node que representa el caràcter final de la paraula donada.
     * @see Node
     */
    private Node getFinalNode(String word) {
        Node current = dawg.getRoot();
        int i = 0;
        while (i < word.length() && current != null) {
            current = current.getSuccessor(word.charAt(i));
            ++i;
        }

        return current;
    }

    /**
     * Serveix per donat l'últim node d'una paraula la qual ja està al tauler
     * comprovar si afegint una peça darrere la paraula continua sent vàlida.
     * @param node Node actual sobre el qual volem fer l'extensió
     * @param piece Peça que afegirem
     * @return True si el següent (o últim dels següents, peces especials amb més
     * d'un caràcter) node és final de paraula, False altrament.
     * @see Node
     */
    private boolean isNextNodeTerminal(Node node, String piece) {
        if (node == null) return false;
        Node current = node;
        for (int i = 0; i < piece.length(); i++) {
            Node successor = current.getSuccessor(piece.charAt(i));
            if (successor == null) return false;
            current = successor;
        }
        return current.isEndOfWord();
    }
}