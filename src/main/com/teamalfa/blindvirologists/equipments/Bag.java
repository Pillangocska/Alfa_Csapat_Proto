package main.com.teamalfa.blindvirologists.equipments;

import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.virologist.backpack.Backpack;
import main.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

public class Bag extends Equipment{
    private int extraSize;

    public Bag(int size){
        extraSize = size;
    }

    public void equip(){
        AController.printCall(this, "equip", null);

        Backpack b = virologist.getBackpack();
        ElementBank e = b.getElementBank();
        e.increaseMaxSize(extraSize);

        virologist.addWorn(this);


        AController.printReturn(null);
    }

    public void unEquip(){
        AController.printCall(this, "unEquip", null);

        Backpack b = virologist.getBackpack();
        ElementBank e = b.getElementBank();
        e.decreaseMaxSize(extraSize);

        virologist.removeWorn(this);

        AController.printReturn(null);
    }
}
