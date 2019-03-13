import java.io.IOException;
import java.util.ArrayList;

class Model {

    private InstructionSet instructionSet;
    private FileManager fileManager;
    private Hex corruptedHex;
    private Hex resultHex;

    Model() {
        fileManager = new FileManager();
        instructionSet = new InstructionSet();
        corruptedHex = new Hex();
        resultHex = new Hex();
    }

    boolean isSet() {
        return instructionSet.isSet() && !corruptedHex.isEmpty();
    }

    public Hex getResultHex() {
        return resultHex;
    }

    void setInstruction(String patch) throws Exception {
        fileManager.setInstructionDir(patch);
        instructionSet = fileManager.readInstructions();
    }

    InstructionSet getInstructionSet() {
        return instructionSet;
    }

    void loadCorruptedHex(String path) throws IOException {
        corruptedHex = fileManager.getCorruptedHex(path);
    }

    Hex getCorruptedHex() {
        return corruptedHex;
    }

    boolean identify() {
        Hex identifyRaw = instructionSet.getIdentify().getIdentifyRaw();
        Hex identify = instructionSet.getIdentify().getIdentify();

        if (identifyRaw.size() != corruptedHex.size()) {
            return false;
        }

        ArrayList<Integer> match = corruptedHex.allMatches(identify);
        if (match.size() == 0) {
            return false;
        }

        return true;
    }

    ArrayList<FindedPosition> find() {
        ArrayList<FindedPosition> resultList = new ArrayList<>();
        for(Find find : instructionSet.getFindList()) {
            ArrayList<Integer> positionList = corruptedHex.allMatches(find.find);
            if(!positionList.isEmpty()) {
                FindedPosition position = new FindedPosition();
                position.addAll(positionList);
                position.setFileIndex(find.fileIndex);
                resultList.add(position);
            }
        }
        return resultList;
    }

    void fix(ArrayList<FindedPosition> findedPositions) {
        resultHex = (Hex)corruptedHex.clone();

        for (FindedPosition position : findedPositions) {
            System.out.println(position.getFileIndex());
            for (Replace replace : instructionSet.getReplaceList()) {
                if(replace.fileIndex == position.getFileIndex()) {
                    for (int hexIndex : position) {
                        replaceAll(hexIndex, replace);
                    }
                }
            }

            for (Fill fill : instructionSet.getFillList()) {
                if (fill.fileIndex == position.getFileIndex()) {
                    for (int hexIndex : position) {
                        replaceAll(hexIndex, fill);
                    }
                }
            }
        }
    }

    private void replaceAll(int startIndex, Replace replace) {
        //index  - lenght
        resultHex.replace(startIndex - replace.before.size(), replace.before);
        resultHex.replace(startIndex, replace.inside);
        resultHex.replace(startIndex + replace.after.size(), replace.after);
    }
}