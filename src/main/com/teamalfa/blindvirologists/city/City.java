package main.com.teamalfa.blindvirologists.city;

import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.city.fields.Laboratory;
import main.com.teamalfa.blindvirologists.city.fields.SafeHouse;
import main.com.teamalfa.blindvirologists.city.fields.StoreHouse;
import main.com.teamalfa.blindvirologists.equipments.Bag;
import main.com.teamalfa.blindvirologists.equipments.Cloak;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Axe;
import main.com.teamalfa.blindvirologists.equipments.active_equipments.Gloves;

import java.util.ArrayList;
import java.util.Random;

public class City {
    private static City instance;
    private ArrayList<Equipment> allEquipment = new ArrayList<>();
    private final ArrayList<Field> allFields = new ArrayList<>();
    private final ArrayList<Laboratory> allLaboratories = new ArrayList<>();
    private final ArrayList<StoreHouse> allStoreHouses = new ArrayList<>();
    private final ArrayList<SafeHouse> allSafeHouses = new ArrayList<>();

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
        this.GenerateMap();
        allEquipment.add(new Gloves());
        allEquipment.add(new Axe());
        allEquipment.add(new Cloak());
        allEquipment.add(new Bag(50));
    }

    /**
     * Generates the map the players can play on.
     * With random generating random number of fields between 5 & 30
     */
    public void GenerateMap() {
        Random random = new Random();
        int numberOfFields = random.nextInt(30-5+1)+5;
        int numberOfLabs = random.nextInt(20-3+1)+3;
        int numberOfStoreH = random.nextInt(25-5+1)+5;
        int numberOfSafeH = random.nextInt(15-2+1)+2;
        //Generating field numbers
        for(int i = 0; i < numberOfFields; i++){
            this.allFields.add(new Field());
        }
        for(int i = 0; i < numberOfLabs; i++){
            this.allLaboratories.add(new Laboratory());
        }
        for(int i = 0; i < numberOfStoreH; i++){
            this.allStoreHouses.add(new StoreHouse());
        }
        for(int i = 0; i < numberOfSafeH; i++){
            this.allSafeHouses.add(new SafeHouse());
        }
        //Setting up neighbours for fields first
        for(int i = 0; i < numberOfFields; i++){
            //One field gets 1 to 5 normal field neighbours
            int numberOfFieldNeighbours = random.nextInt(5-1+1)+1;
            for(int j = 0; j < numberOfFieldNeighbours; j++) {
                int addIndex = random.nextInt(numberOfFields + 1);
                if(!(allFields.get(i).getNeighbours().contains(this.allFields.get(addIndex))) && i != addIndex) {
                    this.allFields.get(i).setNeighbour(this.allFields.get(addIndex));
                }
            }
            //0 to 2 Laboratories
            int numberOfLabNeighbours = random.nextInt(2+1);
            for(int j = 0; j < numberOfLabNeighbours; j++) {
                int addIndex = random.nextInt(numberOfLabs+1);
                if(!(this.allFields.get(i).getNeighbours().contains(this.allLaboratories.get(addIndex)))) {
                    this.allFields.get(i).setNeighbour(this.allLaboratories.get(addIndex));
                }
            }
            //0 to 2 SafeHouses
            int numberOfSafeHNeighbours = random.nextInt(2+1);
            for(int j = 0; j < numberOfSafeHNeighbours; j++) {
                int addIndex = random.nextInt(numberOfSafeH+1);
                if(!(this.allFields.get(i).getNeighbours().contains(this.allSafeHouses.get(addIndex)))) {
                    this.allFields.get(i).setNeighbour(this.allSafeHouses.get(addIndex));
                }
            }
            //0 to 3 StoreHouses
            int numberOfStoreHNeighbours = random.nextInt(3+1);
            for(int j = 0; j < numberOfStoreHNeighbours; j++) {
                int addIndex = random.nextInt(numberOfStoreH+1);
                if(!(this.allFields.get(i).getNeighbours().contains(this.allStoreHouses.get(addIndex)))) {
                    this.allFields.get(i).setNeighbour(this.allStoreHouses.get(addIndex));
                }
            }
            //TODO to be continued....
        }
    }
}
