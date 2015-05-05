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

        initPopulation(5);

        population.printPopulation();

        System.out.println("Fittest: " + population.getFittest().toString());
        System.out.println("Fittest: " + population.getFittest().getFitness());

        population.getNextPopulation();
    }

    private ArrayList<Factory> cloneFactoryArray() {
        ArrayList<Factory> factoriesCloned = new ArrayList<Factory>();
        for (Factory factory : this.factories) {
            factoriesCloned.add(new Factory(factory.getCapacity(), factory.getPosition()));
        }
        return factoriesCloned;
    }

    private ArrayList<Store> cloneStoreArray() {
        ArrayList<Store> storesCloned = new ArrayList<Store>();
        for (Store store : this.stores) {
            storesCloned.add(new Store(store.getRequiredQuantity(), store.getPosition()));
        }
        return storesCloned;
    }

    public void initPopulation(int size) {
        int chromosomeSize = factories.size() * stores.size() * getMaxNumberBitsNeeded();

        for (int i = 0; i < size; i++) {
            population.add(new Chromosome(chromosomeSize, cloneFactoryArray(), cloneStoreArray()));
        }
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
