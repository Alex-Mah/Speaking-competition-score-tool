package persistence;

import model.Contestant;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Copied from Reader class of TellerApp
// A reader that can read contestant data from a file
public class Reader {
    public static final String DELIMITER = ",";

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: default constructor for Reader
    public Reader() {}

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns a list of contestants parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static List<Contestant> readResults(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns a list of strings parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static List<String> readWinner(File file) throws IOException {
        List<String> winner = readFile(file);
        return parseWinnerContent(winner);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns a list of contestants parsed from list of strings
    // where each string contains data for one contestant
    private static List<Contestant> parseContent(List<String> fileContent) {
        List<Contestant> contestants = new ArrayList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            contestants.add(parseContestant(lineComponents));
        }

        return contestants;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns a list of string parsed from list of strings
    //where string is about the winner
    private static List<String> parseWinnerContent(List<String> fileContent) {
        List<String> winner = new ArrayList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            winner.add(parseWinner(lineComponents));
        }

        return winner;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 11 where element index corresponds
    // to a field of a Contestant, in the same order as listed in the
    // Contestant class
    // MODIFIES:
    // EFFECTS: returns a contestant constructed from components
    private static Contestant parseContestant(List<String> components) {
        String name = components.get(0);
        String topic = components.get(1);
        int prepTime = Integer.parseInt(components.get(2));
        int impTime = Integer.parseInt(components.get(3));
        double prep1 = Double.parseDouble(components.get(4));
        double prep2 = Double.parseDouble(components.get(5));
        double prep3 = Double.parseDouble(components.get(6));
        double imp1 = Double.parseDouble(components.get(7));
        double imp2 = Double.parseDouble(components.get(8));
        double imp3 = Double.parseDouble(components.get(9));
        double total = Double.parseDouble(components.get(10));

        return new Contestant(name, topic, prepTime, impTime, prep1, prep2, prep3, imp1, imp2, imp3, total);
    }

    //REQUIRES: component has size 1, a line containing a string statement about the winner
    //MODIFIES:
    //EFFECTS: returns a brief statement about the winner
    private static String parseWinner(List<String> components) {
        return components.get(0);
    }
}
