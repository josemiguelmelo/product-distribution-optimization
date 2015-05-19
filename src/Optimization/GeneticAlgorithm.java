package Optimization;


import Objects.Factory;
import Objects.Population;
import Objects.Store;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GeneticAlgorithm {

    private ArrayList<Factory> factories;
    private ArrayList<Store> stores;
    private Population population;


    public GeneticAlgorithm(ArrayList<Integer> factoriesProduction, ArrayList<Integer> storesQuantity) {
        factories = new ArrayList<Factory>();
        stores = new ArrayList<Store>();
        population = new Population();

        initStores(storesQuantity);

        initFactories(factoriesProduction);

        initPopulation(20);
        population.printPopulation();
        System.out.println("Fittest: " + population.getFittest().getFitness());


        for(int i = 0; i < 10; i ++)
        {
            population = population.getNextPopulation();
            population.printPopulation();
            System.out.println("Fittest: " + population.getFittest().getFitness());
        }

    }

    public static ArrayList<Factory> cloneFactoryArray(ArrayList<Factory> factories) {
        ArrayList<Factory> factoriesCloned = new ArrayList<Factory>();
        for (Factory factory : factories) {
            factoriesCloned.add(factory.clone());
        }
        return factoriesCloned;
    }

    public static ArrayList<Store> cloneStoreArray(ArrayList<Store> stores) {
        ArrayList<Store> storesCloned = new ArrayList<Store>();
        for (Store store : stores) {
            storesCloned.add(store.clone());
        }
        return storesCloned;
    }

    public void initPopulation(int size) {
        int chromosomeSize = factories.size() * stores.size() * getMaxNumberBitsNeeded();

        for (int i = 0; i < size; i++) {
            population.add(new Chromosome(chromosomeSize, cloneFactoryArray(this.factories), cloneStoreArray(this.stores)));
        }
    }

    public static byte[] cloneByteArray(byte[] byteArray){
        byte[] clonedByteArray = new byte[byteArray.length];
        System.arraycopy(byteArray, 0, clonedByteArray, 0, byteArray.length);
        return clonedByteArray;
    }



    public void initFactories(ArrayList<Integer> factoriesProduction) {
        int factoriesCounter = 1;
        for (Integer production : factoriesProduction) {

            System.out.print("Factory #" + factoriesCounter + " - X Coordinate: ");
            int xPos = Optimization.scan.nextInt();
            System.out.print("Factory #" + factoriesCounter + " - Y Coordinate: ");
            int yPos = Optimization.scan.nextInt();

            Factory factory = new Factory(production, new Point(xPos, yPos));

            factories.add(factory);
            factoriesCounter++;
        }
    }

    public void initStores(ArrayList<Integer> storesQuantity) {
        int storesCounter = 1;
        for (Integer quantity : storesQuantity) {

            System.out.print("Store #" + storesCounter + " - X Coordinate: ");
            int xPos = Optimization.scan.nextInt();
            System.out.print("Store #" + storesCounter + " - Y Coordinate: ");
            int yPos = Optimization.scan.nextInt();

            Store store = new Store(quantity, new Point(xPos, yPos));

            stores.add(store);
        }
    }



    /**
     * @return Max number of bits to represent the number of products required by store.
     */
    private int getMaxNumberBitsNeeded() {
        int numberRequired = 0;
        for (Store store : stores) {
            int requiredQuantity = numberBitsRepresentInteger(store.getRequiredQuantity());
            if (numberRequired < requiredQuantity) {
                numberRequired = requiredQuantity;
            }
        }
        return numberRequired;
    }


    /**
     * @param integ Integer to calculate the number of bits required to represent it
     * @return Number of bits required to represent integ.
     */
    private int numberBitsRepresentInteger(int integ) {
        int count = 0;
        while (integ > 0) {
            count++;
            integ = integ >> 1;
        }
        return count;
    }


}
