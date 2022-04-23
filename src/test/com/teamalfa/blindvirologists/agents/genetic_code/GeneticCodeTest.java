package test.com.teamalfa.blindvirologists.agents.genetic_code;

import main.com.teamalfa.blindvirologists.agents.genetic_code.*;
import main.com.teamalfa.blindvirologists.virologist.Virologist;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneticCodeTest {
    static AmnesiaCode a1, a2;
    static BearCode b1, b2;
    static DanceCode d1, d2;
    static ParalyzeCode p1, p2;
    static Virologist v1;

    @BeforeAll
    static void setUp() {
        a1 = new AmnesiaCode(); a2 = new AmnesiaCode();
        b1 = new BearCode(); b2 = new BearCode();
        d1 = new DanceCode(); d2 = new DanceCode();
        p1 = new ParalyzeCode(); p2 = new ParalyzeCode();
        v1 = new Virologist();
    }

    @Test
    void testSameGeneticCodes(){
        String msg = "Codes should be equal.";
        assertTrue(a1.equals(a2), msg);
        assertTrue(b1.equals(b2), msg);
        assertTrue(d1.equals(d2), msg);
        assertTrue(p1.equals(p2), msg);
    }

    @Test
    void testDifferentGeneticCodes(){
        String msg = "Codes should not be equal";
        assertFalse(a1.equals(p2), msg);
        assertFalse(b1.equals(d2), msg);
        assertFalse(d1.equals(b2), msg);
        assertFalse(p1.equals(a2), msg);
    }

    @Test
    void testDifferentTypeObjectGeneticCodes() {
        String msg = "Code should not be equal to different types of object.";
        assertFalse(a1.equals(v1), msg);
        assertFalse(b1.equals(v1), msg);
        assertFalse(d1.equals(v1), msg);
        assertFalse(p1.equals(v1), msg);
    }
}