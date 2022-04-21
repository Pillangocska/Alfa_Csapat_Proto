package main.com.teamalfa.blindvirologists.turn_handler;

import main.com.teamalfa.blindvirologists.agents.virus.BearVirus;
import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.util.ArrayList;

public class Game implements Steppable{
    private static Game instance;
    private ArrayList<Virologist> bears;

    static {
        instance = new Game();
    }

    private Virologist virologist;

    private Game() {
        bears = new ArrayList<>();
    }

    public static Game getInstance() {
        return instance;
    }

    public void startGame() {}

    public void endGame(ArrayList<Virologist> winners) {}

    public void accept(Virologist v) {
        bears.add(v);
    }

    public void remove(Virologist v) {
        bears.remove(v);
    }

    public void controlBears() {
        for (Virologist bear : bears) {
            Field f = bear.getField();
            bear.move(f);
            for (Virologist enemy : bear.searchForVirologist()) {
                bear.use(new BearVirus(), enemy);
            }
            bear.getField().destroy();
        }
    }

    public void step() {
        controlBears();
    }

    public ArrayList<Virologist> getBears() {
        return bears;
    }

}
