package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.pieces.PieceDrawer;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandVerifier;
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
        this.diff = diff;
    }

    //devuelve las posiciones validas a izquierda y derecha de posiciones co piezas anteriormente
    //(donde se puede empezar a construir una nueva palabra
    private ArrayList<Pair<Integer,Integer>> getAnchors(Board board){
        ArrayList<Pair<Integer,Integer>> anchor = new ArrayList<>();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if(!board.isCellEmpty(i, j)){
                    if(board.isCellValid(i, j+1) && board.isCellEmpty(i, j+1)) anchor.add(new Pair(i, j+1));
                    if(board.isCellValid(i, j-1) && board.isCellEmpty(i, j-1)) anchor.add(new Pair(i, j-1));
                }
            }
        }
        return anchor;
    }

    //devuelve un vector con para cada pieza la posicionen la que va
    public Vector<Pair<Piece, Pair<Integer, Integer>>> movementFinder() {
        //para otras dificultades podemos hacer que se guarden TODOS los movimientos
        //ordenados por puntuacion y que no se coja el que tenga mejor score sino uno demedio (normal) o del final(facil)
        //hacer vector de movimientos y vector de pair<int, int> que haga referencia a
        //numero del indice del movimiento en el vecdtor de movimientos --puntuacion asociada
        Vector<Pair<Piece, Pair<Integer, Integer>>> bestMove = new Vector<>();
        int bestScore = 0;
        Boolean found = false;
        Integer boardSize = board.getSize();
        int centerPos = boardSize / 2;
        Piece[] hand = bot.getHand();
        // Caso primer movimiento (tablero vacío, colocar al menos una pieza en el centro )
        if (board.isCellEmpty(centerPos, centerPos)) {
            findCenterPlay(centerPos, hand);
        }
        else {//caso normal
            ArrayList<Pair<Integer,Integer>> anchor = getAnchors(board);

        }
        return bestMove;//hacer que si best move es empty se haga
        //intercambio de piezas en la mano/skip
    }


    //intenta formar una palabra en el centro de manera horizontal
    //funcion a parte porque es mucho mas sencillo al ser el movimiento inicial
    //da igual verticallmente porque es lo mismo no hay nada que 'moleste'
    private void findCenterPlay(int centerPos,Piece[] hand) {

        //supongo que el tablero nuca será mas pequeño que 7 desde el centro
        //SINO CUIDADO por si acaso
        for (int l = 1; l < 7; l++) {//tamaño de la parte izquierda
            // Iniciamos el algoritmo de LeftPart desde el nodo raíz del DAWG
            StringBuilder partialWord = new StringBuilder();
            Piece[] copiamano = hand;

            // Llamamos a LeftPart (siempre tendremos espacio)
            //leftPart(partialWord, l, copiamano, centerPos);
            //en este caso da igual en verdad mirar la derecha ya que seria como desplazarlo
            //pero tampoco hay nada con lo que interactuar
            //solo seria relevante si el tablero horzontalmente fuese < 14
            //--------------------------------------------------------------------------------------------------------------------------
        }
    }



}