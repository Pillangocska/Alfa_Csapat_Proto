package main.com.teamalfa.blindvirologists.random;

public class DetRandom extends MyRandom{
    private boolean choice;
    private int chosenNumber;

    public DetRandom(boolean ret, int num)
    {
        choice = ret;
        chosenNumber = num;
    }

    @Override
    public boolean YorN(double num) {
        return choice;
    }

    @Override
    public int PickRandom(int num) {
        return chosenNumber;
    }
}
