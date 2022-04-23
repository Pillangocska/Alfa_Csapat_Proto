package main.com.teamalfa.blindvirologists.city.fields;

import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.util.ArrayList;

public class SafeHouse extends Field{
    private ArrayList<Equipment> equipments;

    public SafeHouse() {
        equipments = new ArrayList<>();
    }


    /**
     * Adds an equipment to the SafeHouse.
     * @param equipment The equipment that was dropped down to the SafeHouse.
     */
    public void add(Equipment equipment) {
        equipments.add(equipment);
    }

    /**
     * Removes an equipment from the SafeHouse.
     * @param equipment The equipment that was picke up from the SafeHouse.
     */
    public void remove(Equipment equipment) {
        equipments.remove(equipment);
    }

    /**
     * This method is called when searched by a Virologist.
     * It puts an equipment into the Virologist's backpack.
     * @param virologist The Virologist who stands on the Field and searches it.
     */
    @Override
    public void searchedBy(Virologist virologist) {
        //todo
    }

    /**
     * Tells if player's can change their equipment on the field.
     * @return Always returns true.
     */
    @Override
    public boolean canChangeEquipment(){
        return true;
    }

    //getter
    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }
}