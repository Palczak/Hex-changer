class Fill extends Replace {

    Fill() {
        super();
    }

    Fill(Hex before, Hex inside, Hex after, int index) {
        this.before = new Hex();
        this.inside = new Hex();
        this.after = new Hex();
        this.fileIndex = -1;

        setFill(before, inside, after, index);
    }

    void setFill(Hex before, Hex inside, Hex after, int index) {
        this.before = spaceInverted(before);
        this.inside = spaceInverted(inside);
        this.after = spaceInverted(after);
        this.fileIndex = index;
    }
}
