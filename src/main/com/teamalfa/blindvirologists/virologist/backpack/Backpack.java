package main.com.teamalfa.blindvirologists.virologist.backpack;

import main.com.teamalfa.blindvirologists.agents.Agent;
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

    public boolean add(Equipment equipment) {
        return equipmentPocket.add(equipment);
    }

    public void add(GeneticCode geneticCode) {
        geneticCodePocket.add(geneticCode);
    }

    public void add(ElementBank elements) {
        ElementBank added = elementBank.add(elements);
        elements.remove(added);
    }

    public Agent createVirus(GeneticCode geneticCode) {
        Virus virus = geneticCode.createVirus(elementBank);
        if(virus != null) {
            agentPocket.addAgent(virus);
        }
        return virus;
    }

    public Agent createVaccine(GeneticCode geneticCode) {
        Vaccine vaccine = geneticCode.createVaccine(elementBank);
        if(vaccine != null) {
            agentPocket.addAgent(vaccine);
        }
        return vaccine;
    }


    public Virologist getVirologist() {
        return virologist;
    }

    public EquipmentPocket getEquipmentPocket(){
        return equipmentPocket;
    }

    public AgentPocket getAgentPocket() { return agentPocket; }

    public GeneticCodePocket getGeneticCodePocket() { return geneticCodePocket;}

    public void deleteAllGeneticCodes() {
        geneticCodePocket.deleteAll();
    }

    public ElementBank getElementBank() {
        return (ElementBank) elementBank;
    }

    public void setElementBank(ElementBank elementBank) {
        this.elementBank = elementBank;
    }
}