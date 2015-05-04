package Optimization;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Optimization {

    public static GeneticAlgorithm optimizationAlgorithm;

    public static Scanner scan;


    public ArrayList<Integer> getStoresQuantityRequired(int numStores){

        ArrayList<Integer> quantities = new ArrayList<Integer>();

        for(int i = 0; i < numStores; i++){
            System.out.println("Required Quantity (Store #" + i + "): ");
            int quant = scan.nextInt();

            quantities.add(quant);
        }

        return quantities;
    }


    public ArrayList<Integer> getFactoriesProduction(int numFactories){

        ArrayList<Integer> productions = new ArrayList<Integer>();

        for(int i = 0; i < numFactories; i++){
            System.out.println("Production (Factory #" + i + "): ");
            int prod = scan.nextInt();

            productions.add(prod);
        }

        return productions;
    }

    public static void main(String[] args){

        Optimization opt = new Optimization();

        scan = new Scanner(System.in);

        System.out.println("Number of factories: ");
        int numFactories = scan.nextInt();

        System.out.println("Number of stores: ");
        int numStores = scan.nextInt();

        ArrayList<Integer> storesQuantities = opt.getStoresQuantityRequired(numStores);
        ArrayList<Integer> factoriesProduction = opt.getFactoriesProduction(numFactories);

        optimizationAlgorithm = new GeneticAlgorithm(factoriesProduction, storesQuantities);


    }
}
