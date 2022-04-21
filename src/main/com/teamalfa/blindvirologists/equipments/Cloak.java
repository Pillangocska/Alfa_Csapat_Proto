package main.com.teamalfa.blindvirologists.equipments;

import main.com.teamalfa.blindvirologists.random.MyRandom;

public class Cloak extends Equipment{
    MyRandom random;

    private final double protectionRate;

    public Cloak(){
        protectionRate = 82.3;
    }

    public void setRandom (MyRandom random) {
        this.random = random;
    }

    public boolean protect(){
       return random.YorN(protectionRate);
    }

}
