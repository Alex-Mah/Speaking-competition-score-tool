package model;

import exceptions.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContestantTest {
    Contestant con1;
    Contestant con2;
    Contestant con3;

    @BeforeEach
    public void setup() {
        con1 = new Contestant("con one", "topicOne");
        con2 = new Contestant("con two", "topicTwo");
        con3 = new Contestant("con three", "topicThree");
    }

    @Test
    public void testConstructor() {
        assertEquals("con one", con1.getName());
        assertEquals("topicOne", con1.getTopic());
        assertEquals(0, con1.getPrepSpeechTime());
        assertEquals(0, con1.getImpSpeechTime());
        assertEquals(0.0, con1.getFinalScore());
    }

    @Test
    public void testSumPrepScore() {
        con1.setPrepScores(1.0, 2.0, 3.0);
        assertEquals(6.0, con1.sumPrepScore());
    }

    @Test
    public void testSumImpScore() {
        con1.setImpScores(3.0, 2.0,1.0);
        assertEquals(6.0, con1.sumImpScore());
    }

    @Test
    public void testTimePenaltyPrepared() {
        con1.setPreparedSpeechTime(330); //suitable time
        assertEquals(0.0, con1.penaltyDeduction(Contestant.PREP_MAX_TIME, Contestant.PREP_MIN_TIME));
        con1.setPreparedSpeechTime(300); //minimum time ideal
        assertEquals(0.0, con1.penaltyDeduction(Contestant.PREP_MAX_TIME, Contestant.PREP_MIN_TIME));
        con1.setPreparedSpeechTime(360); //maximum time ideal
        assertEquals(0.0, con1.penaltyDeduction(Contestant.PREP_MAX_TIME, Contestant.PREP_MIN_TIME));
        con2.setPreparedSpeechTime(265); //very under time
        assertEquals(7.0, con2.penaltyDeduction(Contestant.PREP_MAX_TIME, Contestant.PREP_MIN_TIME));
        con2.setPreparedSpeechTime(200); //definitely under time
        assertEquals(7.0, con2.penaltyDeduction(Contestant.PREP_MAX_TIME, Contestant.PREP_MIN_TIME));
        con2.setPreparedSpeechTime(295); //bit under time
        assertEquals(1.0, con2.penaltyDeduction(Contestant.PREP_MAX_TIME, Contestant.PREP_MIN_TIME));
        con2.setPreparedSpeechTime(365); //bit over time
        assertEquals(1.0, con2.penaltyDeduction(Contestant.PREP_MAX_TIME, Contestant.PREP_MIN_TIME));
        con2.setPreparedSpeechTime(395); //very over time
        assertEquals(7.0, con2.penaltyDeduction(Contestant.PREP_MAX_TIME, Contestant.PREP_MIN_TIME));
        con2.setPreparedSpeechTime(500); //definitely over time
        assertEquals(7.0, con2.penaltyDeduction(Contestant.PREP_MAX_TIME, Contestant.PREP_MIN_TIME));
    }

    @Test
    public void testTimePenaltyImpromptu() {
        con1.setImpromptuSpeechTime(150); //suitable time
        assertEquals(0.0, con1.penaltyDeduction(Contestant.IMP_MAX_TIME, Contestant.IMP_MIN_TIME));
        con1.setImpromptuSpeechTime(120); //minimum time ideal
        assertEquals(0.0, con1.penaltyDeduction(Contestant.IMP_MAX_TIME, Contestant.IMP_MIN_TIME));
        con1.setImpromptuSpeechTime(180); //maximum time ideal
        assertEquals(0.0, con1.penaltyDeduction(Contestant.IMP_MAX_TIME, Contestant.IMP_MIN_TIME));
        con2.setImpromptuSpeechTime(105); //very under time
        assertEquals(3.0, con2.penaltyDeduction(Contestant.IMP_MAX_TIME, Contestant.IMP_MIN_TIME));
        con2.setImpromptuSpeechTime(30); //definitely under time
        assertEquals(3.0, con2.penaltyDeduction(Contestant.IMP_MAX_TIME, Contestant.IMP_MIN_TIME));
        con2.setImpromptuSpeechTime(115); //bit under time
        assertEquals(1.0, con2.penaltyDeduction(Contestant.IMP_MAX_TIME, Contestant.IMP_MIN_TIME));
        con2.setImpromptuSpeechTime(185); //bit over time
        assertEquals(1.0, con2.penaltyDeduction(Contestant.IMP_MAX_TIME, Contestant.IMP_MIN_TIME));
        con2.setImpromptuSpeechTime(195); //very over time
        assertEquals(3.0, con2.penaltyDeduction(Contestant.IMP_MAX_TIME, Contestant.IMP_MIN_TIME));
        con2.setImpromptuSpeechTime(300); //definitely over time
        assertEquals(3.0, con2.penaltyDeduction(Contestant.IMP_MAX_TIME, Contestant.IMP_MIN_TIME));
    }

    @Test
    public void testFinalScore() {
        con3.setPreparedSpeechTime(300);
        con3.setPrepScores(10.0,15.0,10.0);
        con3.setImpromptuSpeechTime(120);
        con3.setImpScores(20.0, 20.0, 15.0);
        assertEquals(30, con3.getFinalScore());
    }

    @Test
    public void testSetDetails() {
        try {
            con1.setDetails("contestant notOne", "aNewTopic");
            assertEquals("contestant notOne", con1.getName());
            assertEquals("aNewTopic", con1.getTopic());
        } catch (InvalidNameException e) {
            fail();
        }
    }

    @Test
    public void testSetInvalidName() {
        try {
            con1.setDetails("", "aNewTopic");
            fail();
        } catch (InvalidNameException e) {
            System.out.println("Name cannot be a blank string");
        }
    }
}