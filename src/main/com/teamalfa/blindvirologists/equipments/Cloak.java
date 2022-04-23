package main.com.teamalfa.blindvirologists.equipments;

import main.com.teamalfa.blindvirologists.random.MyRandom;

public class Cloak extends Equipment{
    MyRandom random;

    private final double protectionRate;

    public Cloak(){
        protectionRate = 82.3;
    }

    //setter
    public void setRandom (MyRandom random) {
        this.random = random;
    }

    /**
     * Tells if the cloak protected the virologist from an infection.
     * @return true if it did, false if it did not.
     */
    public boolean protect(){
       return random.YorN(protectionRate);
    }

}
