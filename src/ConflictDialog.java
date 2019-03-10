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

        this.conflictList = conflictList;
        this.containerList = new ArrayList<>();

        confirmButton = new JButton("Wykonaj zaznaczone instrukcje");
        confirmButton.addActionListener(e -> updateConflictList());
        confirmButton.setSize(200,200);


        for (FindedPosition findedPosition : conflictList) {
            containerList.add(new ConflictContainer(findedPosition));
        }

        for (ConflictContainer container : containerList) {
            this.add(container);
            container.setVisible(true);
        }

        this.add(confirmButton);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        pack();
        setVisible(true);
    }

    private void updateConflictList() {
        ArrayList<FindedPosition> updatedList = new ArrayList<>();
        for (ConflictContainer container : containerList) {
            FindedPosition newPosition = new FindedPosition();
            for (ConflictCheckBox checkBox : container.getCheckBoxList()) {
                if(checkBox.isSelected()) {
                    newPosition.add(checkBox.getHexIndex());
                }
            }
            if(!newPosition.isEmpty()) {
                updatedList.add(newPosition);
            }
        }

        conflictList = updatedList;
        hide();
    }

    public ArrayList<FindedPosition> getConflictList() {
        return  conflictList;
    }
}