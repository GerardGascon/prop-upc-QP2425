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

import java.util.Arrays;

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

        HandView handView = new HandView(false);
        handView.showPieces(player.getHand());
        view = new TestWindow<>(200, 100, handView);
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
    public void clickSelectPieceAllow1() {
        Piece pieceA = new Piece("A", 1);
        Piece pieceB = new Piece("B", 2);
        player = new Player("test", false);
        player.addPiece(pieceA);
        player.addPiece(pieceB);

        HandView handView = new HandView(false);
        handView.showPieces(player.getHand());
        view = new TestWindow<>(200, 100, handView);

        JButton buttonA = getPieceButton(0);
        JButton buttonB = getPieceButton(1);
        queueClick(buttonA);
        queueClick(buttonB);

        doClick(buttonA);
        String[] selectedA = view.getPanel().getSelectedPiece();
        assertNotNull(selectedA);
        assertEquals(1, selectedA.length);
        assertEquals("A", selectedA[0]);
        assertEquals(1, view.getPanel().getSelectedPiecePoints());

        doClick(buttonB);
        String[] selectedB = view.getPanel().getSelectedPiece();
        assertNotNull(selectedB);
        assertEquals(1, selectedB.length);
        assertEquals("B", selectedB[0]);
        assertEquals(2, view.getPanel().getSelectedPiecePoints());
        finish();
    }

    @Test
    public void clickUnselectPieceAllow1() {
        Piece pieceF = new Piece("F", 5);
        player = new Player("test", false);
        player.addPiece(pieceF);

        HandView handView = new HandView(false);
        handView.showPieces(player.getHand());
        view = new TestWindow<>(200, 100, handView);

        JButton buttonF = getPieceButton(0);
        queueClick(buttonF);
        queueClick(buttonF);

        doClick(buttonF);
        String[] selectedF = view.getPanel().getSelectedPiece();
        assertNotNull(selectedF);
        assertEquals(1, selectedF.length);
        assertEquals("F", selectedF[0]);
        assertEquals(5, view.getPanel().getSelectedPiecePoints());

        doClick(buttonF);

        assertNull(view.getPanel().getSelectedPiece());
        assertEquals(0, view.getPanel().getSelectedPiecePoints());
        finish();
    }

    @Test
    public void clickSelectAllowMore() {
        Piece pieceA = new Piece("A", 1);
        Piece pieceB = new Piece("B", 2);
        Piece pieceC = new Piece("C", 3);
        player = new Player("test", false);
        player.addPiece(pieceA);
        player.addPiece(pieceB);
        player.addPiece(pieceC);

        HandView handView = new HandView(true);
        handView.showPieces(player.getHand());
        view = new TestWindow<>(300, 100, handView);
        JButton buttonA = getPieceButton(0);
        JButton buttonB = getPieceButton(1);
        JButton buttonC = getPieceButton(2);
        doClick(buttonA);
        String[] selected1 = view.getPanel().getSelectedPiece();
        assertNotNull(selected1);
        assertEquals(1, selected1.length);
        assertTrue(Arrays.asList(selected1).contains("A"));
        assertEquals(1, view.getPanel().getSelectedPiecePoints());

        doClick(buttonB);
        String[] selected2 = view.getPanel().getSelectedPiece();
        assertNotNull(selected2);
        assertEquals(2, selected2.length);
        assertTrue(Arrays.asList(selected2).contains("A"));
        assertTrue(Arrays.asList(selected2).contains("B"));
        assertEquals(1 + 2, view.getPanel().getSelectedPiecePoints());

        doClick(buttonC);
        String[] selected3 = view.getPanel().getSelectedPiece();
        assertNotNull(selected3);
        assertEquals(3, selected3.length);
        assertTrue(Arrays.asList(selected3).contains("A"));
        assertTrue(Arrays.asList(selected3).contains("B"));
        assertTrue(Arrays.asList(selected3).contains("C"));
        assertEquals(1 + 2 + 3, view.getPanel().getSelectedPiecePoints());

        finish();
    }

    @Test
    public void clickSelectAndUnselectPiecesMultiMode() {
        Piece pieceA = new Piece("A", 1);
        Piece pieceB = new Piece("B", 2);
        Piece pieceC = new Piece("C", 3);
        player = new Player("test", false);
        player.addPiece(pieceA);
        player.addPiece(pieceB);
        player.addPiece(pieceC);

        HandView handView = new HandView(true);
        handView.showPieces(player.getHand());
        view = new TestWindow<>(300, 100, handView);

        JButton buttonA = getPieceButton(0);
        JButton buttonB = getPieceButton(1);
        JButton buttonC = getPieceButton(2);

        doClick(buttonA);
        doClick(buttonB);
        doClick(buttonC);

        doClick(buttonB);
        String[] selectedAfterDeselectB = view.getPanel().getSelectedPiece();
        assertNotNull(selectedAfterDeselectB);
        assertEquals(2, selectedAfterDeselectB.length);
        assertTrue(Arrays.asList(selectedAfterDeselectB).contains("A"));
        assertFalse(Arrays.asList(selectedAfterDeselectB).contains("B")); // B should be gone
        assertTrue(Arrays.asList(selectedAfterDeselectB).contains("C"));
        assertEquals(1 + 3, view.getPanel().getSelectedPiecePoints());

        doClick(buttonA);
        String[] selectedAfterDeselectA = view.getPanel().getSelectedPiece();
        assertNotNull(selectedAfterDeselectA);
        assertEquals(1, selectedAfterDeselectA.length);
        assertFalse(Arrays.asList(selectedAfterDeselectA).contains("A"));
        assertTrue(Arrays.asList(selectedAfterDeselectA).contains("C"));
        assertEquals(3, view.getPanel().getSelectedPiecePoints());

        doClick(buttonC);
        String[] selectedAfterDeselectC = view.getPanel().getSelectedPiece();
        assertNull(selectedAfterDeselectC); // Or check for empty array if your getSelectedPiece returns empty array instead of null
        assertEquals(0, view.getPanel().getSelectedPiecePoints());
        finish();
    }
}
