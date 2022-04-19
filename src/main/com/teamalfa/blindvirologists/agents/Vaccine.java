package main.com.teamalfa.blindvirologists.agents;

import main.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

public class Vaccine extends Agent {

    public Vaccine(GeneticCode geneticcode) {
        this.geneticCode = geneticcode;
    }

    /**
     * This method is called when a Vaccine is used on a Virologist.
     * @param target The virologist that got vaccinated.
     */
    @Override
    public void apply(Virologist target) {
        target.protectedBy(this);
        this.target = target;
    }
}
