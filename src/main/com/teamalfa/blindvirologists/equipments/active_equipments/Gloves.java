package main.com.teamalfa.blindvirologists.equipments.active_equipments;

import main.com.teamalfa.blindvirologists.agents.virus.Virus;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

public class Gloves extends ActiveEquipment {

    public void use(Virologist target){
        Virus toUse = null; //todo
        if(toUse != null) {
            virologist.removeVirus(toUse);
            toUse.apply(target);
            startCooldown();
        }
    }
}
