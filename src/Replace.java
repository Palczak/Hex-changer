class Replace extends Instruction {
    Hex before;
    Hex inside;
    Hex after;
    int fileIndex;

    Replace(){
        before = new Hex();
        inside = new Hex();
        after = new Hex();
        fileIndex = -1;
    }

    Replace(Hex before, Hex inside, Hex after, int fileIndex){
        this.before = new Hex();
        this.inside = new Hex();
        this.after = new Hex();
        this.fileIndex = -1;

        setReplace(before, inside, after, fileIndex);
    }

    void setReplace(Hex before, Hex inside, Hex after, int fileIndex) {
        this.before = space(before);
        this.inside = space(inside);
        this.after = space(after);
        this.fileIndex = fileIndex;
    }

    public String toString() {
        return fileIndex + "\n before" + before + "\n inside" + fileIndex + "\n after" + after;
    }

    @Override
    boolean isSet(){
        return !before.isEmpty() && !inside.isEmpty() && !after.isEmpty() && fileIndex >= 0;
    }
}
