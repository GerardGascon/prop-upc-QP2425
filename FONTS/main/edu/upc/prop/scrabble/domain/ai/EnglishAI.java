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
    protected void processLeftPartSpecialPieces(String partialWord, Map.Entry<Character, Node> entry, int limit) {
    }

    @Override
    protected void processNextLeftPiece(String partialWord, Map.Entry<Character, Node> entry, int limit, Piece usedPiece) {
        goToNextLeftPiece(partialWord + entry.getKey(), entry.getValue(), limit, usedPiece);
    }

    @Override
    protected void extendToNextNewPieceRight(String partialWord, Map.Entry<Character, Node> entry, Piece usedPiece, Vector2 nextCell) {
        goToNextRightPiece(partialWord, entry, usedPiece, nextCell);
    }

    @Override
    protected void extendToNextExistingPieceRight(String partialWord, Piece placedPiece, Node node, Vector2 nextCell) {
        ExtendRight(partialWord + placedPiece.letter(), node.getSuccessor(placedPiece.letter().charAt(0)), nextCell);
    }

    @Override
    protected void processRightPartSpecialPieces(String partialWord, Node node, Vector2 cell, Map.Entry<Character, Node> entry, Vector2 nextCell) {
    }
}