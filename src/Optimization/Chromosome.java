package Optimization;

import Objects.Factory;
import Objects.Store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Chromosome {

    private byte[] genes;
    static Random rand = new Random();


    ArrayList<Factory> factories;
    ArrayList<Store> stores;

    private double selectionProbability;

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


    public ArrayList<Factory> getFactories() {
        return factories;
    }

    public void setFactories(ArrayList<Factory> factories) {
        this.factories = factories;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public void setSelectionProbability(double selectionProbability) {
        this.selectionProbability = selectionProbability;
    }

    public Chromosome clone(){
        Chromosome cloneChromosome = new Chromosome();
        cloneChromosome.setGenes(GeneticAlgorithm.cloneByteArray(this.genes));
        cloneChromosome.setFactories(GeneticAlgorithm.cloneFactoryArray(this.factories));
        cloneChromosome.setSelectionProbability(this.selectionProbability);
        cloneChromosome.setStores(GeneticAlgorithm.cloneStoreArray(this.stores));
        return cloneChromosome;
    }



    public double getSelectionProbability(){
        return this.selectionProbability;
    }

    public void calculateSelectionProbability(double totalPopulationFitness){
        selectionProbability = getFitness()/totalPopulationFitness;
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

    private ArrayList<Integer> getTotalQuantitiesSuppliedToStores(){
        int numStores = stores.size();

        ArrayList<Integer> supplyToStores = new ArrayList<>();

        // init supplyToStores with quantity each store requires
        for(int  i = 0; i < stores.size() ; i++){
            supplyToStores.add(stores.get(i).getRequiredQuantity());
        }

        // calculate quantity supplied to each store
        for(Factory factory : factories)
        {
            for(int i = 0; i < numStores; i++)
            {
                int suppliedToStore = factory.suppliedToStore(i);
                int stillRequiredByStore = supplyToStores.get(i);
                supplyToStores.set(i, stillRequiredByStore - suppliedToStore);
                System.out.println(stillRequiredByStore - suppliedToStore);
            }
        }

        return supplyToStores;
    }

    public double getPenalty() {
        double totalPenalty = 0.0;

        for(Factory factory : factories) {
            totalPenalty += factory.getPenalty();
        }

        ArrayList<Integer> supplyToStores = getTotalQuantitiesSuppliedToStores();

        for(Integer quantity : supplyToStores){
            if(quantity > 0) {
                totalPenalty -= quantity;
            }
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
            return (1 / Math.pow(totalDistance + (1/this.getPenalty()), 2));
        } else {
            return 0 + Math.pow(this.getPenalty(), 2);
        }

    }


    public byte[] getGenes(){ return this.genes; }

    public void setGenes(byte[] genes){ this.genes = genes; }


    public static void crossover(Chromosome c1, Chromosome c2){
        int randomBit = rand.nextInt(c1.getGenes().length);

        System.out.println("Cross bit: " + randomBit);
        byte[] c1Genes = c1.getGenes();
        byte[] c2Genes = c2.getGenes();

        for(int i = 0; i < c1.getGenes().length; i++){
            if(i > randomBit){
                byte bit = c1Genes[i];
                c1Genes[i] = c2Genes[i];
                c2Genes[i] = bit;
            }
        }

        c1.setGenes(c1Genes);
        c2.setGenes(c2Genes);
        System.out.println("CROSSOVER FUNCTION:");
        System.out.println(Integer.toHexString(c1.hashCode()));
        System.out.println(Integer.toHexString(c2.hashCode()));
    }

    @Override
    public String toString(){
        //printFactories();

        System.out.println("Supplied: " + factories.get(0).getStores().get(0).getSuppliedItemNumber());

        System.out.println("Fitness: " + this.getFitness());

        return Arrays.toString(genes);
    }


}
