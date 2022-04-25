package main.com.teamalfa.blindvirologists.virologist.backpack;

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

    public ElementBank(int nucleoQuantity, int aminoQuantity, int nucleoSize, int aminoSize) {
        nucleotide = nucleoQuantity;
        aminoAcid = aminoQuantity;
        nucleotideMaxSize = nucleoSize;
        aminoAcidMaxSize = aminoSize;
    }

    public void increaseMaxSize(int extraSize) {
        aminoAcidMaxSize += extraSize;
        nucleotideMaxSize += extraSize;
    }


    public void decreaseMaxSize(int extraSize) {
        aminoAcidMaxSize -= extraSize;
        nucleotideMaxSize -= extraSize;
    }

    public ElementBank add(ElementBank elements) {
       nucleotide += elements.nucleotide;
       aminoAcid += elements.aminoAcid;

       ElementBank added = new ElementBank(elements.aminoAcid, elements.nucleotide);
       added.nucleotide = nucleotide > nucleotideMaxSize ? added.nucleotide - (nucleotide - nucleotideMaxSize) : nucleotide;
       added.aminoAcid = aminoAcid > aminoAcidMaxSize ? added.aminoAcid - (aminoAcid - aminoAcidMaxSize) : aminoAcid;

       return added;
    }

    public boolean remove(ElementBank added) {

        Boolean ret = false;
        if(added.getAminoAcid() < aminoAcid && added.getNucleotide() < nucleotide) {
            ret = true;
        }
        return ret;
    }

    public int getAminoAcid() {
        return aminoAcid;
    }
    public int getNucleotide() {
        return  nucleotide;
    }
    public int getAminoAcidMaxSize() {
        return nucleotideMaxSize;
    }
    public int getNucleotideMaxSize() {
        return aminoAcidMaxSize;
    }

    public void setAminoAcid(int num) {
        aminoAcid = num;
    }

    public void setNucleotide(int num) {
        nucleotide = num;
    }

    public void removeAll() {
        nucleotide = aminoAcid = 0;
    }
}
