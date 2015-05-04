package Objects;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Factory extends Individual{

    private int capacity;

    private ArrayList<Store> stores;

    private byte[] gene;




    public Factory(int capacity, Point position){
        super(position);
        this.capacity = capacity;
        this.stores = new ArrayList<Store>();
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

    public byte[] getGene() {
        return gene;
    }

    public void setGene(byte[] gene) {
        this.gene = gene;
    }

    private void copyStoresArray(ArrayList<Store> storesList){
        stores.clear();

        for(Store store : storesList){
            stores.add(new Store(store.getRequiredQuantity(), store.getPosition()));
        }
    }


    public void splitStoresGenes(ArrayList<Store> storesList){
        copyStoresArray(storesList);

        int numByteStore = gene.length / stores.size();

        int currentPosition = 0;

        for(int i = 0; i < stores.size(); i++){
            stores.get(i).setGene(Arrays.copyOfRange(gene, currentPosition, currentPosition + numByteStore));
            currentPosition += numByteStore;
        }
    }




    @Override
    public String toString(){
        String resultString = "";
        System.out.println("Stores genes: ");
        for(Store store : stores){
            System.out.println(store);
        }

        for (byte gene : this.gene) {
            resultString += Byte.toString(gene);
        }

        return resultString;
    }
}
