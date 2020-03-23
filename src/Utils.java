// Aziz Yokubjonov - aziz.yokubjonov@gmail.com
// GitHub: @aziz512
// azizwrites.xyz

import java.util.ArrayList;

class Utils {
    // parses a line in a CSV file and returns an array of its columns
    public static ArrayList<String> parseCSVLine(String line) {
        ArrayList<String> result = new ArrayList<>();
        boolean isQuoted = false;
        ArrayList<Integer> delimeterLocations = new ArrayList<>();

        // walk the string char-by-char and find true delimeters
        for (int i = 0; i < line.length(); i++) {
            char currentChar = line.charAt(i);
            if (currentChar == '"') { // handle quote escaping
                if (line.length() > i + 1) {
                    char nextChar = line.charAt(i + 1);
                    if (nextChar == '"') {
                        i++; // skip the escaped quote
                        continue;
                    }
                }
                isQuoted = !isQuoted; // no escaping, value wrapped in quotes
            } else if (!isQuoted && currentChar == ',') {
                delimeterLocations.add(i);
            }
        }

        if (delimeterLocations.size() == 0) { // only one column in line
            result.add(line);
            return result;
        }
        for (int i = 0; i < delimeterLocations.size(); i++) {
            int previous = 0;
            if (i != 0) {
                previous = delimeterLocations.get(i - 1) + 1;
            }
            result.add(line.substring(previous, delimeterLocations.get(i)));
        }
        // add column after last delimeter (aka last column)
        int lastDelimeter = delimeterLocations.get(delimeterLocations.size() - 1);
        result.add(line.substring(lastDelimeter + 1));
        return result;
    }
}