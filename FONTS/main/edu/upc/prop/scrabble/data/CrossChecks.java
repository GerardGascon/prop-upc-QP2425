package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.utils.Direction;

import java.util.BitSet;

public class CrossChecks {
    //0 se puede poner, 1 no se puede poner
    BitSet[][] crossChecksVer;
    //almacena que piezas no podemos poner en la casilla [][]
    //por culpa de las palabras en Vertical
    BitSet[][] crossChecksHor;
    //lo mimsmo pero por culpa de las palabras en horizontal

    DAWG dawg;
    String language;
    Board board;
    WordValidator wordValidator;

    //ENG: letras normales (hacer int(letra)- int('A')
    //CAT: letras normales + pos 27 para la ç, pos 28 para L.L, pos 29 para NY
    //ESP: letras normales + pos 27 para la ñ, pos 28 para la RR, 29 LL, 30 CH
    String[] englishLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    String[] catalanLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","Ç","L.L","NY"};
    String[] spanishLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","Ñ","RR","LL","CH"};


    public CrossChecks(Board board, String language, DAWG dawg) {
        this.board = board;
        this.language = language;
        crossChecksVer = initializeCrossChecks(board.getSize(), language);
        crossChecksHor = initializeCrossChecks(board.getSize(), language);
        wordValidator = new WordValidator(dawg);
    }


    //inicializa todos las posiciones de crosscheck del tamaño del board a
    //bitSets a 0 (con tamaño segun el idioma)
    private BitSet[][] initializeCrossChecks(int boardSize, String language) {
        // Determinar el tamaño del BitSet según el idioma (usando if-else)
        int bitSetSize;
        if (language == "english") bitSetSize = 26;
        else if (language == "catalan") bitSetSize = 29;
        else if (language == "spanish") bitSetSize = 30;
        else bitSetSize = 26; // Valor por defecto (inglés)
        BitSet[][] crossChecksMatrix = new BitSet[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                crossChecksMatrix[row][col] = new BitSet(bitSetSize);
                //se inicializa a O's
            }
        }
        return crossChecksMatrix;
    }

    public BitSet getCrossCheckVer(int x, int y) {
        return crossChecksVer[x][y];
    }

    public BitSet getCrossCheckHor(int x, int y) {
        return crossChecksHor[x][y];
    }

    //para cada casilla su array de bits que representa que letras se pueden poner y cuales no
    //cada bit indica si se puede poner la pieza de esa letra
    //ENG: letras normales (hacer int(letra)- int('A')
    //CAT: letras normales + pos 27 para la ç, pos 28 para L.L, pos 29 para NY
    //ESP: letras normales + pos 27 para la ñ, pos 28 para la RR, 29 LL, 30 CH
    public void updateCrossChecks(Movement move) {
        int numletters = 26;
        String[] letters = englishLetters;
        if (language == "catalan"){
            numletters = 29;
            letters = catalanLetters;
        }
        else if (language == "spanish"){
            numletters = 30;
            letters = spanishLetters;
        }

        //ponemos la palabra en horizontal
        if (move.direction() == Direction.Horizontal) {
            int begginingaddedword = move.x();
            int endaddedword = move.x()+move.word().length();
            if (board.isCellValid(begginingaddedword-1, move.y()) && board.isCellEmpty(begginingaddedword-1, move.y())) {
                //+1 pq hay que hacer hueco al principio para la letra a probar si va
                Integer l = move.word().length()+1;
                String[] wordinboard = new String[l];
                //MIRAR LOGICA QUE NO PETE ESTO POR ACCESO ERRONEO
                for(int i = begginingaddedword; i < begginingaddedword+l-1; ++i) wordinboard[i-begginingaddedword+1] = board.getCellPiece(i,move.y()).letter();
                //prueba a poner todas las letras del diccionario delante
                for(int i = 0; i < numletters;++i){
                    wordinboard[0] = letters[i];
                    String word = "";
                    for(int j = 0; j < wordinboard.length; j++){
                        word = word.concat(wordinboard[j]);
                    }
                    if(!wordValidator.runString(word)) {
                        crossChecksHor[begginingaddedword-1][move.y()].set(i);//pone a 1 la letra que
                        //ha salido que no hace una palabra valida
                    }
                }
            }
            //crosscheck posterior(si se puede obvio)
            if (board.isCellValid(endaddedword+1, move.y()) && board.isCellEmpty(endaddedword+1, move.y())) {
                //hacer que lo de ponerlo detras sea eficiente consultarlo con el dawg
                //llegar hasta el nodo del que seria el final de la palabra y para ese nodo mirar si luego tiene otro siguiente
                // con la letra que toca y sea 'terminal'
                String wordinboard = "";
                for(int i = begginingaddedword; i < begginingaddedword+move.word().length();++i){
                    //palabra que existia en la mesa
                    wordinboard = wordinboard.concat(board.getCellPiece(i,move.y()).letter());
                }
                Integer finalnode = wordValidator.getFinalNode(wordinboard);
                for(int j = 0; j < numletters;++j){
                    //probamos de ver si podemos avanzar en el dawg a esas letras
                    if(!wordValidator.nextNodeTerminal(finalnode,letters[j])) {
                        crossChecksHor[endaddedword+1][move.y()].set(j);//pone a 1 la letra que
                        //ha salido que no hace una palabra valida

                    }
                }

            }
        }
        else { //ponemos la palabra en vertical
            //y coord's, colocada en vertical asi q x la misma
            int begginingaddedword = move.y();
            /*while (board.isCellValid(move.x(), begginingaddedword-1) && !board.isCellEmpty(move.x(),begginingaddedword-1)) {
                begginingaddedword -= 1; //buscamos donde empieza la palabra que acabamos de poner
            }*/
            int endaddedword = move.y()+move.word().length();
            /*while (board.isCellValid(move.x(), endaddedword+1) && !board.isCellEmpty(move.x(),endaddedword+1)) {
                begginingaddedword += 1;//buscamos donde acaba la palabra que acabamos de poner
            }*/
            //corsscheck anterior (si se puede siquiera poner una antes)
            if (board.isCellValid(move.x(), begginingaddedword-1) && board.isCellEmpty(move.x(), begginingaddedword-1)) {
                Integer l = move.word().length()+1;
                String[] wordinboard = new String[l];//+1 pq hay que hacer hueco al principio para la letra a probar si va
                for(int i = begginingaddedword; i < begginingaddedword+l-1; ++i) wordinboard[i-begginingaddedword+1] = board.getCellPiece(move.x(),i).letter();
                //prueba a poner todas las letras del diccionario delante
                for(int i = 0; i < numletters;++i){
                    wordinboard[0] = letters[i];
                    String word = "";
                    for(int j = 0; j < wordinboard.length; j++){
                        word = word.concat(wordinboard[j]);
                    }
                    if(!wordValidator.runString(word)) {
                        crossChecksHor[move.x()][begginingaddedword-1].set(i);//pone a 1 la letra que
                        //ha salido que no hace una palabra valida
                    }
                }

            }
            //crosscheck posterior(si se puede obvio)
            if (board.isCellValid(move.x(), endaddedword+1) && board.isCellEmpty(move.x(), endaddedword+1)) {
                //hacer que lo de ponerlo detras sea eficiente consultarlo con el dawg
                //llegar hasta el nodo del que seria el final de la palabra y para ese nodo mirar si luego tiene otro siguiente
                // con la letra que toca y sea 'terminal'
                String wordinboard = "";
                for(int i = begginingaddedword; i < begginingaddedword+move.word().length();++i){
                    //palabra que existia en la mesa
                    wordinboard = wordinboard.concat(board.getCellPiece(move.x(),i).letter());
                }
                Integer finalnode = wordValidator.getFinalNode(wordinboard);
                for(int j = 0; j < numletters;++j){
                    //probamos de ver si podemos avanzar en el dawg a esas letras
                    if(!wordValidator.nextNodeTerminal(finalnode,letters[j])){
                        crossChecksHor[move.x()][endaddedword+1].set(j);//pone a 1 la letra que
                        //ha salido que no hace una palabra valida

                    }

                }
            }
        }
    }

}
