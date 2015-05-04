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



    public Chromosome(int size, ArrayList<Factory> factories, ArrayList<Store> stores){
        this.genes = new byte[size];

        this.factories = factories;
        this.stores = stores;

        initChromosome();

        splitGenes();

        System.out.println("Number Factories = " + factories.size());
        System.out.println("Number Stores = " + stores.size());
        printFactories();
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


    @Override
    public String toString(){
        String resultString = "";
        for (byte gene : genes) {
            resultString += Byte.toString(gene);
        }
        return resultString;
    }


}
