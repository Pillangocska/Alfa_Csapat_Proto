package main.com.teamalfa.blindvirologists.equipments;

public class Cloak extends Equipment{

    private final double protectionRate;

    public Cloak(){
        protectionRate = 82.3;
    }

    public boolean protect(){
       return AController.askYesOrNo("Did the cloak protect the virologist?");
    }

}
