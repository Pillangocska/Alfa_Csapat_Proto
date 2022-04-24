package main.com.teamalfa.blindvirologists;

import main.com.teamalfa.blindvirologists.agents.Agent;
import main.com.teamalfa.blindvirologists.agents.Vaccine;
import main.com.teamalfa.blindvirologists.agents.genetic_code.*;
import main.com.teamalfa.blindvirologists.agents.virus.*;
import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.city.fields.Laboratory;
import main.com.teamalfa.blindvirologists.city.fields.SafeHouse;
import main.com.teamalfa.blindvirologists.city.fields.StoreHouse;
import main.com.teamalfa.blindvirologists.equipments.Bag;
import main.com.teamalfa.blindvirologists.equipments.Cloak;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.ActiveEquipment;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Axe;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Gloves;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;
import main.com.teamalfa.blindvirologists.virologist.backpack.Backpack;
import main.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

import static main.com.teamalfa.blindvirologists.ControllerHelper.*;
import static main.com.teamalfa.blindvirologists.ErrorPrinter.*;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ControllerRefactor {
    // test
    private HashMap<String, Object> objectHashMap = new HashMap<>();

    private HashMap<String,Field> fieldHashMap = new HashMap<>();
    private HashMap<String, Virologist> virologistHashMap = new HashMap<>();
    private HashMap<String, Backpack> backpackHashMap = new HashMap<>();
    private HashMap<String, ElementBank> elementBankHashMap = new HashMap<>();
    private HashMap<String, Equipment> equipmentHashMap = new HashMap<>();
    private HashMap<String, GeneticCode> geneticCodeHashMap = new HashMap<>();
    private HashMap<String, Agent> agentHashMap = new HashMap<>();

    private HashMap<String, Integer> idCounter = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public ControllerRefactor() {
        initIdCounter();
    }

    private void initIdCounter(){
        for(Prefixes prefix : Prefixes.values())
            idCounter.put(prefix.toString(), 0);
    }

    public void startProgram() {
        boolean stopped = false;
        while(!stopped)
            stopped = handleCommands(readCommands());
    }

    private ArrayList<String> readCommands() {
        // make array list from input string
        return new ArrayList<>(Arrays.asList(scanner.nextLine().split(" ")));
    }

    private boolean handleCommands(ArrayList<String> input) {
        try{
            if(!printHelp(input)){
                // get command if not empty
                String command = getNextArgument(input);

                switch(command.toLowerCase()){
                    case "createfield": createField(input); break;
                    case "createvirologist": createVirologist(input); break;
                    case "createelements": createElements(input); break;
                    case "createequipment": createEquipment(input); break;
                    case "creategeneticcode": createGeneticCode(input); break;
                    case "createagent": createAgent(input); break;
                    case "move": move(input); break;
                    case "pickupequipment": pickUpEquipment(input); break;
                    case "dropequipment": dropEquipment(input); break;
                    case "learngeneticcode": learnGeneticCode(input); break;
                    case "useequipment": useEquipment(input); break;
                    case "craftagent": craftAgent(input); break;
                    case "useagent": useAgent(input); break;
                    case "pickupmaterial": pickUpMaterial(input); break;
                    case "startturn": startTurn(input); break;
                    case "status": status(input); break;
                    case "toggle": toggle(input); break;
                    case "runscript": runScript(input); break;
                    case "search": search(input); break;
                    case "setrandom": setRandom(input); break;
                    case "exit": return true;
                    default: System.out.println("Wrong command.");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private void createField(ArrayList<String> input) {
        // get neighbour ids until flag and check if all neighbours exist.
        ArrayList<String> neighbourIds = getParametersUntilFirstDash(input);
        ArrayList<Field> neighbours = handleManyDoNotExistError(neighbourIds, fieldHashMap);

        // strip down neighbour ids because they are no longer necessary
        input.removeAll(neighbourIds);

        // check if flag isn't missing
        if(!input.isEmpty())
            handleMissingFlagError("t", input);

        // by default create a "simple" field
        String type = (input.isEmpty()) ? "field" : input.remove(0);

        Field field = null;
        String idPrefix = "";

        switch (type) {
            case "field": field = new Field(); idPrefix = Prefixes.Field.toString(); break;
            case "laboratory": field = new Laboratory(); idPrefix = Prefixes.Laboratory.toString();  break;
            case "storehouse": field = new StoreHouse(); idPrefix = Prefixes.StoreHouse.toString(); break;
            case "safehouse": field = new SafeHouse(); idPrefix = Prefixes.SafeHouse.toString(); break;
            default: handleWrongTypeError(type, FIELD_TYPES);
        }

        // register object and get id
        field.setNeighbours(neighbours);
        String id = registerObject(field, fieldHashMap, idCounter, idPrefix);

        // print creation
        System.out.println("Field created:");
        System.out.println("ID: " + id);
        System.out.println("Type: " + capitalizeString(type));
        System.out.println("Neighbours: " + String.join(", ", neighbourIds));
    }

    private void createVirologist(ArrayList<String> input) {
        Field field = null;
        String fieldId = "";

        // Check if field is exists
        fieldId = getNextArgument(input);
        field = handleDoesNotExistError(fieldId, fieldHashMap);

        // create virologist and position on given field
        Virologist virologist = new Virologist();
        Backpack backpack = virologist.getBackpack();
        field.accept(virologist);

        // register objects and get ids
        String virologistId = registerObject(virologist, virologistHashMap, idCounter, Prefixes.Virologist.toString());
        String backpackId = registerObject(backpack, backpackHashMap, idCounter, Prefixes.Backpack.toString());

        // print creation
        System.out.println("Virologist created:");
        System.out.println("ID: " + virologistId);
        System.out.println("Field: " + fieldId);
        System.out.println("Backpack: " + backpackId);
    }

    private void createElements(ArrayList<String> input) {
        // get all parameters before flag, should be element quantities and max sizes
        ArrayList<String> quantitiesParams = getParametersUntilFirstDash(input);
        input.removeAll(quantitiesParams);

        // check if quantity and max size syntax are correct
        int[] quantities = handleNucleotideAminoAcidQuantityFormat(quantitiesParams);
        int[] maxSizes = handleNucleotideAminoAcidSizeFormat(quantitiesParams);

        // check if flag is missing
        String flag = handleMissingFlagError("vs", input);

        ElementBank elementBank = new ElementBank(quantities[0], quantities[1], maxSizes[0], maxSizes[1]);
        String virologistId = ""; Virologist virologist = null;
        String storeHouseId = ""; StoreHouse storeHouse = null;

        if(flag.equals("-v")) {
            virologistId = getNextArgument(input);
            virologist = handleDoesNotExistError(virologistId, virologistHashMap);
            virologist.getBackpack().setElementBank(elementBank);
        }else {
            storeHouseId = getNextArgument(input);
            storeHouse = (StoreHouse) handleDoesNotExistError(storeHouseId, fieldHashMap);
            storeHouse.setElements(elementBank);
        }

        // register object
        String elementId = registerObject(elementBank, elementBankHashMap, idCounter, Prefixes.ElementBank.toString());
        String destination = virologistId.isEmpty() ? storeHouseId : virologistId;

        // print creation
        System.out.println("Elements created:");
        System.out.println("ID: " + elementId);
        System.out.println("Nucleotide: " + quantities[0]);
        System.out.println("AminoAcid: " + quantities[1]);
        System.out.println("AminoAcidSize: " + maxSizes[0]);
        System.out.println("AminoAcidSize: " + maxSizes[1]);
        System.out.println("Destination: " + destination);
    }

    private void createEquipment(ArrayList<String> input) {
        String type = getNextArgument(input);

        Equipment equipment = null;

        switch(type) {
            case "cloak": equipment = new Cloak(); break;
            case "bag": equipment = new Bag(); break;
            case "gloves": equipment = new Gloves(); break;
            case "axe": equipment = new Axe(); break;
            default: handleWrongTypeError(type, "cloak, bag, gloves or axe.");
        }

        String flag = handleMissingFlagError("sv", input);

        String virologistId = ""; Virologist virologist = null;
        String safeHouseId = ""; SafeHouse safeHouse = null;
        boolean successful = true;

        if(flag.equals("-v")){
            virologistId = !input.isEmpty() ? input.remove(0) : "";
            virologist = handleDoesNotExistError(virologistId, virologistHashMap);
            successful = virologist.getBackpack().getEquipmentPocket().add(equipment);
        }else {
            safeHouseId = !input.isEmpty() ? input.remove(0) : "";
            safeHouse = (SafeHouse)handleDoesNotExistError(safeHouseId, fieldHashMap);
            safeHouse.add(equipment);
        }

        String equipmentId = registerObject(equipment, equipmentHashMap, idCounter, Prefixes.Equipment.toString());
        String destination = virologistId.isEmpty() ? safeHouseId : virologistId;

        // print creation
        System.out.println("Equipment created:");
        System.out.println("ID: " + equipmentId);
        System.out.println("Type: " + type);
        System.out.println("Destination: " + destination);
        System.out.println("Result: " + (successful ? "Successful" : "Failed"));
    }

    private void createGeneticCode(ArrayList<String> input) {
        // check argument count and get code type
        String type = getNextArgument(input);
        GeneticCode geneticCode = null;

        // create code based on type, handle error if type does not exist
        switch(type) {
            case "paralyze": geneticCode = new ParalyzeCode(); break;
            case "amnesia": geneticCode = new AmnesiaCode(); break;
            case "dance": geneticCode = new DanceCode(); break;
            case "bear": geneticCode = new BearCode(); break;
            default: handleWrongTypeError(type, GENETIC_CODES);
        }

        // check if flag is missing
        String flag = handleMissingFlagError("lv", input);

        String virologistId = ""; Virologist virologist = null;
        String laboratoryId = ""; Laboratory laboratory = null;

        // set destination based on flag
        if(flag.equals("-v")){
            virologistId = getNextArgument(input);
            virologist = handleDoesNotExistError(virologistId, virologistHashMap);
            virologist.getBackpack().add(geneticCode);
        }else {
            laboratoryId = getNextArgument(input);
            laboratory = (Laboratory) handleDoesNotExistError(laboratoryId, fieldHashMap);
            laboratory.setGeneticCode(geneticCode);
        }

        // register object
        String geneticCodeId = registerObject(geneticCode, geneticCodeHashMap, idCounter, Prefixes.GeneticCode.toString());
        String destination = virologistId.isEmpty() ? laboratoryId : virologistId;

        // print creation
        System.out.println("GeneticCode created:");
        System.out.println("ID: " + geneticCodeId);
        System.out.println("Type: " + type);
        System.out.println("Destination: " + destination);
    }

    private void createAgent(ArrayList<String> input) {
        String agentType = getNextArgument(input);
        Agent agent = null;

        // handle type of agent error
        if(!agentType.equals("vaccine") && !agentType.equals("virus"))
            handleWrongTypeError(agentType, "vaccine or virus.");


        String geneticCodeType = getNextArgument(input);

        if(agentType.equals("vaccine")) {
            switch(geneticCodeType) {
                case "paralyze": agent = new Vaccine(new ParalyzeCode()); break;
                case "amnesia": agent = new Vaccine(new AmnesiaCode()); break;
                case "dance":  agent = new Vaccine(new DanceCode()); break;
                case "bear": agent = new Vaccine(new BearCode()); break;
                default: handleWrongTypeError(geneticCodeType, GENETIC_CODES);
            }
        }else {
            switch(geneticCodeType) {
                case "paralyze": agent = new ParalyzeVirus();break;
                case "amnesia": agent = new AmnesiaVirus();break;
                case "dance": agent = new DanceVirus();break;
                case "bear": agent = new BearVirus();break;
                default: handleWrongTypeError(geneticCodeType, GENETIC_CODES);
            }
        }

        // check if virologist exists and try to put agent into backpack
        String virologistId = getNextArgument(input);
        Virologist virologist = handleDoesNotExistError(virologistId, virologistHashMap);
        boolean successful = virologist.getBackpack().getAgentPocket().addAgent(agent);

        // register object
        String agentID = registerObject(agent, agentHashMap, idCounter, Prefixes.Agent.toString());

        // print creation
        System.out.println("Agent created:");
        System.out.println("ID: " + agentID);
        System.out.println("Type: " + agentType);
        System.out.println("GeneticCode: " + geneticCodeType);
        System.out.println("Virologist: " + virologistId);
        System.out.println("Result: " + (successful ? "Successful" : "Failed"));
    }

    private void move(ArrayList<String> input) {
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();

        // get start and destination field ids
        String startId = getObjectId(virologist.getField(), fieldHashMap);
        String destinationId = getNextArgument(input);

        // get destination or print error
        Field destination = handleDoesNotExistError(destinationId, fieldHashMap);

        virologist.move(destination);

        // check if virologist moved
        if(destination != virologist.getField()) {
            destinationId = startId;
        }

        // print result
        System.out.println("Virologist moved:");
        System.out.println("Origin:" + startId);
        System.out.println("Destination: " + destinationId);
    }

    private void pickUpEquipment(ArrayList<String> input) {
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        String equipmentId = getNextArgument(input);

        if(equipmentId == null)
            printError("Missing equipment id.");

        Equipment equipment = handleDoesNotExistError(equipmentId, equipmentHashMap);
        Field field = virologist.getField();
        String fieldId = getObjectId(field, fieldHashMap);

        boolean successful = virologist.pickUpEquipment(equipment);

        // print result
        System.out.println("Equipment added to inventory:");
        System.out.println("Virologist:" + virologistId);
        System.out.println("Equipment: " + equipmentId);
        System.out.println("Field: " + fieldId);
        System.out.println("Result: " + (successful ? "Successful" : "Failed"));
    }

    private void dropEquipment(ArrayList<String> input) {
        System.out.println("Not yet implemented.");
    }

    private void learnGeneticCode(ArrayList<String> input) {
        System.out.println("Not yet implemented.");
    }

    private void useEquipment(ArrayList<String> input) {
        System.out.println("Not yet implemented.");
    }

    private void craftAgent(ArrayList<String> input) {
        System.out.println("Not yet implemented.");
    }

    private void useAgent(ArrayList<String> input) {
        System.out.println("Not yet implemented.");
    }

    private void pickUpMaterial(ArrayList<String> input) {
        System.out.println("Not yet implemented.");
    }

    private void startTurn(ArrayList<String> input) {
        System.out.println("Not yet implemented.");
    }

    private void status(ArrayList<String> input) {
        for(String id : input) {
            String prefix = id.replaceAll("\\d", "");

            if(FIELD_PREFIXES.contains(prefix))
                printStatus(handleDoesNotExistError(id, fieldHashMap));

            switch (prefix) {
                case "V": printStatus(handleDoesNotExistError(id, virologistHashMap)); break;
                case "EB": printStatus(handleDoesNotExistError(id, elementBankHashMap)); break;
                case "GC": printStatus(handleDoesNotExistError(id, geneticCodeHashMap)); break;
                case "A": printStatus(handleDoesNotExistError(id, agentHashMap)); break;
                case "E": printStatus(handleDoesNotExistError(id, equipmentHashMap)); break;
                default:
            }
            System.out.println();
        }
    }

    private void toggle(ArrayList<String> input) {
        System.out.println("Not yet implemented.");
    }

    private void runScript(ArrayList<String> input) {
        System.out.println("Not yet implemented.");
    }

    private void search(ArrayList<String> input) {
        System.out.println("Not yet implemented.");
    }
    
    private void setRandom(ArrayList<String> input) {
        System.out.println("Not yet implemented.");
    }

    // Status print help methods
    private void printStatus(Virologist virologist) {
        System.out.println("Virologist created:");
        System.out.println("ID: " + getObjectId(virologist, virologistHashMap));
        System.out.println("Field: " + getObjectId(virologist.getField(), fieldHashMap));
        System.out.println("Backpack: " + getObjectId(virologist.getBackpack(), backpackHashMap));
        System.out.println("Wearing: " + joinWithComma(virologist.getWornEquipment(), equipmentHashMap));
        //System.out.println("ActiveWearing: " + String.join(", ", getManyObjectIds(activeWearing, equipmentHashMap)));
        //System.out.println("ActiveViruses: " + String.join(",", getManyObjectIds(activeViruses, agentHashMap)));
        System.out.println("ProtectionBank: " + joinWithComma(virologist.getProtectionBank(), geneticCodeHashMap));
    }

    private void printStatus(Field field) {
        String id = getObjectId(field, fieldHashMap);
        String prefix = id.replaceAll("\\d", "").toLowerCase();
        String type = "";
        for(String fieldType : FIELD_TYPES) {
            if(fieldType.substring(0,2).equals(prefix) ||fieldType.charAt(0) == prefix.charAt(0)){
                type = fieldType;
                break;
            }
            type = "field";
        }

        System.out.println("Field:");
        System.out.println("ID: " + id);
        System.out.println("Type: " + capitalizeString(type));
        System.out.println("Neighbours: " + joinWithComma(field.getNeighbours(), fieldHashMap));
    }

    private void printStatus(ElementBank elementBank) {

    }

    private void printStatus(GeneticCode geneticCode) {

    }

    private  void printStatus(Equipment equipment) {

    }

    private void printStatus(Agent agent) {

    }
}
