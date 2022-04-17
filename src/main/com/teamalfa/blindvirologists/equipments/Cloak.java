package main.com.teamalfa.blindvirologists.equipments;

import main.com.teamalfa.blindvirologists.AController;

import java.util.Random;

public class Cloak extends Equipment{

    private final double protectionRate;

    public Cloak(){
        protectionRate = 82.3;
    }

    public boolean protect(){
       return AController.askYesOrNo("Did the cloak protect the virologist?");
    }

}
