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

public class EnglishAI extends AI {
    public EnglishAI(PiecesConverter piecesConverter, PointCalculator pointCalculator, DAWG dawg, Board board, Player bot, Anchors anchors, CrossChecks crossChecks) {
        super(piecesConverter, pointCalculator, dawg, board, bot, anchors, crossChecks);
    }

    @Override
    protected void processLeftPartSpecialPieces(String partialWord, int limit, Map.Entry<Character, Node> entry) {
    }

    @Override
    protected void processNextLeftPiece(String partialWord, int limit, Map.Entry<Character, Node> entry, Piece usedPiece) {
        if(usedPiece.isBlank()) goToNextLeftPiece(partialWord + Character.toLowerCase(entry.getKey()), entry.getValue(), limit, usedPiece);
        else goToNextLeftPiece(partialWord + entry.getKey(), entry.getValue(), limit, usedPiece);
    }

    @Override
    protected void extendToNextNewPieceRight(String partialWord, Vector2 cell, Map.Entry<Character, Node> entry, Piece usedPiece) {
        if(usedPiece.isBlank()) goToNextRightPiece(partialWord + Character.toLowerCase(entry.getKey()), entry.getValue(), cell, usedPiece);
        else goToNextRightPiece(partialWord + entry.getKey(), entry.getValue(), cell, usedPiece);
    }

    @Override
    protected void extendToNextExistingPieceRight(String partialWord, Node node, Vector2 cell, Piece placedPiece) {
        Node nextNode = node.getSuccessor(placedPiece.letter().charAt(0));
        if(nextNode != null) ExtendRight(partialWord + placedPiece.letter(), nextNode, cell);
    }

    @Override
    protected void processRightPartSpecialPieces(String partialWord, Vector2 cell, Map.Entry<Character, Node> entry) {
    }

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