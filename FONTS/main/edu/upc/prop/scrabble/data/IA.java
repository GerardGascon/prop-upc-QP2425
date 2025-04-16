package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.dawg.Node;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.pieces.PieceDrawer;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandVerifier;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Pair;

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
    private Pair<Integer,Integer> pos;
    private String language;

    int diff;

    //los world placers y demás simlares hacer que se declaren en action maker y usarlos
    //tanto para persona como bot
    public IA(String language, DAWG dawg, Board board, Player bot, int diff, WordPlacer wordPlacer, WordGetter wordGetter, PiecesInHandVerifier piecesInHandVerifier, WordValidator wordValidator, Anchors anchors, CrossChecks crossChecks) {
        this.dawg = dawg;
        this.bot = bot;
        this.board = board;
        this.wordPlacer = wordPlacer;
        this.wordGetter = wordGetter;
        this.piecesInHandVerifier = piecesInHandVerifier;
        this.wordValidator = wordValidator;
        this.anchors = anchors;
        this.crossChecks = crossChecks;

    }

    //devuelve un vector con para cada pieza la posicionen la que va
    public Movement run() {
        Movement bestMove = new Movement("", 0, 0, Direction.Horizontal);
        int bestScore = -1;

        //cambiar lo del size
        for (int i = 0; i < anchors.getSize(); ++i) {
            //current AnchorSquare
            pos = anchors.getAnchor(i);
            //MOVIMIENTOS HORIZONTALES---------------------------
            //extend rightwards
            //caso esta vacio y tenemos que llenar del rack
            //(los anchors siempre deberian de ser valid y estar vacios)
            int limit = 0;
            while (board.isCellValid(pos.first() - 1, pos.second()) &&
                    board.isCellEmpty(pos.first() - 1, pos.second()) &&
                    !anchors.exists(pos.first() - 1, pos.second())) {
                ++limit;
            }
            LeftPart("", dawg.getRoot(),limit);


        }
        //tmb que best score sea -1 significa que no ha encontrado nada
        return bestMove; //si es null hacer luego que se cambien piezas o pase turno
    }
    void LeftPart(String partialWord, Node node, int limit){
        //ExtendRight(partialWord, node, pos);
        if(limit > 0) {
            Map<Character, Node> nextnodes = node.getSuccessors();
        }
    }
}
