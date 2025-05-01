package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.PremiumTileType;
import edu.upc.prop.scrabble.domain.board.IBoard;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;
import edu.upc.prop.scrabble.utils.Pair;

public class BoardView extends SceneObject implements IBoard {
    private Pair<String, Integer>[][] board;

    @SuppressWarnings("unchecked")
    public void setSize(int size) {
        board = (Pair<String, Integer>[][]) new Pair[size][size];
    }

    @Override
    public void updateBoard() {
        String s = buildBoardString();
        System.out.print(s);
    }

    @Override
    public void updateCell(String piece, int points, int x, int y) {
        board[y][x] = new Pair<>(piece, points);
    }

    @Override
    public void setPremiumTile(PremiumTileType type, int x, int y) {

    }

    private String buildBoardString() {
        StringBuilder ret = new StringBuilder();

        ret.append(printBoardLetterCoord());

        for (int i = 0; i < board.length; i++) {
            ret.append(getBoardNumberCoord(i));

            for (int j = 0; j < board.length; j++) {
                if (board[j][i] != null) {
                    ret.append(board[j][i].first()).append("  ");
                    continue;
                }

//                if (board.isPremiumTile(j, i)) {
//                    ret.append(getPremiumTile(board, j, i)).append(" ");
//                    continue;
//                }

                if (i == board.length / 2 && j == board.length / 2) {
                    ret.append("*  ");
                    continue;
                }

                ret.append(".  ");
            }
            ret.append("\n");
        }

        return ret.toString();
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
        for (int j = 0; j < board.length; j++) {
            ret.append((char) (j + (int) 'A')).append("  ");
        }
        ret.append("\n\n");
        return ret.toString();
    }
}
