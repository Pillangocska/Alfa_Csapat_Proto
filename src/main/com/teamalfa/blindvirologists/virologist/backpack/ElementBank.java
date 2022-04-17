package main.com.teamalfa.blindvirologists.virologist.backpack;

import main.com.teamalfa.blindvirologists.AController;

public class ElementBank {
    private int aminoAcid;
    private int nucleotide;
    private int aminoAcidMaxSize;
    private int nucleotideMaxSize;

    public ElementBank(int amino, int nucleotide){
        this.aminoAcid = amino;
        this.nucleotide = nucleotide;
        aminoAcidMaxSize = 20;
        nucleotideMaxSize = 20;

    }

    public void increaseMaxSize(int extraSize) {
        AController.printCall(this, "increaseMaxSize", new Object[]{extraSize});

        aminoAcidMaxSize += extraSize;
        nucleotideMaxSize += extraSize;

        AController.printReturn(null);
    }


    public void decreaseMaxSize(int extraSize) {
        AController.printCall(this, "decreaseMaxSize", new Object[]{extraSize});

        aminoAcidMaxSize -= extraSize;
        nucleotideMaxSize -= extraSize;

        AController.printReturn(null);
    }

    public ElementBank add(ElementBank elements) {
        AController.printCall(this, "add", new Object[]{elements});

       nucleotide += elements.nucleotide;
       aminoAcid += elements.aminoAcid;

       ElementBank added = new ElementBank(elements.aminoAcid, elements.nucleotide);
       added.nucleotide = nucleotide > nucleotideMaxSize ? added.nucleotide - (nucleotide - nucleotideMaxSize) : nucleotide;
       added.aminoAcid = aminoAcid > aminoAcidMaxSize ? added.aminoAcid - (aminoAcid - aminoAcidMaxSize) : aminoAcid;
       AController.registerObject(null, added, "added" + added.aminoAcid + "_" + added.nucleotide);

       return (ElementBank) AController.printReturn(added);
    }

    public boolean remove(ElementBank added) {
        AController.printCall(this, "remove", new Object[]{added});

        Boolean ret = false;
        if(AController.askYesOrNo("Is there enough elements in the bank?")) {
            ret = true;
        }
        return (Boolean) AController.printReturn(ret);
    }

    public int getAminoAcid() {
        return aminoAcid;
    }
    public int getNucleotide() {
        return  nucleotide;
    }
}
