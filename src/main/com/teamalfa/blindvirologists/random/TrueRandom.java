package main.com.teamalfa.blindvirologists.random;

import java.util.Random;

public class TrueRandom extends MyRandom{

    /**
     * Creates a random double number and compers it to the param number.
     * @param num One of the compared numbers.
     * @return The generated number is greater or equals the parameter number.
     */
    @Override
    public boolean YorN(double num) {
        Random r = new Random();
        double tmp = 100 * r.nextDouble();
        return tmp >= num;
    }

    /**
     * Randomises a number between 0 and the param number.
     * @param num The param number
     * @return The randomised number.
     */
    @Override
    public int PickRandom(int num) {
            Random r = new Random();
            return r.nextInt(num);
    }
}
