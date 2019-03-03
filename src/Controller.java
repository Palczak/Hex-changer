import java.io.IOException;
import java.util.ArrayList;

class Controller {
    private Model model;
    private View view;

    Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        view.initButtons(this);
    }

    void loadCorruptedListener() {
        String path = view.loadPathBin();
        if(path != null) {
            try {
                model.loadCorruptedHex(path);
                view.setCorruptedValue(model.getCorruptedHex());
            } catch (IOException e) {
                view.showErrorDialog("Nie udało się wczytać uszkodzonego pliku.\n" + e.getMessage());
            }
        }
    }

    void loadInstructionListener() {
        String path = view.loadPathInstruction();
        if(path != null) {
            try {
                model.setInstruction(path);
                view.showErrorDialog(model.getInstructionSet().toString());
            } catch (IOException e) {
                view.showErrorDialog(e.getMessage());
            } catch (Exception e) {
                view.showErrorDialog("Wystąpił nieoczekiwany błąd.\n" + e.getMessage());
            }
        }
    }

    void saveFixedListener() {
        ArrayList<FindedPosition> conflictList = new ArrayList<>();
        FindedPosition p1 = new FindedPosition();
        FindedPosition p2 = new FindedPosition();
        FindedPosition p3 = new FindedPosition();
        p1.setFileIndex(1);
        p1.add(5);
        p1.add(10);
        p1.add(15);
        p2.setFileIndex(2);
        p2.add(15);
        p2.add(30);
        p2.add(45);
        p3.add(40);
        p3.add(80);
        p3.add(120);
        p3.setFileIndex(3);
        conflictList.add(p1);
        conflictList.add(p2);
        conflictList.add(p3);
        view.fixConflict(conflictList);
        System.out.println("dupa");

    }

    void startFixListener() {
        if (model.isSet()) {
            if (!model.identify()) {
                view.showErrorDialog("Plik identyfikacyjny i uszkodzony nie zostały dopasowane.");
            } else {
                ArrayList<FindedPosition> findedPositions = model.find();
                if (findedPositions.isEmpty()) {
                    view.showErrorDialog("Żadna instrukcja szukająca nie została dopasowana.");
                } else {
                    ArrayList<FindedPosition> conflictList = new ArrayList<>();
                    int index = 0;
                    for (FindedPosition position : findedPositions) {
                        if (position.isConflict()) {
                            conflictList.add(position);
                            position.remove(index);
                        }
                        index++;
                    }
                    if(!conflictList.isEmpty()) {
                        conflictList = view.fixConflict(conflictList);
                    }

                }
            }
        }
    }
}