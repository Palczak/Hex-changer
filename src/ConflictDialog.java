import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class ConflictDialog extends JDialog {

    ArrayList<FindedPosition> conflictList;
    ArrayList<ConflictContainer> containerList;

    public ConflictDialog(Frame owner, ArrayList<FindedPosition> conflictList) {
        super(owner, "Konflikty", true);

        setLayout(new FlowLayout());
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        this.conflictList = conflictList;
        this.containerList = new ArrayList<>();

        for (FindedPosition findedPosition : conflictList) {
            containerList.add(new ConflictContainer(findedPosition));
        }

        for (ConflictContainer container : containerList) {
            this.add(container);
            container.setVisible(true);
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }
}