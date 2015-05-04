package Optimization;


import Objects.Factory;
import Objects.Store;

import java.awt.*;
import java.util.ArrayList;

public class GeneticAlgorithm {

    private ArrayList<Factory> factories;
    private ArrayList<Store> stores;
    private ArrayList<Chromosome> population;


    public GeneticAlgorithm(ArrayList<Integer> factoriesProduction, ArrayList<Integer> storesQuantity){
        factories = new ArrayList<Factory>();
        stores = new ArrayList<Store>();
        population = new ArrayList<Chromosome>();

        initStores(storesQuantity);

        initFactories(factoriesProduction);

        initPopulation(5);

        printPopulation();

    }

    private ArrayList<Factory> cloneFactoryArray(){
        ArrayList<Factory> factoriesCloned = new ArrayList<Factory>();
        for (Factory factory : this.factories) {
            factoriesCloned.add(new Factory(factory.getCapacity(), factory.getPosition()));
        }
        return factoriesCloned;
    }

    private ArrayList<Store> cloneStoreArray(){
        ArrayList<Store> storesCloned = new ArrayList<Store>();
        for (Store store : this.stores) {
            storesCloned.add(new Store(store.getRequiredQuantity(), store.getPosition()));
        }
        return storesCloned;
    }

    public void initPopulation(int size){
        int chromosomeSize = factories.size() * stores.size() * getMaxNumberBitsNeeded();

        for(int i = 0; i < size; i++)
        {
            population.add(new Chromosome(chromosomeSize, cloneFactoryArray(), cloneStoreArray()));
        }
    }

    public void printPopulation()
    {
        int counter = 0;
        for(Chromosome chromosome : population)
        {
            System.out.println("CHROMOSOME #" + counter);
            counter++;
            System.out.println(chromosome);
        }

    }


    public void initFactories(ArrayList<Integer> factoriesProduction){
        for(Integer production : factoriesProduction){
            Factory factory = new Factory(production, new Point(0,0));

            factories.add(factory);
        }
    }

    public void initStores(ArrayList<Integer> storesQuantity){
        for(Integer quantity : storesQuantity){
            Store store = new Store(quantity, new Point(0,0));

            stores.add(store);
        }
    }


    /**
     * @return Max number of bits to represent the number of products required by store.
     */
    private int getMaxNumberBitsNeeded(){
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
     *
     * @param integ Integer to calculate the number of bits required to represent it
     * @return Number of bits required to represent integ.
     */
    private int numberBitsRepresentInteger(int integ){
        int count = 0;
        while (integ > 0) {
            count++;
            integ = integ >> 1;
        }
        return count;
    }



}
