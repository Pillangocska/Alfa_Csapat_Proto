package main.com.teamalfa.blindvirologists.virologist;

import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.agents.Agent;
import main.com.teamalfa.blindvirologists.agents.Vaccine;
import main.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;
import main.com.teamalfa.blindvirologists.agents.virus.AmnesiaVirus;
import main.com.teamalfa.blindvirologists.agents.virus.Virus;
import main.com.teamalfa.blindvirologists.agents.virus.VirusComparator;
import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.ActiveEquipment;
import main.com.teamalfa.blindvirologists.virologist.backpack.Backpack;

import java.util.ArrayList;
import java.util.Collections;

public class Virologist {
    private ArrayList<GeneticCode> protectionBank;
    private ArrayList<Virus> activeViruses;
    private ArrayList<Equipment> wornEquipment = new ArrayList<>();
    private ArrayList<ActiveEquipment> activeEquipments = new ArrayList<>();
    private Backpack backpack;
    private Field field;

    public Virologist() {
        protectionBank = new ArrayList<>();
        activeViruses = new ArrayList<>();
        backpack = new Backpack(this);
    }

    /**
     * This method registers the Virologist object's into the Controller class.
     */
    public void registerObjects() {
        AController.registerObject(this, protectionBank, "protectionBank");
        AController.registerObject(this, activeViruses, "activeViruses");
        AController.registerObject(this, backpack, "backpack");

        // register nested objects
        backpack.registerObjects();
    }

    //getters setters

    public Field getField() {
        AController.printCall(this, "getField", null);
        return (Field) AController.printReturn(field);
    }
    public void setField(Field f){
        this.field = f;
    }

    public Backpack getBackpack() {
        AController.printCall(this, "getBackpack", null);
        return (Backpack) AController.printReturn(backpack);
    }

    /**
     * The method is called when the virologist moves to another field,
     * it checks if the virologist is affected by any viruses, if yes
     * the affectmovement method of the virus with the highest priority number is called.
     * It overrides the parameter of the field the virologist would like to step onto, or returns with null.
     * If the return statement is null than the destination field doesn't change, if it's not then it changes to the return statement.
     * After that the virologist is removed from their current field, and is accepted by the destination field.
     * @param destination the field the virologist would like to step onto.
     */
    public void move(Field destination) {
        AController.printCall(this, "move", new Object[]{destination});

        Field modified = null;

        if(!activeViruses.isEmpty()) modified = activeViruses.get(0).affectMovement(field);
        if(modified != null) destination = modified;

        field.remove(this);
        destination.accept(this);
        field = destination;

        AController.printReturn(null);
    }

    public void use(ActiveEquipment a, Virologist v) {
        AController.printCall(this, "use", new Object[]{a, v});
        if(!AController.askYesOrNo("Is the equipment on cooldown?")) {
            a.use(v);
        }
        AController.printReturn(null);
    }

    /**
     * This method is called when the virologist uses an agent on another virologist.
     * It calls the agent's apply method to the other virologist.
     * @param a The agent that is used on a virologist.
     * @param v The virologist the agent is used on.
     */
    public void use(Agent a, Virologist v){
        AController.printCall(this, "use", new Object[] {a, v});
        if (a != null && !checkUsageAffect())
            a.apply(v);
        AController.printReturn(null);
    }

    public void learn(GeneticCode gc) {
        if(!(this.checkUsageAffect())) {
            backpack.add(gc);
        }
    }

    /**
     * This method is called when the virologist is being robbed.
     * @return the virologist backpack, if the virologist is paralyzed, null if the virologist is not paralyzed.
     */
    public Backpack robbed() {
        AController.printCall(this, "robbed", null);
        if(!(activeViruses.isEmpty())){
            if(activeViruses.get(0).affectRobbability()){
                return (Backpack) AController.printReturn(this.backpack);
            }
        }
        AController.printReturn( "null");
        return null;
    }

    /**
     * This method is called when the virologist tries to rob another virologist.
     * It calls the other virologist's robbed method.
     * @param v The virologist that is being robbed.
     */
    public void rob(Virologist v) {
        AController.printCall(this, "rob", new Object[] {v});
        v.robbed();
        AController.printReturn(null);
    }

    /**
     * This method is called when a virologist gets infected by a virus.
     * @param virus The virus that the virologist gets infected by.
     * @return true if rhe infection was successful, otherwise it returns false.
     */
    public boolean infectedBy(Virus virus) {
        // print method call
        AController.printCall(this, "infectedBy", new Object[]{virus});

        Boolean ret = true;

        // check if protected by any vaccine
        if(protectionBank.contains(virus.getGeneticCode())) {
            ret = false;
        }else {
            // check if protected by any equipment
            for(Equipment equipment : wornEquipment)
                if(equipment.protect())
                    ret = false;
        }
        activeViruses.add(virus);

        // print return value to console
        return (Boolean) AController.printReturn(ret);
    }

    /**
     * This method is called when the virologist gets a vaccine shot.
     * @param vaccine The vaccine that gets injected to the virologist.
     */
    public void protectedBy(Vaccine vaccine) {
        AController.printCall(this, "protectedBy", new Object[]{vaccine});

        GeneticCode geneticcode = vaccine.getGeneticCode();
        AController.printCall(this, "addProtection", new Object[]{geneticcode});
        protectionBank.add(geneticcode);
        AController.printReturn(null);

        AController.printReturn(null);
    }

    /**
     * This method is called when an active virus is expired and it gets removed from the virologist.
     * @param virus The expired virus.
     */
    public void removeVirus(Virus virus) {
        AController.printCall(this, "removeVirus", new Object[]{virus});
        activeViruses.remove(virus);
        AController.printReturn(null);
    }

    /**
     * This method is called when a vaccine expires, and it gets removed from the Virologist's protectionBank.
     * @param vaccine The expired vaccine.
     */
    public void removeVaccine(Vaccine vaccine) {
        protectionBank.remove(vaccine.getGeneticCode());
    }

    /**
     * This method is called when the virologist steps on a new field and choses to explore it.
     * It calls the current field's searchedBy method.
     */
    public void search() {
        AController.printCall(this, "search", null);
        field.searchedBy(this);
        AController.printReturn(null);
    }

    //todo searchForVirologist

    /**
     * This method is called when the Virologist does some kind of action,
     * it checks if the virologist is affected by anything that would block the virologist from doing the action.
     * @return true, if it blocks the action, otherwise it returns false.
     */
    private boolean checkUsageAffect() {
        AController.printCall(this, "checkUsageAffect", null);

        if(activeViruses.isEmpty()){
            AController.printReturn(false);
            return false;
        }


        return (Boolean) AController.printReturn(activeViruses.get(0).affectUsage());
    }

    /**
     * When the virologist is infected by a new Virus the method adds said virus to the active viruses list.
     * @param virus The virus the virologist gets infected by.
     */
    public void addVirus(Virus virus) {
        activeViruses.add(virus);
        sortViruses();
    }

    private boolean checkRobbable() {
        //todo ilyet sehol se használunk
        return true;
    }

    /**
     * This method is called when the Virologist removes an equipment from themselves,
     * it removes the equipment from the wornEquipments list.
     * @param equipment The equipment the virologist removed.
     */
    public void removeWorn(Equipment equipment) {
        AController.printCall(this, "removeWorn", new Object[]{equipment});

        wornEquipment.remove(equipment);

        AController.printReturn(null);
    }

    /**
     * This method is called when the Virologist starts wearing an equipment,
     * it adds the equipment from the wornEquipments list.
     * @param equipment The equipment the virologist added.
     */
    public void addWorn(Equipment equipment) {
        AController.printCall(this, "addWorn", new Object[]{equipment});

        if(wornEquipment.size() < 3)
            wornEquipment.add(equipment);

        AController.printReturn(null);
    }

    /**
     * This method is called when the Virologist starts wearing an activeequipment,
     * it adds the activeequipment from the ActiveEquipments list.
     * @param activeEquipment The activeequipment the virologist added.
     */
    public void addActive(ActiveEquipment activeEquipment) {
        if(wornEquipment.size() < 3)
            activeEquipments.add(activeEquipment);
    }

    /**
     * This method is called when the Virologist stops wearing an activeequipment,
     * it removes the activeequipment from the ActiveEquipments list.
     * @param activeEquipment The activeequipment the virologist removed.
     */
    public void removeActive(ActiveEquipment activeEquipment) {
        activeEquipments.remove(activeEquipment);
    }

    /**
     * This method sorts the active viruses by their priority number in a ascending order.
     */
    private void sortViruses(){
        Collections.sort(activeViruses, new VirusComparator());
    }

    public ArrayList<Object> getViruses() {
        ArrayList<Object> objects = new ArrayList<>();
        for(Virus virus : activeViruses)
            objects.add(virus);
        return objects;
    }
}