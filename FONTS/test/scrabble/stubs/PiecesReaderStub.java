package scrabble.stubs;

import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.domain.pieces.IFileReader;

public class PiecesReaderStub implements IFileReader {
    private final String returnValue;

    public PiecesReaderStub(String returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public String run(Language locale) {
        return returnValue;
    }
}
