package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.domain.IBoard;

public class BoardView implements IBoard {
    private final Board board;

    public BoardView(Board board) {
        this.board = board;
    }

    @Override
    public void UpdateBoard() {
        String s = buildBoardString();
        System.out.print(s);
    }

    private String buildBoardString() {
        StringBuilder ret = new StringBuilder();

        ret.append(printBoardLetterCoord());

        for (int i = 0; i < board.getSize(); i++) {
            ret.append(getBoardNumberCoord(i));

            for (int j = 0; j < board.getSize(); j++) {
                if (!board.isCellEmpty(j, i)) {
                    ret.append(getCellPiece(j, i)).append("  ");
                    continue;
                }

                if (board.isPremiumTile(j, i)) {
                    ret.append(getPremiumTile(j, i)).append(" ");
                    continue;
                }

                if (board.isCenter(j, i)){
                    ret.append("*  ");
                    continue;
                }

                ret.append(".  ");
            }
            ret.append("\n");
        }

        return ret.toString();
    }

    private String getCellPiece(int j, int i) {
        return TileConverter.convert(board.getCellPiece(j, i));
    }

    private String getPremiumTile(int j, int i) {
        return TileConverter.convert(board.getPremiumTileType(j, i));
    }

    private static String getBoardNumberCoord(int index) {
        StringBuilder ret = new StringBuilder();
        if (index + 1 < 10)
            ret.append(" ").append(index + 1).append("   ");
        else
            ret.append(index + 1).append("   ");
        return ret.toString();
    }

    private String printBoardLetterCoord() {
        StringBuilder ret = new StringBuilder();
        ret.append("     ");
        for (int j = 0; j < board.getSize(); j++) {
            ret.append((char) (j + (int) 'A')).append("  ");
        }
        ret.append("\n\n");
        return ret.toString();
    }
}
