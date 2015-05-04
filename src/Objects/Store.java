package Objects;


import java.awt.*;

public class Store extends Individual{

    private int requiredQuantity;

    private byte[] gene;


    public Store(int requiredQuantity, Point position){
        super(position);
        this.requiredQuantity = requiredQuantity;
    }

    public int getRequiredQuantity(){ return this.requiredQuantity; }

    public void setRequiredQuantity(int requiredQuantity){ this.requiredQuantity = requiredQuantity; }


    public byte[] getGene() {
        return gene;
    }

    public void setGene(byte[] gene) {
        this.gene = gene;
    }



    @Override
    public String toString(){
        String resultString = "";
        for(byte gene : this.gene){
            System.out.print(gene);
        }
        return resultString;
    }
}
