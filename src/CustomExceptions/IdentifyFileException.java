package CustomExceptions;

import java.io.IOException;

public class IdentifyFileException extends IOException {
    public IdentifyFileException(String message) {
        super(message);
    }
}
