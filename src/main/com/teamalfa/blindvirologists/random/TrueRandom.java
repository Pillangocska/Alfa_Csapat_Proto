package main.com.teamalfa.blindvirologists.random;

import java.util.Random;

public class TrueRandom extends MyRandom{

    @Override
    public boolean YorN() {
        return false;
    }

    @Override
    public int PickRandom(int num) {
        Random r = new Random();
        return r.nextInt(num);
    }
}
