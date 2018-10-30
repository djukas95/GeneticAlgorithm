package Main;

import java.util.ArrayList;
import java.util.Random;

public class Population {
    int popSize = 8;
    ArrayList<Individual> populacija = new ArrayList<>();

    // Inicijalizacija populacije
    public void initializePopulation(int size) {
        popSize = size;
        for (int i = 0; i < size; i++) {
            populacija.add(new Individual());
        }
    }

    //funkcija za trazenje najbolje jedinke
    public Individual getFittest() {
        Individual fittest = populacija.get(0);
        for (Individual individual : populacija) {
            if (individual.odstupanje < fittest.odstupanje) {
                fittest = individual;
            }
        }
        return fittest;
    }

    //funkcija za selekciju jedinki, koristili smo turnirsku selekciju
    public void selection() {
        ArrayList<Individual> tmpPop = new ArrayList<Individual>();
        Random rn = new Random();
        for (int i = 0; i < popSize; i++) {
            Individual individual1 = populacija.get(rn.nextInt(popSize));
            Individual individual2 = populacija.get(rn.nextInt(popSize));
            if (individual1.odstupanje < individual2.odstupanje) {
                tmpPop.add(individual1.clone());
            } else {
                tmpPop.add(individual2.clone());
            }
        }
        populacija = tmpPop;
    }

    //crossover u dvije tacke
    public void crossover() {
        ArrayList<Individual> tmpPop = new ArrayList<Individual>();
        Random rn = new Random();
        while (populacija.isEmpty() == false) {
            Individual individual1 = populacija.remove(rn.nextInt(populacija.size()));
            Individual individual2 = populacija.remove(rn.nextInt(populacija.size()));
            Individual i_tmp = individual1.clone();
            individual1.crossover(individual2);
            individual2.crossover(i_tmp);
            tmpPop.add(individual1);
            tmpPop.add(individual2);
        }
        populacija = tmpPop;
    }
    //funkcija za mutaciju jedinke
    public void mutation() {
        for (Individual i : populacija) {
            i.mutate();
        }
    }
}

