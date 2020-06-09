package model;

import exceptions.InvalidNameException;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//Collection of all the added contestants participating in a speaking competition
public class Participants implements Saveable {
    private List<Contestant> participants; // list of Contestants
    private String winnerOutput;

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: creates an empty list of Contestants
    public Participants() {
        participants = new ArrayList<>();
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: adds a Contestant, with their name and topic, to the Participants
    public void addParticipant(String name, String topic) throws InvalidNameException {
        if (name.equals("")) {
            throw new InvalidNameException();
        } else {
            Contestant newContestant = new Contestant(name, topic);
            participants.add(newContestant);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: adds a saved Contestant from the file to the Participants
    public void addContestant(Contestant contestantToAdd) {
        participants.add(contestantToAdd);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: removes the Contestant with a matching name
    public void removeParticipant(String name) throws InvalidNameException {
        if (name.equals("")) {
            throw new InvalidNameException();
        } else {
            Contestant toBeRemoved = null;
            for (Contestant nextContestant : participants) {
                if (nextContestant.getName().equals(name)) {
                    toBeRemoved = nextContestant;
                }
            }

            if (toBeRemoved != null) {
                participants.remove(toBeRemoved);
            }
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: produces the Contestant with a matching name, produces the empty "foundContestant" otherwise
    public Contestant findParticipant(String name) throws InvalidNameException {
        if (name.equals("")) {
            throw new InvalidNameException();
        } else {
            Contestant foundContestant = new Contestant("empty", "empty");
            for (Contestant nextContestant : participants) {
                if (nextContestant.getName().equals(name)) {
                    foundContestant = nextContestant;
                }
            }
            return foundContestant;
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: produces the names of all Contestants who were added as Participants
    public String listParticipants() {
        String toPrint = "";
        for (Contestant nextContestant : participants) {
            toPrint = toPrint.concat(nextContestant.getName() + ", \n");
        }
        return toPrint;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: produces the number of contestants added to participants
    public int size() {
        return participants.size();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: produces the contestant at the given index point of participants
    public Contestant getParticipant(int index) {
        return participants.get(index);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: finds the winning contestant
    public String winner() {
        Contestant winner = new Contestant("no winner", "nothing");

        for (Contestant nextContestant : participants) {
            if (winner.getFinalScore() < nextContestant.getFinalScore()) {
                winner = nextContestant;
            }
        }

        winnerOutput = "Winner is " + winner.getName() + "!";
        return winnerOutput;
    }

    //REQUIRES: must run winner() method prior to save so winner field is given a value
    //MODIFIES:
    //EFFECTS: saves the winner of the competition
    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(winnerOutput);
    }
}
