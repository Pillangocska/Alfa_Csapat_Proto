package main.com.teamalfa.blindvirologists.equipments;

import main.com.teamalfa.blindvirologists.random.MyRandom;

import java.util.Random;

public class Cloak extends Equipment{

    private final int protectionRate;

    public Cloak(){
        protectionRate = 823;
    }

    public boolean protect(){
       if(MyRandom.getInstance().isYesOrNoDeterministic()) {
           return MyRandom.getInstance().getYesOrNo();
       }else {
           return new Random().nextInt(1001) < protectionRate;
       }
    }

    @Override
    public String getType() {
        return "Cloak";
    }

}
