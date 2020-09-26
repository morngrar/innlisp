package iterator;

public class StringIterator {
    private final String string;
    private final int length;
    private int counter = 0;

    public StringIterator(String string) {
        this.string = string;
        length = string.length();
    }

    public boolean hasNext() {
        return counter < length;
    }

    public char next() {
        return string.charAt(counter++);
    }
}
