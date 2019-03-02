package CustomExceptions;

import java.io.IOException;

public class FillFileException extends IOException {
    public FillFileException(String message) {
        super(message);
    }
}

