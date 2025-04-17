package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.dawg.Node;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.pieces.PieceDrawer;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandVerifier;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Pair;
import edu.upc.prop.scrabble.utils.Vector2;

import java.awt.*;
import java.util.*;

public class IA {
    private DAWG dawg;
    private Player bot;//para la mano
    private Board board;
    private PieceDrawer drawer;
    private WordPlacer wordPlacer;
    private WordGetter wordGetter;
    private PiecesInHandVerifier piecesInHandVerifier;
    //private DAWG se le pasa el dawg o q
    private WordValidator wordValidator;
    private Anchors anchors;
    private CrossChecks crossChecks;
    private Pair<Integer,Integer> currentAnchor; // Current anchor
    private String language;
    private PointCalculator pointCalculator;
    private PiecesConverter piecesConverter;
    private Movement bestMove;
    private int bestScore;


    int diff;

    //los world placers y demás simlares hacer que se declaren en action maker y usarlos
    //tanto para persona como bot
    public IA(PiecesConverter piecesConverter, PointCalculator pointCalculator, String language, DAWG dawg, Board board, Player bot, int diff, WordPlacer wordPlacer, WordGetter wordGetter, PiecesInHandVerifier piecesInHandVerifier, WordValidator wordValidator, Anchors anchors, CrossChecks crossChecks) {
        this.dawg = dawg;
        this.bot = bot;
        this.board = board;
        this.wordPlacer = wordPlacer;
        this.wordGetter = wordGetter;
        this.piecesInHandVerifier = piecesInHandVerifier;
        this.wordValidator = wordValidator;
        this.anchors = anchors;
        this.crossChecks = crossChecks;
        this.pointCalculator = pointCalculator;
        this.piecesConverter = piecesConverter;

    }

    //devuelve un vector con para cada pieza la posicionen la que va
    public Movement run() {

        // Move initialization
        bestMove = new Movement("", 0, 0, Direction.Horizontal);
        bestScore = -1;
        // Iterate over every available anchor
        for (int i = 0; i < anchors.getSize(); ++i) {
            currentAnchor = anchors.getAnchor(i); // Global variable
            int currentAnchorX = currentAnchor.first();
            int currentAnchorY = currentAnchor.second();
            //MOVIMIENTOS HORIZONTALES---------------------------
            int limit = 0; // How far to the left can we go
            while (board.isCellValid(currentAnchorX - limit - 1, currentAnchorY) &&
                    board.isCellEmpty(currentAnchorX - limit - 1, currentAnchorY) &&
                    !anchors.exists(currentAnchorX - limit - 1, currentAnchorY) ) ++limit;

            // LeftPart is composed by bot's pieces or already placed letters, never both
            if(limit == 0) { // Already placed letters
                String partialWord = "";
                int j = 0;
                while(board.isCellValid(currentAnchorX- j - 1, currentAnchorY)) {
                    partialWord = board.getCellPiece(currentAnchorX - i - 1, currentAnchorY).letter() + partialWord;
                    ++j;
                }
                ExtendRight(partialWord, getFinalNode(partialWord), currentAnchor);
            }
            else LeftPart("", dawg.getRoot(), limit);
        }

        //tmb que best score sea -1 significa que no ha encontrado nada
        return bestMove; //si es null hacer luego que se cambien piezas o pase turno
    }

    void LeftPart(String partialWord, Node node, int limit){
        ExtendRight(partialWord, node, currentAnchor);
        if(limit > 0) {
            Map<Character, Node> nextNodes = node.getSuccessors();
            for (Map.Entry<Character, Node> entry : nextNodes.entrySet()) {
                // Special cases check
                if(language.equals("Catalan")) {
                    if(entry.getKey() == 'N') { // NY
                        Node nextNode = node.getSuccessor('Y');
                        Piece usedPiece = bot.hasPiece("NY");
                        if(nextNode != null && usedPiece != null) {
                            bot.removePiece(usedPiece);
                            LeftPart(partialWord + "NY", nextNode, limit - 1);
                            bot.addPiece(usedPiece);
                        }
                    }
                    else if(entry.getKey() == 'L') { // L·L
                        Node nextNode = node.getSuccessor('·');
                        Piece usedPiece = bot.hasPiece("L·L");
                        if(nextNode != null && usedPiece != null) {
                            bot.removePiece(usedPiece);
                            LeftPart(partialWord + "L·L", nextNode.getSuccessor('L'), limit - 1);
                            bot.addPiece(usedPiece);
                        }
                    }
                }
                if(language.equals("Spanish")) {
                    if(entry.getKey() == 'R') { // RR
                        Node nextNode = node.getSuccessor('R');
                        Piece usedPiece = bot.hasPiece("RR");
                        if(nextNode != null && usedPiece != null) {
                            bot.removePiece(usedPiece);
                            LeftPart(partialWord + "RR", nextNode, limit - 1);
                            bot.addPiece(usedPiece);
                        }
                    }
                    else if(entry.getKey() == 'L') { // LL
                        Node nextNode = node.getSuccessor('L');
                        Piece usedPiece = bot.hasPiece("LL");
                        if(nextNode != null && usedPiece != null) {
                            bot.removePiece(usedPiece);
                            LeftPart(partialWord + "LL", nextNode, limit - 1);
                            bot.addPiece(usedPiece);
                        }
                    }
                    else if(entry.getKey() == 'C') { // CH
                        Node nextNode = node.getSuccessor('H');
                        Piece usedPiece = bot.hasPiece("CH");
                        if(nextNode != null && usedPiece != null) {
                            bot.removePiece(usedPiece);
                            LeftPart(partialWord + "CH", nextNode, limit - 1);
                            bot.addPiece(usedPiece);
                        }
                    }
                }
                Piece usedPiece = bot.hasPiece(String.valueOf(entry.getKey()));
                if (usedPiece != null) {
                    char lastLetter = partialWord.charAt(partialWord.length() - 1);
                    if(language.equals("Catalan")) {
                        if((lastLetter != 'N' || entry.getKey() != 'Y') &&
                           (lastLetter != 'L' || entry.getKey() != '·')) {
                            bot.removePiece(usedPiece);
                            LeftPart(partialWord + entry.getKey(), entry.getValue(), limit);
                            bot.addPiece(usedPiece);
                        }
                    }
                    else if(language.equals("Spanish")) {
                        if((lastLetter != 'R' || entry.getKey() != 'R') &&
                           (lastLetter != 'L' || entry.getKey() != 'L') &&
                           (lastLetter != 'C' || entry.getKey() != 'H')) {
                            bot.removePiece(usedPiece);
                            LeftPart(partialWord + entry.getKey(), entry.getValue(), limit);
                            bot.addPiece(usedPiece);
                        }
                    }
                    else {
                        bot.removePiece(usedPiece);
                        LeftPart(partialWord + entry.getKey(), entry.getValue(), limit);
                        bot.addPiece(usedPiece);
                    }
                }
            }
        }
    }

    void ExtendRight(String partialWord, Node node, Pair<Integer,Integer> cell){
        // FALTA CHECAR SI ES UNA PALABRA VALIDA CALCULAR PUNTOS MEJOR MOVIMIENTO Y TAL Y CUAL
        Pair<Integer, Integer> nextCell = new Pair<>(cell.first() + 1, cell.second());
        if (board.isCellValid(cell.first(), cell.second()) &&
            board.isCellEmpty(cell.first(), cell.second())) {
            Map<Character, Node> nextNodes = node.getSuccessors();
            for (Map.Entry<Character, Node> entry : nextNodes.entrySet()) {

                if(entry.getValue().isEndOfWord()) {
                    Piece[] pieceArray = piecesConverter.run(partialWord);
                    Vector2[] posVector = new Vector2[pieceArray.length];
                    for(int i = 0; i < pieceArray.length; ++i) {
                        posVector[i] = new Vector2(cell.first() - i, cell.second() - i);
                    }
                    int points = pointCalculator.run(posVector, pieceArray);
                    if(points > bestScore) {
                        bestScore = points;
                        bestMove = new Movement(partialWord, posVector[0].x, posVector[0].y, getWordDirection(posVector));
                    }
                }

                // Special cases check
                if(language.equals("Catalan")) {
                    if(entry.getKey() == 'N') { // NY
                        Node nextNode = node.getSuccessor('Y');
                        Piece usedPiece = bot.hasPiece("NY");
                        if(nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.first(), cell.second(), "NY")) {
                            bot.removePiece(usedPiece);
                            ExtendRight(partialWord + "NY", nextNode, nextCell);
                            bot.addPiece(usedPiece);
                        }
                    }
                    else if(entry.getKey() == 'L') { // L·L
                        Node nextNode = node.getSuccessor('·');
                        Piece usedPiece = bot.hasPiece("L·L");
                        if(nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.first(), cell.second(), "L·L")) {
                            bot.removePiece(usedPiece);
                            ExtendRight(partialWord + "L·L", nextNode.getSuccessor('L'), nextCell);
                            bot.addPiece(usedPiece);
                        }
                    }
                }
                if(language.equals("Spanish")) {
                    if(entry.getKey() == 'R') { // RR
                        Node nextNode = node.getSuccessor('R');
                        Piece usedPiece = bot.hasPiece("RR");
                        if(nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.first(), cell.second(), "RR")) {
                            bot.removePiece(usedPiece);
                            ExtendRight(partialWord + "RR", nextNode, nextCell);
                            bot.addPiece(usedPiece);
                        }
                    }
                    else if(entry.getKey() == 'L') { // LL
                        Node nextNode = node.getSuccessor('L');
                        Piece usedPiece = bot.hasPiece("LL");
                        if(nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.first(), cell.second(), "LL")) {
                            bot.removePiece(usedPiece);
                            ExtendRight(partialWord + "LL", nextNode, nextCell);
                            bot.addPiece(usedPiece);
                        }
                    }
                    else if(entry.getKey() == 'C') { // CH
                        Node nextNode = node.getSuccessor('H');
                        Piece usedPiece = bot.hasPiece("CH");
                        if(nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.first(), cell.second(), "CH")) {
                            bot.removePiece(usedPiece);
                            ExtendRight(partialWord + "CH", nextNode, nextCell);
                            bot.addPiece(usedPiece);
                        }
                    }
                }

                Piece usedPiece = bot.hasPiece(String.valueOf(entry.getKey()));
                if (usedPiece != null && crossChecks.ableToPlace(cell.first(), cell.second(), String.valueOf(entry.getKey()))) {
                    char lastLetter = partialWord.charAt(partialWord.length() - 1);
                    if(language.equals("Catalan")) {
                        if((lastLetter != 'N' || entry.getKey() != 'Y') &&
                                (lastLetter != 'L' || entry.getKey() != '·')) {
                            bot.removePiece(usedPiece);
                            ExtendRight(partialWord + entry.getKey(), entry.getValue(), nextCell);
                            bot.addPiece(usedPiece);
                        }
                    }
                    else if(language.equals("Spanish")) {
                        if((lastLetter != 'R' || entry.getKey() != 'R') &&
                                (lastLetter != 'L' || entry.getKey() != 'L') &&
                                (lastLetter != 'C' || entry.getKey() != 'H')) {
                            bot.removePiece(usedPiece);
                            ExtendRight(partialWord + entry.getKey(), entry.getValue(), nextCell);
                            bot.addPiece(usedPiece);
                        }
                    }
                    else {
                        bot.removePiece(usedPiece);
                        ExtendRight(partialWord + entry.getKey(), entry.getValue(), nextCell);
                        bot.addPiece(usedPiece);
                    }
                }
            }
        }
        else if (board.isCellValid(cell.first(), cell.second())) { // NO PONER CONVINATIONS ILEGALES!!!!! NY y tal
            Piece placedPiece = board.getCellPiece(cell.first(), cell.second());
            char lastLetter = partialWord.charAt(partialWord.length() - 1);
            String placedLetter = placedPiece.letter();
            Node nextNode = node.getSuccessor(placedPiece.letter().charAt(0));
            if(placedLetter.length() == 1) {
                if (language.equals("Catalan")) {
                    if ((lastLetter != 'N' || !placedLetter.equals("Y")) &&
                       (lastLetter != 'L' || !placedLetter.equals("·")) &&
                        nextNode != null) {
                        ExtendRight(partialWord + placedLetter, nextNode, nextCell);
                    }
                } else if (language.equals("Spanish")) {
                    if ((lastLetter != 'R' || !placedLetter.equals("R")) &&
                            (lastLetter != 'L' || !placedLetter.equals("L")) &&
                            (lastLetter != 'C' || !placedLetter.equals("H"))) {
                        ExtendRight(partialWord + placedLetter, nextNode, nextCell);
                    }
                } else {
                    if (nextNode != null) { // La letra puesta puede seguir a la leftword!
                        ExtendRight(partialWord + placedLetter, nextNode, nextCell);
                    }
                }
            }
            else {
                for(int i = 1; i < partialWord.length() && nextNode != null; i++) nextNode = node.getSuccessor(placedPiece.letter().charAt(i));
                if(nextNode != null) ExtendRight(partialWord + placedLetter, nextNode, nextCell);
            }
        }
    }

    private Node getFinalNode(String word) {
        Node current = dawg.getRoot();
        for (int i = 0; i < word.length(); i++) {
            current = current.getSuccessor(word.charAt(i));
        }

        return current;
    }

    private Direction getWordDirection(Vector2[] positions) {
        if (positions.length == 1)
            return null;

        if (positions[0].x == positions[1].x)
            return Direction.Vertical;
        return Direction.Horizontal;
    }
}
