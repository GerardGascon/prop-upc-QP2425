package scrabble.stubs;

import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.presenter.localization.PiecesReader;

public class PiecesReaderStub extends PiecesReader {
    private final String returnValue;

    public PiecesReaderStub(String returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public String run(Language locale) {
        return returnValue;
    }
}
