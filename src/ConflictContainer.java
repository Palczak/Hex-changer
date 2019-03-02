import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConflictContainer extends JPanel {
    int fileIndex;
    ArrayList<ConflictCheckBox> checkBoxList;

    public ConflictContainer(FindedPosition findedPosition) {
        super();

        checkBoxList = new ArrayList<>();
        this.fileIndex = findedPosition.getFileIndex();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new JLabel(" Instrukcja" + " " + fileIndex + " "));

        this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));

        for (int hexIndex : findedPosition) {
            String checkBoxString = lineNumber(hexIndex);
            checkBoxList.add(new ConflictCheckBox(checkBoxString, hexIndex));
        }

        for(ConflictCheckBox box : checkBoxList) {
            this.add(box);
        }

    }

    private String lineNumber(int index) {
        return index+"";
        //return (index / 8) + 1 + "";
    }
}
