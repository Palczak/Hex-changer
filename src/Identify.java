class Identify extends Instruction {

    Hex identify;
    Hex identifyRaw;

    Identify() {
        identify = new Hex();
        identifyRaw = new Hex();
    }

    Identify(Hex identify) {
        this.identify = new Hex();
        identifyRaw = new Hex();
        setIdentify(identify);
    }

    void setIdentify(Hex identify) {
        identifyRaw = identify;
        this.identify = trim(identify);
        //this.identify = identify;
    }

    Hex getIdentify() {
        return identify;
    }

    Hex getIdentifyRaw() {
        return identifyRaw;
    }

    @Override
    boolean isSet() {
        return !identify.isEmpty() && !identifyRaw.isEmpty();
    }

    public String toString() {
        return identify.toString();
    }
}
