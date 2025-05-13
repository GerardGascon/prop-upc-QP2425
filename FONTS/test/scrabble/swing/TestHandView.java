package scrabble.swing;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.presenter.swing.screens.game.hand.HandPieceButton;
import edu.upc.prop.scrabble.presenter.swing.screens.game.hand.HandView;

import org.junit.After;
import org.junit.Test;
import scrabble.swing.utils.SwingTest;
import scrabble.swing.utils.TestWindow;

import javax.swing.*;

import static org.junit.Assert.*;

public class TestHandView extends SwingTest {
    private TestWindow<HandView> view;
    private Player player;

    private HandPieceButton getPieceButton(int index) {
        return (HandPieceButton) view.getPanel().getComponent(index);
    }

    @After
    public void tearDown() {
        view.close();
    }

    @Test
    public void testDisplayIniHand() {
        Piece pieceA = new Piece("A", 1);
        Piece pieceB = new Piece("B", 2);
        player = new Player("test", false);
        player.addPiece(pieceA);
        player.addPiece(pieceB);

        view = new TestWindow<>(200, 100, new HandView(player));
        assertEquals(2, view.getPanel().getComponentCount());

        HandPieceButton buttonA = getPieceButton(0);
        assertNotNull(buttonA);
        assertEquals("A", buttonA.getLetter());

        HandPieceButton buttonB = getPieceButton(1);
        assertNotNull(buttonB);
        assertEquals("B", buttonB.getLetter());
        finish();
    }

    @Test
    public void clickSelectPiece() {
        Piece pieceA = new Piece("A", 1);
        Piece pieceB = new Piece("B", 2);
        player = new Player("test", false);
        player.addPiece(pieceA);
        player.addPiece(pieceB);

        view = new TestWindow<>(200, 100, new HandView(player));

        JButton buttonA = getPieceButton(0);
        JButton buttonB = getPieceButton(1);
        queueClick(buttonA);
        queueClick(buttonB);

        doClick(buttonA);
        assertEquals("A", view.getPanel().getSelectedPiece());
        assertEquals(1, view.getPanel().getSelectedPiecePoints());

        doClick(buttonB);
        assertEquals("B", view.getPanel().getSelectedPiece()); // Now it should be B
        assertEquals(2, view.getPanel().getSelectedPiecePoints());
        finish();
    }

    @Test
    public void clickUnselectPiece() {
        Piece pieceF = new Piece("F", 5);
        player = new Player("test", false);
        player.addPiece(pieceF);
        view = new TestWindow<>(100, 100, new HandView(player));

        JButton buttonF = getPieceButton(0);
        queueClick(buttonF);
        queueClick(buttonF);

        doClick(buttonF);

        assertEquals("F", view.getPanel().getSelectedPiece());
        assertEquals(5, view.getPanel().getSelectedPiecePoints());

        doClick(buttonF);

        assertNull(view.getPanel().getSelectedPiece());
        assertEquals(0, view.getPanel().getSelectedPiecePoints());
        finish();
    }
}
