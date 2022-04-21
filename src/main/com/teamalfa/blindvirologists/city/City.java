package main.com.teamalfa.blindvirologists.city;

import main.com.teamalfa.blindvirologists.equipments.Bag;
import main.com.teamalfa.blindvirologists.equipments.Cloak;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Axe;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Gloves;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import java.util.ArrayList;

public class City {
    private static City instance = null;
    private ArrayList<Equipment> allEquipment = new ArrayList<>();

    static {
        instance = new City();
    }

    public static City getInstance() {
        return instance;
    }

    private City() {
        allEquipment.add(new Gloves());
        allEquipment.add(new Axe());
        allEquipment.add(new Cloak());
        allEquipment.add(new Bag(50));
    }

    public void GenerateMap() {
        //todo majd amikor a grafikust csinaljuk
    }
}
