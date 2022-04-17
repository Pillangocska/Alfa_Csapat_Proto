package main.com.teamalfa.blindvirologists.agents.virus;

import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.city.fields.Field;

import java.util.ArrayList;

public class DanceVirus extends Virus {

    /**
     * This method is responsible for picking a random Field that neighbours the Field the Virologist
     * is standing on at the moment.
     * @param neighbours The neighbouring fields.
     * @return The random chosen Field.
     */
    private Field pickRandom(ArrayList<Field> neighbours) {

        AController.printCall(this, "pickRandom", new Object[]{neighbours});
        return (Field) AController.printReturn(AController.askMultiChoice("destination field", migrateChoices(neighbours)));
    }

    /**
     * This method makes the Virologist move to a random Field instead of the chosen one.
     * There's a slight chance the chosen Field and the random Field will be the same.
     * @param current The Field the Virologist is standing on.
     * @return The chosen Field.
     */
    @Override
    public Field affectMovement(Field current) {
        AController.printCall(this, "affectMovement", new Object[]{current});
        return (Field) AController.printReturn(pickRandom(current.getNeighbours()));
    }

    /**
     * Helper method, creates an ArrayList<Object> from an ArrayList<Field> for the choices.
     * @param neighbours The starter List.
     * @return The end result List.
     */
    private ArrayList<Object> migrateChoices(ArrayList<Field> neighbours) {
        ArrayList<Object> choices = new ArrayList<>();
        for(Field field : neighbours)
            choices.add(field);
        return choices;
    }
}