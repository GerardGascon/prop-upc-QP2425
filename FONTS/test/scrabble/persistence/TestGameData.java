package scrabble.persistence;

import edu.upc.prop.scrabble.data.GameData;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.BoardType;
import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGameData {
    @Test
    public void gameDataGetsLoadedWithCorrectValues() {
        GameData gameData = new GameData();
        gameData.setLanguage(Language.Catalan);
        gameData.setBoardType(BoardType.Standard);
        gameData.setPlayers(new Player[]{new Player("test", false)});

        PersistentDictionary dict = gameData.encode();
        GameData sut = new GameData();
        sut.decode(dict);

        assertEquals(Language.Catalan, sut.getLanguage());
        assertEquals(BoardType.Standard, sut.getBoardType());
        assertEquals(new Player("test", false).getName(), sut.getPlayers()[0].getName());
    }
}
