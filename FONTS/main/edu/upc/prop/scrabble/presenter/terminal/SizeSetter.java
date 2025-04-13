package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.data.board.BoardType;

import static java.lang.Math.abs;

public class SizeSetter {

    BoardType size = BoardType.Junior;

    //Modificadores
    public void next() {
        switch (size) {
            case Junior:
                size = BoardType.Standard;
                break;
            case Standard:
                size = BoardType.Super;
                break;
            case Super:
                size = BoardType.Junior;
                break;
        }
        print_interface();
    }

    public void previous() {
        switch (size) {
            case Junior:
                size = BoardType.Super;
                break;
            case Standard:
                size = BoardType.Junior;
                break;
            case Super:
                size = BoardType.Standard;
                break;
        }
        print_interface();
    }

    //Visualize
    public void print_interface() {
        String Up = " ________________________________________\n";
        String Up2 = "|                                        |\n";
        String Mid = switch (size) {
            case Junior    -> "|    |Junior|    Standard     Super      |\n";
            case Standard   -> "|     Junior    |Standard|    Super      |\n";
            case Super -> "|     Junior     Standard    |Super|     |\n";
            default -> "";
        };
        String Down = "|________________________________________|\n";
        System.out.println(Up + Up2 + Mid + Down);
    }

    //Consultores
    public BoardType getBoardSize() {
        return size;
    }
}
