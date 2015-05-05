package Objects;


import Optimization.Chromosome;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Population {

    private ArrayList<Chromosome> population;

    private  double sumFittness;

    private double crossProbability;

    Random rand = new Random();


    private int elitism;


    public Population(){
        this.population = new ArrayList<Chromosome>();
        this.elitism = 2;
        this.crossProbability = 0.75;
    }



    public void printPopulation() {
        int counter = 0;
        for (Chromosome chromosome : population) {
            System.out.println("CHROMOSOME #" + counter);
            counter++;
            System.out.println(chromosome);
        }

    }

    public void add(Chromosome chromosome){
        this.population.add(chromosome);
    }

    public Chromosome getFittest() {
        double bestFitness = 0.0;

        Chromosome bestChromosome = new Chromosome();

        for(Chromosome chromosome : this.population) {
            double chromosomeFitness = chromosome.getFitness();

            if(chromosomeFitness > bestFitness)
            {
                bestChromosome = chromosome;
                bestFitness = chromosomeFitness;
            }
        }

        return bestChromosome;
    }

    private ArrayList<Chromosome> cloneChromosomesArray(){
        ArrayList<Chromosome> clone = new ArrayList<Chromosome>();

        for(int i  = 0; i < population.size(); i++){
            clone.add(population.get(i));
        }

        return clone;
    }


    public Chromosome getFittest(int index) {

        ArrayList<Chromosome> populationClone = cloneChromosomesArray();

        Chromosome bestChromosome = new Chromosome();

        for(int i = 0; i < index; i++){
            double bestFitness = 0.0;

            int bestChromosomeIndex = 0;

            for(int j = 0; j < populationClone.size() ; j++) {
                double chromosomeFitness = populationClone.get(j).getFitness();

                if(chromosomeFitness > bestFitness)
                {
                    bestChromosomeIndex = j;
                    bestChromosome = populationClone.get(j);
                    bestFitness = chromosomeFitness;
                }
            }
            populationClone.remove(bestChromosomeIndex);
        }

        return bestChromosome;
    }


    public double getFitnessSum(){
        return  sumFittness;
    }

    public void calculateFitnessSum(){
        for(Chromosome chromosome : population){
            sumFittness += chromosome.getFitness();
        }
    }

    public void calculateAllPopulationSelectionProbability(){
        System.out.println("Sum Fitness = " + sumFittness);

        for(Chromosome chromosome : population){
            chromosome.calculateSelectionProbability(sumFittness);
        }
    }


    private double generateRandomProbability(){
        return rand.nextDouble();
    }



    public Population getNextPopulation(){
        Population nextPopulation = new Population();



        // calculate population fitness sum
        calculateFitnessSum();

        // get probability of selection for each chromosome in population
        calculateAllPopulationSelectionProbability();

        int numProbabilitiesCalculated = 0;


        ArrayList<Double> sumProbabilities = new ArrayList<>();

        for(Chromosome chromosome : population)
        {
            if( numProbabilitiesCalculated == 0){
                sumProbabilities.add(chromosome.getSelectionProbability());
            }else{
                double lastProbability = sumProbabilities.get(numProbabilitiesCalculated - 1);
                sumProbabilities.add(  lastProbability + chromosome.getSelectionProbability());
            }
            numProbabilitiesCalculated++;
        }




        // get best elitism chromosomes
        ArrayList<Chromosome> bestChromosomes = new ArrayList<Chromosome>();
        for(int i = 1; i <= this.elitism; i++){
            Chromosome fittestChromosome = this.getFittest(i);
            bestChromosomes.add(fittestChromosome);
            System.out.println(fittestChromosome);
        }




        // generate generation with random values
        ArrayList<Double> randomProbabilities = new ArrayList<Double>();
        for(int i = 0; i < sumProbabilities.size() - this.elitism; i++){
            randomProbabilities.add(generateRandomProbability());
        }


        // selected population
        ArrayList<Chromosome> selectedPopulation = new ArrayList<Chromosome>();
        for(int i = 0; i < randomProbabilities.size(); i++){
            for(int j = 0; j < sumProbabilities.size()-1; j++){
                if(j == 0){
                    if(randomProbabilities.get(i) < sumProbabilities.get(j)){
                        selectedPopulation.add(population.get(j));
                    }
                }else{
                    if(randomProbabilities.get(i) > sumProbabilities.get(j-1) && randomProbabilities.get(i) < sumProbabilities.get(j)){
                        selectedPopulation.add(population.get(j));
                    }
                }
            }
        }

        // generate generation with random values
        randomProbabilities.clear();
        for(int i = 0; i < sumProbabilities.size() - this.elitism; i++){
            randomProbabilities.add(generateRandomProbability());
        }

        ArrayList<Integer> selectedForCrossPositions = new ArrayList<>();
        for(int i = 0; i < randomProbabilities.size(); i++){
            if(randomProbabilities.get(i) < crossProbability){
                selectedForCrossPositions.add(i);
            }
        }

        // crossover
        for(int i = 0; i < selectedForCrossPositions.size()-1; i = i+2){
            Chromosome c1 = selectedPopulation.get(i);
            Chromosome c2 = selectedPopulation.get(i+1);

            Chromosome.crossover(c1, c2);
        }






        System.out.println(randomProbabilities);
        System.out.println(sumProbabilities);

        return nextPopulation;
    }


}
