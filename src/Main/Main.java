package Main;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    //vrijednosti koje korisnik unosi
    private static double prepreka;
    private static double rupa;

    Population population = new Population();
    int generationCount = 0;

    public static double getPrepreka() {
        return prepreka;
    }

    public static double getRupa() {
        return rupa;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Unesite poziciju prepreke i rupe");
        Scanner s = new Scanner(System.in);
        prepreka = s.nextDouble();
        rupa = s.nextDouble();
        Random rn = new Random();
        s.close();

        long startTime = System.currentTimeMillis();

        Main main = new Main();

        // Inicijalizacija populacije
        main.population.initializePopulation(64);

        // Dok ne nadjemo najbolji
        while (main.population.getFittest().odstupanje != 0) {
            ++main.generationCount;
            //vrsimo selekciju
            main.population.selection();
            //vrsimo rekombinaciju
            main.population.crossover();
            //vrsimo mutaciju
            if(rn.nextInt() % 100 < 5) {
                main.population.mutation();
            }
            System.out.println( "Generation: " + main.generationCount + " Odstupanje: " + main.population.getFittest().odstupanje);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " milliseconds");
        System.out.println("\nSolution found in generation " + main.generationCount);
        System.out.print("Best Gen: ");
        Individual fittest = main.population.getFittest();
        System.out.print((fittest.brzina) / (Integer.MAX_VALUE / 22) + " "
                + Math.toDegrees(fittest.ugao / (Integer.MAX_VALUE / 1.5)));
        System.out.println();
    }
}
