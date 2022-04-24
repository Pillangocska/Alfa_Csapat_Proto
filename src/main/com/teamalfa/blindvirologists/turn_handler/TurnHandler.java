package main.com.teamalfa.blindvirologists.turn_handler;

import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.util.ArrayList;

public class TurnHandler {
    private static TurnHandler instance = null;
    private static final ArrayList<Steppable> steppables = new ArrayList<>();
    private static ArrayList<Virologist> order = new ArrayList<>();
    private static Virologist activeVirologist; // the virologist, who's turn is active

    // Needed for singleton design pattern.
    static {
      instance = new TurnHandler();
    }

    public static TurnHandler getInstance() {
        // Any class that wants to access the TurnHandler can call this method.
        return instance;
    }

    private void tick() {
        for(Steppable steppable : steppables) {
            steppable.step();
        }
    }

    public static void accept(Steppable steppable) {
        steppables.add(steppable);
    }

    public static void accept(Virologist virologist) {
        if(order.isEmpty())
            activeVirologist = virologist;
        order.add(virologist);
    }

    public void remove(Steppable steppable) {
        steppables.remove(steppable);
    }

    public void remove(Virologist virologist) {
        order.remove(virologist);
    }

    public ArrayList<Virologist> GetOrder() {
        return order;
    }

    private void reOrderVirologists() {

    }

    public static Virologist getActiveVirologist() {
        return activeVirologist;
    }

    public void setActiveVirologist(Virologist v) {
        activeVirologist = v;
    }
}
