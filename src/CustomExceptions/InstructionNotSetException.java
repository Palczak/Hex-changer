package CustomExceptions;

public class InstructionNotSetException extends Exception {
    public InstructionNotSetException() {
        super("Instrukcje nie zostały ustawione.");
    }
}
