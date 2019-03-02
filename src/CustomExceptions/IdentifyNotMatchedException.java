package CustomExceptions;

public class IdentifyNotMatchedException extends Exception {
    public IdentifyNotMatchedException() {
        super("Plik identyfikacyjne nie został dopasowały do pliku uszkodzonego");
    }
}
