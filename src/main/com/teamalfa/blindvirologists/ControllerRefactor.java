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
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ControllerRefactor {
    private static HashMap<String,Field> fieldHashMap = new HashMap<>();
    private static HashMap<String, Virologist> virologistHashMap = new HashMap<>();
    private static HashMap<String, Backpack> backpackHashMap = new HashMap<>();
    private static HashMap<String, ElementBank> elementBankHashMap = new HashMap<>();
    private static HashMap<String, Equipment> equipmentHashMap = new HashMap<>();
    private static HashMap<String, GeneticCode> geneticCodeHashMap = new HashMap<>();
    private static HashMap<String, Agent> agentHashMap = new HashMap<>();

    private static HashMap<String, Integer> idCounter = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public ControllerRefactor() {
        initIdCounter();
    }

    private void initIdCounter(){
        for(Prefixes prefix : Prefixes.values())
            idCounter.put(prefix.toString(), 1);
    }
    private void reset() {
        fieldHashMap.clear();
        virologistHashMap.clear();
        backpackHashMap.clear();
        elementBankHashMap.clear();
        equipmentHashMap.clear();
        geneticCodeHashMap.clear();
        agentHashMap.clear();
        idCounter.clear();
        initIdCounter();
    }

    public void startProgram() {
        boolean stopped = false;
        while(!stopped)
            stopped = handleCommands(readCommands());
    }

    private ArrayList<String> readCommands() {
        // make array list from input string
        return splitParameters(scanner.nextLine());
    }

    private ArrayList<String> splitParameters(String parameters) {
        return new ArrayList<>(Arrays.asList(parameters.split(" ")));
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
                    case "pickupequipment": pickDropEquipment(input, command.toLowerCase()); break;
                    case "dropequipment": pickDropEquipment(input, command.toLowerCase()); break;
                    case "learngeneticcode": learnGeneticCode(input); break;
                    case "useequipment": useEquipment(input); break;
                    case "craftagent": craftAgent(input); break;
                    case "useagent": useAgent(input); break;
                    case "pickupmaterial": pickUpMaterial(input); break;
                    case "startturn": startTurn(input); break;
                    case "endturn": endTurn(input); break;
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
        System.out.println();
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
        System.out.println("Neighbours: " + joinWithComma(neighbours, fieldHashMap));
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
        ElementBank elementBank = virologist.getBackpack().getElementBank();
        field.accept(virologist);

        // register virologist, backpack and element bank
        String virologistId = registerObject(virologist, virologistHashMap, idCounter, Prefixes.Virologist.toString());
        String backpackId = registerObject(backpack, backpackHashMap, idCounter, Prefixes.Backpack.toString());
        registerObject(elementBank, elementBankHashMap, idCounter, Prefixes.ElementBank.toString());

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

        // create equipment based on type or print error
        switch(type) {
            case "cloak": equipment = new Cloak(); break;
            case "bag": equipment = new Bag(); break;
            case "gloves": equipment = new Gloves(); break;
            case "axe": equipment = new Axe(); break;
            default: handleWrongTypeError(type, "cloak, bag, gloves or axe.");
        }

        // get flag or print error
        String flag = handleMissingFlagError("sv", input);

        String virologistId = ""; Virologist virologist = null;
        String safeHouseId = ""; SafeHouse safeHouse = null;
        boolean successful = true;

        // set destination based on flag
        if(flag.equals("-v")){
            virologistId = !input.isEmpty() ? input.remove(0) : "";
            virologist = handleDoesNotExistError(virologistId, virologistHashMap);
            successful = virologist.getBackpack().getEquipmentPocket().add(equipment);
            equipment.setVirologist(virologist);
        }else {
            safeHouseId = !input.isEmpty() ? input.remove(0) : "";
            safeHouse = (SafeHouse)handleDoesNotExistError(safeHouseId, fieldHashMap);
            safeHouse.add(equipment);
        }

        // register object
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

        // create agent based on agent type and genetic code type
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
        System.out.println("Virologist: " + getObjectId(virologist, virologistHashMap));
        System.out.println("Origin: " + startId);
        System.out.println("Destination: " + destinationId);
    }

    private void pickDropEquipment(ArrayList<String> input, String command) {
        // pick and drop actions are very similar, so handled in same function
        boolean pickAction = command.equals("pickupequipment");

        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        // get next argument or print error if missing
        String equipmentId = getNextArgument(input);

        // get equipment and field or print error
        Equipment equipment = handleDoesNotExistError(equipmentId, equipmentHashMap);
        Field field = virologist.getField();
        String fieldId = getObjectId(field, fieldHashMap);

        // check if toss/pick was successful
        boolean successful = pickAction ? virologist.pickUpEquipment(equipment) : virologist.toss(equipment);

        // print result
        System.out.println( pickAction ? "Equipment added to inventory:" : "Equipment dropped:");
        System.out.println("Virologist:" + virologistId);
        System.out.println("Equipment: " + equipmentId);
        System.out.println("Field: " + fieldId);
        System.out.println("Result: " + (successful ? "Successful" : "Failed"));
    }

    private void learnGeneticCode(ArrayList<String> input) {
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        handleWrongArgumentCountError(0,0, input);

        Field field = virologist.getField();
        String fieldId = getObjectId(field, fieldHashMap);
        String fieldType = getFieldTypeBasedOnId(fieldId);

        boolean successful = false;
        String geneticCodeId = "null";

        if(fieldType.equals("Laboratory")) {
            Laboratory laboratory = (Laboratory)field;
            GeneticCode geneticCode = laboratory.getGeneticCode();
            geneticCodeId = getObjectId(geneticCode, geneticCodeHashMap);
            successful = virologist.learn(geneticCode);
        }

        // print result
        System.out.println("Genetic code learned:");
        System.out.println("Virologist: " + virologistId);
        System.out.println("Field: " + fieldId);
        System.out.println("GeneticCode: " + geneticCodeId );
        System.out.println("Result: " + (successful ? "Successful" : "Failed"));
    }

    private void useEquipment(ArrayList<String> input) { // TODO:
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        String equipmentId = getNextArgument(input);
        Equipment equipment = handleDoesNotExistError(equipmentId, equipmentHashMap);

        if(!virologist.getActiveEquipments().contains(equipment))
            printError("Current virologist does not have equipment " + equipmentId + ".");

        // now it is safe to cast
        ActiveEquipment activeEquipment = (ActiveEquipment) equipment;

        String targetId = getNextArgument(input);
        Virologist target = handleDoesNotExistError(targetId, virologistHashMap);

        virologist.use(activeEquipment, target);

        // print result
        System.out.println("Equipment used on Virologist:");
        System.out.println("Equipment:" + equipmentId);
        System.out.println("Virologist: " + virologistId);
        System.out.println("Target: " + targetId);
        System.out.println("Result: ");
        System.out.println("Inf: ");
    }

    private void craftAgent(ArrayList<String> input) {
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        // get agent and code types
        String agentType = getNextArgument(input);
        String codeType = getNextArgument(input);
        GeneticCode code = virologist.getCodeByType(codeType);

        Agent agent = null;
        String agentId = null;

        // if virologist owns code than try creating agent
        if(code != null) {
            if(agentType.equals("vaccine")) {
                agent = virologist.getBackpack().createVaccine(code);
            }else {
                agent = virologist.getBackpack().createVirus(code);
            }
        }

        // register agent
        if(agent != null) {
            agentId = registerObject(agent, agentHashMap, idCounter, Prefixes.Agent.toString());
        }

        // print result
        System.out.println("Agent crafted:");
        System.out.println("ID:" + agentId);
        System.out.println("Type: " + agentType);
        System.out.println("GeneticCode: " + codeType);
        System.out.println("Result: " + (agent != null ? "Successful" : "Failed"));
    }

    private void useAgent(ArrayList<String> input) {
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        // check if agent exists
        String agentId = getNextArgument(input);
        Agent agent = handleDoesNotExistError(agentId, agentHashMap);

        //check if target virologist exists
        String targetId = getNextArgument(input);
        Virologist target = handleDoesNotExistError(targetId, virologistHashMap);

        virologist.use(agent, target);
        boolean successful = target.getViruses().contains(agent);

        // print result
        System.out.println("Agent used on virologist:");
        System.out.println("Agent: " + agentId);
        System.out.println("Virologist: " + virologistId);
        System.out.println("Target: " + targetId);
        System.out.println("Result: " + (successful ? "Successful" : "Failed"));
    }

    private void pickUpMaterial(ArrayList<String> input) {
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        handleWrongArgumentCountError(0,0, input);

        Field field = virologist.getField();
        String fieldId = getObjectId(field, fieldHashMap);
        String fieldType = getFieldTypeBasedOnId(fieldId);

        int currentNuc = virologist.getBackpack().getElementBank().getNucleotide();
        int currentAmi = virologist.getBackpack().getElementBank().getAminoAcid();

        virologist.pickUpMaterial();

        int pickedNuc = virologist.getBackpack().getElementBank().getNucleotide() - currentNuc;
        int pickedAmi = virologist.getBackpack().getElementBank().getAminoAcid() -currentAmi;


        // print result
        System.out.println("Material picked up:");
        System.out.println("Field: " + fieldId);
        System.out.println("Nucleotide: " + pickedNuc);
        System.out.println("AminoAcid: " + pickedAmi);
    }

    private void startTurn(ArrayList<String> input) {
        // check if virologist exists
        String virologistId = getNextArgument(input);
        Virologist virologist = handleDoesNotExistError(virologistId, virologistHashMap);

        // set current virologist and reorder
        TurnHandler.setActiveVirologist(virologist);

        System.out.println(virologistId + "'s turn started.");
    }

    private void endTurn(ArrayList<String> input) {
        // get current virologist
        Virologist current = TurnHandler.getActiveVirologist();
        String currentId = getObjectId(current, virologistHashMap);
        ArrayList<Virologist> order = TurnHandler.GetOrder();

        // calculate next virologists idx
        int idx = order.indexOf(current);
        int nextIdx =  idx < order.size() - 1 ? idx+1 : 0;

        Virologist next = order.get(nextIdx);
        String nextId = getObjectId(next, virologistHashMap);

        TurnHandler.setActiveVirologist(next);

        // print result
        System.out.println(currentId+"'s turn ended. Next virologist is " + nextId + ".");
    }

    private void status(ArrayList<String> input) {
        for(String id : input) {
            String prefix = id.replaceAll("\\d", "");

            if(FIELD_PREFIXES.contains(prefix))
                printStatus(handleDoesNotExistError(id, fieldHashMap), false);

            switch (prefix) {
                case "V": printStatus(handleDoesNotExistError(id, virologistHashMap)); break;
                case "EB": printStatus(handleDoesNotExistError(id, elementBankHashMap)); break;
                case "GC": printStatus(handleDoesNotExistError(id, geneticCodeHashMap)); break;
                case "A": printStatus(handleDoesNotExistError(id, agentHashMap)); break;
                case "E": printStatus(handleDoesNotExistError(id, equipmentHashMap)); break;
                case "B": printStatus(handleDoesNotExistError(id, backpackHashMap)); break;
                default:
            }
            System.out.println();
        }
    }

    private void toggle(ArrayList<String> input) {
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);


        String equipmentId = getNextArgument(input);
        Equipment equipment = handleDoesNotExistError(equipmentId, equipmentHashMap);
        virologist.toggle(equipment);

        String info = virologist.getWornEquipment().contains(equipment) ? "wear" : "unwear";

        // print result
        System.out.println("Equipment toggled on Virologist:");
        System.out.println("Equipment: " + equipmentId);
        System.out.println("Virologist: " + virologistId);
        System.out.println("Inf: " + info);
    }

    private void runScript(ArrayList<String> input) {
        String path = getNextArgument(input);
        String script = "";
        String fullPath = System.getProperty("user.dir") + "/" + path;
        try {
            script = new String(Files.readAllBytes(Paths.get(fullPath)));
        } catch (IOException e) {
            System.out.println("An error occurred while reading a testscript from " + fullPath + "!");
        }

        String[] lines = script.split("\n");
        for(String line : lines){
            if(!line.isEmpty()) {
                handleCommands(splitParameters(line));
            }
        }
        reset();
    }

    private void search(ArrayList<String> input) {
        // get current virologist or print error
        Virologist virologist = handleCurrentVirologistError();
        String virologistId = getObjectId(virologist, virologistHashMap);

        Field field = virologist.getField();
        String fieldId = getObjectId(field, fieldHashMap);

        if(virologist.isParalyzed())
            ErrorPrinter.printError("You are paralyzed.");

        printStatus(field, true);
    }
    
    private void setRandom(ArrayList<String> input) { // TODO
        String yesOrNo = getNextArgument(input);
        String choiceType = getNextArgument(input);


    }

    // Status print help methods
    private void printStatus(Virologist virologist) {
        // put derived classes into super arrays
        ArrayList<Equipment> activeWearing =  createSuperArrayList(virologist.getActiveEquipments());
        ArrayList<Agent> activeViruses = createSuperArrayList(virologist.getViruses());

        System.out.println("Virologist:");
        System.out.println("ID: " + getObjectId(virologist, virologistHashMap));
        System.out.println("Field: " + getObjectId(virologist.getField(), fieldHashMap));
        System.out.println("Backpack: " + getObjectId(virologist.getBackpack(), backpackHashMap));
        System.out.println("Wearing: " + joinWithComma(virologist.getWornEquipment(), equipmentHashMap));
        System.out.println("ActiveWearing: " + joinWithComma(activeWearing, equipmentHashMap));
        System.out.println("ActiveViruses: " + joinWithComma(activeViruses, agentHashMap));
        System.out.println("ProtectionBank: " + joinWithComma(virologist.getProtectionBank(), geneticCodeHashMap));
    }

    public static void handleBearCreated(Virologist virologist, Agent virus, boolean successful) {
        String virusId = registerObject(virus, agentHashMap, idCounter, Prefixes.Agent.toString());

        System.out.println("Agent created:");
        System.out.println("ID: " + virusId);
        System.out.println("GeneticCode: Bear");
        System.out.println("Virologist: " + getObjectId(virologist, virologistHashMap));
        System.out.println("Result: Successful");
        System.out.println();
        System.out.println("Agent used on Virologist:");
        System.out.println("Agent: " + virusId);
        System.out.println("Target: " + getObjectId(virologist, virologistHashMap));
        System.out.println("Result: " + (successful ? "Successful" : "Failed"));
        System.out.println();
    }

    private void printStatus(Field field, boolean searched) {
        String id = getObjectId(field, fieldHashMap);
        String type = getFieldTypeBasedOnId(id);

        GeneticCode geneticCode = null; String geneticText = "null";
        ArrayList<Equipment> equipments = null; String equipmentText = "null";
        ElementBank elements = null; String elementsText = "null";

        if (type.equals("Laboratory")) {
            Laboratory laboratory = (Laboratory) field;
            geneticCode = laboratory.getGeneticCode();
            geneticText = getObjectId(geneticCode, geneticCodeHashMap);
        }
        else if(type.equals("SafeHouse")) {
            SafeHouse safeHouse = (SafeHouse) field;
            equipments = safeHouse.getEquipments();
            equipmentText = joinWithComma(equipments, equipmentHashMap);
        }else if(type.equals("StoreHouse")){
            StoreHouse storeHouse = (StoreHouse) field;
            elements = storeHouse.getElements();
            elementsText = getObjectId(elements, elementBankHashMap);
        }

        System.out.println((searched ? "Virologist searched:" : "Field:"));
        System.out.println("ID: " + id);
        System.out.println("Type: " + capitalizeString(type));
        System.out.println("Neighbours: " + joinWithComma(field.getNeighbours(), fieldHashMap));
        System.out.println("Virologists: " + joinWithComma(field.getVirologists(), virologistHashMap));
        System.out.println("GeneticCodes: " + geneticText);
        System.out.println("Equipments: " + equipmentText);
        System.out.println("Elements: " + elementsText);
    }

    private void printStatus(ElementBank elementBank) {
        System.out.println("ElementBank:");
        System.out.println("ID: " + getObjectId(elementBank, elementBankHashMap));
        System.out.println("Nucleotide: " + elementBank.getNucleotide());
        System.out.println("AminoAcid: " + elementBank.getAminoAcid());
        System.out.println("NucleotideSize: " + elementBank.getNucleotideMaxSize());
        System.out.println("AminoAcidSize: " + elementBank.getAminoAcidMaxSize());
    }

    private void printStatus(GeneticCode geneticCode) {
        System.out.println("GeneticCode:");
        System.out.println("ID: " + getObjectId(geneticCode, geneticCodeHashMap));
        System.out.println("GeneticCode: " + capitalizeString(geneticCode.getType()));
    }

    private  void printStatus(Equipment equipment) {
        System.out.println("Equipment:");
        System.out.println("ID: " + getObjectId(equipment, equipmentHashMap));
        System.out.println("Type: " + capitalizeString(equipment.getType()));
    }

    private void printStatus(Agent agent) {
        System.out.println("Agent:");
        System.out.println("ID: " + getObjectId(agent, agentHashMap));
        System.out.println("Name:" + capitalizeString(agent.getGeneticCode().getType()));
    }

    private void printStatus(Backpack backpack) {
        System.out.println("Backpack:");
        System.out.println("ID: " + getObjectId(backpack, backpackHashMap));
        System.out.println("Equipments: " + joinWithComma(backpack.getEquipmentPocket().getEquipmentHolder(), equipmentHashMap));
        System.out.println("Agents: " + joinWithComma(backpack.getAgentPocket().getAgentHolder(), agentHashMap));
        System.out.println("ElementBank: " + getObjectId(backpack.getElementBank(), elementBankHashMap));
    }
}
