package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.pieces.PieceDrawer;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandVerifier;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Pair;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Vector;

public class IA {
    private Player bot;//para la mano
    private Board board;
    private PieceDrawer drawer;
    private WordPlacer wordPlacer;
    private WordGetter wordGetter;
    private PiecesInHandVerifier piecesInHandVerifier;
    //private DAWG se le pasa el dawg o q
    private WordValidator wordValidator;

    int diff;

    //los world placers y demás simlares hacer que se declaren en action maker y usarlos
    //tanto para persona como bot
    public IA(Board board, Player bot, int diff, WordPlacer wordPlacer, WordGetter wordGetter, PiecesInHandVerifier piecesInHandVerifier, WordValidator wordValidator) {
        this.bot = bot;
        this.board = board;
        this.wordPlacer = wordPlacer;
        this.wordGetter = wordGetter;
        this.piecesInHandVerifier = piecesInHandVerifier;
        this.wordValidator = wordValidator;

    }

    //devuelve un vector con para cada pieza la posicionen la que va
    public Movement run(CrossChecks crossChecks, Anchors anchors) {
        Movement bestMove = new Movement("",0,0, Direction.Horizontal);
        int bestScore = -1;

        //cambiar lo del size
        for(int i = 0; i<anchors.getSize();++i) {
            //MOVIMIENTOS HORIZONTALES
            //extend rightwards
            //caso esta vacio y tenemos que llenar del rack
            //los anchors siempre deberian de ser valid y estar vacios
            Pair<Integer,Integer> currentposition = anchors.getAnchor(i);

        }
        //tmb que best score sea -1 significa que no ha encontrado nada
        return bestMove; //si es null hacer luego que se cambien piezas o pase turno
    }
}