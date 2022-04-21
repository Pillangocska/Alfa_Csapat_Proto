package main.com.teamalfa.blindvirologists.equipments.active_equipments;

import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.turn_handler.Steppable;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

abstract public class ActiveEquipment extends Equipment implements Steppable {
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
        cooldown = cooldownDuration;
    }

    public abstract void wornOut();

    public void step() {
        if(cooldown > 0)
            cooldown--;
    }

}
