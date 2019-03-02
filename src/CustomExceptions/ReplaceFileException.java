package CustomExceptions;

import java.io.IOException;

public class ReplaceFileException extends IOException {
    public ReplaceFileException(String message) {
        super(message);
    }
}
