package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {
    public static final String TEST_FILE = "./data/testResults.txt";
    public static final String TEST_FILE2 = "./data/testWinner.txt";
    public Writer testWriter;
    public Writer testWinnerWriter;
    public Contestant contestantOne;
    public Contestant contestantTwo;
    public Participants testParticipants = new Participants();

    @BeforeEach
    public void setup() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        testWinnerWriter = new Writer(new File(TEST_FILE2));
        contestantOne = new Contestant("contestant one", "AI", 300,107,
                57.0,69.0,52.0,
                15.0,8.0,16.0,214.0/3.0);
        contestantTwo = new Contestant("contestant two", "AI",377,169,
                76.0,61.0,67.0,
                21.0,21.0,21.5,263.5/3.0);
        testParticipants.addContestant(contestantOne);
        testParticipants.addContestant(contestantTwo);
    }

    @Test
    public void testWriteResults() {
        testWriter.write(contestantOne);
        testWriter.write(contestantTwo);
        testWriter.close();

        try {
            List<Contestant> contestants = Reader.readResults(new File(TEST_FILE));

            Contestant contestantOne = contestants.get(0);

            assertEquals("contestant one", contestantOne.getName());
            assertEquals("AI", contestantOne.getTopic());
            assertEquals(300, contestantOne.getPrepSpeechTime());
            assertEquals(107, contestantOne.getImpSpeechTime());
            assertEquals(57.0, contestantOne.getPreparedScore1());
            assertEquals(69.0, contestantOne.getPreparedScore2());
            assertEquals(52.0, contestantOne.getPreparedScore3());
            assertEquals(15.0, contestantOne.getImpromptuScore1());
            assertEquals(8.0, contestantOne.getImpromptuScore2());
            assertEquals(16.0, contestantOne.getImpromptuScore3());
            assertEquals(214.0/3.0, contestantOne.getFinalScore());

            Contestant contestantTwo = contestants.get(1);

            assertEquals("contestant two", contestantTwo.getName());
            assertEquals("AI", contestantTwo.getTopic());
            assertEquals(377, contestantTwo.getPrepSpeechTime());
            assertEquals(169, contestantTwo.getImpSpeechTime());
            assertEquals(76.0, contestantTwo.getPreparedScore1());
            assertEquals(61.0, contestantTwo.getPreparedScore2());
            assertEquals(67.0, contestantTwo.getPreparedScore3());
            assertEquals(21.0, contestantTwo.getImpromptuScore1());
            assertEquals(21.0, contestantTwo.getImpromptuScore2());
            assertEquals(21.5, contestantTwo.getImpromptuScore3());
            assertEquals(263.5/3.0, contestantTwo.getFinalScore());
        } catch (IOException e) {
            fail("There should not be an exception thrown");
        }
    }

    @Test
    public void testWriteWinner() {
        testParticipants.winner();
        testWinnerWriter.write(testParticipants);
        testWinnerWriter.close();
        try {
            List<String> winnerList = Reader.readWinner(new File(TEST_FILE2));

            String winner = winnerList.get(0);

            assertEquals("Winner is contestant two!", winner);
        } catch (IOException e) {
            fail("There should not be an exception thrown");
        }
    }
}
