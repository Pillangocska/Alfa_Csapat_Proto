package main.com.teamalfa.blindvirologists.turn_handler;

import main.com.teamalfa.blindvirologists.agents.virus.BearVirus;
import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.util.ArrayList;

public class Game implements Steppable{
    private static Game instance = null;
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
        for(int i = 0; i < bears.size(); i++){
            Field f = bears.get(i).getField();
            bears.get(i).move(f);
            for(Virologist enemy : bears.get(i).searchForVirologist()) {
                bears.get(i).use(new BearVirus(), enemy);
            }
            bears.get(i).getField().destory();
        }
    }

    public void step() {
        controlBears();
    }

    public ArrayList<Virologist> getBears() {
        return bears;
    }

}
