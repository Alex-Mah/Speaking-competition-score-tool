package model;

import exceptions.InvalidNameException;
import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;

import static java.lang.Math.min;

//A person competing in a public speaking competition
public class Contestant implements Saveable {
    private String name; //first and last name
    private String topic; //topic for prepared speech

    private int preparedSpeechTime; //time in seconds
    private int impromptuSpeechTime; //time in seconds

    private double preparedScore1; //score from judge 1
    private double preparedScore2; //score from judge 2
    private double preparedScore3; //score from judge 3

    private double impromptuScore1; //score from judge 1
    private double impromptuScore2; //score from judge 2
    private double impromptuScore3; //score from judge 3

    private double finalScore; //average of three scores and time penalties

    public static final int PREP_MAX_TIME = 6;
    public static final int PREP_MIN_TIME = 5;
    public static final int IMP_MAX_TIME = 3;
    public static final int IMP_MIN_TIME = 2;

    public static final double MAX_TOTAL_POINTS = 300.0;

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: makes a Contestant with the provided name and topic, scores and times zeroed
    public Contestant(String name, String topic) {
        this.name = name;
        this.topic = topic;
        preparedSpeechTime = 0;
        impromptuSpeechTime = 0;
        preparedScore1 = 0.0;
        preparedScore2 = 0.0;
        preparedScore3 = 0.0;
        impromptuScore1 = 0.0;
        impromptuScore2 = 0.0;
        impromptuScore3 = 0.0;
        finalScore = 0.0;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: makes a Contestant with the provided name, topic, times, and scores
    public Contestant(String name, String topic, int prepTime, int impTime, double prepScore1, double prepScore2,
                      double prepScore3, double impScore1, double impScore2, double impScore3, double finalScore) {
        this.name = name;
        this.topic = topic;
        preparedSpeechTime = prepTime;
        impromptuSpeechTime = impTime;
        preparedScore1 = prepScore1;
        preparedScore2 = prepScore2;
        preparedScore3 = prepScore3;
        impromptuScore1 = impScore1;
        impromptuScore2 = impScore2;
        impromptuScore3 = impScore3;
        this.finalScore = finalScore;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: sets the name and/or topic for a Contestant
    public void setDetails(String newName, String newTopic) throws InvalidNameException {
        if (newName.equals("")) {
            throw new InvalidNameException();
        } else {
            name = newName;
            topic = newTopic;
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: sets how long a contestant's prepared speech lasted
    public void setPreparedSpeechTime(int time) {
        preparedSpeechTime = time;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: sets how long a contestant's impromptu speech lasted
    public void setImpromptuSpeechTime(int time) {
        impromptuSpeechTime = time;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: sets the judges' scores for a contestant's prepared speech
    public void setPrepScores(double first, double second, double third) {
        preparedScore1 = first;
        preparedScore2 = second;
        preparedScore3 = third;
        finalScore();
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: sets the judges' scores for a contestant's impromptu speech
    public void setImpScores(double first, double second, double third) {
        impromptuScore1 = first;
        impromptuScore2 = second;
        impromptuScore3 = third;
        finalScore();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: adds up a contestant's prepared speech scores
    public double sumPrepScore() {
        double result;
        result = preparedScore1 + preparedScore2 + preparedScore3;
        return result;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: adds up a contestant's impromptu speech scores
    public double sumImpScore() {
        double result;
        result = impromptuScore1 + impromptuScore2 + impromptuScore3;
        return result;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: determines the correct range of deductions to use
    public double penaltyDeduction(int upperBound, int lowerBound) {
        int speechTime;
        double maxDeduction;

        if (upperBound == PREP_MAX_TIME) {
            speechTime = preparedSpeechTime;
            maxDeduction = 7.0;
        } else {
            speechTime = impromptuSpeechTime;
            maxDeduction = 3.0;
        }

        double penalty = penaltyCalculation(speechTime, upperBound, lowerBound);

        return min(penalty, maxDeduction);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: calculates the point deduction
    private double penaltyCalculation(int speechTime, int upperBound, int lowerBound) {
        int timeDifference;
        double penalty = 0.0;

        if ((60 * lowerBound) <= speechTime && speechTime <= (60 * upperBound)) {
            penalty = 0.0;
        } else {
            if (speechTime < (60 * lowerBound)) {
                timeDifference = (60 * lowerBound) - speechTime;
                while (timeDifference > 0) {
                    timeDifference = timeDifference - 5;
                    penalty = penalty + 1.0;
                }
            } else {
                timeDifference = speechTime - (60 * upperBound);
                while (timeDifference > 0) {
                    timeDifference = timeDifference - 5;
                    penalty = penalty + 1.0;
                }
            }
        }
        return penalty;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: adds prepared and impromptu scores, deducts penalties, averages the result,
    //         then converts to a percent
    private void finalScore() {
        finalScore = (((sumPrepScore() + sumImpScore() - penaltyDeduction(PREP_MAX_TIME, PREP_MIN_TIME)
                - penaltyDeduction(IMP_MAX_TIME, IMP_MIN_TIME)) / MAX_TOTAL_POINTS) * 100.0);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns Contestant's name
    public String getName() {
        return name;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns Contestant's topic
    public String getTopic() {
        return topic;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns Contestant's prepared speech length
    public int getPrepSpeechTime() {
        return preparedSpeechTime;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns Contestant's impromptu speech length
    public int getImpSpeechTime() {
        return impromptuSpeechTime;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns score form judge 1
    public double getPreparedScore1() {
        return preparedScore1;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns score from judge 2
    public double getPreparedScore2() {
        return preparedScore2;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns score from judge 3
    public double getPreparedScore3() {
        return preparedScore3;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns score from judge 1
    public double getImpromptuScore1() {
        return impromptuScore1;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns score from judge 2
    public double getImpromptuScore2() {
        return impromptuScore2;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns score from judge 3
    public double getImpromptuScore3() {
        return impromptuScore3;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: returns Contestant's final score
    public double getFinalScore() {
        return finalScore;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: saves the fields of a Contestant
    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(name);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(topic);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(preparedSpeechTime);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(impromptuSpeechTime);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(preparedScore1);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(preparedScore2);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(preparedScore3);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(impromptuScore1);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(impromptuScore2);
        printWriter.print(Reader.DELIMITER);
        printWriter.print(impromptuScore3);
        printWriter.print(Reader.DELIMITER);
        printWriter.println(finalScore);
    }
}
