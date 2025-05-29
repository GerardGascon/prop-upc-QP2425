package edu.upc.prop.scrabble.presenter.swing.screens.game.board;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.PremiumTileType;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;
import edu.upc.prop.scrabble.domain.board.IBoard;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.*;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.premium.*;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Vista del tauler de Scrabble.
 * <p>
 * Gestiona la graella de fitxes, les peces temporals i les fitxes prèmium.
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
     * Les caselles de coordenades mostren la fila i columna amb lletres o números.
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
     * @return cadena que representa la fila (o buit si és bord)
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
     * Canvia la fitxa en la posició (x,y) per la nova BoardTile proporcionada.
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
     * @throws RuntimeException si no es troba cap component del tipus i índex indicats
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

    /**
     * Retorna la paraula temporal formada per les peces col·locades temporalment.
     *
     * @return paraula formada
     * @throws RuntimeException si el cas no està suportat
     */
    public Movement getTemporalWord() {
        if (temporalPieces.isEmpty())
            return null;

        if (temporalPieces.size() > 1) {
            if (temporalPieces.get(0).getPosition().x == temporalPieces.get(1).getPosition().x) {
                return getVerticalTemporalWord();
            }
            return getHorizontalTemporalWord();
        }

        if (hasAdjacentVerticalPieces())
            return getVerticalTemporalWord();
        return getHorizontalTemporalWord();
    }

    private boolean hasAdjacentVerticalPieces() {
        return getVerticalTiles().length > 1;
    }

    /**
     * Retorna la paraula formada horitzontalment per les peces temporals i fixes.
     *
     * @return paraula horitzontal
     */
    private Movement getHorizontalTemporalWord() {
        StringBuilder word = new StringBuilder();
        List<BoardTile> sortedTiles = getSortedTiles(true);
        for (BoardTile boardTile : sortedTiles){
            if (boardTile instanceof BoardTemporalPieceTile temporalWord) {
                word.append(temporalWord.getLetter());
            } else if (boardTile instanceof BoardPieceTile pieceWord) {
                word.append(pieceWord.getLetter());
            }
        }
        Vector2 startPos = sortedTiles.getFirst().getPosition();
        return new Movement(word.toString(), startPos.x, startPos.y, Direction.Horizontal);
    }

    /**
     * Retorna la paraula formada verticalment per les peces temporals i fixes.
     *
     * @return paraula vertical
     */
    private Movement getVerticalTemporalWord() {
        StringBuilder word = new StringBuilder();
        List<BoardTile> sortedTiles = getSortedTiles(false);
        for (BoardTile boardTile : sortedTiles){
            if (boardTile instanceof BoardTemporalPieceTile temporalWord) {
                word.append(temporalWord.getLetter());
            } else if (boardTile instanceof BoardPieceTile pieceWord) {
                word.append(pieceWord.getLetter());
            }
        }
        Vector2 startPos = sortedTiles.getFirst().getPosition();
        return new Movement(word.toString(), startPos.x, startPos.y, Direction.Vertical);
    }

    /**
     * Retorna les fitxes horitzontals adjacents a la peça temporal.
     *
     * @return array de fitxes horitzontals
     */
    private BoardTile[] getHorizontalTiles() {
        List<BoardTile> tiles = new ArrayList<>();

        BoardTile temporalPiece = temporalPieces.get(0);
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

    /**
     * Retorna les fitxes verticals adjacents a la peça temporal.
     *
     * @return array de fitxes verticals
     */
    private BoardTile[] getVerticalTiles() {
        List<BoardTile> tiles = new ArrayList<>();

        BoardTile temporalPiece = temporalPieces.get(0);
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

    /**
     * Retorna la llista de fitxes ordenades horitzontalment o verticalment.
     *
     * @param horizontal true si és horitzontal, false si és vertical
     * @return llista ordenada de fitxes
     */
    private List<BoardTile> getSortedTiles(boolean horizontal) {
        if (horizontal)
            return Arrays.stream(getHorizontalTiles())
                    .sorted(Comparator.comparingInt(obj -> obj.getPosition().x)).toList();
        return Arrays.stream(getVerticalTiles())
                .sorted(Comparator.comparingInt(obj -> obj.getPosition().y)).toList();
    }

    /**
     * Comprova si la posició (x,y) és vàlida per col·locar-hi una peça temporal.
     * Si no hi ha peces temporals, qualsevol posició és vàlida;
     * en cas contrari, ha de ser adjacent i alineada amb les peces ja col·locades temporalment.
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

    /**
     * Comprova si la posició (x,y) és adjacent a altres peces ja col·locades temporalment.
     *
     * @param x coordenada x
     * @param y coordenada y
     * @return true si és adjacent
     */
    private boolean isAdjacentToOtherPlacedWords(int x, int y) {
        Direction dir;
        if (temporalPieces.size() == 1)
            dir = temporalPieces.get(0).getPosition().x == x ? Direction.Vertical : Direction.Horizontal;
        else
            dir = temporalPieces.get(0).getPosition().x == temporalPieces.get(1).getPosition().x ? Direction.Vertical : Direction.Horizontal;

        if (dir == Direction.Vertical){
            int startY = Math.min(y, getSortedTiles(false).get(0).getPosition().y);
            int endY = Math.max(y, getSortedTiles(false).get(0).getPosition().y);

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

        int startX = Math.min(x, getSortedTiles(true).get(0).getPosition().x);
        int endX = Math.max(x, getSortedTiles(true).get(0).getPosition().x);

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

    /**
     * Comprova si la posició (x,y) està alineada amb les peces ja col·locades temporalment.
     *
     * @param x coordenada x
     * @param y coordenada y
     * @return true si està alineada
     */
    private boolean isInLineWithAlreadyPlacedWords(int x, int y) {
        Direction dir;
        if (temporalPieces.size() == 1)
            dir = temporalPieces.get(0).getPosition().x == x ? Direction.Vertical : Direction.Horizontal;
        else
            dir = temporalPieces.get(0).getPosition().x == temporalPieces.get(1).getPosition().x ? Direction.Vertical : Direction.Horizontal;

        if (dir == Direction.Horizontal)
            return temporalPieces.get(0).getPosition().y == y;
        return temporalPieces.get(0).getPosition().x == x;
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
            case QuadrupleWord:
                changeTile(new BoardQuadrupleWordTile(x, y, handView, this, blankPieceSelector), x, y);
                break;
            case TripleWord:
                changeTile(new BoardTripleWordTile(x, y, handView, this, blankPieceSelector), x, y);
                break;
            case DoubleWord:
                changeTile(new BoardDoubleWordTile(x, y, handView, this, blankPieceSelector), x, y);
                break;
            case QuadrupleLetter:
                changeTile(new BoardQuadrupleLetterTile(x, y, handView, this, blankPieceSelector), x, y);
                break;
            case TripleLetter:
                changeTile(new BoardTripleLetterTile(x, y, handView, this, blankPieceSelector), x, y);
                break;
            case DoubleLetter:
                changeTile(new BoardDoubleLetterTile(x, y, handView, this, blankPieceSelector), x, y);
                break;
        }
    }

    public void startPlace() {
        for (BoardCell[] boardCells : board) {
            for (BoardCell boardCell : boardCells) {
                boardCell.getTile().setEnabled(true);
            }
        }
    }

    public void endPlace() {
        for (BoardTemporalPieceTile tile : temporalPieces) {
            Vector2 pos = tile.getPosition();
            board[pos.x][pos.y].removeTemporalTile();
        }
        temporalPieces.clear();

        for (BoardCell[] boardCells : board) {
            for (BoardCell boardCell : boardCells) {
                boardCell.getTile().setEnabled(false);
            }
        }
    }

    public Movement getPlacement() {
        return getTemporalWord();
    }
}
