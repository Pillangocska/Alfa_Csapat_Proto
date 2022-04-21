package main.com.teamalfa.blindvirologists.agents.virus;

import main.com.teamalfa.blindvirologists.agents.genetic_code.BearCode;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

public class BearVirus extends DanceVirus{

    @Override
    public void apply(Virologist target) {
        if(target.infectedBy(this)) {
            this.addVirologist(target);
            target.addVirus(this);
            target.turntoBear();
        }
    }

    /**
     * This method doesn't do anything,
     * because the BearVirus(unlike the other viruses) doesn't expire.
     */
    public void step(){}
}
