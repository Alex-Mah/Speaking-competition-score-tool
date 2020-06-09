package ui;

import exceptions.InvalidNameException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Application to store and parse scores for contestants
public class ScoringAppUI extends JFrame implements ActionListener {

    private List<JButton> buttonList = new ArrayList<>();

    public static final int BUTTON_HORIZONTAL_OFFSET = 640;
    public static final int BUTTON_HEIGHT = 30;
    public static final int BUTTON_WIDTH = 600;

    public static final int FIELD_HEIGHT = 30;
    public static final int FIELD_LABEL_HEIGHT = 15;

    private JTextField addNameField = new JTextField(10);
    private JTextField addTopicField = new JTextField(30);

    private JTextField removeNameField = new JTextField(10);

    private JTextField prepNameField = new JTextField(10);
    private JTextField prepTimeField = new JTextField(10);
    private JTextField prepOne = new JTextField(5);
    private JTextField prepTwo = new JTextField(5);
    private JTextField prepThree = new JTextField(5);

    private JTextField impNameField = new JTextField(10);
    private JTextField impTimeField = new JTextField(10);
    private JTextField impOne = new JTextField(5);
    private JTextField impTwo = new JTextField(5);
    private JTextField impThree = new JTextField(5);

    private JTextField calcScoreName = new JTextField(10);

    private JTextField findDetailsName = new JTextField(10);

    private JTextField editDetailsName = new JTextField(10);
    private JTextField newNameField = new JTextField(10);
    private JTextField newTopicField = new JTextField(10);

    private JLabel actionTheLastButtonDid = new JLabel("Type the inputs in the fields on the left,"
            + " then click the corresponding button on the right");

    private JLabel addNameLabel = new JLabel("Name of contestant to add");
    private JLabel addTopicLabel = new JLabel("Topic of contestant's prepared speech");
    private JLabel removeNameLabel = new JLabel("Name of contestant to remove");

    private JLabel prepNameLabel = new JLabel("Name");
    private JLabel prepTimeLabel = new JLabel("Time in seconds");
    private JLabel prepScoreLabel = new JLabel("Scores in decimal form, max per judge is 76.0");

    private JLabel impNameLabel = new JLabel("Name");
    private JLabel impTimeLabel = new JLabel("Time in seconds");
    private JLabel impScoreLabel = new JLabel("Scores in decimal form, max per judge is 24.0");

    private JLabel calcNameLabel = new JLabel("Name of contestant to score");
    private JLabel findNameLabel = new JLabel("Name of contestant to find");
    private JLabel editNameLabel = new JLabel("Name of contestant to edit");

    private JLabel newNameLabel = new JLabel("New name");
    private JLabel newTopicLabel = new JLabel("New topic");

    private JProgressBar progressBar = new JProgressBar(0, 100);
    public static final int PROGRESS_HORIZONTAL_OFFSET = 640;
    public static final int PROGRESS_WIDTH = 600;
    public static final int PROGRESS_HEIGHT = 15;

    private ScoringApp scoringApp;

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: runs the score keeper
    public ScoringAppUI() throws IOException {
        super("Effective Speaking Score Keeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 720);
        makeAllTheButtons();
        addAllTheButtons();
        addAllTheFields();
        addFieldLabels();
        fieldBounds();
        progressSetup();
        new DisplayImage(this);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        scoringApp = new ScoringApp();
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: constructs the various buttons
    private void makeAllTheButtons() {
        buttonMaker("Add a contestant", "addContestant", 30);
        buttonMaker("Remove a contestant", "removeContestant", 90);
        buttonMaker("Add time and scores for a prepared speech", "setPrep", 150);
        buttonMaker("Add time and scores for an impromptu speech", "setImp", 210);
        buttonMaker("Contestant's final %", "finalScore", 270);
        buttonMaker("Find details of a contestant", "findDetails", 330);
        buttonMaker("Edit details of a contestant", "editDetails", 390);
        buttonMaker("Display all participants", "listAll", 450);
        buttonMaker("Display the winning contestant", "winner", 510);
        buttonMaker("Save the current results to a file", "saveResult", 570);

    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: creates a button with the given text, command, and bounds
    private void buttonMaker(String buttonText, String command, int verticalOffset) {
        JButton b = new JButton(buttonText);
        b.setActionCommand(command);
        b.addActionListener(this);
        b.setBounds(BUTTON_HORIZONTAL_OFFSET, verticalOffset, BUTTON_WIDTH, BUTTON_HEIGHT);
        buttonList.add(b);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: adds all the created JButtons to the given JFrame
    private void addAllTheButtons() {
        for (JButton nextButton : buttonList) {
            add(nextButton);
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: adds all the JFields to the given JFrame
    private void addAllTheFields() {
        add(addNameField);
        add(addTopicField);
        add(removeNameField);
        add(prepNameField);
        add(prepTimeField);
        add(prepOne);
        add(prepTwo);
        add(prepThree);
        add(impNameField);
        add(impTimeField);
        add(impOne);
        add(impTwo);
        add(impThree);
        add(calcScoreName);
        add(findDetailsName);
        add(editDetailsName);
        add(newNameField);
        add(newTopicField);
        add(actionTheLastButtonDid);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: adds the JLabels which describe the corresponding JField
    private void addFieldLabels() {
        setFieldLabelBounds();
        add(addNameLabel);
        add(addTopicLabel);
        add(removeNameLabel);
        add(prepNameLabel);
        add(prepTimeLabel);
        add(prepScoreLabel);
        add(impNameLabel);
        add(impTimeLabel);
        add(impScoreLabel);
        add(calcNameLabel);
        add(findNameLabel);
        add(editNameLabel);
        add(newNameLabel);
        add(newTopicLabel);

    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: sets the bounds for the JFields' JLabels
    private void setFieldLabelBounds() {
        addNameLabel.setBounds(40, 60, 260, FIELD_LABEL_HEIGHT);
        addTopicLabel.setBounds(340, 60, 260, FIELD_LABEL_HEIGHT);
        removeNameLabel.setBounds(40, 120, 260, FIELD_LABEL_HEIGHT);
        prepNameLabel.setBounds(40, 180, 100, FIELD_LABEL_HEIGHT);
        prepTimeLabel.setBounds(155, 180, 100, FIELD_LABEL_HEIGHT);
        prepScoreLabel.setBounds(270, 180, 300, FIELD_LABEL_HEIGHT);
        impNameLabel.setBounds(40, 240, 100, FIELD_LABEL_HEIGHT);
        impTimeLabel.setBounds(155, 240, 100, FIELD_LABEL_HEIGHT);
        impScoreLabel.setBounds(270, 240, 300, FIELD_LABEL_HEIGHT);
        calcNameLabel.setBounds(40, 300, 260, FIELD_LABEL_HEIGHT);
        findNameLabel.setBounds(40, 360, 260, FIELD_LABEL_HEIGHT);
        editNameLabel.setBounds(40, 420, 180, FIELD_LABEL_HEIGHT);
        newNameLabel.setBounds(230, 420, 180, FIELD_LABEL_HEIGHT);
        newTopicLabel.setBounds(420, 420, 180, FIELD_LABEL_HEIGHT);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: sets the bounds for the JFields
    private void fieldBounds() {
        addNameField.setBounds(40, 30, 260, FIELD_HEIGHT);
        addTopicField.setBounds(340, 30, 260, FIELD_HEIGHT);
        removeNameField.setBounds(40, 90, 260, FIELD_HEIGHT);
        prepNameField.setBounds(40, 150, 100, FIELD_HEIGHT);
        prepTimeField.setBounds(155, 150, 100, FIELD_HEIGHT);
        prepOne.setBounds(270, 150, 100, FIELD_HEIGHT);
        prepTwo.setBounds(385, 150, 100, FIELD_HEIGHT);
        prepThree.setBounds(500, 150, 100, FIELD_HEIGHT);
        impNameField.setBounds(40, 210, 100, FIELD_HEIGHT);
        impTimeField.setBounds(155, 210, 100, FIELD_HEIGHT);
        impOne.setBounds(270, 210, 100, FIELD_HEIGHT);
        impTwo.setBounds(385, 210, 100, FIELD_HEIGHT);
        impThree.setBounds(500, 210, 100, FIELD_HEIGHT);
        calcScoreName.setBounds(40, 270, 260, FIELD_HEIGHT);
        findDetailsName.setBounds(40, 330, 260, FIELD_HEIGHT);
        editDetailsName.setBounds(40, 390, 180, FIELD_HEIGHT);
        newNameField.setBounds(230, 390, 180, FIELD_HEIGHT);
        newTopicField.setBounds(420, 390, 180, FIELD_HEIGHT);
        actionTheLastButtonDid.setBounds(340, 600, 600, 100);
        actionTheLastButtonDid.setHorizontalAlignment(JLabel.CENTER);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: sets initial values for a progressbar
    private void progressSetup() {
        progressBar.setBounds(0, 0, 0, 0);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);
        add(progressBar);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: shifts progressbar when button is pressed
    private void progressBarShift(int verticalOffset) {
        progressBar.setVisible(false);
        progressBar.setBounds(PROGRESS_HORIZONTAL_OFFSET, verticalOffset, PROGRESS_WIDTH, PROGRESS_HEIGHT);
        progressBar.setVisible(true);
        progressBar.setValue(0);
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: adds a contestant and their topic to the participants
    private void addContestant() {
        progressBarShift(60);
        try {
            scoringApp.addContestant(addNameField.getText(), addTopicField.getText());
            progressBar.setValue(50);
            actionTheLastButtonDid.setText("Added " + addNameField.getText());
            progressBar.setValue(100);
        } catch (InvalidNameException e) {
            actionTheLastButtonDid.setText("Please ensure a valid name of at least one character is used");
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: removes a contestant from the participants
    private void removeContestant() {
        progressBarShift(120);
        try {
            scoringApp.removeContestant(removeNameField.getText());
            progressBar.setValue(50);
            actionTheLastButtonDid.setText("Removed " + removeNameField.getText());
            progressBar.setValue(100);
        } catch (InvalidNameException e) {
            actionTheLastButtonDid.setText("Please ensure a valid name of at least one character is used");
        }

    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: adds the duration and scores for a contestant's prepared speech
    private void inputPreparedScores() {
        progressBarShift(180);
        try {
            int prepTime = Integer.parseInt(prepTimeField.getText());
            double prepScoreOne = Double.parseDouble(prepOne.getText());
            double prepScoreTwo = Double.parseDouble(prepTwo.getText());
            double prepScoreThree = Double.parseDouble(prepThree.getText());
            scoringApp.inputPreparedScores(prepNameField.getText(),
                    prepTime,
                    prepScoreOne,
                    prepScoreTwo,
                    prepScoreThree);
            actionTheLastButtonDid.setText(prepNameField.getText() + "'s prepared speech time and scores added");
            progressBar.setValue(100);
        } catch (InvalidNameException e) {
            actionTheLastButtonDid.setText("Please ensure a valid name of at least one character is used");
        } catch (NumberFormatException e) {
            actionTheLastButtonDid.setText("Please ensure number fields contain valid numbers");
        }
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: adds the duration and scores for a contestant's impromptu speech
    private void inputImpromptuScores() {
        progressBarShift(240);

        try {
            int impTime = Integer.parseInt(impTimeField.getText());
            double impScoreOne = Double.parseDouble(impOne.getText());
            double impScoreTwo = Double.parseDouble(impTwo.getText());
            double impScoreThree = Double.parseDouble(impThree.getText());
            scoringApp.inputImpromptuScores(impNameField.getText(),
                    impTime,
                    impScoreOne,
                    impScoreTwo,
                    impScoreThree);
            actionTheLastButtonDid.setText(impNameField.getText() + "'s impromptu speech time and scores added");
            progressBar.setValue(100);
        } catch (InvalidNameException e) {
            actionTheLastButtonDid.setText("Please ensure a valid name of at least one character is used");
        } catch (NumberFormatException e) {
            actionTheLastButtonDid.setText("Please ensure number fields contain valid numbers");
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: calculates the contestant's final score as a percent
    private void calculateScore() {
        progressBarShift(300);
        try {
            double score = scoringApp.calculateScore(calcScoreName.getText());
            actionTheLastButtonDid.setText(calcScoreName.getText() + "'s score is " + score + "%");
            progressBar.setValue(100);
        } catch (InvalidNameException e) {
            actionTheLastButtonDid.setText("Please ensure a valid name of at least one character is used");
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: produces the name, topic, speech durations, and final score of the contestant
    private void findContestant() {
        progressBarShift(360);
        try {
            actionTheLastButtonDid.setText(scoringApp.findContestant(findDetailsName.getText()));
            progressBar.setValue(100);
        } catch (InvalidNameException e) {
            actionTheLastButtonDid.setText("Please ensure a valid name of at least one character is used");
        }
    }

    //TODO: implement guarding or exception for REQUIRES clause
    //REQUIRES: Contestant must already have been added to Participants
    //MODIFIES: this
    //EFFECTS: updates the name and/or topic for the contestant
    private void editContestant() {
        progressBarShift(420);
        try {
            scoringApp.editContestant(editDetailsName.getText(), newNameField.getText(), newTopicField.getText());
            actionTheLastButtonDid.setText(editDetailsName.getText() + "'s name and/or topic updated");
            progressBar.setValue(100);
        } catch (InvalidNameException e) {
            actionTheLastButtonDid.setText("Please ensure a valid name of at least one character is used");
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: lists all added Contestants
    private void showParticipants() {
        progressBarShift(480);
        actionTheLastButtonDid.setText(scoringApp.showParticipants());
        progressBar.setValue(100);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: displays the winner of the competition
    private void winningContestant() {
        progressBarShift(540);
        actionTheLastButtonDid.setText(scoringApp.winningContestant());
        progressBar.setValue(100);
    }

    //method was copied from TellerApp
    // REQUIRES:
    // MODIFIES:
    // EFFECTS: saves state of listOfParticipants to RESULTS_FILE
    private void saveResults() {
        progressBarShift(600);
        actionTheLastButtonDid.setText(scoringApp.saveResults());
        progressBar.setValue(100);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: determines which action to perform
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "addContestant": addContestant();
                break;
            case "removeContestant": removeContestant();
                break;
            case "setPrep": inputPreparedScores();
                break;
            case "setImp": inputImpromptuScores();
                break;
            case "finalScore": calculateScore();
                break;
            case "findDetails": findContestant();
                break;
            case "editDetails": editContestant();
                break;
            case "listAll": showParticipants();
                break;
            case "winner": winningContestant();
                break;
            case "saveResult": saveResults();
        }
    }
}
