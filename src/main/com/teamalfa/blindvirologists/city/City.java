package main.com.teamalfa.blindvirologists.city;

import main.com.teamalfa.blindvirologists.equipments.Bag;
import main.com.teamalfa.blindvirologists.equipments.Cloak;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Axe;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Gloves;

import java.util.ArrayList;

public class City {
    private static City instance;
    private ArrayList<Equipment> allEquipment = new ArrayList<>();

    /**
     * Creates one object in the beginning of the game.
     */
    static {
        instance = new City();
    }

    //getter
    public static City getInstance() {
        return instance;
    }

    /**
     * ctr
     */
    private City() {
        allEquipment.add(new Gloves());
        allEquipment.add(new Axe());
        allEquipment.add(new Cloak());
        allEquipment.add(new Bag(50));
    }

    /**
     * Generates the map the players can play on.
     */
    public void GenerateMap() {
        //todo majd amikor a grafikust csinaljuk
    }
}
