
package main.com.teamalfa.blindvirologists.agents;

import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;
import main.com.teamalfa.blindvirologists.virologist.Virologist;
import main.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

abstract public class Agent {
    protected ElementBank cost;
    protected Virologist target = null;
    protected GeneticCode geneticCode;

    public Agent() {
        cost = new ElementBank(0,0);
    }

    public void registerObjects() {
        // register nested object names  to controller
        AController.registerObject(this, cost, "cost");
    }

    public GeneticCode getGeneticCode() {
        // print method call
        AController.printCall(this, "getGeneticCode", null);
        // print return value
        return (GeneticCode) AController.printReturn(geneticCode);
    }

    abstract public void apply(Virologist target);

    public ElementBank getCost() {
        // print method call
        AController.printCall(this, "getCost", null);
        // print return value
        return (ElementBank) AController.printReturn(cost);
    }
}