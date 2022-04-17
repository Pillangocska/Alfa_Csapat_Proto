package main.com.teamalfa.blindvirologists.equipments;

import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

abstract public class Equipment {
    protected Virologist virologist;

    public void unEquip(){
        AController.printCall(this, "unEquip", null);
        virologist.removeWorn(this);
        AController.printReturn(null);
    }

    public void equip(){
        AController.printCall(this, "equip", null);
        virologist.addWorn(this);
        AController.printReturn(null);
    }

    public void setVirologist(Virologist v) {this.virologist = v;}

    public boolean protect() {
        AController.printCall(this, "protect", null);
        return (Boolean) AController.printReturn(false);
    }
}
