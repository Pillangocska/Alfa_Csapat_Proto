package main.com.teamalfa.blindvirologists.city.fields;
import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Field {
    //Virologists on the field
    protected ArrayList<Virologist> virologists = new ArrayList<>();
    //Neighbouring fields
    protected ArrayList<Field> neighbours = new ArrayList<>();

    //Constructor todo
    public Field(){

    }

    //Getters&Setters
    public ArrayList<Virologist> getVirologists(){
        return virologists;
    }
    public ArrayList<Field> getNeighbours(){
        return neighbours;
    }

    //Adding and removing a virologist
    public void accept(Virologist virologist){
        AController.printCall(this, "accept", new Object[]{virologist});
        virologists.add(virologist);
        AController.printReturn(null);
    }

    public void remove(Virologist virologist){
        AController.printCall(this, "remove", new Object[]{virologist});
        virologists.remove(virologist);
        AController.printReturn(null);
    }

    //Gives back the virologists on the field
    //except the one calling it todo
    public List<Virologist> searchForVirologist(){ return null; }

    /**
     * This method is called when a virologist searches a Field.
     * Doesn't do anything cause field is always empty.
     * @param virologist The Virologist who stands on the Field and searches it.
     */
    public void searchedBy(Virologist virologist){}

    /**
     * Tells the Virologist if it's possible to change or toss any equipment on this Field.
     * Default: it's not possible.
     * @return false
     */
    public boolean canChangeEquipment() {
        // print method call
        AController.printCall(this, "canChangeEquipment", null);
        // print return value
        return (Boolean) AController.printReturn(false);
    }

    /**
     * doesnt do anything
     * @param e the equipment that gets put down to the field.
     */
    public void add(Equipment e) { }

    public void setNeighbour(Field f1) {
        this.neighbours.add(f1);
    }

    public void setNeighbours(ArrayList<Field> neighbours) {
        this.neighbours = neighbours;
    }
}
