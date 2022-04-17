package main.com.teamalfa.blindvirologists.agents.virus;

import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.agents.Agent;
import main.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;
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
        AController.printCall(this, "apply", new Object[]{target});
        if (target.infectedBy(this)) {
            this.addVirologist(target);
        }
        AController.printReturn(null);
    }

    public void addVirologist(Virologist v){
        AController.printCall(this, "addVirologist", new Object[] {v});
        this.target = v;
        AController.printReturn(null);
    }

    /**
     * Tells the Virologist if they can do any kind of actions.
     * Default: Virologist can do any action.
     * @return false
     */
    public boolean affectUsage() {
        AController.printCall(this, "affectUsage", null);
        return (boolean) AController.printReturn(false);
    }

    /**
     * Tells other Virologist if the Virologist they are trying to rob can be robbed or not.
     * Default: they cannot be robbed.
     * @return false
     */
    public boolean affectRobbability() {
        AController.printCall(this, "affectRobbability", null);

        return (boolean) AController.printReturn(false);
    }

    /**
     * Returns null if the Virus is not affecting the Virologist's movement.
     * @param current The Field the Virologist is standing on.
     * @return null
     */
    public Field affectMovement(Field current) {
        AController.printCall(this, "affectMovement", new Object[]{current});

        AController.printReturn("null");
        return null;
    }

    //getter
    public int getPriority() {
        return priority;
    }
}