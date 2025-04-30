package edu.upc.prop.scrabble.presenter.swing.screens.game.board;

import edu.upc.prop.scrabble.utils.Rand;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel {
    public BoardView(int size) {
        super();
        setLayout(new GridLayout(size, size, 2, 2));
        setBackground(new Color(0x50, 0x84, 0x6e));

        for (int i = 0; i < size * size; i++) {
            Rand rand = new Rand();
            BoardTile cell;
            if (rand.nextInt() % 2 == 0)
                cell = new BoardPieceTile();
            else
                cell = new BoardTile();
            int row = i / size;
            int col = i % size;
            cell.addActionListener(_ -> System.out.println("Clicked cell: " + row + ", " + col));
            add(cell);
        }
    }
}
