package main.com.teamalfa.blindvirologists.turn_handler;

import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.util.ArrayList;
import java.util.Collections;

public class TurnHandler {
    private static TurnHandler instance;
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

    /**
     * Makes every steppable step.
     */
    private void tick() {
        for(Steppable steppable : steppables) {
            steppable.step();
        }
    }

    /**
     * Adds the new steppable to the list.
     * @param steppable The steppable that's added to the list.
     */
    public void accept(Steppable steppable) {
        steppables.add(steppable);
    }

    /**
     * Adds the virologist to the list.
     * @param virologist The new Virologist.
     */
    public void accept(Virologist virologist) {
        order.add(virologist);
    }

    /**
     * Removes the steppable from the list.
     * @param steppable
     */
    public void remove(Steppable steppable) {
        steppables.remove(steppable);
    }

    /**
     * Removes the virologist from the list.
     * @param virologist
     */
    public void remove(Virologist virologist) {
        order.remove(virologist);
    }

    /**
     * Reorders the virologists list.
     */
    private void reOrderVirologists() {
        Collections.shuffle(order);
    }

    //getters, setters
    public ArrayList<Virologist> GetOrder() {
        return order;
    }

    public Virologist getActiveVirologist() {
        return activeVirologist;
    }

    public void setActiveVirologist(Virologist v) {
        activeVirologist = v;
    }
}
