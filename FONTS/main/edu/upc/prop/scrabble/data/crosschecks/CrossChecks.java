package edu.upc.prop.scrabble.data.crosschecks;

import java.util.BitSet;

/**
 * Classe que representa els crosschecks actuals del board.
 * Un crosscheck és una casella que emmagatzema informació sobre
 * quines peces no es poden col·locar a aquella casella tenint
 * en compte els extrems de paraules al tauler i en els eixos
 * horitzontal i vertical.
 * @author Albert Usero && Felipe Martínez
 */
public abstract class CrossChecks {
    /**
     * Matriu de BitSets per emmagatzemar i representar els CrossChecks.
     * Cada BitSet representa les peces permeses o no per a una posició.
     * 0 permesa, 1 no permesa. La posició dels bits al BitSet la lletra:
     * 0-a,1-b,2-c etc. Caràcters especials de certs idiomes al final del bit set.
     */
    private final BitSet[][] crossChecks;

    /**
     * Mida del tauler sobre el qual s'emmagatzemen els crosschecks
     */
    protected final int boardSize;

    /**
     * Crea uns nous CrossChecks per la mida de tauler donada.
     * Inicialitzats perquè qualsevol lletra es pot col·locar a qualsevol posició.
     * @param boardSize Mida del tauler del board sobre el qual s'han de crear els CrossChecks
     */
    public CrossChecks(int boardSize) {
        this.boardSize = boardSize;
        crossChecks = new BitSet[boardSize][boardSize];

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                crossChecks[row][col] = new BitSet(getNumberOfLetters());
            }
        }
    }
    /**
     * Obté el conjunt de peces vàlides per aquest idioma.
     * @return Array de Strings amb totes les peces possibles
     */
    public abstract String[] getLetters();
    /**
     * Obté el nombre total de peces diferents per a aquest idioma.
     * @return Nombre total de peces
     */
    public abstract int getNumberOfLetters();
    /**
     * Determina si es pot col·locar una peça específica en una posició del tauler.
     * @param x Coordenada x de la posició a comprovar
     * @param y Coordenada y de la posició a comprovar
     * @param letter Peça a verificar
     * @return True si es pot col·locar la peça, False altrament
     */
    public abstract Boolean ableToPlace(int x, int y, String letter);
    /**
     * Obté el BitSet de crosschecks per a una posició específica del tauler.
     * @param x Coordenada x de la posició a accedir
     * @param y Coordenada y de la posició a accedir
     * @return BitSet que representa les peçes permeses/denegades
     */
    public BitSet getCrossCheck(int x, int y) {
        return crossChecks[x][y];
    }
    /**
     * Estableix a cert valor (1 no permès, 0 permès) un bit d'un crosscheck
     * específic per a una posició del tauler.
     * @param x Coordenada x de la posició al tauler
     * @param y Coordenada y de la posició al tauler
     * @param value Índex de la peça a marcar com a permès/denegat
     */
    public void setCrossCheck(int x, int y, int value) {
        crossChecks[x][y].set(value);
    }
    /**
     * Estableix tots els bits de crosscheck per a una posició específica.
     * @param x Coordenada x de la posició al tauler
     * @param y Coordenada y de la posició al tauler
     * @param bits BitSet complet per a substituir els valors actuals
     */
    private void setAllBits(int x, int y, BitSet bits) { this.crossChecks[x][y] = bits;}
    /**
     * Crea una còpia dels crosschecks actuals.
     * @return Nova instància de CrossChecks amb la mateixa configuració que l'actual.
     */
    protected abstract CrossChecks copy();
    /**
     * Rota els crosschecks 90 graus en sentit horari.
     * @return Nova instància de CrossChecks amb la matriu rotada
     */
    public CrossChecks rotate() {
        CrossChecks copy = copy();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                copy.setAllBits(boardSize - col - 1,row, getCrossCheck(row, col));
            }
        }
        return copy;
    }
}