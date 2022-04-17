package main.com.teamalfa.blindvirologists.equipments.active_equipments;

import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.agents.virus.Virus;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

abstract public class ActiveEquipment extends Equipment {
    //private int cooldownDuration;
    //private int cooldown = 0;

    public abstract void use(Virologist v);

    public void equip(){
        AController.printCall(this, "equip", null);
        virologist.addWorn(this);
        virologist.addActive(this);
        AController.printReturn(null);
    }

    public void unEquip(){
        AController.printCall(this, "unEquip", null);
        virologist.removeWorn(this);
        virologist.removeActive(this);
        AController.printReturn(null);
    }

    public void startCooldown() {
        AController.printCall(this, "startCooldown", null);
        AController.printReturn(null);
    }

}
