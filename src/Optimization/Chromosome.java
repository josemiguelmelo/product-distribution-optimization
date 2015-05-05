package Optimization;

import Objects.Factory;
import Objects.Store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Chromosome {

    private byte[] genes;
    Random rand = new Random();


    ArrayList<Factory> factories;
    ArrayList<Store> stores;

    public Chromosome()
    {}

    public Chromosome(int size, ArrayList<Factory> factories, ArrayList<Store> stores){
        this.genes = new byte[size];

        this.factories = factories;
        this.stores = stores;

        initChromosome();

        System.out.println( Arrays.toString(genes));
        splitGenes();

    }

    public void printFactories(){
        int counter = 0;
        for(Factory factory : factories){
            System.out.println("FACTORY #" + counter);
            counter++;
            System.out.println(factory);
        }
    }

    private int generateRandomValue(int min, int max){
        return rand.nextInt((max - min) + 1) + min;
    }

    public void initChromosome(){
        for (int i = 0; i < genes.length ; i++) {
            Integer value = generateRandomValue(0, 1);
            genes[i] = value.byteValue();
        }
    }

    public void splitGenes(){
        int numBytes = genes.length / factories.size();

        int currentPosition = 0;

        // factories genes added
        for(int i = 0; i < factories.size(); i++){
            factories.get(i).setGene(Arrays.copyOfRange(genes, currentPosition, currentPosition + numBytes));
            currentPosition += numBytes;

            // set stores in factory i
            factories.get(i).splitStoresGenes(stores);
        }
    }

    public double getPenalty() {
        double totalPenalty = 0.0;

        for(Factory factory : factories) {
            totalPenalty += factory.getPenalty();
        }

        return totalPenalty;
    }

    public double getFitness() {
        double totalDistance = 0.0;

        for(Factory factory : this.factories)
        {
            totalDistance += factory.getDistancesSum();
        }

        if(totalDistance != 0)
        {
            return (1/totalDistance) + this.getPenalty();
        } else {
            return 0 + this.getPenalty();
        }

    }

    @Override
    public String toString(){
        //printFactories();

        System.out.println("Supplied: " + factories.get(0).getStores().get(0).getSuppliedItemNumber());

        System.out.println("Fitness: " + this.getFitness());

        return Arrays.toString(genes);
    }


}
