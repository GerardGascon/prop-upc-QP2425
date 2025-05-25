package edu.upc.prop.scrabble.presenter.swing.screens.game.board;

import edu.upc.prop.scrabble.data.board.PremiumTileType;
import edu.upc.prop.scrabble.domain.board.IBoard;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.*;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.premium.*;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;
import edu.upc.prop.scrabble.utils.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Vista del tauler de Scrabble.
 * <p>
 * Gestiona la graella de fitxes, les peces temporals, i les fitxes premi.
 * </p>
 *
 * @author Gerard Gascón
 */
public class BoardView extends JPanel implements IBoard {
    /**
     * Mida (nombre de caselles per fila i columna).
     */
    private final int size;

    /**
     * Vista de la mà per gestionar la selecció de peces.
     */
    private final IHandView handView;

    /**
     * Selector per fitxes en blanc.
     */
    private final IBlankPieceSelector blankPieceSelector;

    private final BoardCell[][] board;

    /**
     * Llista de peces temporals col·locades al tauler però no confirmades.
     */
    private final List<BoardTemporalPieceTile> temporalPieces = new ArrayList<>();

    /**
     * Constructor del tauler.
     *
     * @param size mida del tauler
     * @param handView vista de la mà
     * @param blankPieceSelector selector per fitxes en blanc
     */
    public BoardView(int size, IHandView handView, IBlankPieceSelector blankPieceSelector) {
        super();
        setOpaque(false);
        setLayout(new GridLayout(size + 2, size + 2, 2, 2));
        setBackground(new Color(0x51, 0x78, 0x85));

        this.size = size;
        this.handView = handView;
        this.blankPieceSelector = blankPieceSelector;

        board = new BoardCell[size][size];

        generateEmptyTiles(size, handView);
    }

    /**
     * Genera les caselles buides i les caselles de coordenades del tauler.
     * Les caselles de coordenades són la fila i columna amb lletres o números.
     *
     * @param size mida del tauler
     * @param handView vista de la mà
     */
    private void generateEmptyTiles(int size, IHandView handView) {
        for (int i = 0, j = 0; i < (size + 2) * (size + 2); i++) {
            int borderRow = i / (size + 2);
            int borderCol = i % (size + 2);

            if (borderRow == 0 || borderRow == size + 1) {
                add(new BoardCoordinateTile(getBorderCol(borderCol)));
                continue;
            }

            if (borderCol == 0 || borderCol == size + 1) {
                add(new BoardCoordinateTile(getBorderRow(borderRow)));
                continue;
            }

            int y = j / size;
            int x = j % size;
            j++;

            BoardCell cell = new BoardCell();
            cell.setTile(new BoardEmptyTile(x, y, handView, this, blankPieceSelector));
            add(cell);
            board[x][y] = cell;
        }
    }

    /**
     * Personalitza el pintat del tauler.
     *
     * @param g context gràfic
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        super.paintComponent(g);
        g2.dispose();
    }

    /**
     * Retorna la cadena que representa la fila per la coordenada.
     *
     * @param row número de fila
     * @return cadena de la fila (o buit si és bord)
     */
    private String getBorderRow(int row) {
        if (row == 0 || row == size + 1)
            return "";
        return Integer.toString(row);
    }

    /**
     * Retorna la cadena que representa la columna per la coordenada.
     *
     * @param col número de columna
     * @return cadena amb lletra (o buit si és bord)
     */
    private String getBorderCol(int col) {
        if (col == 0 || col == size + 1)
            return "";
        return String.valueOf((char)('A' + col - 1));
    }

    /**
     * Canvia la fitxa en la posició (x,y) pel nou BoardTile proporcionat.
     *
     * @param tile nova fitxa
     * @param x coordenada x
     * @param y coordenada y
     */
    public void changeTile(BoardTile tile, int x, int y) {
        BoardCell cell = getCell(x, y);
        cell.setTile(tile);
    }

    /**
     * Retorna la cel·la corresponent a la posició (x,y).
     *
     * @param x coordenada x
     * @param y coordenada y
     * @return BoardCell a la posició indicada
     */
    public BoardCell getCell(int x, int y) {
        return getComponentOfType(BoardCell.class, y * size + x);
    }

    /**
     * Retorna el component de tipus clazz en la posició index,
     * filtrant només components de tipus clazz.
     *
     * @param <TComponent> tipus del component
     * @param clazz classe del component
     * @param index índex dins dels components d'aquest tipus
     * @return component sol·licitat
     */
    public <TComponent extends Component> TComponent getComponentOfType(Class<TComponent> clazz, int index) {
        int count = 0;
        for (Component comp : getComponents()) {
            if (clazz.isInstance(comp)) {
                if (count == index)
                    return clazz.cast(comp);
                count++;
            }
        }
        throw new RuntimeException("No component found for " + clazz.getName());
    }

    /**
     * Col·loca una peça temporal al tauler a la posició (x,y),
     * si la cel·la està disponible i la posició és vàlida segons les regles.
     *
     * @param piece lletra de la peça
     * @param points punts de la peça
     * @param x coordenada x
     * @param y coordenada y
     */
    public void placeTemporalPiece(String piece, int points, int x, int y) {
        BoardCell cell = getCell(x, y);
        if (isCellAvailable(cell) && isPositionValid(x, y)) {
            BoardTemporalPieceTile boardTile = new BoardTemporalPieceTile(piece, points, x, y, handView, this, blankPieceSelector);
            temporalPieces.add(boardTile);
            cell.setTile(boardTile);
            handView.piecePlaced();
        }
    }

    public String getTemporalWord() {
        if (temporalPieces.size() > 1) {
            if (temporalPieces.get(0).getPosition().x == temporalPieces.get(1).getPosition().x) {
                return getVerticalTemporalWord();
            }
            return getHorizontalTemporalWord();
        }

        // TODO: Test this case
        throw new RuntimeException("Still not supported");
    }

    private String getHorizontalTemporalWord() {
        StringBuilder word = new StringBuilder();
        List<BoardTile> sortedTiles = getSortedTiles(true);
        for (BoardTile boardTile : sortedTiles){
            if (boardTile instanceof BoardTemporalPieceTile temporalWord) {
                word.append(temporalWord.getLetter());
            } else if (boardTile instanceof BoardPieceTile pieceWord) {
                word.append(pieceWord.getLetter());
            }
        }
        return word.toString();
    }

    private String getVerticalTemporalWord() {
        StringBuilder word = new StringBuilder();
        List<BoardTile> sortedTiles = getSortedTiles(false);
        for (BoardTile boardTile : sortedTiles){
            if (boardTile instanceof BoardTemporalPieceTile temporalWord) {
                word.append(temporalWord.getLetter());
            } else if (boardTile instanceof BoardPieceTile pieceWord) {
                word.append(pieceWord.getLetter());
            }
        }
        return word.toString();
    }

    private BoardTile[] getHorizontalTiles() {
        List<BoardTile> tiles = new ArrayList<>();

        BoardTile temporalPiece = temporalPieces.getFirst();
        for (int i = temporalPiece.getPosition().x; i < size; i++) {
            BoardTile tile = board[i][temporalPiece.getPosition().y].getTile();
            if (tile instanceof BoardTemporalPieceTile || tile instanceof BoardPieceTile) {
                tiles.add(tile);
                continue;
            }

            break;
        }
        for (int i = temporalPiece.getPosition().x - 1; i >= 0; i--) {
            BoardTile tile = board[i][temporalPiece.getPosition().y].getTile();
            if (tile instanceof BoardTemporalPieceTile || tile instanceof BoardPieceTile) {
                tiles.add(tile);
                continue;
            }

            break;
        }

        return tiles.toArray(BoardTile[]::new);
    }

    private BoardTile[] getVerticalTiles() {
        List<BoardTile> tiles = new ArrayList<>();

        BoardTile temporalPiece = temporalPieces.getFirst();
        for (int i = temporalPiece.getPosition().y; i < size; i++) {
            BoardTile tile = board[temporalPiece.getPosition().x][i].getTile();
            if (tile instanceof BoardTemporalPieceTile || tile instanceof BoardPieceTile) {
                tiles.add(tile);
                continue;
            }

            break;
        }
        for (int i = temporalPiece.getPosition().y - 1; i >= 0; i--) {
            BoardTile tile = board[temporalPiece.getPosition().x][i].getTile();
            if (tile instanceof BoardTemporalPieceTile || tile instanceof BoardPieceTile) {
                tiles.add(tile);
                continue;
            }

            break;
        }

        return tiles.toArray(BoardTile[]::new);
    }

    private List<BoardTile> getSortedTiles(boolean horizontal) {
        if (horizontal)
            return Arrays.stream(getHorizontalTiles())
                    .sorted(Comparator.comparingInt(obj -> obj.getPosition().x)).toList();
        return Arrays.stream(getVerticalTiles())
                .sorted(Comparator.comparingInt(obj -> obj.getPosition().y)).toList();
    }

    /**
     * Comprova si la posició és vàlida segons la lògica del joc:
     * si no hi ha peces temporals, qualsevol posició és vàlida;
     * sinó, ha de ser adjacent i en línia.
     *
     * @param x coordenada x
     * @param y coordenada y
     * @return true si la posició és vàlida
     */
    private boolean isPositionValid(int x, int y) {
        if (temporalPieces.isEmpty())
            return true;

        if (isAdjacentToOtherPlacedWords(x, y) && isInLineWithAlreadyPlacedWords(x, y))
            return true;

        return false;
    }

    private boolean isAdjacentToOtherPlacedWords(int x, int y) {
        Direction dir;
        if (temporalPieces.size() == 1)
            dir = temporalPieces.get(0).getPosition().x == x ? Direction.Vertical : Direction.Horizontal;
        else
            dir = temporalPieces.get(0).getPosition().x == temporalPieces.get(1).getPosition().x ? Direction.Vertical : Direction.Horizontal;

        if (dir == Direction.Vertical){
            int startY = Math.min(y, getSortedTiles(false).getFirst().getPosition().y);
            int endY = Math.max(y, getSortedTiles(false).getFirst().getPosition().y);

            boolean passedBlank = false;
            for (int i = startY; i <= endY; i++) {
                BoardTile tile = board[x][i].getTile();
                if (tile instanceof BoardTemporalPieceTile || tile instanceof BoardPieceTile) {
                    continue;
                }

                if (passedBlank)
                    return false;

                passedBlank = true;
            }

            return true;
        }

        int startX = Math.min(x, getSortedTiles(false).getFirst().getPosition().x);
        int endX = Math.max(x, getSortedTiles(false).getFirst().getPosition().x);

        boolean passedBlank = false;
        for (int i = startX; i <= endX; i++) {
            BoardTile tile = board[i][y].getTile();
            if (tile instanceof BoardTemporalPieceTile || tile instanceof BoardPieceTile) {
                continue;
            }

            if (passedBlank)
                return false;

            passedBlank = true;
        }

        return true;
    }

    private boolean isInLineWithAlreadyPlacedWords(int x, int y) {
        Direction dir;
        if (temporalPieces.size() == 1)
            dir = temporalPieces.get(0).getPosition().x == x ? Direction.Vertical : Direction.Horizontal;
        else
            dir = temporalPieces.get(0).getPosition().x == temporalPieces.get(1).getPosition().x ? Direction.Vertical : Direction.Horizontal;

        if (dir == Direction.Horizontal)
            return temporalPieces.getFirst().getPosition().y == y;
        return temporalPieces.getFirst().getPosition().x == x;
    }

    /**
     * Comprova si una cel·la està disponible per col·locar-hi una peça (no ocupada per peces ja col·locades).
     *
     * @param cell cel·la a comprovar
     * @return true si està disponible
     */
    private static boolean isCellAvailable(BoardCell cell) {
        return cell.getTile().getClass() != BoardPieceTile.class && cell.getTile().getClass() != BoardTemporalPieceTile.class;
    }

    /**
     * Actualitza el tauler (mètode pendent d'implementar).
     */
    @Override
    public void updateBoard() {

    }

    /**
     * Actualitza la cel·la (x,y) amb una peça definitiva.
     *
     * @param piece lletra de la peça
     * @param points punts de la peça
     * @param x coordenada x
     * @param y coordenada y
     */
    @Override
    public void updateCell(String piece, int points, int x, int y) {
        changeTile(new BoardPieceTile(piece, points, x, y, handView, this, blankPieceSelector), x, y);
    }

    /**
     * Assigna una fitxa prèmium a la posició (x,y).
     * La fitxa central té un tractament especial.
     *
     * @param type tipus de fitxa prèmium
     * @param x coordenada x
     * @param y coordenada y
     */
    @Override
    public void setPremiumTile(PremiumTileType type, int x, int y) {
        if (x == y && x == size / 2){
            changeTile(new BoardCenterTile(x, y, handView, this, blankPieceSelector), x, y);
            return;
        }

        switch (type) {
            case QuadrupleWord -> changeTile(new BoardQuadrupleWordTile(x, y, handView, this, blankPieceSelector), x, y);
            case TripleWord -> changeTile(new BoardTripleWordTile(x, y, handView, this, blankPieceSelector), x, y);
            case DoubleWord -> changeTile(new BoardDoubleWordTile(x, y, handView, this, blankPieceSelector), x, y);
            case QuadrupleLetter -> changeTile(new BoardQuadrupleLetterTile(x, y, handView, this, blankPieceSelector), x, y);
            case TripleLetter -> changeTile(new BoardTripleLetterTile(x, y, handView, this, blankPieceSelector), x, y);
            case DoubleLetter -> changeTile(new BoardDoubleLetterTile(x, y, handView, this, blankPieceSelector), x, y);
        }
    }
}
