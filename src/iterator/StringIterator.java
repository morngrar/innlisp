package iterator;

import java.util.NoSuchElementException;

public class StringIterator {
    private final String string;
    private final int length;
    private int pos = 0;

    public StringIterator(String string) {
        this.string = string;
        length = string.length();
    }

    public boolean hasNext() {
        return pos < length;
    }

    public char next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return string.charAt(pos++);
    }

    public void reset() {
        pos = 0;
    }
}
