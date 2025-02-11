import java.util.List;

abstract class LineCounter {
    protected boolean isValidLine(String line) {
        throw new UnsupportedOperationException("Unimplemented method 'isValidLine'");
    }

    abstract int count(List<String> lines);
}