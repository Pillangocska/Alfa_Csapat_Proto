package main.com.teamalfa.blindvirologists.equipments.active_equipments;

import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.agents.virus.Virus;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

public class Gloves extends ActiveEquipment {

    public void use(Virologist target){
        AController.printCall(this, "use", new Object[]{target});
        Virus toUse = (Virus) AController.askMultiChoice("virus to apply", virologist.getViruses());
        if(toUse != null) {
            virologist.removeVirus(toUse);
            toUse.apply(target);
            startCooldown();
        }
        AController.printReturn(null);
    }
}
