package edu.upc.prop.scrabble.domain.ai;

import edu.upc.prop.scrabble.data.Anchors;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.dawg.Node;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.Map;

/**
 * Intel·ligència artificial que computa el moviment a realitzar
 * pel jugador controlat per la màquina i partides on la llengua seleccionada
 * és l'anglès.
 * @see Player
 * @see Board
 * @see Anchors
 * @see CrossChecks
 * @see Movement
 * @see PointCalculator
 * @see PiecesConverter
 * @see DAWG
 * @author Albert Usero
 * @author Felipe Martínez
 */
public class EnglishAI extends AI {

    /**
     * Constructor per a la implementació d'IA en anglès.
     * <p>
     * Inicialitza els components necessaris perquè la IA pugui calcular moviments
     * vàlids en partides amb l'idioma anglès. Hereta la funcionalitat bàsica
     * de la classe pare AI.
     *
     * @param piecesConverter  Convertidor de peces per a la gestió de fitxes
     * @param pointCalculator  Calculador de puntuacions per a moviments
     * @param dawg             Estructura de dades DAWG que conté el diccionari anglès
     * @param board            Tauler actual del joc
     * @param bot              Jugador controlat per la IA
     * @param anchors          Gestor d'àncores per a posicionament de paraules
     * @param crossChecks      Verificador de creuaments vàlids entre paraules
     * @see AI
     * @see PiecesConverter
     * @see PointCalculator
     * @see DAWG
     * @see Board
     * @see Player
     * @see Anchors
     * @see CrossChecks
     */
    public EnglishAI(PiecesConverter piecesConverter, PointCalculator pointCalculator, DAWG dawg, Board board, Player bot, Anchors anchors, CrossChecks crossChecks) {
        super(piecesConverter, pointCalculator, dawg, board, bot, anchors, crossChecks);
    }

    /**
     * Cap treball en aquesta subclasse
     * @param partialWord Tros de la paraula actual
     * @param limit Com de lluny podem anar
     * @param entry Caràcter/node següent que estem comprovant
     */
    @Override
    protected void processLeftPartSpecialPieces(String partialWord, int limit, Map.Entry<Character, Node> entry) {
    }

    /**
     * Comprova que no hi ha cap combinació il·legal i afegeix la lletra utilitzada
     * @param partialWord Tros de la paraula actual
     * @param limit Com de lluny podem anar
     * @param entry Caràcter/node següent que estem comprovant
     * @param usedPiece Peça utilitzada a la iteració actual
     */
    @Override
    protected void processNextLeftPiece(String partialWord, int limit, Map.Entry<Character, Node> entry, Piece usedPiece) {
        if(usedPiece.isBlank()) goToNextLeftPiece(partialWord + Character.toLowerCase(entry.getKey()), entry.getValue(), limit, usedPiece);
        else goToNextLeftPiece(partialWord + entry.getKey(), entry.getValue(), limit, usedPiece);
    }

    /**
     * Estén cap a la dreta fent servir una peça de la mà
     * @param partialWord Tros de paraula actual
     * @param cell Casella sobre la qual estem estenent
     * @param entry Caràcter/Node següent que estem comprovant
     * @param usedPiece Peça utilitzada
     */
    @Override
    protected void extendToNextNewPieceRight(String partialWord, Vector2 cell, Map.Entry<Character, Node> entry, Piece usedPiece) {
        if(usedPiece.isBlank()) goToNextRightPiece(partialWord + Character.toLowerCase(entry.getKey()), entry.getValue(), cell, usedPiece);
        else goToNextRightPiece(partialWord + entry.getKey(), entry.getValue(), cell, usedPiece);
    }

    /**
     * Estén cap a la dreta amb una peça ja col·locada al tauler
     * @param partialWord Tros de paraula actual
     * @param node Node que referencia a la peça ja col·locada
     * @param cell Casella sobre la qual estem estenent
     * @param placedPiece Peça ja al tauler
     */
    @Override
    protected void extendToNextExistingPieceRight(String partialWord, Node node, Vector2 cell, Piece placedPiece) {
        Node nextNode = node.getSuccessor(placedPiece.letter().charAt(0));
        if(nextNode != null) ExtendRight(partialWord + placedPiece.letter(), nextNode, cell);
    }

    /**
     * Cap treball en aquesta subclasse
     * @param partialWord Tros de la paraula actual
     * @param cell Posició a la qual ens trobem en expansió
     * @param entry Caràcter/node següent que estem comprovant
     */
    @Override
    protected void processRightPartSpecialPieces(String partialWord, Vector2 cell, Map.Entry<Character, Node> entry) {
    }

    /**
     * Validem si quan estenem la paraula actual no fem invàlides
     * les paraules presents al board
     * @param cell Casella on comença la paraula
     * @param board Tauler actual
     * @param c Caràcter a afegir
     * @return Cert si la paraula és vàlida
     */
    @Override
    protected boolean validExistingWord(Vector2 cell, Board board, char c) {
        String adjWord = String.valueOf(c);
        int i = 1;
        while(board.isCellValid(cell.x, cell.y - i) && !board.isCellEmpty(cell.x, cell.y - i)) {
            adjWord = board.getCellPiece(cell.x, cell.y - i) + adjWord;
            ++i;
        }
        i = 0;
        while(board.isCellValid(cell.x, cell.y + i) && !board.isCellEmpty(cell.x, cell.y - i)) {
            adjWord = adjWord + board.getCellPiece(cell.x, cell.y + i);
            ++i;
        }
        Node aux = getFinalNode(adjWord);
        return aux != null && aux.isEndOfWord();
    }
}