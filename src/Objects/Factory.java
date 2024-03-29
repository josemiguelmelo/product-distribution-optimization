package Objects;

import Optimization.GeneticAlgorithm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Factory extends Individual {

    private int capacity;

    private ArrayList<Store> stores;

    private String gene;

    public Factory(int capacity, Point position) {
        super(position);
        this.capacity = capacity;
        this.stores = new ArrayList<Store>();
    }

    public Factory clone(){
        Factory cloneFactory = new Factory(this.capacity, this.getPosition());
        cloneFactory.setStores(GeneticAlgorithm.cloneStoreArray(this.stores));
        String copyGene = null;
        if(this.gene!= null)
            copyGene = this.gene;
            cloneFactory.setGene(copyGene);

        return cloneFactory;
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }

    private void copyStoresArray(ArrayList<Store> storesList) {
        stores.clear();

        for (Store store : storesList) {
            stores.add(new Store(store.getRequiredQuantity(), store.getPosition()));
        }
    }

    public double getPenalty() {
        double totalSuppliedProducts = 0.0;

        for(int i = 0; i < stores.size(); i++)
        {
            totalSuppliedProducts += suppliedToStore(i);
        }

        double penalty = this.capacity - totalSuppliedProducts;


        if(penalty < 0) {
            return penalty;
        } else {
            return 0;
        }
    }

    public int suppliedToStore(int storeIndex){
        return stores.get(storeIndex).getSuppliedItemNumber();
    }


    public void splitStoresGenes(ArrayList<Store> storesList) {
        copyStoresArray(storesList);

        int numByteStore = gene.length() / stores.size();

        int currentPosition = 0;

        for (int i = 0; i < stores.size(); i++) {
            stores.get(i).setGene(gene.substring(currentPosition, currentPosition + numByteStore));
            currentPosition += numByteStore;
        }
    }

    public double getDistanceToStore(Store store) {

        return Math.sqrt(
                Math.pow(this.getPosition().getX() - store.getPosition().getX(), 2) +
                Math.pow(this.getPosition().getY() - store.getPosition().getY(), 2)
        );

    }

    @Override
    public String toString() {
        String resultString = "";
        int counter = 0;
        for (Store store : stores) {
            System.out.println("STORE #" + counter);
            counter++;
            System.out.println(store);
        }

        resultString += this.gene;

        return resultString;
    }

    public double getDistancesSum() {
        double distanceSum = 0.0;

        for (Store store : this.stores) {
            if(store.getSuppliedItemNumber() != 0)
            {
                distanceSum += this.getDistanceToStore(store);
            }
        }

        return distanceSum;
    }
}
