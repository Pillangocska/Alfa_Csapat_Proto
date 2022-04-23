package main.com.teamalfa.blindvirologists.random;

public class DetRandom extends MyRandom{
    private boolean choice;
    private int chosenNumber;

    public DetRandom(boolean ret, int num)
    {
        choice = ret;
        chosenNumber = num;
    }

    /**
     * returns the choice
     * @param num irrelevant
     * @return choice
     */
    @Override
    public boolean YorN(double num) {
        return choice;
    }

    /**
     * return the chosen number
     * @param num irrelevant
     * @return chosenNumber
     */
    @Override
    public int PickRandom(int num) {
        return chosenNumber;
    }
}
