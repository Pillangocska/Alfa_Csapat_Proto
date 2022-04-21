package main.com.teamalfa.blindvirologists.equipments.active_equipments;

import main.com.teamalfa.blindvirologists.agents.virus.Virus;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

public class Gloves extends ActiveEquipment {

    public Gloves() {
        usetime = 3;
    }

    public void use(Virologist target){
        Virus toUse = null; //todo
        if(toUse != null && usetime > 0) {
            virologist.removeVirus(toUse);
            toUse.apply(target);
            startCooldown();
            usetime--;
        }
        else if(usetime == 0) {
            wornOut();
        }
    }

    public void wornOut() {
        virologist.removeWorn(this);
        virologist.removeActive(this);
    }
}
