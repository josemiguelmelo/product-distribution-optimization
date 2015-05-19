package Objects;


import Optimization.GeneticAlgorithm;

import java.awt.*;

public class Store extends Individual{

    private int requiredQuantity;

    private byte[] gene;


    public Store(int requiredQuantity, Point position){
        super(position);
        this.requiredQuantity = requiredQuantity;
    }

    public Store clone(){
        Store cloneStore = new Store(this.requiredQuantity, this.getPosition());
        if(this.gene!= null)
            cloneStore.setGene(GeneticAlgorithm.cloneByteArray(this.gene));
        return cloneStore;
    }

    public int getRequiredQuantity(){ return this.requiredQuantity; }

    public void setRequiredQuantity(int requiredQuantity){ this.requiredQuantity = requiredQuantity; }


    public byte[] getGene() {
        return gene;
    }

    public void setGene(byte[] gene) {
        this.gene = gene;
    }

    public int getSuppliedItemNumber() {
        int res =  0;

        if(gene == null){
            return res;
        }

        for (int i = 0; i < gene.length; i++){
            if(gene[i] == 1)
            {
                res += Math.pow(2, gene.length - i - 1);
            }
        }
        return res;
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
