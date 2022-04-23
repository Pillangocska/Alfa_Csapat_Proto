package main.com.teamalfa.blindvirologists.agents.virus;

import main.com.teamalfa.blindvirologists.agents.genetic_code.DanceCode;
import main.com.teamalfa.blindvirologists.city.fields.Field;
import main.com.teamalfa.blindvirologists.random.MyRandom;
import main.com.teamalfa.blindvirologists.random.TrueRandom;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;

public class DanceVirus extends Virus {

    public MyRandom random;

    public DanceVirus(){
        priority = 3;
        expiry = duration = 5;
        this.random = new TrueRandom();
        geneticCode = new DanceCode();

        TurnHandler.getInstance().accept(this);
    }

    //setter
    public void setRandom(MyRandom random){
        this.random = random;
    }


    /**
     * This method makes the Virologist move to a random Field instead of the chosen one.
     * There's a slight chance the chosen Field and the random Field will be the same.
     * @param current The Field the Virologist is standing on.
     * @return The chosen Field.
     */
    @Override
    public Field affectMovement(Field current) {
        int r = random.PickRandom(current.getNeighbours().size());
        return current.getNeighbours().get(r);
    }

}