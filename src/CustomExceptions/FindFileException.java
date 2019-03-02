package CustomExceptions;

import java.io.IOException;

public class FindFileException extends IOException {
    public FindFileException(String message) {
        super(message);
    }
}
