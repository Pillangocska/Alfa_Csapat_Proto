package main.com.teamalfa.blindvirologists;

import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.turn_handler.Game;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();

        System.out.println("Would you like to play the game or run tests?");
        System.out.println("1. Play Game");
        System.out.println("2. Run Tests");

        Scanner inputScanner = new Scanner(System.in);
        while (true) {
            try {
                int choice = Integer.parseInt(inputScanner.nextLine());
                if (choice == 1) {
                    controller.playGame();
                    return;
                }
                if (choice == 2) {
                    controller.runTest();
                    return;
                } else {
                    throw new NumberFormatException("Invalid input!");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Incorrect number format! Enter either '1' or '2'!");
            }
        }

    }
}
