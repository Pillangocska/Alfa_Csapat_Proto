package main.com.teamalfa.blindvirologists.agents;

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

    public GeneticCode getGeneticCode() {
        return geneticCode;
    }

    abstract public void apply(Virologist target);

    public ElementBank getCost() {
        return cost;
    }
}