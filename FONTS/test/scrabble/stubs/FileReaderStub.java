package scrabble.stubs;

import edu.upc.prop.scrabble.domain.localization.IFileReader;
import edu.upc.prop.scrabble.domain.localization.Locale;

public class FileReaderStub implements IFileReader {
    private final String fileContents;

    public FileReaderStub(String fileContents) {
        this.fileContents = fileContents;
    }

    @Override
    public String run(Locale locale) {
        return fileContents;
    }
}
