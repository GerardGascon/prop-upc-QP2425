package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.domain.board.IBoard;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;

public class BoardView extends SceneObject implements IBoard {
    @Override
    public void updateBoard(Board board) {
        String s = buildBoardString(board);
        System.out.print(s);
    }

    private String buildBoardString(Board board) {
        StringBuilder ret = new StringBuilder();

        ret.append(printBoardLetterCoord(board));

        for (int i = 0; i < board.getSize(); i++) {
            ret.append(getBoardNumberCoord(i));

            for (int j = 0; j < board.getSize(); j++) {
                if (!board.isCellEmpty(j, i)) {
                    ret.append(getCellPiece(board, j, i)).append("  ");
                    continue;
                }

                if (board.isPremiumTile(j, i)) {
                    ret.append(getPremiumTile(board, j, i)).append(" ");
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

    private String getCellPiece(Board board, int j, int i) {
        return TileConverter.convert(board.getCellPiece(j, i));
    }

    private String getPremiumTile(Board board, int j, int i) {
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

    private String printBoardLetterCoord(Board board) {
        StringBuilder ret = new StringBuilder();
        ret.append("     ");
        for (int j = 0; j < board.getSize(); j++) {
            ret.append((char) (j + (int) 'A')).append("  ");
        }
        ret.append("\n\n");
        return ret.toString();
    }
}
