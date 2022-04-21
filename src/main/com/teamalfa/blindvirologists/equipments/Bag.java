package main.com.teamalfa.blindvirologists.equipments;

import main.com.teamalfa.blindvirologists.virologist.backpack.Backpack;
import main.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

public class Bag extends Equipment{
    private int extraSize;

    public Bag(int size){
        extraSize = size;
    }

    public Bag() {
        extraSize = 20;
    }

    public void equip(){
        Backpack b = virologist.getBackpack();
        ElementBank e = b.getElementBank();
        e.increaseMaxSize(extraSize);
        virologist.addWorn(this);
    }

    public void unEquip(){
        Backpack b = virologist.getBackpack();
        ElementBank e = b.getElementBank();
        e.decreaseMaxSize(extraSize);
        virologist.removeWorn(this);
    }
}
