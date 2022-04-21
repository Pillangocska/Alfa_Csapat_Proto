package main.com.teamalfa.blindvirologists.random;

public class DetRandom extends MyRandom{
    private boolean choice;

    public DetRandom(boolean ret)
    {
        choice = ret;
    }

    @Override
    public boolean YorN(double num) {
        return choice;
    }

    @Override
    public int PickRandom(int num) {
        return num;
    }
}
