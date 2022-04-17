package main.com.teamalfa.blindvirologists.city.fields;

import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.util.ArrayList;

public class SafeHouse extends Field{
    private ArrayList<Equipment> equipments;

    public SafeHouse() {
        equipments = new ArrayList<>();
    }

    /**
     * Only for the TestCases in the Skeleton.
     */
    public void registerChoices() {
        AController.registerObject(null, equipments, "equipments");
    }

    /**
     * Adds an equipment to the SafeHouse.
     * @param equipment The equipment that was dropped down to the SafeHouse.
     */
    public void add(Equipment equipment) {
        AController.printCall(this, "add", new Object[]{equipment});
        equipments.add(equipment);
        AController.registerObject(null, equipments, "equipments");
        AController.printReturn(null);
    }

    /**
     * Removes an equipment from the SafeHouse.
     * @param equipment The equipment that was picke up from the SafeHouse.
     */
    public void remove(Equipment equipment) {
        AController.printCall(this, "remove", new Object[]{equipment});
        equipments.remove(equipment);
        AController.printReturn(null);
    }

    /**
     * This method is called when searched by a Virologist.
     * It puts an equipment into the Virologist's backpack.
     * @param virologist The Virologist who stands on the Field and searches it.
     */
    @Override
    public void searchedBy(Virologist virologist) {
        AController.printCall(virologist, "searchedBy", new Object[]{virologist});
        virologist.getBackpack().add((Equipment) AController.askMultiChoice("equipment", migrateChoices(equipments)));
        AController.printReturn(null);
    }

    private ArrayList<Object> migrateChoices(ArrayList<Equipment> equipments) {
        ArrayList<Object> choices = new ArrayList<>();
        for(Equipment equipment : equipments)
            choices.add(equipment);
        return choices;
    }

    @Override
    public boolean canChangeEquipment(){
        AController.printCall(this, "canChangeEquipment", null);

        return (boolean) AController.printReturn(true);
    }
}