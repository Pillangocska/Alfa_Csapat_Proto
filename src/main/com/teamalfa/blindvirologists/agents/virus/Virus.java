package main.com.teamalfa.blindvirologists.agents.virus;

import main.com.teamalfa.blindvirologists.agents.Agent;
import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

abstract public class Virus extends Agent {

    protected int priority;

    /**
     * This method is called when the Virus is getting used on a Virologist.
     * If the infection was successful the Virus becomes active on the Virologist.
     * @param target The Virologist the Virus was used on.
     */
    @Override
    public void apply(Virologist target) {
        if (target.infectedBy(this)) {
            this.target = target;
        }
    }

    public void addVirologist(Virologist v){
        this.target = v;
    }

    /**
     * Tells the Virologist if they can do any kind of actions.
     * Default: Virologist can do any action.
     * @return false
     */
    public boolean affectUsage() {
        return false;
    }

    /**
     * Tells other Virologist if the Virologist they are trying to rob can be robbed or not.
     * Default: they cannot be robbed.
     * @return false
     */
    public boolean affectRobbability() {

        return false;
    }

    /**
     * Returns null if the Virus is not affecting the Virologist's movement.
     * @param current The Field the Virologist is standing on.
     * @return null
     */
    public Field affectMovement(Field current) {
        return null;
    }

    //getter
    public int getPriority() {
        return priority;
    }
}