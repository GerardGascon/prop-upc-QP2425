package edu.upc.prop.scrabble.presenter.terminal.factories;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.presenter.terminal.BoardView;
import edu.upc.prop.scrabble.presenter.terminal.movements.MovementMaker;
import edu.upc.prop.scrabble.presenter.terminal.players.PlayerObject;

public class PlayerFactory {
    public static void createPlayer(PlayerObject playerObject, String name, boolean bot, PiecesConverter piecesConverter,
                                    PointCalculator pointCalculator, Board board, BoardView boardView) {
        Player player = new Player(name, bot);
        WordPlacer wordPlacer = new WordPlacer(player, board, boardView, pointCalculator);
        MovementMaker movementMaker = new MovementMaker(piecesConverter, wordPlacer);
        playerObject.configure(player, movementMaker);
    }
}
