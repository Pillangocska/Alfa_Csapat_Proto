package main.com.teamalfa.blindvirologists;

import java.util.ArrayList;

public class ErrorPrinter {

    public static void printError(String msg) {
        System.out.println("ERROR: " + msg);
    }

    public static  void doesntExistError(String objectId) {
        printError(objectId.isEmpty() ? "Missing object ID." : objectId + " does not exist.");
    }

    public static void tooManyParametersError() {
        printError("Too many parameters.");
    }

    public static boolean missingFlagError(String flags, ArrayList<String> input){
        boolean failed = true;
        ArrayList<String> formattedFlags = new ArrayList<>();

        for(int i = 0; i < flags.length(); i++) {
            formattedFlags.add("-" + flags.charAt(i));
        }

        if(!input.isEmpty()) {
            for(String flag : formattedFlags) {
                if(flag.equals(input.get(0))) {
                    failed = false;
                }
            }
        }

        if(failed)
            printError("Missing flag(s) " + formattedFlags + ".");
        return failed;
    }

    public static void wrongTypeError(String kind, String options) {
        String front = kind.isEmpty() ? "Missing keyword." : "Wrong key word: '" + kind + "'.";
        printError(front + " Possible choices are " + options);
    }
}
