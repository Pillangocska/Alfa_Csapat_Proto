package test.com.teamalfa.blindvirologists.virologist;

import main.com.teamalfa.blindvirologists.agents.GeneticCodeBank;
import main.com.teamalfa.blindvirologists.agents.Vaccine;
import main.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;
import main.com.teamalfa.blindvirologists.agents.virus.Virus;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.virologist.Virologist;
import main.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VirologistInfectionTest {
    static Virologist virologist;
    static ArrayList<GeneticCode> geneticCodes;
    static ElementBank elementBank;

    @BeforeAll
    static void setUp(){
        geneticCodes = GeneticCodeBank.getInstance().getCodes();
    }

    @BeforeEach
    void reset(){
        virologist = new Virologist();
        elementBank = new ElementBank(1000,1000);
    }

    @Test
    void normalVirologistInfection(){
        // In normal circumstances a virus should infect a virologist
        // which means that the viruses target is set to the virologist
        // and the virus gets placed into the virologists active viruses
        // array.
        for(GeneticCode code : geneticCodes) {
            //create virus from code and apply to virologist
            Virus virus = code.createVirus(elementBank);
            virus.apply(virologist);

            assertEquals(virus.getTarget(), virologist,
                    "The target should be equal to the virologist.");
            assertTrue(virologist.getViruses().contains(virus),
                    "The virus should be in the active viruses array.");
        }
    }

    @Test
    void vaccinatedVirologistInfection(){
        // If a virologist is vaccinated against a virus then
        // it should have no effect on the virologist.
        for(GeneticCode code : geneticCodes) {
            // create vaccine from code and apply to virologist
            Vaccine vaccine = code.createVaccine(elementBank);
            vaccine.apply(virologist);

            // create virus code and apply to virologist
            Virus virus = code.createVirus(elementBank);
            virus.apply(virologist);

            assertNotEquals(virus.getTarget(), virologist,
                    "The target should not be equal to the virologist.");
            assertFalse(virologist.getViruses().contains(virus),
                    "The virus should not be in the active viruses array.");
        }
    }
}