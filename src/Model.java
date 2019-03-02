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
}