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
                if(model.isSet()) {
                    startFixListener();
                }
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
                if(model.isSet()) {
                    startFixListener();
                }
                //view.showErrorDialog(model.getInstructionSet().toString());
            } catch (IOException e) {
                view.showErrorDialog(e.getMessage());
            } catch (Exception e) {
                view.showErrorDialog("Wystąpił nieoczekiwany błąd.\n" + e.getMessage());
            }
        }
    }

    void saveFixedListener() {
        String path = view.savePathBin();
        if (path != null) {
            if (!model.isResultSet()) {
                view.showErrorDialog("Plik wynikowy nie został wygenerowany.");
            } else {
                try {
                    model.saveFixed(path);
                } catch (IOException e) {
                    view.showErrorDialog(e.getMessage());
                }
            }
        }
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
                    ArrayList<Integer> indexToRemove = new ArrayList<>();
                    for (FindedPosition position : findedPositions) {
                        if (position.isConflict()) {
                            conflictList.add(position);
                            indexToRemove.add(0, index);
                        }
                        index++;
                    }
                    if(!conflictList.isEmpty()) {
                        for (int removeIndex : indexToRemove) {
                            findedPositions.remove(removeIndex);
                        }
                        conflictList = view.fixConflict(conflictList);
                        findedPositions.addAll(conflictList);

                    }
                    //System.out.println(findedPositions);
                    //System.out.println(model.getInstructionSet());
                    model.fix(findedPositions);
                    view.setFixedValue(model.getResultHex());
                }
            }
        }
    }
}