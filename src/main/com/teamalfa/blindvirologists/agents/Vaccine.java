package main.com.teamalfa.blindvirologists.agents;

import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.agents.genetic_code.AmnesiaCode;
import main.com.teamalfa.blindvirologists.agents.genetic_code.DanceCode;
import main.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;
import main.com.teamalfa.blindvirologists.agents.genetic_code.ParalyzeCode;
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
        // print method call
        AController.printCall(this, "apply", new Object[]{target});

        target.protectedBy(this);
        this.target = target;

        // since printReturn handles the tab depth counter need to be called
        AController.printReturn(null);
    }
}
