package main.com.teamalfa.blindvirologists.city.fields;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

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
        virologists.add(virologist);
    }

    public void remove(Virologist virologist){
        virologists.remove(virologist);
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
        // print return value
        return false;
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

    public void destory() {}
}
