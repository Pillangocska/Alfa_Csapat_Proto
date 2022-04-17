package main.com.teamalfa.blindvirologists.virologist.backpack;

import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.agents.Vaccine;
import main.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;
import main.com.teamalfa.blindvirologists.agents.virus.Virus;
import main.com.teamalfa.blindvirologists.city.fields.SafeHouse;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.virologist.Virologist;
import main.com.teamalfa.blindvirologists.virologist.backpack.pockets.AgentPocket;
import main.com.teamalfa.blindvirologists.virologist.backpack.pockets.EquipmentPocket;
import main.com.teamalfa.blindvirologists.virologist.backpack.pockets.GeneticCodePocket;

import java.util.ArrayList;

public class Backpack {
    private EquipmentPocket equipmentPocket;
    private AgentPocket agentPocket;
    private ElementBank elementBank;
    private GeneticCodePocket geneticCodePocket;
    private Virologist virologist;

    public Backpack(Virologist virologist) {
        equipmentPocket = new EquipmentPocket(this);
        agentPocket = new AgentPocket(this);
        elementBank = new ElementBank(0,0);
        geneticCodePocket = new GeneticCodePocket(this);
        this.virologist = virologist;
    }

    public void registerObjects() {
        AController.registerObject(this, equipmentPocket, "eqPocket"); equipmentPocket.registerObjects();
        AController.registerObject(this, agentPocket, "agPocket"); agentPocket.registerObjects();
        AController.registerObject(this, elementBank, "elBank");
        AController.registerObject(this, geneticCodePocket, "gcPocket");
    }

    public void add(Equipment equipment) {
        AController.printCall(this, "add", new Object[]{equipment});
        if(equipmentPocket.add(equipment)){
            SafeHouse safeHouse = (SafeHouse) virologist.getField();
            safeHouse.remove(equipment);
        }
        AController.printReturn(null);
    }

    public void add(GeneticCode geneticCode) {
        AController.printCall(this,"add", new Object[]{geneticCode});
        geneticCodePocket.add(geneticCode);
        AController.printReturn(null);
    }

    public void add(ElementBank elements) {
        AController.printCall(this, "add", new Object[]{elements});
        ElementBank added = elementBank.add(elements);
        elements.remove(added);
        AController.printReturn(null);
    }

    public void createVirus(GeneticCode geneticCode) {
        AController.printCall(this, "createVirus", new Object[]{geneticCode});
        Virus virus = geneticCode.createVirus(elementBank);
        if(virus != null) {
            agentPocket.addAgent(virus);
        }
    }

    public void createVaccine(GeneticCode geneticCode) {
        AController.printCall(this, "createVaccine", new Object[]{geneticCode});
        Vaccine vaccine = geneticCode.createVaccine(elementBank);
        if(vaccine != null) {
            agentPocket.addAgent(vaccine);
        }
        AController.printReturn(null);
    }


    public Virologist getVirologist() {
        AController.printCall(this, "getVirologist", null);
        return (Virologist) AController.printReturn(virologist);
    }

    public EquipmentPocket getEquipmentPocket(){
        return equipmentPocket;
    }

    public AgentPocket getAgentPocket() { return agentPocket; }

    public void deleteAllGeneticCodes() {
        geneticCodePocket.deleteAll();
    }

    public ElementBank getElementBank() {
        AController.printCall(this, "getElementBank", null);
        return (ElementBank) AController.printReturn(elementBank);
    }

    public ArrayList<Object> getAgents() {
        return agentPocket.getAgentHolder();
    }
}