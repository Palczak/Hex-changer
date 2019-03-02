import java.util.ArrayList;

class InstructionSet {
    Identify identify;
    ArrayList<Find> findList;
    ArrayList<Replace> replaceList;
    ArrayList<Fill> fillList;

    InstructionSet() {
        identify = new Identify();
        findList = new ArrayList<>();
        replaceList = new ArrayList<>();
        fillList = new ArrayList<>();
    }

    boolean isSet() {
        return identify.isSet();
    }

    public String toString() {
        return "idenfity \n" + identify.toString() + "\n\n find\n" + findList.toString() + "\n\n replace\n" + replaceList.toString() + "\n\n fill\n" + fillList.toString();
    }

    Identify getIdentify() {
        return identify;
    }

    void setIdentify(Identify identify) {
        this.identify = identify;
    }

    ArrayList<Find> getFindList() {
        return findList;
    }

    void setFindList(ArrayList<Find> findList) {
        this.findList = findList;
    }

    ArrayList<Replace> getReplaceList() {
        return replaceList;
    }

    void setReplaceList(ArrayList<Replace> replaceList) {
        this.replaceList = replaceList;
    }

    ArrayList<Fill> getFillList() {
        return fillList;
    }

    void setFillList(ArrayList<Fill> fillList) {
        this.fillList = fillList;
    }
}