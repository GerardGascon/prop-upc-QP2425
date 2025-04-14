package scrabble.stubs;

import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.domain.localization.IFileReader;

public class FileReaderStub implements IFileReader {
    private final String fileContents;

    public FileReaderStub(String fileContents) {
        this.fileContents = fileContents;
    }

    @Override
    public String run(Language locale) {
        return fileContents;
    }
}
