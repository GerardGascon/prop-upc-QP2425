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
 * és el català.
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
public class CatalanAI extends AI {
    public CatalanAI(PiecesConverter piecesConverter, PointCalculator pointCalculator, DAWG dawg, Board board, Player bot, Anchors anchors, CrossChecks crossChecks) {
        super(piecesConverter, pointCalculator, dawg, board, bot, anchors, crossChecks);
    }

    /**
     * Tracta casos especials cap a l'esquerra (NY, L·L)
     * @param partialWord Tros de la paraula actual
     * @param limit Com de lluny podem anar
     * @param entry Caràcter/node següent que estem comprovant
     * @see DAWG
     * @see Node
     */
    @Override
    protected void processLeftPartSpecialPieces(String partialWord, int limit, Map.Entry<Character, Node> entry) {
        char c = entry.getKey();
        Node nextNode;
        Piece usedPiece;
        switch (c) {
            case 'N':
                nextNode = entry.getValue().getSuccessor('Y');
                usedPiece = bot.hasPiece("NY");
                if(nextNode != null && usedPiece != null) {
                    if(usedPiece.isBlank()) goToNextLeftPiece(partialWord + "ny", nextNode, limit, usedPiece);
                    else goToNextLeftPiece(partialWord + "NY", nextNode, limit, usedPiece);
                }
                break;

            case 'L':
                nextNode = entry.getValue().getSuccessor('·');
                usedPiece = bot.hasPiece("L·L");
                if(nextNode != null && usedPiece != null) {
                    if(usedPiece.isBlank()) goToNextLeftPiece(partialWord + "l·l", nextNode, limit, usedPiece);
                    else goToNextLeftPiece(partialWord + "L·L", nextNode.getSuccessor('L'), limit, usedPiece);
                }
                break;
        }
    }

    /**
     * Comprova que no hi ha cap combinació il·legal (NY, L·L)
     * @param partialWord Tros de la paraula actual
     * @param limit Com de lluny podem anar
     * @param entry Caràcter/node següent que estem comprovant
     * @param usedPiece Peça utilitzada a la iteració actual
     * @see DAWG
     * @see Node
     */
    @Override
    protected void processNextLeftPiece(String partialWord, int limit, Map.Entry<Character, Node> entry, Piece usedPiece) {
        char lastLetter = ' ';
        if(!partialWord.isEmpty()) lastLetter = partialWord.charAt(partialWord.length() - 1);
        if((((lastLetter != 'N') && (lastLetter != 'n')) || entry.getKey() != 'Y') &&
        (((lastLetter != 'L')&&(lastLetter != 'l'))|| entry.getKey() != '·')) {
            if(usedPiece.isBlank()) goToNextLeftPiece(partialWord + Character.toLowerCase(entry.getKey()), entry.getValue(), limit, usedPiece);
            else goToNextLeftPiece(partialWord + entry.getKey(), entry.getValue(), limit, usedPiece);
        }
    }

    /**
     * Estén cap a la dreta fent servir una peça de la mà i comprova casos especials (NY, L·L)
     * @param partialWord Tros de paraula actual
     * @param cell Casella sobre la qual estem estenent
     * @param entry Caràcter/Node següent que estem comprovant
     * @param usedPiece Peça utilitzada
     */
    @Override
    protected void extendToNextNewPieceRight(String partialWord, Vector2 cell, Map.Entry<Character, Node> entry, Piece usedPiece) {
        char lastLetter = ' ';
        if(!partialWord.isEmpty()) lastLetter = partialWord.charAt(partialWord.length() - 1);
        if((((lastLetter != 'N') && (lastLetter != 'n')) || entry.getKey() != 'Y') &&
        (((lastLetter != 'L')&&(lastLetter != 'l'))|| entry.getKey() != '·')){
            if(usedPiece.isBlank()) goToNextRightPiece(partialWord + Character.toLowerCase(entry.getKey()), entry.getValue(), cell, usedPiece);
            else goToNextRightPiece(partialWord + entry.getKey(), entry.getValue(), cell, usedPiece);
        }
    }

    /**
     * Estén cap a la dreta amb una peça ja col·locada al tauler i comprova casos especials (NY, L·L)
     * @param partialWord Tros de paraula actual
     * @param node Node que referencia a la peça ja col·locada
     * @param cell Casella sobre la qual estem estenent
     * @param placedPiece Peça ja al tauler
     */
    @Override
    protected void extendToNextExistingPieceRight(String partialWord, Node node, Vector2 cell, Piece placedPiece) {
        char lastLetter = ' ';
        if(!partialWord.isEmpty()) lastLetter = partialWord.charAt(partialWord.length() - 1);
        String placedLetter = placedPiece.letter();
        Node nextNode = node.getSuccessor(placedPiece.letter().charAt(0));
        if (nextNode == null)
            return;
        if (placedLetter.length() == 1) {
                if((((lastLetter != 'N') && (lastLetter != 'n')) || !placedLetter.equals("Y")) &&
                (((lastLetter != 'L')&&(lastLetter != 'l'))|| !placedLetter.equals("·"))){
                ExtendRight(partialWord + placedLetter, nextNode, cell);
            }
        }
        else {
            for (int i = 1; i < placedLetter.length() && nextNode != null; i++) {
                nextNode = nextNode.getSuccessor(placedPiece.letter().charAt(i));
            }
            if (nextNode != null) ExtendRight(partialWord + placedLetter, nextNode, cell);

        }
    }

    /**
     * Tracta casos especials cap a la dreta (NY, L·L)
     * @param partialWord Tros de la paraula actual
     * @param cell Posició a la qual ens trobem en expansió
     * @param entry Caràcter/node següent que estem comprovant
     * @see DAWG
     * @see Node
     */
    @Override
    protected void processRightPartSpecialPieces(String partialWord, Vector2 cell, Map.Entry<Character, Node> entry) {
        char c = entry.getKey();
        Node nextNode;
        Piece usedPiece ;
        switch (c) {
            case 'N':
                nextNode = entry.getValue().getSuccessor('Y');
                usedPiece = bot.hasPiece("NY");
                if(nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, "NY")) {
                    if(nextNode.isEndOfWord()) {
                        if(usedPiece.isBlank()) checkWord(partialWord + "ny", cell);
                        else checkWord(partialWord + "NY", cell);
                    }
                    if(usedPiece.isBlank()) goToNextRightPiece(partialWord + "ny", nextNode, cell, usedPiece);
                    else goToNextRightPiece(partialWord + "NY", nextNode, cell, usedPiece);
                }
                break;

            case 'L':
                nextNode = entry.getValue().getSuccessor('·');
                if(nextNode != null) nextNode = nextNode.getSuccessor('L');
                usedPiece = bot.hasPiece("L·L");
                if(nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, "L·L")) {
                    if(nextNode.isEndOfWord()) {
                        if(usedPiece.isBlank()) checkWord(partialWord + "l·l", cell);
                        else checkWord(partialWord + "L·L", cell);
                    }
                    if(usedPiece.isBlank()) goToNextRightPiece(partialWord + "l·l", nextNode, cell, usedPiece);
                    else goToNextRightPiece(partialWord + "L·L", nextNode, cell, usedPiece);
                }
                break;
        }
    }

    /**
     * Validem si quan estenem la paraula actual no fem invàlides
     * les paraules presents al board
     * @param cell Casella on comença la paraula
     * @param board Tauler actual
     * @param c Caràcter a afegir
     * @return Cert si la paraula és vàlida
     */
    protected boolean validExistingWord(Vector2 cell, Board board, char c) {
        if(c == 'N' || c == 'Y' || c == 'L') return false;
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