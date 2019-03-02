import java.util.ArrayList;

class Hex extends ArrayList<Integer> {

    ArrayList<Integer> allMatches(Hex hex) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for(int i = 0; i < this.size(); i++) {
            if(get(i).equals(hex.get(0))) {
                int counter = 0;
                for(int j = 0; j < hex.size(); j++) {
                    if(get(i+j).equals(hex.get(j))) {
                        counter++;
                    } else {
                        break;
                    }
                }
                if(hex.size() == counter) {
                    indexes.add(i);
                }
            }
        }
        return indexes;
    }

     void replace(int startIndex, Hex hex) {
        for(int value : hex) {
            this.set(startIndex, value);
            startIndex++;
        }
    }

    public String toString() {
        /*
       StringBuilder builder = new StringBuilder();
       for (int i = 0; i < size(); i += 16) {
           Object[] array =  new Object[16];
           String format = "  ";
           StringBuilder formatBuiler = new StringBuilder();
           for(int j = 0; j < 16; j++) {
               array[j] = get(i + j);
               formatBuiler.append("%02X  ");
           }
           format += formatBuiler.toString() + "\n";
           builder.append(String.format(format, array));
       }
       return builder.toString();
       */
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < size(); i++) {
            if(i != 0 && i%16 == 0) {
                builder.append("\n");
            }
            builder.append(String.format("%02X  ", get(i)));
        }
        return builder.toString();
    }
}
