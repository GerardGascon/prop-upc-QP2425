package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.TileConverter;
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

        ret.append("     ");
        for (int j = 0; j < board.getSize(); j++) {
            ret.append((char) (j + (int) 'A')).append("  ");
        }
        ret.append("\n\n");

        for (int i = 0; i < board.getSize(); i++) {
            if (i + 1 < 10)
                ret.append(" ").append(i + 1).append("   ");
            else
                ret.append(i + 1).append("   ");

            for (int j = 0; j < board.getSize(); j++) {
                if (board.getCell(j, i) != null) {
                    ret.append(board.getCell(j, i)).append("  ");
                    continue;
                }

                if (board.getTileType(j, i) != null) {
                    ret.append(TileConverter.convert(board.getTileType(j, i))).append(" ");
                    continue;
                }

                if (i == j && i == board.getSize() / 2){
                    ret.append("*  ");
                    continue;
                }

                ret.append(".  ");
            }
            ret.append("\n");
        }

        return ret.toString();
    }
}
