package main.com.teamalfa.blindvirologists.virologist.backpack.pockets;

import main.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;
import main.com.teamalfa.blindvirologists.virologist.backpack.Backpack;

import java.util.ArrayList;

public class GeneticCodePocket extends Pocket{

    private ArrayList<GeneticCode> geneticCodes = new ArrayList<GeneticCode>();
    private Backpack backpack;

    public ArrayList<GeneticCode> getGeneticCodes() {
        return geneticCodes;
    }

    public GeneticCodePocket(Backpack b) {
        backpack = b;
    }

    public void add(GeneticCode gc){
        geneticCodes.add(gc);
    }

    public void deleteAll() {
        geneticCodes.clear();
    }

    public int getCurrentSize(){
        return geneticCodes.size();
    }
}
