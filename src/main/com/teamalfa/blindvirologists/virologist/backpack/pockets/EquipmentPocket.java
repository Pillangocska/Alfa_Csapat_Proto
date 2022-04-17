package main.com.teamalfa.blindvirologists.virologist.backpack.pockets;
import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.virologist.Virologist;
import main.com.teamalfa.blindvirologists.virologist.backpack.Backpack;

import java.util.ArrayList;

public class EquipmentPocket extends Pocket{

    private Backpack backpack;
    private ArrayList<Equipment> equipmentHolder = new ArrayList<>();
    private ArrayList<Equipment> wornEquipments= new ArrayList<>();
    //private int wornSize;

    public EquipmentPocket(Backpack b){
        backpack = b;
        //wornSize = 3;
        maxSize = 5;
    }

    public void registerObjects(){
        AController.registerObject(this, equipmentHolder, "eqHolder");
        AController.registerObject(this, wornEquipments, "worHolder");
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack b) {
        backpack = b;
    }

    public ArrayList<Equipment> getEquipmentHolder() {
        return (ArrayList<Equipment>) AController.printReturn(equipmentHolder);
    }

    public void setEquipEquipmentHolder(ArrayList<Equipment> e) {
        equipmentHolder = e;
    }

    public ArrayList<Equipment> getWornEquipments() {
        return wornEquipments;
    }

    public void setWornEquipments(ArrayList<Equipment> e) {
        wornEquipments = e;
    }

    public boolean add(Equipment equipment) {
        AController.printCall(this, "add", new Object[]{equipment});

        Boolean ret = false;
        if(AController.askYesOrNo("Does it fit to the bag?")){
            equipmentHolder.add(equipment);
            ret = true;
        }

        return  (Boolean)AController.printReturn(ret);
    }

    public void toggle(Equipment e){
        AController.printCall(this, "toggle", new Object[]{e});

        Virologist v = backpack.getVirologist();
        Field f = v.getField();
        if(f.canChangeEquipment()){
            if(AController.askYesOrNo("Is the virologist wearing this equipment?"))
                e.unEquip();
            else
                e.equip();
        }

        AController.printReturn(null);
    }


    public void toss(Equipment e){
        AController.printCall(this, "toss", new Object[]{e});

        if(!AController.askYesOrNo("Is the virologist wearing the item you want to toss?")){
            Virologist v = backpack.getVirologist();
            Field f = v.getField();
            if(f.canChangeEquipment()){
                equipmentHolder.remove(e);
                AController.printCall(this, "remove", new Object[]{e});
                AController.printReturn(null);
                f.add(e);
            }
        }

        AController.printReturn(null);
    }

    @Override
    public int getCurrentSize() {
        return equipmentHolder.size();
    }
}
