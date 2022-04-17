package main.com.teamalfa.blindvirologists.agents.genetic_code;
import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.agents.Vaccine;
import main.com.teamalfa.blindvirologists.agents.virus.ParalyzeVirus;
import main.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

public class ParalyzeCode extends GeneticCode{

    /**
     * This method creates a Virus with the paralyze geneticcode.
     * @return The ParalyzeVirus that has been created.
     */
    @Override
    public ParalyzeVirus createVirus(ElementBank elementBank) {
        // print out method call
        AController.printCall(this, "createVirus", new Object[]{elementBank});

        // create paralyze virus and register its name to the controller
        ParalyzeVirus pv = new ParalyzeVirus();
        AController.registerObject(null, pv, "paralyzeVirus");
        // register nested objects
        pv.registerObjects();

        // handle cost
        ElementBank cost = pv.getCost();
        if(elementBank.remove(cost)) {
            return (ParalyzeVirus) AController.printReturn(pv);
        }

        // Null return can be printed out like this
        AController.printReturn("null");
        return null;
    }

    /**
     * This method create a Vaccine with the paralyze geneticcode.
     * @return The Vaccine that has been created.
     */
    @Override
    public Vaccine createVaccine(ElementBank elementBank) {
        AController.printCall(this, "createVaccine", new Object[]{elementBank});

        Vaccine pv = new Vaccine(this);
        AController.registerObject(null, pv, "paralyzeVaccine");
        // register nested objects
        pv.registerObjects();

        // handle cost
        ElementBank cost = pv.getCost();
        if(elementBank.remove(cost)) {
            return (Vaccine) AController.printReturn(pv);
        }

        // Null return can be printed out like this
        AController.printReturn("null");
        return null;
    }
}
