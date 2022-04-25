package main.com.teamalfa.blindvirologists.equipments.active_equipments;

import main.com.teamalfa.blindvirologists.virologist.Virologist;

public class Axe extends ActiveEquipment{

    public Axe() {
        usetime = 1;
    }

    @Override
    public boolean use(Virologist v) {
        if(usetime == 1){
            v.die();
            startCooldown();
            usetime--;
            return true;
        }
        return false;
    }

    @Override
    public void wornOut() {
        // Ennek üresnek kell lennie, mert nem dobódik el magától, foglalja a helyet.
    }

    @Override
    public void step() {
        // Ennek is üresnek kell lennie, mert nincs rajta cooldown.
    }

    @Override
    public String getType() {
        return "Axe";
    }
}
