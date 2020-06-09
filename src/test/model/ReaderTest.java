package model;

import org.junit.jupiter.api.Test;
import persistence.Reader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {

    @Test
    public void testConstructor() {
        Reader testReader = new Reader();
        assertNotNull(testReader);
    }

    @Test
    public void testParseResultFile1() {
        try {
            List<Contestant> contestants = Reader.readResults(new File("./data/testresult1.txt"));

            Contestant contestantOne = contestants.get(0);

            assertEquals("contestant one", contestantOne.getName());
            assertEquals(" historical event", contestantOne.getTopic());
            assertEquals(346, contestantOne.getPrepSpeechTime());
            assertEquals(156, contestantOne.getImpSpeechTime());
            assertEquals(69.0, contestantOne.getPreparedScore1());
            assertEquals(70.0, contestantOne.getPreparedScore2());
            assertEquals(69.0, contestantOne.getPreparedScore3());
            assertEquals(19.0, contestantOne.getImpromptuScore1());
            assertEquals(21.0, contestantOne.getImpromptuScore2());
            assertEquals(22.0, contestantOne.getImpromptuScore3());
            assertEquals(90.0, contestantOne.getFinalScore());
        } catch (IOException e) {
            fail("There should not be an exception thrown");
        }
    }

    @Test
    public void testParseResultFile2() {
        try {
            List<Contestant> contestants = Reader.readResults(new File("./data/testresult2.txt"));

            Contestant contestantTwo = contestants.get(0);

            assertEquals("contestant two", contestantTwo.getName());
            assertEquals(" bravery", contestantTwo.getTopic());
            assertEquals(358, contestantTwo.getPrepSpeechTime());
            assertEquals(159, contestantTwo.getImpSpeechTime());
            assertEquals(65.0, contestantTwo.getPreparedScore1());
            assertEquals(57.0, contestantTwo.getPreparedScore2());
            assertEquals(73.0, contestantTwo.getPreparedScore3());
            assertEquals(20.0, contestantTwo.getImpromptuScore1());
            assertEquals(21.0, contestantTwo.getImpromptuScore2());
            assertEquals(15.0, contestantTwo.getImpromptuScore3());
            assertEquals(251.0/3.0, contestantTwo.getFinalScore());

            Contestant contestantThree = contestants.get(1);

            assertEquals("contestant three", contestantThree.getName());
            assertEquals(" bravery", contestantThree.getTopic());
            assertEquals(323, contestantThree.getPrepSpeechTime());
            assertEquals(128, contestantThree.getImpSpeechTime());
            assertEquals(60.0, contestantThree.getPreparedScore1());
            assertEquals(74.0, contestantThree.getPreparedScore2());
            assertEquals(55.0, contestantThree.getPreparedScore3());
            assertEquals(20.0, contestantThree.getImpromptuScore1());
            assertEquals(18.0, contestantThree.getImpromptuScore2());
            assertEquals(19.0, contestantThree.getImpromptuScore3());
            assertEquals(82.0, contestantThree.getFinalScore());
        } catch (IOException e) {
            fail("There should not be an exception thrown");
        }
    }

    @Test
    public void testIOException() {
        try {
            Reader.readResults(new File("./path/some/nonexistant/file.txt"));
        } catch (IOException e) {
            // expected
        }
    }
}
