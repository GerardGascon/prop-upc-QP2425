package edu.upc.prop.scrabble.domain.ai;

import edu.upc.prop.scrabble.data.Anchors;
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
 * és el castellà.
 * @see Player
 * @see Board
 * @see Anchors
 * @see CrossChecks
 * @see edu.upc.prop.scrabble.data.Movement
 * @see PointCalculator
 * @see PiecesConverter
 * @see DAWG
 * @author Albert Usero
 * @author Felipe Martínez
 */
public class SpanishAI extends AI {
    /**
     * Constructor per a la implementació d'IA en castellà.
     * <p>
     * Inicialitza els components necessaris perquè la IA pugui calcular moviments
     * vàlids en partides amb l'idioma castellà, incloent el tractament especial
     * per a les lletres específiques del castellà com 'Ñ' o 'CH'.
     * Hereta la funcionalitat bàsica de la classe pare {@link AI}.
     *
     * @param piecesConverter  Convertidor de peces per a la gestió de fitxes
     * @param pointCalculator  Calculador de puntuacions per a moviments
     * @param dawg             Estructura de dades DAWG que conté el diccionari català
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
    public SpanishAI(PiecesConverter piecesConverter, PointCalculator pointCalculator, DAWG dawg, Board board, Player bot, Anchors anchors, CrossChecks crossChecks) {
        super(piecesConverter, pointCalculator, dawg, board, bot, anchors, crossChecks);
    }

    /**
     * Tracta casos especials cap a l'esquerra (RR, LL, CH)
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
            case 'R':
                nextNode = entry.getValue().getSuccessor('R');
                usedPiece = bot.hasPiece("RR");
                if(nextNode != null && usedPiece != null) {
                    if(usedPiece.isBlank()) goToNextLeftPiece(partialWord + "rr", nextNode, limit, usedPiece);
                    else goToNextLeftPiece(partialWord + "RR", nextNode, limit, usedPiece);
                }
                break;

            case 'L':
                nextNode = entry.getValue().getSuccessor('L');
                usedPiece = bot.hasPiece("LL");
                if(nextNode != null && usedPiece != null) {
                    if(usedPiece.isBlank()) goToNextLeftPiece(partialWord + "ll", nextNode, limit, usedPiece);
                    else goToNextLeftPiece(partialWord + "LL", nextNode, limit, usedPiece);
                }
                break;

            case 'C':
                nextNode = entry.getValue().getSuccessor('C');
                usedPiece = bot.hasPiece("CH");
                if(nextNode != null && usedPiece != null) {
                    if(usedPiece.isBlank()) goToNextLeftPiece(partialWord + "ch", nextNode, limit, usedPiece);
                    else goToNextLeftPiece(partialWord + "CH", nextNode, limit, usedPiece);
                }
                break;
        }
    }

    /**
     * Comprova que no hi ha cap combinació il·legal (RR, LL, CH)
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
        if((((lastLetter != 'R')&&(lastLetter != 'r'))|| entry.getKey() != 'R') && // Illegal combinations check
        (((lastLetter != 'L')&&(lastLetter != 'l')) || entry.getKey() != 'L') &&
        (((lastLetter != 'C')&&(lastLetter != 'c')) || entry.getKey() != 'H')){
            if(usedPiece.isBlank()) goToNextLeftPiece(partialWord + Character.toLowerCase(entry.getKey()), entry.getValue(), limit, usedPiece);
            else goToNextLeftPiece(partialWord + entry.getKey(), entry.getValue(), limit, usedPiece);
        }
    }

    /**
     * Estén cap a la dreta fent servir una peça de la mà i comprova casos especials (RR, LL, CH)
     * @param partialWord Tros de paraula actual
     * @param cell Casella sobre la qual estem estenent
     * @param entry Caràcter/Node següent que estem comprovant
     * @param usedPiece Peça utilitzada
     */
    @Override
    protected void extendToNextNewPieceRight(String partialWord, Vector2 cell, Map.Entry<Character, Node> entry, Piece usedPiece) {
        char lastLetter = ' ';
        if(!partialWord.isEmpty()) lastLetter = partialWord.charAt(partialWord.length() - 1);
        if((((lastLetter != 'R')&&(lastLetter != 'r'))|| entry.getKey() != 'R') && // Illegal combinations check
           (((lastLetter != 'L')&&(lastLetter != 'l')) || entry.getKey() != 'L') &&
           (((lastLetter != 'C')&&(lastLetter != 'c')) || entry.getKey() != 'H')) {
            if(usedPiece.isBlank()) goToNextRightPiece(partialWord + Character.toLowerCase(entry.getKey()), entry.getValue(), cell, usedPiece);
            else goToNextRightPiece(partialWord + entry.getKey(), entry.getValue(), cell, usedPiece);
        }
    }

    /**
     * Estén cap a la dreta amb una peça ja col·locada al tauler i comprova casos especials (RR, LL, CH)
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
                if((((lastLetter != 'R')&&(lastLetter != 'r'))|| !placedLetter.equals("R")) && // Illegal combinations check
                (((lastLetter != 'L')&&(lastLetter != 'l')) || !placedLetter.equals("L")) &&
                (((lastLetter != 'C')&&(lastLetter != 'c')) || !placedLetter.equals("H"))){
                ExtendRight(partialWord + placedLetter, nextNode, cell);
            }
        }
        else { //Special piece
            nextNode = nextNode.getSuccessor(placedPiece.letter().charAt(1));
            if (nextNode != null) ExtendRight(partialWord + placedLetter, nextNode, cell);
        }
    }

    /**
     * Tracta casos especials cap a la dreta (RR, LL, CH)
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
        Piece usedPiece;
        switch (c) {
            case 'R':
                nextNode = entry.getValue().getSuccessor('R');
                usedPiece = bot.hasPiece("RR");
                if (nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, "RR")) {
                    if (nextNode.isEndOfWord()) {
                        if(usedPiece.isBlank()) checkWord(partialWord + "rr", cell);
                        else checkWord(partialWord + "RR", cell);
                    }
                    else {
                        if(usedPiece.isBlank()) goToNextRightPiece(partialWord + "rr", nextNode, cell, usedPiece);
                        else goToNextRightPiece(partialWord + "RR", nextNode, cell, usedPiece);
                    }
                }
                break;

            case 'L':
                nextNode = entry.getValue().getSuccessor('L');
                usedPiece = bot.hasPiece("LL");
                if (nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, "LL")) {
                    if (nextNode.isEndOfWord()) {
                        if(usedPiece.isBlank()) checkWord(partialWord + "ll", cell);
                        else checkWord(partialWord + "LL", cell);
                    }
                    else {
                        if(usedPiece.isBlank()) goToNextRightPiece(partialWord + "ll", nextNode, cell, usedPiece);
                        else goToNextRightPiece(partialWord + "LL", nextNode, cell, usedPiece);
                    }
                }
                break;
            case 'C':
                nextNode = entry.getValue().getSuccessor('H');
                usedPiece = bot.hasPiece("CH");
                if (nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, "CH")) {
                    if (nextNode.isEndOfWord()) {
                        if(usedPiece.isBlank()) checkWord(partialWord + "ch", cell);
                        else checkWord(partialWord + "CH", cell);
                    }
                    else {
                        if(usedPiece.isBlank()) goToNextRightPiece(partialWord + "ch", nextNode, cell, usedPiece);
                        else goToNextRightPiece(partialWord + "CH", nextNode, cell, usedPiece);
                    }
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
    @Override
    protected boolean validExistingWord(Vector2 cell, Board board, char c) {
        if(c == 'R' || c == 'C' || c == 'H' || c == 'L') return false;
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