abstract class Instruction {

    abstract boolean isSet();

    protected Hex trim(Hex raw) {
        Hex result = new Hex();

        int counter = 0;
        int pointer = 0;
        for (int value : raw) {
            if (value != 35) {
                pointer = counter;
            }
            counter++;
        }
        for (int i = 0; i <= pointer; i++) {
            result.add(raw.get(i));
        }
        return result;
    }

    protected Hex space(Hex raw) {
        Hex result = new Hex();
        for (int value : raw) {
            if (value != 35) {
                result.add(value);
            } else {
                result.add(-1);
            }
        }
        return result;
    }

    protected Hex spaceInverted(Hex raw) {
        Hex result = new Hex();
        for (int value : raw) {
            if (value != 35) {
                result.add(-1);
            } else {
                result.add(value);
            }
        }
        return result;
    }
}
