package main.com.teamalfa.blindvirologists.equipments.active_equipments;

import main.com.teamalfa.blindvirologists.agents.virus.Virus;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

public class Gloves extends ActiveEquipment {

    private Virus usedVirus = null;

    public Gloves() {
        cooldownDuration = 3;
        usetime = 3;
        cooldown = 0;
    }

    public void setUseTime(int num) {
        usetime = num;
    }

    public void setUsedVirus(Virus virus) {
        usedVirus = virus;
    }

    public void use(Virologist target){
        if(usedVirus != null && usetime > 0 && cooldown == 0) {
            virologist.removeVirus(usedVirus);
            usedVirus.apply(target);
            startCooldown();
            usetime--;
            usedVirus = null;
        }
        else if(usetime == 0) {
            wornOut();
        }
    }

    public void wornOut() {
        virologist.removeWorn(this);
        virologist.removeActive(this);
    }

    @Override
    public void wornOut() {
        // TODO:
    }
}
