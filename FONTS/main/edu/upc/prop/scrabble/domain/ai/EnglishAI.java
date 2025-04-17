package edu.upc.prop.scrabble.domain.ai;

import edu.upc.prop.scrabble.data.Anchors;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.dawg.Node;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandVerifier;
import edu.upc.prop.scrabble.utils.Pair;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.Map;

public class EnglishAI extends AI {
    public EnglishAI(PiecesConverter piecesConverter, PointCalculator pointCalculator, String language, DAWG dawg, Board board, Player bot, int diff, WordPlacer wordPlacer, WordGetter wordGetter, PiecesInHandVerifier piecesInHandVerifier, WordValidator wordValidator, Anchors anchors, CrossChecks crossChecks) {
        super(piecesConverter, pointCalculator, dawg, board, bot, anchors, crossChecks);
    }

    @Override
    protected void processLeftPartSpecialPieces(String partialWord, Node node, int limit, Map.Entry<Character, Node> entry) {
    }

    @Override
    protected void processNextLeftPiece(char lastLetter, String partialWord, int limit, Map.Entry<Character, Node> entry, Piece usedPiece) {
        goToNextLeftPiece(partialWord, limit - 1, entry, usedPiece);
    }

    @Override
    protected void extendToNextNewPieceRight(String partialWord, Map.Entry<Character, Node> entry, char lastLetter, Piece usedPiece, Vector2 nextCell) {
        goToNextRightPiece(partialWord, entry, usedPiece, nextCell);
    }

    @Override
    protected void extendToNextExistingPieceRight(String partialWord, char lastLetter, String placedLetter, Node nextNode, Vector2 nextCell) {
        ExtendRight(partialWord + placedLetter, nextNode, nextCell);
    }

    @Override
    protected void processRightPartSpecialPieces(String partialWord, Node node, Vector2 cell, Map.Entry<Character, Node> entry, Vector2 nextCell) {
    }
}