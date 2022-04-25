package main.com.teamalfa.blindvirologists.equipments.active_equipments;

import main.com.teamalfa.blindvirologists.virologist.Virologist;

public class Axe extends ActiveEquipment{
    boolean blunt;

    public Axe() {
        usetime = 1;
        blunt = false;
    }

    @Override
    public void use(Virologist v) {
        if(usetime == 1 && !(blunt)){

            virologist.die();
            startCooldown();
            usetime--;
        }
        else if(blunt && usetime < 1) {
            wornOut();
        }
    }

    @Override
    public void wornOut() {}

    @Override
    public void step() {

    }

    @Override
    public String getType() {
        return "Axe";
    }
}
