package Optimization;


import Objects.Factory;
import Objects.Store;

import java.awt.*;
import java.util.ArrayList;

public class GeneticAlgorithm {

    private Chromosome chromosome;

    private ArrayList<Factory> factories;
    private ArrayList<Store> stores;


    public GeneticAlgorithm(ArrayList<Integer> factoriesProduction, ArrayList<Integer> storesQuantity){
        factories = new ArrayList<Factory>();
        stores = new ArrayList<Store>();

        initStores(storesQuantity);

        initFactories(factoriesProduction);

        initChromosome();


        System.out.println("Chromosome: ");
        System.out.println(chromosome);
    }

    public void initChromosome(){
        int chromosomeSize = factories.size() * stores.size() * getMaxNumberBitsNeeded();

        chromosome = new Chromosome(chromosomeSize, factories, stores);
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
