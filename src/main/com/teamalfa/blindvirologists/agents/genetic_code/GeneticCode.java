package main.com.teamalfa.blindvirologists.agents.genetic_code;
import main.com.teamalfa.blindvirologists.agents.Vaccine;
import main.com.teamalfa.blindvirologists.agents.virus.Virus;
import main.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

abstract public class GeneticCode {
    abstract public Virus createVirus(ElementBank elementBank);
    abstract public Vaccine createVaccine(ElementBank elementBank);
}
