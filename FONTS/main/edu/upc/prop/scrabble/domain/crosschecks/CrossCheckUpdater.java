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

    //al crearlo hacer tmb la llamada con el piececonverter que corresponda al idioma correcto
    public CrossCheckUpdater(PiecesConverter pc,  CrossChecks crossChecks, Board board,  DAWG dawg) {
        this.crossChecks = crossChecks;
        this.board = board;
        this.dawg = dawg;
        this.wordValidator = new WordValidator(dawg);
        this.piecesConverter = pc;
    }

    //para cada casilla su array de bits que representa que letras se pueden poner y cuales no
    //cada bit indica si se puede poner la pieza de esa letra
    //ENG: letras normales (hacer int(letra)- int('A')
    //CAT: letras normales + pos 27 para la ç, pos 28 para L.L, pos 29 para NY
    //ESP: letras normales + pos 27 para la ñ, pos 28 para la RR, 29 LL, 30 CH
    public void run(Movement move) {
        if (move.direction() == Direction.Horizontal)
            calculateHorizontalCrossChecks(move);
        else
            calculateVerticalCrossChecks(move);
    }

    private void calculateHorizontalCrossChecks(Movement move) {
        int beginningOfAddedWord = move.x();
        //-----------------------------------REVISAR ESTO--------------------------------------------------------
        //que vaya con todos los idiomas
        Piece[] size= piecesConverter.run(move.word());//cuantas piezas ocupa en el tablero
        //para casos con piezas que sean mas de una letra
        int endOfAddedWord = move.x() + size.length-1;
        //int endOfAddedWord = move.x() + move.word().length()-1;
        calculateHorizontalBeginCrossCheck(move, beginningOfAddedWord);
        calculateHorizontalEndCrossCheck(move, endOfAddedWord);
    }

    private void calculateHorizontalEndCrossCheck(Movement move, int endOfAddedWord) {
        //System.out.println("Llamada a funcion");
        if (!board.isCellValid(endOfAddedWord + 1, move.y()) || !board.isCellEmpty(endOfAddedWord + 1, move.y()))
            return;
        //System.out.println("WE'RE IN");
        //System.out.println(endOfAddedWord+1);
        //System.out.println(move.y());
        Node finalNode = getFinalNode(move.word().toUpperCase());
        //System.out.println(finalNode.getSuccessor('A'));
        for (int j = 0; j < crossChecks.getLetters().length; ++j) {
            //probamos de ver si podemos avanzar en el dawg a esas letras
            if (!nextNodeTerminal(finalNode, crossChecks.getLetters()[j])) {
                crossChecks.setCrossCheckHor(endOfAddedWord + 1, move.y(), j);
                //pone a 1 la letra que ha salido que no hace una palabra valida
            }
        }
    }

    private void calculateHorizontalBeginCrossCheck(Movement move, int beginningOfAddedWord) {
        if (!board.isCellValid(beginningOfAddedWord - 1, move.y()) || !board.isCellEmpty(beginningOfAddedWord - 1, move.y()))
            return;

        String[] wordInBoard = new String[2];
        /*for (int i = beginningOfAddedWord; i < beginningOfAddedWord + move.word().length(); ++i)
            wordInBoard[i - beginningOfAddedWord + 1] = board.getCellPiece(i, move.y()).letter();*/
        wordInBoard[1] = move.word();
        //prueba a poner todas las letras del diccionario delante

        for (int i = 0; i < crossChecks.getLetters().length; ++i) {
            wordInBoard[0] = crossChecks.getLetters()[i];
            String word = "";
            for (String s : wordInBoard)
                word = word.concat(s);
            //System.out.println("Current word" + word);
            //System.out.println(wordValidator.run(word));
            if (!wordValidator.run(word)) {
                crossChecks.setCrossCheckHor(beginningOfAddedWord - 1, move.y(), i);
                //pone a 1 la letra que ha salido que no hace una palabra valida
            }
        }
    }

    private void calculateVerticalCrossChecks(Movement move) {
        int beginningOfAddedWord = move.y();
        //mirar que vaya con todos los idiomas
        Piece[] size= piecesConverter.run(move.word());
        int endOfAddedWord = move.y() + size.length-1;
        //int endOfAddedWord = move.y() + move.word().length()-1;//-1 pq empezamos en el 0
        calculateVerticalBeginCrossCheck(move, beginningOfAddedWord);
        calculateVerticalEndCrossCheck(move, endOfAddedWord);
    }

    private void calculateVerticalEndCrossCheck(Movement move, int endOfAddedWord) {
        if (!board.isCellValid(move.x(), endOfAddedWord + 1) || !board.isCellEmpty(move.x(), endOfAddedWord + 1))
            return;

        //hacer que lo de ponerlo detras sea eficiente consultarlo con el dawg
        //llegar hasta el nodo del que seria el final de la palabra y para ese nodo mirar si luego tiene otro siguiente
        // con la letra que toca y sea 'terminal'
        Node finalNode = getFinalNode(move.word().toUpperCase());
        for (int j = 0; j < crossChecks.getLetters().length; ++j) {
            //probamos de ver si podemos avanzar en el dawg a esas letras
            if (!nextNodeTerminal(finalNode, crossChecks.getLetters()[j])) {
                crossChecks.setCrossCheckVer(move.x(), endOfAddedWord + 1, j);
                //pone a 1 la letra que ha salido que no hace una palabra valida
            }
        }
    }

    private void calculateVerticalBeginCrossCheck(Movement move, int beginningOfAddedWord) {
        if (!board.isCellValid(move.x(), beginningOfAddedWord - 1) || !board.isCellEmpty(move.x(), beginningOfAddedWord - 1))
            return;

        String[] wordInBoard = new String[2];//+1 pq hay que hacer hueco al principio para la letra a probar si va
        /*for (int i = beginningOfAddedWord; i < beginningOfAddedWord + move.word().length(); ++i)
            wordInBoard[i - beginningOfAddedWord + 1] = board.getCellPiece(move.x(), i).letter();*/
        wordInBoard[1] = move.word();
        //prueba a poner todas las letras del diccionario delante
        for (int i = 0; i < crossChecks.getLetters().length; ++i) {
            wordInBoard[0] = crossChecks.getLetters()[i];
            String word = "";
            for (String s : wordInBoard)
                word = word.concat(s);

            if (!wordValidator.run(word)) {
                crossChecks.setCrossCheckVer(move.x(), beginningOfAddedWord - 1, i);
                //pone a 1 la letra que ha salido que no hace una palabra valida
            }
        }
    }

    //sabes que la palabra ya esta bien (estaba puesta en el tablero), quieres el nodo de final
    private Node getFinalNode(String word) {
        Node current = dawg.getRoot();
        int i = 0;
        while(i < word.length() && current != null) {
            current = current.getSuccessor(word.charAt(i));
            ++i;
        }

        return current;
        /*Node current = dawg.getRoot();
        for (int i = 0; i < word.length(); i++) {
            current = current.getSuccessor(word.charAt(i));
            //System.out.println(i);
            //System.out.println(current.getSuccessor('s'));
        }

        return current;*/
    }

    //dado un nodo y una pieza dice si en sus sucesores se encuentra e y es final
    private boolean nextNodeTerminal(Node almostlast, String piece) {
        if(almostlast == null) return false;
        Node current = almostlast;
        for (int i = 0; i < piece.length(); i++) {
            Node successor = current.getSuccessor(piece.charAt(i));
            if (successor == null) return false;
            current = successor;
        }
        return current.isEndOfWord();
    }
}