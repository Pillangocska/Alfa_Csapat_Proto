package main.com.teamalfa.blindvirologists.city.fields;

import main.com.teamalfa.blindvirologists.AController;
import main.com.teamalfa.blindvirologists.agents.genetic_code.GeneticCode;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

public class Laboratory extends Field{
    private GeneticCode geneticCode;

    /**
     * This method is called when the Virologist seacrhes the LAboratory they are standing on.
     * It puts the geneticCode from the laboratory into their Backpack.
     * @param virologist The Virologist who stands on the Field and searches it.
     */
    @Override
    public void searchedBy(Virologist virologist) {
        // print method call
        AController.printCall(this, "searchedBy", new Object[]{virologist});

        virologist.getBackpack().add(geneticCode);

        // print return value
        AController.printReturn(null);
    }
    public void setGeneticCode(GeneticCode geneticCode) {
        this.geneticCode = geneticCode;
    }
}