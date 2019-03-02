import javax.swing.*;

public class ConflictCheckBox extends JCheckBox {
    int hexIndex;

    public ConflictCheckBox(String text, int hexIndex) {
        super(text);
        this.hexIndex = hexIndex;
    }

    public int getHexIndex() {
        return hexIndex;
    }

    public void setHexIndex(int hexIndex) {
        this.hexIndex = hexIndex;
    }
}
