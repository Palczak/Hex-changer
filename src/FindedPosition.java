import java.util.ArrayList;

public class FindedPosition extends ArrayList<Integer> {
    private int fileIndex;

    public int getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(int fileIndex) {
        this.fileIndex = fileIndex;
    }

    public boolean isConflict() {
        return size() > 1;
    }
}
