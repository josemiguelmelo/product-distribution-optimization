package Objects;


import Optimization.GeneticAlgorithm;

import java.awt.*;
import java.util.Arrays;

public class Store extends Individual{

    private int requiredQuantity;

    private String gene;


    public Store(int requiredQuantity, Point position){
        super(position);
        this.requiredQuantity = requiredQuantity;
    }

    public Store clone(){
        Store cloneStore = new Store(this.requiredQuantity, this.getPosition());
        String geneCopy = null;
        if(this.gene!= null)
        {
            geneCopy = this.gene;
            cloneStore.setGene(geneCopy);
        }


        return cloneStore;
    }

    public int getRequiredQuantity(){ return this.requiredQuantity; }

    public void setRequiredQuantity(int requiredQuantity){ this.requiredQuantity = requiredQuantity; }


    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }

    public int getSuppliedItemNumber() {
        int res = 0;

        if(gene == null){
            return res;
        }

        for (int i = 0; i < gene.length(); i++){
            if(gene.charAt(i) == '1')
            {
                res += Math.pow(2, gene.length() - i - 1);
            }
        }
        return res;
    }

    @Override
    public String toString(){
        return gene;
    }
}
