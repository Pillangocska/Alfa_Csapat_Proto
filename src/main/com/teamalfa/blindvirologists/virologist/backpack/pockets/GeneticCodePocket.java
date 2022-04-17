package main.com.teamalfa.blindvirologists.virologist.backpack.pockets;

import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;
import main.com.teamalfa.blindvirologists.virologist.backpack.Backpack;

import java.util.ArrayList;

public class GeneticCodePocket extends Pocket{

    private ArrayList<GeneticCode> geneticCodes = new ArrayList<GeneticCode>();
    private Backpack backpack;

    public ArrayList<GeneticCode> getGeneticCodes() {
        AController.printCall(this, "getGeneticCodes", null);
        return (ArrayList<GeneticCode>) AController.printReturn(geneticCodes);
    }

    public GeneticCodePocket(Backpack b) {
        backpack = b;

    }

    public void registerObjects() {
        AController.registerObject(this, geneticCodes, "gCodes");
    }

    public void add(GeneticCode gc){
        AController.printCall(this, "add", new Object[]{gc});
        geneticCodes.add(gc);
        AController.printReturn(null);
    }

    public void deleteAll() {
        geneticCodes.clear();
    }

    public int getCurrentSize(){
        return geneticCodes.size();
    }
}
