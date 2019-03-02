package CustomExceptions;

public class CorruptedNotSetException extends Exception {
    public CorruptedNotSetException() {
        super("Uszkodozny plik nie został ustawiony.");
    }
}
