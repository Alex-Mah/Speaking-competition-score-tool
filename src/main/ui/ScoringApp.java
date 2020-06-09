package ui;

import exceptions.InvalidNameException;
import model.Contestant;
import model.Participants;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ScoringApp {
    private static final String RESULTS_FILE = "./data/results.txt";
    private static final String WINNER_FILE = "./data/winner.txt";
    private Participants participants = new Participants();

    public ScoringApp() {
        loadResults();
    }

    //method was copied from TellerApp
    // MODIFIES: this
    // EFFECTS: loads participants from RESULTS_FILE, if that file exists;
    // otherwise initializes listOfParticipants with default values
    private void loadResults() {
        try {
            List<Contestant> contestants = Reader.readResults(new File(RESULTS_FILE));
            for (Contestant nextContestant : contestants) {
                participants.addContestant(nextContestant);
            }
        } catch (IOException e) {
            init();
        }
    }

    //method was copied from TellerApp
    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        participants = new Participants();
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: adds a contestant and their topic to the participants
    public void addContestant(String name, String topic) throws InvalidNameException {
        participants.addParticipant(name, topic);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: removes a contestant from the participants
    public void removeContestant(String name) throws InvalidNameException {
        participants.removeParticipant(name);

    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: adds the duration and scores for a contestant's prepared speech
    public void inputPreparedScores(String name,
                                    int time,
                                    double one,
                                    double two,
                                    double three) throws InvalidNameException {
        participants.findParticipant(name).setPreparedSpeechTime(time);
        participants.findParticipant(name).setPrepScores(one, two, three);
        participants.winner();
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: adds the duration and scores for a contestant's impromptu speech
    public void inputImpromptuScores(String name,
                                     int time,
                                     double one,
                                     double two,
                                     double three) throws InvalidNameException {
        participants.findParticipant(name).setImpromptuSpeechTime(time);
        participants.findParticipant(name).setImpScores(one, two, three);
        participants.winner();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: calculates the contestant's final score as a percent
    public double calculateScore(String name) throws InvalidNameException {
        return participants.findParticipant(name).getFinalScore();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: produces the name, topic, speech durations, and final score of the contestant
    public String findContestant(String name) throws InvalidNameException {
        return "Name: " + participants.findParticipant(name).getName() + ", \n"
                + "Topic: " + participants.findParticipant(name).getTopic() + ", \n"
                + "Prepared time: "
                + participants.findParticipant(name).getPrepSpeechTime() + ", \n"
                + "Impromptu time: "
                + participants.findParticipant(name).getImpSpeechTime() + ", \n"
                + "Final "
                + "score: " + participants.findParticipant(name).getFinalScore();

    }

    //TODO: implement guarding or exception for REQUIRES clause
    //REQUIRES: Contestant must already have been added to Participants
    //MODIFIES: this
    //EFFECTS: updates the name and/or topic for the contestant
    public void editContestant(String oldName, String newName, String newTopic) throws InvalidNameException {
        participants.findParticipant(oldName).setDetails(newName, newTopic);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: lists all added Contestants
    public String showParticipants() {
        return "Participants: " + "\n" + participants.listParticipants();
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: displays the winner of the competition
    public String winningContestant() {
        return participants.winner();
    }

    //method was copied from TellerApp
    // REQUIRES:
    // MODIFIES:
    // EFFECTS: saves state of listOfParticipants to RESULTS_FILE
    public String saveResults() {
        try {
            writeResult();
            writeWinner();
            return "Results saved to file " + RESULTS_FILE + ". \nWinner saved to file " + WINNER_FILE;
        } catch (FileNotFoundException e) {
            return "Unable to save results to " + RESULTS_FILE;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Unable to save results to " + RESULTS_FILE;
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: writes the participants to a file
    private void writeResult() throws FileNotFoundException, UnsupportedEncodingException {
        Writer writer = new Writer(new File(RESULTS_FILE));
        for (int i = 0; i < participants.size(); i++) {
            writer.write(participants.getParticipant(i));
        }
        writer.close();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: writes the winner to a file
    private void writeWinner() throws FileNotFoundException, UnsupportedEncodingException {
        Writer writeWinner = new Writer(new File(WINNER_FILE));
        participants.winner();
        writeWinner.write(participants);
        writeWinner.close();
    }
}
