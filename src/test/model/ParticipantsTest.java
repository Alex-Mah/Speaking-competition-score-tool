package model;

import exceptions.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantsTest {
    Participants participants;

    @BeforeEach
    public void setup() {
        participants = new Participants();
    }

    @Test
    public void testConstructor() {
        assertEquals("", participants.listParticipants());
    }

    @Test
    public void testAddParticipant() {
        try {
            participants.addParticipant("part one", "speech");
            assertEquals("part one, \n", participants.listParticipants());
            participants.addParticipant("notPart one", "notSpeeches");
            assertEquals("part one, \nnotPart one, \n", participants.listParticipants());
        } catch (InvalidNameException e) {
            fail();
        }
    }

    @Test
    public void testAddBlankName() {
        try {
            participants.addParticipant("", "speech");
            fail();
        } catch (InvalidNameException e) {
            System.out.println("Name cannot be a blank string");
        }
    }

    @Test
    public void testRemoveParticipant() {
        try {
            participants.addParticipant("part one", "speech");
            participants.addParticipant("notPart one", "notSpeeches");
            assertEquals(2, participants.size());
            participants.removeParticipant("part one");
            assertEquals(1, participants.size());
            assertEquals("notPart one, \n", participants.listParticipants());
            participants.removeParticipant("notAParticipant");
            assertEquals(1, participants.size());
            assertEquals("notPart one, \n", participants.listParticipants());
        } catch (InvalidNameException e) {
            fail();
        }
    }

    @Test
    public void testRemoveBlankName() {
        try {
            participants.addParticipant("part one", "speech");
            participants.removeParticipant("");
        } catch (InvalidNameException e) {
            System.out.println("Name cannot be a blank string");
        }
    }

    @Test
    public void testFindParticipant() {
        try {
            participants.addParticipant("part one", "speech");
            assertEquals("part one", participants.findParticipant("part one").getName());
            participants.addParticipant("notPart one", "notSpeeches");
            assertEquals("notPart one", participants.findParticipant("notPart one").getName());
            assertEquals("empty", participants.findParticipant("not found").getName());
        } catch (InvalidNameException e) {
            fail();
        }
    }

    @Test
    public void testFindBlankName() {
        try {
            participants.addParticipant("part one", "speech");
            participants.findParticipant("");
            fail();
        } catch (InvalidNameException e) {
            System.out.println("Name cannot be a blank string");
        }
    }

    @Test
    public void testListParticipants() {
        try {
            assertEquals("", participants.listParticipants());
            participants.addParticipant("part one", "speech");
            participants.addParticipant("notPart one", "notSpeeches");
            participants.addParticipant("third participant", "something");
            assertEquals(3, participants.size());
            assertEquals("part one, \nnotPart one, \nthird participant, \n", participants.listParticipants());
        } catch (InvalidNameException e) {
            fail();
        }
    }

    @Test
    public void testAddContestant() {
        Contestant partOne = new Contestant("part one", "topic");
        participants.addContestant(partOne);
        try {
            assertEquals("part one", participants.findParticipant("part one").getName());
            assertEquals(1, participants.size());
            assertEquals(partOne, participants.getParticipant(0));
        } catch (InvalidNameException e) {
            fail();
        }
    }

    @Test
    public void testWinner() {
        assertEquals("Winner is no winner!", participants.winner());
        Contestant contestantOne = new Contestant("contestant one", "AI", 300,107,
                57.0,69.0,52.0,
                15.0,8.0,16.0,214.0/3.0);
        Contestant contestantTwo = new Contestant("contestant two", "AI",377,169,
                76.0,61.0,67.0,
                21.0,21.0,21.5,263.5/3.0);
        Contestant contestantThree = new Contestant("contestant three", "historical event",346,156,
                69.0,70.0,69.0,
                19.0,21.0,22.0,90.0);
        participants.addContestant(contestantOne);
        assertEquals("Winner is contestant one!", participants.winner());
        participants.addContestant(contestantTwo);
        assertEquals("Winner is contestant two!", participants.winner());
        Contestant anotherTwo = new Contestant("other two", "AI",377,169,
                76.0,61.0,67.0,
                21.0,21.0,21.5,263.5/3.0);
        participants.addContestant(anotherTwo);
        assertEquals("Winner is contestant two!", participants.winner());
        participants.addContestant(contestantThree);
        assertEquals("Winner is contestant three!", participants.winner());
        Contestant anotherOne = new Contestant("other one", "AI", 300,107,
                57.0,69.0,52.0,
                15.0,8.0,16.0,214.0/3.0);
        assertEquals("Winner is contestant three!", participants.winner());
        Contestant anotherThree = new Contestant("other three", "historical event",346,156,
                69.0,70.0,69.0,
                19.0,21.0,22.0,90.0);
        participants.addContestant(anotherThree);
        assertEquals("Winner is contestant three!", participants.winner());
    }
}
