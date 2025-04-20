package edu.upc.prop.scrabble.domain.crosschecks;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.dawg.Node;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;

public class CrossCheckUpdater {
    private final CrossChecks crossChecks;
    private final Board board;
    private final WordValidator wordValidator;
    private final DAWG dawg;
    private final PiecesConverter piecesConverter;

    public CrossCheckUpdater(PiecesConverter pc, CrossChecks crossChecks, Board board, DAWG dawg) {
        this.crossChecks = crossChecks;
        this.board = board;
        this.dawg = dawg;
        this.wordValidator = new WordValidator(dawg);
        this.piecesConverter = pc;
    }

    public void run(Movement move) {
        if (move.direction() == Direction.Horizontal)
            calculateHorizontalCrossChecks(move);
        else
            calculateVerticalCrossChecks(move);
    }

    private void calculateHorizontalCrossChecks(Movement move) {
        int beginningOfAddedWord = move.x();
        Piece[] size = piecesConverter.run(move.word());
        int endOfAddedWord = move.x() + size.length - 1;

        calculateHorizontalBeginCrossCheck(move, beginningOfAddedWord);
        calculateHorizontalEndCrossCheck(move, endOfAddedWord);
    }

    private boolean canProcessCell(int x, int y) {
        return board.isCellValid(x, y) && board.isCellEmpty(x, y);
    }

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

    private void calculateVerticalCrossChecks(Movement move) {
        int beginningOfAddedWord = move.y();
        Piece[] size = piecesConverter.run(move.word());
        int endOfAddedWord = move.y() + size.length - 1;
        calculateVerticalBeginCrossCheck(move, beginningOfAddedWord);
        calculateVerticalEndCrossCheck(move, endOfAddedWord);
    }

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

    private Node getFinalNode(String word) {
        Node current = dawg.getRoot();
        int i = 0;
        while (i < word.length() && current != null) {
            current = current.getSuccessor(word.charAt(i));
            ++i;
        }

        return current;
    }

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