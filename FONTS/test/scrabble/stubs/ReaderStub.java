package scrabble.stubs;

import edu.upc.prop.scrabble.presenter.terminal.utils.IReader;

public class ReaderStub implements IReader {
    private final String input;

    public ReaderStub(String input) {
        this.input = input;
    }

    @Override
    public String readLine() {
        return input;
    }
}
