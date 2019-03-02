class Find extends Instruction {
    Hex find;
    int fileIndex;

    Find() {
        find = new Hex();
        fileIndex = -1;
    }

    Find(Hex find, int fileIndex) {
        this.find = new Hex();
        this.fileIndex = -1;

        setFind(find, fileIndex);
    }

    void setFind(Hex find, int fileIndex) {
        this.find = space(find);
        this.fileIndex = fileIndex;
    }

    public String toString() {
        return fileIndex + "\n" + find.toString();
    }

    @Override
    boolean isSet(){
        return !find.isEmpty() && fileIndex >= 0;
    }
}
