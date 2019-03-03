import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class ConflictDialog extends JDialog {

    ArrayList<FindedPosition> conflictList;
    ArrayList<ConflictContainer> containerList;
    JButton confirmButton;

    public ConflictDialog(Frame owner, ArrayList<FindedPosition> conflictList) {
        super(owner, "Konflikty", true);
        setLocationRelativeTo(null);

        setLayout(new FlowLayout());
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        confirmButton.addActionListener(e -> getList());

        this.conflictList = conflictList;
        this.containerList = new ArrayList<>();
        confirmButton = new JButton("Wykonaj zaznaczone instrukcje");

        for (FindedPosition findedPosition : conflictList) {
            containerList.add(new ConflictContainer(findedPosition));
        }

        for (ConflictContainer container : containerList) {
            this.add(container);
            container.setVisible(true);
        }

        add(confirmButton);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public ArrayList<FindedPosition> getList() {
        return  null;
    }
}