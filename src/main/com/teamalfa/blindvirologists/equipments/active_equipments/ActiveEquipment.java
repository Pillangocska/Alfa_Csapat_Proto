package main.com.teamalfa.blindvirologists.equipments.active_equipments;

import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

abstract public class ActiveEquipment extends Equipment {
    protected int cooldownDuration;
    protected int cooldown;
    protected int usetime;

    public abstract void use(Virologist v);

    public void equip(){
        virologist.addWorn(this);
        virologist.addActive(this);
    }

    public void unEquip(){
        virologist.removeWorn(this);
        virologist.removeActive(this);
    }

    public void startCooldown() {
        //todo
    }

    public abstract void wornOut();

}
