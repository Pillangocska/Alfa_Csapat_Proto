package main.com.teamalfa.blindvirologists;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Controller {
    public void createfield(String[] neighbourIDs, String type) {
        for(var n : neighbourIDs)
            System.out.println(n);
        System.out.println();

        System.out.println(type);
    }

    /**
     * Read a set of commands (a testscript) from a file, run it and print the results to the console.
     * @param path The relative path from the testscript from the project's root folder.
     * */
    public void runscript(String path) {
        String script = "";
        String fullPath = System.getProperty("user.dir") + "\\" + path;
        try {
            script = new String(Files.readAllBytes(Paths.get(fullPath)));
        } catch (IOException e) {
            System.out.println("An error occurred while reading a testscript from " + fullPath + "!");
        }

        String[] lines = script.split("\n");
        for(var l : lines)
            if(!l.equals(""))
                runCommand(l);
    }

    /**
     * Run a command, if it is syntactically correct and print an error to the console it is not.
     * @param command The command to be run.
     * */
    public void runCommand(String command) {
        command = command.toLowerCase();
        String[] parts = command.split(" ");
        String fieldType = "";
        String[] neighbours = {};

        if (parts[0].equals("createfield")) {
            if(parts.length > 2) {
                if (parts[parts.length - 2].equals("-t")) {
                    fieldType = parts[parts.length-1];
                    neighbours = Arrays.copyOfRange(parts, 1, parts.length - 2);
                } else {
                    neighbours = Arrays.copyOfRange(parts, 1, parts.length);
                }
            }

            createfield(neighbours, fieldType);

            return;
        }

        if (parts[0].equals("createvirologist")) {

            return;
        }

        if (parts[0].equals("createelements")) {

            return;
        }

        if (parts[0].equals("createequipment")) {

            return;
        }

        if (parts[0].equals("creategeneticcode")) {

            return;
        }

        if (parts[0].equals("createagent")) {

            return;
        }

        if (parts[0].equals("move")) {

            return;
        }

        if (parts[0].equals("pickupequipment")) {

            return;
        }

        if (parts[0].equals("dropequipment")) {

            return;
        }

        if (parts[0].equals("learngeneticcode")) {

            return;
        }

        if (parts[0].equals("useequipment")) {

            return;
        }

        if (parts[0].equals("craftagent")) {

            return;
        }

        if (parts[0].equals("useagent")) {

            return;
        }

        if (parts[0].equals("pickupmaterial")) {

            return;
        }

        if (parts[0].equals("rob")) {

            return;
        }

        if (parts[0].equals("startturn")) {

            return;
        }

        if (parts[0].equals("status")) {

            return;
        }

        if (parts[0].equals("toggle")) {

            return;
        }

        if (parts[0].equals("runscript")) {

            return;
        }

        if (parts[0].equals("search")) {

            return;
        }

        if (parts[0].equals("setrandom")) {

            return;
        }
    }

    /**
     * This method is used to run predefined tests.
     * */
    public void runTest() {
        System.out.println("Choose a test to run! Enter a number between 0 and 38!");
        System.out.println(
                        "0: Exit\n" +
                        "1: Virologist steps on a new field, and searches for another virologist and finds one\n" +
                        "2: Virologist steps on a new field, and searches for other virologist but can’t find any\n" +
                        "3: Virologist searches in a laboratory that contains dance genetic code\n" +
                        "4: Virologist searches in a laboratory that contains an already learnt dance genetic code\n" +
                        "5: Virologist searches in a laboratory that doesn’t contain genetic code\n" +
                        "6: Virologist steps into infected laboratory and gets infected by BearVirus\n" +
                        "7: Virologist steps into infected laboratory but wears a cloak and it doesn’t block the infection\n" +
                        "8: Virologist steps into infected laboratory but they are vaccinated against BearVirus\n" +
                        "9: Virologist searches in a storehouse and collects elements\n" +
                        "10: Virologist searches in an empty storehouse\n" +
                        "11: Virologist with a full bag searches in a storehouse\n" +
                        "12: Virologist searches a safehouse that contains an axe and picks it up\n" +
                        "13: Virologist searches an empty safehouse\n" +
                        "14: Virologist moves\n" +
                        "15: Virologist moves while affected by DanceVirus\n" +
                        "16: Virologist moves while affected by ParalyzeVirus\n" +
                        "17: Virologist moves while affected by AmnesiaVirus\n" +
                        "18: Virologist uses ParalyzeVirus on another Virologist, who’s not vaccinated and without equipment\n" +
                        "19: Virologist uses AmnesiaVirus on another Virologist who’s not vaccinated but wears cape, the cape blocks the virus\n" +
                        "20: Virologist uses AmnesiaVirus on another Virologist who’s not vaccinated but wears cape, the cape doesn’t block the virus\n" +
                        "21: VirologistA uses DanceVirus on VirologistB who’s not vaccinated but wears Gloves. Virologist B applies DanceVirus with gloves\n" +
                        "22: Virologist wants to create a ParalyzeVirus, but doesn't have enough elements\n" +
                        "23: Virologist creates an AmnesiaVaccine and uses it on itself\n" +
                        "24: Virologist starts to wear a bag\n" +
                        "25: Virologist takes off a bag\n" +
                        "26: Virologist can’t take off bag\n" +
                        "27: Virologist can’t wear an axe, because worn equipments are full\n" +
                        "28: Virologist uses glove for the third time\n" +
                        "29: Virologist uses a sharp axe on another virologist\n" +
                        "30: Virologist uses blunt axe on another virologist\n" +
                        "31: Virologist wants to toggle bag, but the Virologist isn't in a Safehouse\n" +
                        "32: Virologist tosses a cloak from the backpack to a Safehouse\n" +
                        "33: Virologist tosses a cloak from the backpack to a Field\n" +
                        "34: Virologist robs another Virologist\n" +
                        "35: Virologist can’t rob an enemy because they are not paralyzed\n" +
                        "36: VirologistA tries to use DanceVirus on VirologistB but VirologistA is under the effect of Paralyze Virus\n" +
                        "37: VirologistA infects VirologistB with BearVirus. The VirologistB is not vaccinated, and doesn’t wear any equipment. VirologistB turns to bear\n" +
                        "38: VirologistA infects VirologistB with BearVirus. The VirologistB doesn’t wear any equipment, but is vaccinated against bearvirus.");

        Scanner inputScanner = new Scanner(System.in);

        while (true) {
            try {
                String userInput = inputScanner.nextLine();
                int choice = Integer.parseInt(userInput);

                if (choice == 0)
                    return;

                if (choice >= 1 && choice <= 38) {
                    // if the user's choice is valid read the test script from the corresponding file
                    runscript("rcs\\testscripts\\test" + userInput + ".txt");
                } else {
                    // if the user's choice is invalid start the read process all over
                    throw new NumberFormatException("Invalid input! Enter a number between 0 and 38!");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Incorrect number format! Enter a number between 0 and 38!");
            }
        }
    }

    /**
     * This method reads commands one at the time from the console and executes them.
     * */
    public void playGame() {
        Scanner inputScanner = new Scanner(System.in);
        while (true) {
            String userInput = inputScanner.nextLine();
            userInput = userInput.toLowerCase();
            if(userInput.equals("quit") || userInput.equals("q"))
                return;
            else
                runCommand(userInput);
        }
    }
}
