package main.com.teamalfa.blindvirologists;

import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ControllerHelper {

    public static ArrayList<String> getParametersUntilFirstDash(ArrayList<String> parameters) {
        // this method slices the parameters which are before a - flag.

        ArrayList<String> slicedParameters = new ArrayList<>();
        for(String parameter : parameters) {
            if(!parameter.contains("-")) {
                slicedParameters.add(parameter);
            }else break;
        }
        return slicedParameters;
    }

    public static <T> ArrayList<T> handleManyDoNotExistError(ArrayList<String> listOfIds, HashMap<String, T> hashMap) {
        ArrayList<T> existingObjects = new ArrayList<>();
        for(String id : listOfIds) {
            existingObjects.add(handleDoesNotExistError(id, hashMap));
        }
        boolean condition = existingObjects.contains(null) ||existingObjects.isEmpty();
        return condition ? null : existingObjects;
    }

    public static <T> T handleDoesNotExistError(String idToCheck, HashMap<String, T> hashMap) {
        T objectFound = hashMap.get(idToCheck);
        if(objectFound == null)
            ErrorPrinter.doesntExistError(idToCheck);
        return objectFound;
    }

    public static <T> String registerObject(
            T object,
            HashMap<String, T> objectHolder,
            HashMap<String, Integer> idCounter,
            String type
    ) {
        String id = type + idCounter.get(type).toString();
        objectHolder.put(id, object);

        int updatedCounter = idCounter.get(type) + 1;
        idCounter.replace(type, updatedCounter);
        return id;
    }

    public static String capitalizeString(String inp) {
        return inp.substring(0, 1).toUpperCase() + inp.substring(1);
    }

    public static boolean checkCorrectFormat(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    public static int[] handleNucleotideAminoAcidQuantityFormat(ArrayList<String> input){
        if(!input.isEmpty() && checkCorrectFormat("n\\d+a\\d+", input.get(0))) {
            String parameter = input.remove(0);

            String aminoPart = parameter.substring(parameter.indexOf('a'));
            String nucleoPart = parameter.replace(aminoPart, "");

            int[] quantities = new int[2];
            quantities[0] = Integer.parseInt(aminoPart.replaceAll("\\D+", ""));
            quantities[1] = Integer.parseInt(nucleoPart.replaceAll("\\D+", ""));

            return quantities;
        }
        ErrorPrinter.printError("Element quantity format should be n{number}a{number}.");
        return null;
    }

    public static int[] handleNucleotideAminoAcidSizeFormat(ArrayList<String> input){
        if(!input.isEmpty() && checkCorrectFormat("ns\\d+as\\d+", input.get(0))) {
            String parameter = input.remove(0);

            String aminoPart = parameter.substring(parameter.indexOf("as"));
            String nucleoPart = parameter.replace(aminoPart, "");

            int[] quantities = new int[2];
            quantities[0] = Integer.parseInt(aminoPart.replaceAll("\\D+", ""));
            quantities[1] = Integer.parseInt(nucleoPart.replaceAll("\\D+", ""));

            return quantities;
        }
        ErrorPrinter.printError("Element size format should be ns{number}as{number}.");
        return null;
    }

    public static boolean printHelp(ArrayList<String> input) {
        if(input.size() == 2 && input.get(1).equals("help")) {
            String helpMsg;

            switch(input.get(0)) {
                case "createfield": helpMsg = "createfield [neighbouring field IDs] [-t <laboratory/storehouse/safehouse>]"; break;
                case "createvirologist": helpMsg = "createvirologist <field ID>"; break;
                case "createelements": helpMsg =  "createelements n<number>a<number> ns<number>as<number> <-v virolgoist Id or -s safehouse ID->"; break;
                case "createequipment": helpMsg = "createequipment <cloak/bag/gloves/axe> <-s safehouse ID/-v virologist ID>"; break;
//                case "creategeneticcode": createGeneticCode(input); break;
//                case "createagent": createAgent(input); break;
//                case "move": move(input); break;
//                case "pickupequipment": pickUpEquipment(input); break;
//                case "dropequipment": dropEquipment(input); break;
//                case "learngeneticcode": learnGeneticCode(input); break;
//                case "useequipment": useEquipment(input); break;
//                case "craftagent": craftAgent(input); break;
//                case "useagent": useAgent(input); break;
//                case "pickupmaterial": pickUpMaterial(input); break;
//                case "startturn": startTurn(input); break;
//                case "status": status(input); break;
//                case "toggle": toggle(input); break;
//                case "runscript": runScript(input); break;
//                case "search": search(input); break;
//                case "setrandom": setRandom(input); break;
//                case "exit": return true;
                default: helpMsg = "";
            }
            if(!helpMsg.isEmpty()){
                System.out.println(helpMsg);
                return true;
            }
        }
        return false;
    }
}
