package main.com.teamalfa.blindvirologists.turn_handler;

import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.util.ArrayList;

public class TurnHandler {
    private static TurnHandler instance = null;
    private final ArrayList<Steppable> steppables;
    private ArrayList<Virologist> order;
    private Virologist activeVirologist; // the virologist, who's turn is active

    // Needed for singleton design pattern.
    static {
      instance = new TurnHandler();
    }

    private TurnHandler(){
        steppables = new ArrayList<>();
        order = new ArrayList<>();
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

    public void accept(Steppable steppable) {
        steppables.add(steppable);
    }

    public void accept(Virologist virologist) {
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

    public Virologist getActiveVirologist() {
        return activeVirologist;
    }
}
