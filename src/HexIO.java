import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class HexIO {

    HexIO() {

    }

    Hex readHex(String path) throws IOException {
        File file = new File(path);
        Hex result = new Hex();
        FileInputStream reader = new FileInputStream(file);
        int value;
        while ((value = reader.read()) != -1) {
            result.add(value);
        }
        reader.close();
        return result;
    }

    Hex readHex(File file) throws IOException {
        String path = file.getPath();
        return readHex(path);
    }

    void writeHex(String path, Hex toWrite) throws IOException {

        if(toWrite.isEmpty()) {
            throw new IOException("Brak wygenerowanego wyniku.");
        }

        File file = new File(path);
        FileOutputStream writer = new FileOutputStream(file);

        for (int value : toWrite) {
            writer.write(value);
        }
        writer.close();
    }
}