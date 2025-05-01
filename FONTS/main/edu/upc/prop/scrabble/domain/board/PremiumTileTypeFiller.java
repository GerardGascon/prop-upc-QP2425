package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.PremiumTileType;

public class PremiumTileTypeFiller {
    private final Board board;
    private final IBoard view;

    public PremiumTileTypeFiller(Board board, IBoard view) {
        this.board = board;
        this.view = view;
    }

    public void run() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.isPremiumTile(i, j)) {
                    view.setPremiumTile(board.getPremiumTileType(i, j), i, j);
                }
            }
        }

        view.setPremiumTile(PremiumTileType.DoubleWord, board.getSize() / 2, board.getSize() / 2);
    }
}
